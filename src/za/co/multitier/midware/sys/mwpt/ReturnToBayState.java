/*
 * ConfirmRemoveState.java
 *
 * Created on February 20, 2007, 4:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpt;

import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.datasource.Bay;
import za.co.multitier.midware.sys.datasource.DataSource;
import za.co.multitier.midware.sys.datasource.ItemPackProduct;
import za.co.multitier.midware.sys.datasource.Pallet;
import za.co.multitier.midware.sys.datasource.PalletisingDAO;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;

/**
 * @author Administrator
 */


public class ReturnToBayState extends DefaultState {


    private Long scanned_carton;
    private Pallet pallet;

    public String getRobotScreen() {
        if (this.robot_screen == null)
            return this.parent.createWarningRobotScreen(this.parent.SCAN_CARTON_FROM_PALLET_MSG, "");
        else
            return this.robot_screen;
    }


    /**
     * Creates a new instance of ConfirmRemoveState
     */
    public ReturnToBayState(DeviceCommands.ROBOT_KEYPAD button_pressed, Long carton_num, String skip_ip, String bay_num, PalletizingAction parent) {
        super(button_pressed, carton_num, skip_ip, bay_num, parent, false);
    }


    //==================================================================
    //RTB Validation rules:
    //1) carton must exist
    //2) cannot belong to other completed bay
    //3)
    //===================================================================
    private boolean isCartonValidForRtb() throws Exception {
        String invalid_reason = "";

        if (this.is_carton_valid()) //this method will set the state to remove by itself if invalid
        {
            if (this.carton.getPallet_id() == null)
                this.setRemoveCtnState(this.parent.NO_PALLET_FOR_CARTON_MSG);
            else {
                this.pallet = ProductLabelingDAO.getPallet(this.carton.getPallet_id());
                if (this.pallet.getLoad_detail_id() != null || this.pallet.getConsigment_note_number() != null) {
                    invalid_reason = this.parent.PALLET_ON_CONSIGNMENT_OR_LOAD_MSG;
                    this.setRemoveCtnState(invalid_reason);
                    return false;
                }

                String procc_status = this.pallet.getProcess_status().toUpperCase();
                String qc_status = this.pallet.getQc_status_code();
                if (qc_status == null) {
                    if (this.pallet.getQc_result_status() != null) {
                        qc_status = "INSPECTED";
                    } else {
                        qc_status = "UNINSPECTED";
                    }
                    this.pallet.setQc_status_code(qc_status);
                } else
                    qc_status = qc_status.toUpperCase();

                String qc_result_status = this.pallet.getQc_result_status() == null ? "" : this.pallet.getQc_result_status().toUpperCase();
                String build_status = this.pallet.getBuild_status().toUpperCase();
                if (build_status.equals("PARTIAL")) {
                    if (procc_status.indexOf("PALLETIZING") > -1) {
                        invalid_reason = this.parent.INCOMPLETE_PALLET;
                    }
                    if (qc_status.equals("INSPECTED")) {
                        if (qc_result_status.equals("FAILED"))
                            invalid_reason = this.parent.FAILED_FULL_PALLET_MSG;
                    }
                } else //can only be full, process status can only be full
                {
                    if (build_status.equals("FULL"))
                        invalid_reason = this.parent.FULL_PALLET_MSG;
                    //if(qc_status.equals("INSPECTED"))
                    //{
                    //	if(qc_result_status.equals("FAILED"))
                    //		invalid_reason = this.parent.FAILED_FULL_PALLET_MSG;
                    //}


                }

                if (!invalid_reason.equals(""))
                    this.setRemoveCtnState(invalid_reason);


            }
            return (invalid_reason.equals(""));
        } else
            return false;


    }


    //------------------------------------------------------
    //Notes: remember to:
    //1) set the process status to palletizing_mixed
    //2) use populate bay to store persistent bay state
    //2) get the relevant set of mixed criteria and store in bay
    //3) create a bay_carton record for each carton on pallet
    //------------------------------------------------------
    private void returnToBayTransaction() throws Exception {
        String curr_procc_status = "";
        //try
        //{
        DataSource.getSqlMapInstance().startTransaction();

        Bay bay = getBay();
        curr_procc_status = this.pallet.getProcess_status();
        this.pallet.setProcess_status("PALLETIZING_MIXED");
        PalletisingDAO.updatePalletProcessStatus(this.pallet.getPallet_number(), "PALLETIZING_MIXED");
        PalletisingDAO.createBayCartons(this.pallet.getId(), this.parent.getBayId(bay));
        bay = this.parent.rePopulateBay(this.bay_num, this.skip_ip, this.pallet);
        //populate bay will have set the mixed criterial instead of normal criteria, because it looks at
        //the process_status to determine which set of criteria to fetch
        PalletisingDAO.setPalletRtbOrderVals(pallet, this.bay_num, this.skip_ip);
        DataSource.getSqlMapInstance().commitTransaction();


        if (this.carton_num == null)
            this.carton_num = this.last_carton_scanned;

        ItemPackProduct ipc = PalletisingDAO.getCartonItemPackDetails(this.carton_num);
        if (ipc == null)
            throw new Exception("Item pack details could not be retrieved for carton num: " + String.valueOf(this.carton_num));

        bay.setState_item_pack(ipc);


        //} catch (Exception ex)
        //{
        //	if(this.pallet!= null) //reset in-memory status to what is was before this attempted transaction
        //	this.pallet.setProcess_status(curr_procc_status);

        //	throw new Exception("Return to bay failed. Reported exception: " + ex.toString());
        //}

        //finally
        //{
        //DataSource.getSqlMapInstance().endTransaction();
        //}


    }


    public void button_pressed() throws Exception {

        if (this.button == DeviceCommands.ROBOT_KEYPAD.btn4) {
            if (this.scanned_carton != null) {
                this.carton_num = this.scanned_carton;
                if (this.transaction_cycle > 1) {
                    if (this.isCartonValidForRtb()) {
                        this.returnToBayTransaction();

                        this.parent.active_state = new MixedPalletBuildingState(this.button, this.carton_num, this.skip_ip, this.bay_num, this.parent);
                    }
                }

            }

        } else if (this.button == DeviceCommands.ROBOT_KEYPAD.btn5)
            this.parent.active_state = this.calculateBaseState();

        this.cacheBay();

    }


    public void carton_scanned() throws Exception {
        this.scanned_carton = this.carton_num;
        this.last_carton_scanned = this.carton_num;

        if (isCartonValidForRtb()) //this method will create(transition to) the appropriate error state itself
        {

            this.robot_screen = this.parent.createWarningRobotScreen(this.parent.RTB_PROMPT, "");
        }


    }


}
