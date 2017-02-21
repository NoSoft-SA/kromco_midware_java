/*
 * ConfirmRemoveState.java
 *
 * Created on February 20, 2007, 4:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpt;

import java.sql.SQLException;
import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.datasource.Bay;
import za.co.multitier.midware.sys.datasource.DataSource;
import za.co.multitier.midware.sys.datasource.Pallet;
import za.co.multitier.midware.sys.datasource.PalletisingDAO;
import za.co.multitier.midware.sys.datasource.PalletizingCriteria;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;

/**
 *
 * @author Administrator
 */


public class ReturnInspectionCtnState extends DefaultState
{
     
     
     private Long scanned_carton;
     private boolean is_valid_rtb_carton;
     private Pallet pallet;
     private boolean await_rtb_answer = false;
     
     public String getRobotScreen ()
     {
          if(this.robot_screen == null)
               return this.parent.createWarningRobotScreen (this.parent.SCAN_INSPECTION_CTN_PROMPT,"");
          else
               return this.robot_screen;
     }
     
     
     /** Creates a new instance of ConfirmRemoveState */
     public ReturnInspectionCtnState (DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
     {
          super (button_pressed,carton_num,skip_ip,bay_num,parent,false);
     }
     
     
     //==================================================================
     //Inspection return  Validation rules:
     //1) carton must exist
     //2) bay must not be empty
     //2) cannot belong to other completed bay
     //3) must belong to the pallet on this bay
     //4) carton must exist on this bay
     //5) pallet's qc status must not be 'uninspected'
     //6) carton must be the qc sample for this bay
     //===================================================================
     private boolean isValidInspectionReturn () throws Exception
     {
          String invalid_reason = "";
          
          if(this.is_carton_valid ()) //this method will set the state to remove by itself if invalid
          {
               if(this.isBayEmpty ())
                    invalid_reason = this.parent.EMPTY_BAY_MSG;
               
               else if(this.carton.getPallet_id ()== null)
                    invalid_reason = this.parent.NO_PALLET_FOR_CARTON_MSG;
               
               else
               {
                    
				   //-------------------------------------------------------------------------------------
				  //Very tricky bit that we have here: we have a pallet that sits in memory. The same
				  //pallet has been updated externally from db, so we need to re-load the pallet from db,yet
				  //in this process we any lose additional info stored in the in-memory version
				  //Any such 'additional' info stored on the in-memory object, needs to be copied over
				  //from the in-memory object to the re-laoded object
				  //Currently the only field affected is the 'cpp' field
				  //---------------------------------------------------------------------------------------
                    this.pallet = PalletisingDAO.getBayPallet (this.bay_num,this.skip_ip);
					Integer cached_cpp = getBay().getState_pallet().getCpp();
					this.pallet.setCpp(cached_cpp);
					getBay().setState_pallet(this.pallet);
					
                    if(!this.pallet.getId ().equals (this.carton.getPallet_id ()))
                         invalid_reason = this.parent.CARTON_OF_OTHER_PALLET;
                    else
                    {
                         
                         String qc_status = this.pallet.getQc_status_code ().toUpperCase ();
                         if(qc_status.equals ("UNINSPECTED"))
                              invalid_reason = this.parent.NO_SAMPLE_CARTON_ON_PALLET;
                         else if(PalletisingDAO.getBayCarton (this.carton.getCarton_number (),this.bay_num,this.skip_ip)== null)
                              invalid_reason = this.parent.CARTON_NOT_LINKED_TO_BAY_MSG;
                         else if(this.carton.isIs_inspection_carton ()== null||!this.carton.isIs_inspection_carton ())
                              invalid_reason = this.parent.IS_NOT_INSPECTION_CARTON_MSG;
                         //else if(!this.carton.getQc_status_code().toUpperCase ().equals ("INSPECTED"))
                         //     invalid_reason = this.parent.CARTON_NOT_INSPECTED_MSG;
						 //----------------------------------------------------------------------
						 //NOTE: AN UNINSPECTED CARTON CAN BE RETURNED
						 //-----------------------------------------------------------------------
                         
                    }
                    
               }
               
               if(!invalid_reason.equals (""))
                    this.setRemoveCtnState (invalid_reason);
               
               return (invalid_reason.equals (""));
          }
          else
               return false;
          
     }
     
     
     private void inspectionReturnTransaction () throws Exception
     {
          try
          {
                DataSource.getSqlMapInstance ().startTransaction ();
               PalletisingDAO.setCartonInspectionDateTime (this.carton.getId (),true);
               if(this.pallet.getQc_status_code ().toUpperCase ().equals ("INSPECTING"))
               {
                    PalletisingDAO.setCartonInspectionFlag (this.carton.getId (),false);
                    PalletisingDAO.setPalletQcStatus (this.pallet.getPallet_number (),"UNINSPECTED");
					getBay().getState_pallet().setQc_status_code("UNINSPECTED");
               }
               
               DataSource.getSqlMapInstance ().commitTransaction ();
               
          }
          catch (Exception ex)
          {
               
               throw new Exception ("Inspection carton return failed. Reported exception: " + ex.toString ());
          }
          
          finally
          {
               //DataSource.getSqlMapInstance ().endTransaction ();
          }
          
     }
     
     
     public void button_pressed () throws Exception
     {
          
          if(this.await_rtb_answer)
          {
               if(this.button == DeviceCommands.ROBOT_KEYPAD.btn4)
               {
                    this.inspectionReturnTransaction ();
                    this.parent.active_state = this.calculateBaseState ();
                    ((DefaultState)this.parent.active_state).last_carton_scanned = this.last_carton_scanned;
                    this.await_rtb_answer = false;
               }
               else if(this.button == DeviceCommands.ROBOT_KEYPAD.btn5) //NO
               {
                    this.setRemoveCtnState(this.parent.USER_REJECTED_RTB_MSG);
                    this.await_rtb_answer = false;
               }
          }
          
     }
     
     
     public void carton_scanned () throws Exception
     {
          this.scanned_carton = this.carton_num;
          if(this.transaction_cycle > 0)
          {
               if(isValidInspectionReturn ()) //this method will create(transition to) the appropriate error state itself
			   {
                    //check if pallet is still inspecting, if so prompt user whether he/she wants to continue with the return
                    if(this.pallet.getQc_result_status ()== null||this.pallet.getQc_result_status ().toUpperCase ().equals ("INSPECTING"))
                    {
                    
                    //this variable to remember the carton number
                    this.last_carton_scanned = this.carton_num;
                    this.robot_screen =  this.parent.createWarningRobotScreen (this.parent.SAMPLE_NOT_INSPECTED_MSG,this.parent.RTB_PROMPT);
                    this.await_rtb_answer = true;
                    return;
                    }
                    else
                    {
                    this.inspectionReturnTransaction ();
                    }
					 this.parent.active_state = this.calculateBaseState ();
					  ((DefaultState)this.parent.active_state).last_carton_scanned = this.scanned_carton;
			   }
              
          }
          
         
     }
     
     
}
