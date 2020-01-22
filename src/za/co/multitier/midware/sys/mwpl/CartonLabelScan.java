/*
 * CartonLabelScan.java
 *
 * Created on February 6, 2007, 5:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.mwpl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import za.co.multitier.mesware.services.email.MailInterface;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.MidwareCache;
import za.co.multitier.midware.sys.appservices.MidwareConfig;
import za.co.multitier.midware.sys.datasource.*;

/**
 * @author Administrator
 */
public class CartonLabelScan extends ProductLabelScan {

    /**
     * Creates a new instance of CartonLabelScan
     */
    public CartonLabelScan(String ip, String mass, String codeCollection[], MessageInterface msg, String bin_num) {
        super(ip, mass, codeCollection, msg, bin_num);
    }

    public CartonLabelScan(String ip, String mass, String codeCollection[], String bin_num) {
        super(ip, mass, codeCollection, bin_num);
    }

    public CartonLabelScan(String ip, String mass, String codeCollection[], MessageInterface msg, MailInterface mail, String from, String to, String bin_num) {
        this(ip, mass, codeCollection, msg, bin_num);
        mailer = mail;
        from_address = from;
        to_address = to;

    }


    private String packer;
    private Long carton_num;
    private Map schedule_order_details;
    private MailInterface mailer;
    private String from_address;
    private String to_address;
    private Bin bin;
    CartonLink carton_link;
    CartonLabelSetup label_setup;
    Carton carton_template;
    ProductionRun run;
    String pick_ref;
    String iso_week;

    protected void setLabelData() throws Exception {
        //try
        //{


        if (this.bin_number != null && this.bin_number != "") {
            bin = BinTippingDAO.getBin(this.bin_number);
            if (bin == null)
                throw new Exception("Bin: " + this.bin_number + " does not exist");

        }

        this.template_name = "E2";
        carton_link = ProductLabelingDAO.getCartonLink(this.codeCollection[0], this.active_device.getProduction_run_id());
        if (carton_link == null) {
            throw new Exception("No carton link was found for station code: " + codeCollection[0]);


        }
        if (carton_link.getRebin_label_setup_id() != null) {
            this.label_message = "REBIN PRODUCT";
            return;
        }

        label_setup = ProductLabelingDAO.getCartonLabelSetup(carton_link.getCarton_label_setup_id());
        if (label_setup == null) {
            throw new Exception("No carton label setup was found for station code: " + codeCollection[0]);


        }
        carton_template = ProductLabelingDAO.getCartonTemplate(carton_link.getCarton_template_id());
        if (carton_template == null) {
            throw new Exception("No carton template was found for station code: " + codeCollection[0]);

        }

        carton_template.setBrand(label_setup.getBrand_code());
        carton_template.calc_gtin();

        run = ProductLabelingDAO.getProductionRun(carton_link.getProduction_run_id());
        if (run == null) {
            throw new Exception("Production run with id: " + String.valueOf(carton_link.getProduction_run_id()) + " does not exist.");


        }


        if (this.bin_number != null && this.bin_number != "") {
            //  System.out.println("Bin number is: " + this.bin_number);
            if (bin.getProduction_run_tipped_id() != null) {

                // System.out.println("Bin production run tipped id is: " + String.valueOf(bin.getProduction_run_tipped_id()));
                // System.out.println("Carton production run_id is: " + String.valueOf(run.getId()));

                ProductionRun tip_run = ProductLabelingDAO.getProductionRun(bin.getProduction_run_tipped_id());

                if (!tip_run.getProduction_run_code().equals(run.getProduction_run_code())) {
                    //  System.out.println("Carton has different run to bin" + "(carton:" + run.getProduction_run_code() + "; bin: " + tip_run.getProduction_run_code() + ")");
                    //TODO uncomment for live
                    throw new Exception("Carton has different run to bin" + "(carton:" + run.getProduction_run_code() + "; bin: " + tip_run.getProduction_run_code() + ")");
                }

            }

        }


        this.carton_num = ProductLabelingDAO.getNextMesObjectId(ProductLabelingDAO.MesObjectTypes.CARTON);
        //-------------------------------------------
        //IF gtin, then '01' (human readable: (01)
        // else if batch, then '0110'
        //   (for human readable): '(01)(10)'
        //-------------------------------------------

        //Print title rule
        //if retail unit pack material is of sub type bag, punnet or jumble: no "COUNT:" print title, else print "COUNT:"
        Map data = this.label_data;

        int required_num_length = Integer.parseInt(MidwareConfig.getInstance().getSettings().getProperty("carton_num_length"));
        String str_num = DeviceScan.pad_number(this.carton_num, required_num_length);
        //this.midware_console.sysMsg("number is: " + str_num);
        String gtin = carton_template.getGtin();
        if (gtin == null) {
            gtin = "";


        }

        //just a little test for github
        String gtin_barcode = "";
        if (gtin.trim() != "") //user batch number
        {
            gtin_barcode = "^01" + gtin + "10" + run.getBatch_code();

        } else {
            gtin_barcode = "0110" + run.getBatch_code();


        }
        data.put("F1", gtin_barcode);
        data.put("F2", str_num);


        String variety = label_setup.getVariety_short_long();
        String lbl_variety = "(" + variety.substring(0, 3) + ")  " + variety.substring(4, variety.length());


        data.put("F3", lbl_variety);
        data.put("F4", label_setup.getCommodity_code());
        data.put("F5", label_setup.getCommodity_description());
        data.put("F6", label_setup.getBrand_code());
        data.put("F7", label_setup.getOld_pack_code());

        boolean print_count = false;
        String ru_type = ProductLabelingDAO.getRUType(label_setup.getCarton_setup_id());
        if (ru_type != null) {
            if (ru_type.equals("T")) {
                print_count = true;
            } else {
                print_count = false;
            }
        } else {
            print_count = false;


        }
        data.put("F8", label_setup.getActual_size_count_code());


        data.put("F9", label_setup.getInventory_code());
        data.put("F10", label_setup.getGrade_code());
        data.put("F11", run.getBatch_code());

        boolean orchard_printed = false;


        try {
            //Pick ref, bit tricky
            pick_ref = "";

            iso_week = ProductLabelingDAO.getCurrentIsoWeek();
            if (iso_week == null) {
                throw new Exception("iso week does not exist for today's date(" + new java.sql.Date(new java.util.Date().getTime()) + ")");

            }
            if (iso_week.length() == 1) {
                iso_week = "0" + iso_week;

                //get wekday

            }
            Calendar today = new GregorianCalendar();
            int weekday = today.get(Calendar.DAY_OF_WEEK) - 1;
            weekday = weekday == 0 ? 7 : weekday;

            String pc_code = carton_template.getPc_code();
            String pc_code_num = carton_template.getPc_code_num();


            pc_code = run.getPc_code();
            pc_code_num = run.getPc_code_num();


            //
            //for DP cartons, use pc-code defined on run itself
            if (bin != null) {
                if (bin.getOrchard_code() != null &&
                        (carton_template.getTarget_market_code().substring(0, 2).equals("NI") ||
                                carton_template.getTarget_market_code().substring(0, 2).equals("P6") || carton_template.getTarget_market_code().substring(0, 2).equals("RS") ||
                                carton_template.getTarget_market_code().substring(0, 2).equals("ME") ||
                                carton_template.getTarget_market_code().substring(0, 2).equals("FE"))) {
                    data.put("F32", "ORCHARD");
                    data.put("F33", bin.getOrchard_code());
                    orchard_printed = true;
                }


                //"44DP44"
                String pack_point_end_chars = this.active_device.getActive_device_code().substring(this.active_device.getActive_device_code().length() - 2);
                if (isNumeric(pack_point_end_chars) && Integer.valueOf(pack_point_end_chars) > 40 && Integer.valueOf(pack_point_end_chars) < 46) {
                    pc_code = bin.getPc_code();
                    pc_code_num = bin.getPc_code_num();

                }
            }

            if (pc_code == null)
                pc_code = "";
            else
                carton_template.setPc_code(pc_code);

            if (run.getTrack_indicator_code() != null)
                carton_template.setTrack_indicator_code(run.getTrack_indicator_code());


            String pc_code_pick = pc_code_num.equals("-1") ? run.getLine_code() : pc_code_num;

            //pick_ref = iso_week + carton_template.getPc_code_num() + iso_week.substring(1,2);
            pick_ref = iso_week.substring(1, 2) + String.valueOf(weekday) + pc_code_pick + iso_week.substring(0, 1);
            data.put("F12", pick_ref);
        } catch (Exception ex) {
            throw new Exception("Pick reference could not be calculated. Reported Exception: " + ex.toString());
        }

        PUC puc = null;
        if (this.bin == null)
            puc = set_puc_account_normal();
        else  //presort context
        {
            puc = set_puc_account_pre_sort();
            carton_template.setBin_id(bin.getId());

        }

        //get nature's choice certificate code'
        String egap = "";
        if (puc.getEurogap_code() != null) {
            egap = puc.getEurogap_code();


        }
        data.put("F13", carton_template.getPuc());
//			data.put("F14",label_setup.getCold_store_code());
        data.put("F14", egap);
        data.put("F15", label_setup.getTarget_market_code().substring(0, 2));
        data.put("F16", label_setup.getClass_code());
        String marking = label_setup.getMarking() == null || label_setup.getMarking() == "*" ? "" : label_setup.getMarking();
        data.put("F17", marking);

        String diameter = label_setup.getDiameter() == null || label_setup.getDiameter() == "*" ? "" : label_setup.getDiameter();
        data.put("F18", diameter);
        data.put("F19", label_setup.getOrganization_code());
        String phc = ProductLabelingDAO.getLinePhc(run.getLine_id());
        data.put("F20", phc);


        if (this.codeCollection[1].trim().equals(""))
            throw new Exception("group id or packer is null");

        if (this.bin == null) {


            this.packer = this.codeCollection[1].substring(2, 7);
        } else
            this.packer = this.codeCollection[1];


        data.put("F21", this.packer);
        String gtin_readable;
        if (gtin.trim() != "") //user batch number
        {
            gtin_readable = "(01)" + gtin + "(10)" + run.getBatch_code();

        } else {
            gtin_readable = "(01)(10)" + run.getBatch_code();


        }
        data.put("F22", gtin_readable);
        data.put("F23", label_setup.getOrganization_address_1());
        data.put("F24", label_setup.getOrganization_address_2());


        if (print_count == true) {
            data.put("F25", "COUNT:");
        } else {
            data.put("F25", "");
        }

        String marking_heading = "";

        String diameter_heading = "";

        if (label_setup.getDiameter() != null && !label_setup.getDiameter().equals("*") && !label_setup.getDiameter().trim().equals("")) {
            diameter_heading = "DIAMETER/WEIGHT";


        }
        if (!marking.equals("*") && !marking.trim().equals("")) {
            marking_heading = "MARKING";


        }
        data.put("F26", diameter_heading);
        data.put("F27", marking_heading);

        String ntc = "";
        if (puc.getNature_choice_certificate_code() != null) {
            ntc = puc.getNature_choice_certificate_code();

        }
        data.put("F28", ntc);
        //Little automation of task to print the digits of carton number to different field from F25 to F36
        data.put("F29", carton_template.getExtended_fg_code());
        String pfp = "";
        if (label_setup.getPallet_format_product_code() != null) {
            pfp = label_setup.getPallet_format_product_code();

        }
        data.put("F30", pfp);
        String sell_by = carton_template.getSell_by_code();
        if (sell_by != null && !sell_by.equals("-")) {
            data.put("F31", sell_by);

        } else {
            data.put("F31", "");


        }


        //print empty lines for 32 & 33 if orchard, according to printing rule, was not printed. This is so that lines 34 onwards can be used
        if (!orchard_printed) {
            data.put("F32", "");
            data.put("F33", "");
        }


        String mass_printing_tms_text = MidwareConfig.getInstance().getSettings().getProperty("tms_to_print_tu_mass");

        if (mass_printing_tms_text != null) {
            String[] mass_printing_tms = mass_printing_tms_text.split(",");

            if (tm_in_list(carton_template.getTarget_market_code().split("_")[0], mass_printing_tms)) {

                String cpc_mass = "";
                if (carton_template.getCpc_tu_mass() != null)
                    cpc_mass = String.valueOf(carton_template.getCpc_tu_mass()) + " kg";


                data.put("F34", "Nett Mass");
                data.put("F35", cpc_mass);
            }
        }


    }


    private boolean tm_in_list(String tm, String[] tm_list) {
        for (int i = 0; i < tm_list.length; i++) {
            if (tm_list[i].equals(tm))
                return true;
        }

        return false;
    }


    private PUC set_puc_account_pre_sort() throws Exception {
        Account account = null;
        PUC puc = null;

        carton_template.setFarm_code(bin.getFarm_code());
        account = ProductLabelingDAO.getMarketerAccountCodeForFarm(bin.getFarm_code(), carton_template.getOrganization_code());
        if (account != null) {
            carton_template.setAccount_code(account.getAccount_code());
            carton_template.setPuc(account.getPuc_code());
            puc = ProductLabelingDAO.getPUC(carton_template.getPuc());
            if (puc == null) {
                throw new Exception("PUC does not exist for farm: " + run.getFarm_code() + " and  marketing org: " + carton_template.getOrganization_code());

            }
            carton_template.setEgap(puc.getEurogap_code());
        } else {
            throw new Exception("Farm_puc_account record could not be looked up for farm: " + run.getFarm_code() + " and  marketing org: " + carton_template.getOrganization_code());


        }

        return puc;
    }

    private PUC set_puc_account_normal() throws Exception {
        Account account = null;
        PUC puc = null;
        if (run.getFarm_code() != null) {
            carton_template.setFarm_code(run.getFarm_code());
            account = ProductLabelingDAO.getMarketerAccountCodeForFarm(run.getFarm_code(), carton_template.getOrganization_code());
            if (account != null) {
                carton_template.setAccount_code(account.getAccount_code());
                carton_template.setPuc(account.getPuc_code());
                puc = ProductLabelingDAO.getPUC(carton_template.getPuc());
                if (puc == null) {
                    throw new Exception("PUC does not exist for farm: " + run.getFarm_code() + " and  marketing org: " + carton_template.getOrganization_code());

                }
                carton_template.setEgap(puc.getEurogap_code());
            } else {
                throw new Exception("Farm_puc_account record could not be looked up for farm: " + run.getFarm_code() + " and  marketing org: " + carton_template.getOrganization_code());


            }
        }
        return puc;
    }

    //==================================================================
    //This method does the following as an atomic transaction;
    //1) create carton
    //2) update order_quantity_produced on carton setup
    //3) update mes_ctl_sequence
    //=================================================================
    //
    public void send_integration_record() throws Exception {

    }

    protected void post_labeling_transaction() throws Exception {
        //try
        //{
        // System.out.println("in post label data");
        DataSource.getSqlMapInstance().startTransaction();

        carton_template.setCarton_number(this.carton_num);
        carton_template.setCarton_label_station_code(this.ip);

        if (this.bin == null) {
            String erp_station = this.codeCollection[0].substring(0, 3);
            carton_template.setErp_station(erp_station);
            String erp_pack_point = this.codeCollection[0].substring(3, 5);
            carton_template.setErp_pack_point(erp_pack_point);
            carton_template.setCarton_pack_station_code(this.codeCollection[0]);
            carton_template.setPacker_number(this.codeCollection[1]);
        } else
            carton_template.setPacker_number(this.codeCollection[1]);

        carton_template.setCarton_pack_station_code(this.active_device.getActive_device_code());

        carton_template.setUnit_pack_product_code(label_setup.getUnit_pack_product_code());
        carton_template.setProduct_class_code(carton_template.getClass_code());

        carton_template.setProduction_run_code(run.getProduction_run_code());
        carton_template.setProduction_run_id(run.getId());
        carton_template.setPack_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));

        try {
            double real_mass = Double.parseDouble(this.mass);

            if (real_mass > 0.00) {
                carton_template.setCarton_fruit_nett_mass(real_mass);

            }
        } finally {
        }

        carton_template.setLine_code(run.getLine_code());

        HashMap shift = ProductLabelingDAO.getShift(run.getLine_code());

        if (shift == null)
            throw new Exception("No shift found for line: " + run.getLine_code());


        carton_template.setShift_code((String) shift.get("shift_code"));
        carton_template.setShift_id((Integer) shift.get("id"));
        //carton_template.setPuc(run.getPuc_code());

        carton_template.setIs_inspection_carton(false);

        carton_template.setCarton_template_id(carton_template.getId());
        carton_template.setPick_reference(this.pick_ref);
        carton_template.setIso_week_code(this.iso_week);
        carton_template.setDate_time_created(new java.sql.Timestamp(new java.util.Date().getTime()));

        //System.out.print("about to call create carton");
        ProductLabelingDAO.createCarton(carton_template, bin);
        //DataSource.getSqlMapInstance().commitTransaction();

        //MidwareCache.getDevicesCache().labelTransactionDone(this.codeCollection[0]);
        //TODO uncomment for live!
        this.getPltransaction().set_do_db_transactio(false);

        //   ProductLabelingDAO.updateRunStats(carton_template, null, null, null);
        DataSource.getSqlMapInstance().commitTransaction();
        //System.out.print("carton created");

//                        if(this.schedule_order_details!=null)
//                        {
//                            int new_qty = (Integer)this.schedule_order_details.get("quantity_produced");
//                            ProductLabelingDAO.updateSeasonOrderQty(new_qty,(Integer)schedule_order_details.get("id"));
//


        //System.out.println("exit label data");
        //TODO: comment out for live!!
        // DataSource.getSqlMapInstance().commitTransaction();


        //} catch (Exception ex)
        //{
        //throw new Exception("carton label post printing transaction failed. Reported Exception: " + ex.toString());
        //}

        //finally
        //{
        //DataSource.getSqlMapInstance().endTransaction();
        //}


    }
}
