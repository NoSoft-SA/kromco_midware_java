/*
 * ConfirmRemoveState.java
 *
 * Created on February 20, 2007, 4:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpt;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.MidwareCache;
import za.co.multitier.midware.sys.appservices.MidwareConfig;
import za.co.multitier.midware.sys.appservices.PalletizingException;
import za.co.multitier.midware.sys.datasource.Bay;
import za.co.multitier.midware.sys.datasource.BayCarton;
import za.co.multitier.midware.sys.datasource.Carton;
import za.co.multitier.midware.sys.datasource.DataSource;
import za.co.multitier.midware.sys.datasource.FgSetup;
import za.co.multitier.midware.sys.datasource.Pallet;
import za.co.multitier.midware.sys.datasource.PalletisingDAO;
import za.co.multitier.midware.sys.datasource.PalletizingCriteria;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;
import za.co.multitier.midware.sys.datasource.ProductionRun;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.mesware.services.email.MailInterface;


/**
 *PURPOSES OF THIS CLASS
 * 1) To provide default behaviour, in the sense of
 *   a)  The transactional context is unclear and we need to provide sensible behaviour
 *        e.g when our Palletizing cache was cleared unexpectatly
 *   b)  Standard Behaviour that is needed by most Palletizing states
 *
 *  HOW DO WE ENTER INTO THIS STATE?
 *  A) If the main transaction found the in-memory bay to be empty, in which case
 *     the 'special' populate_bay method will be called- which will construct state for
 *     following scenarios:
 *     a) Restoration of bay state due to unexpectec deletion of entite app's memory in process space
 *     b) New pallet creation
 *  B) A subclass of this class did not override or delegated the main interface methods
 *
 */
public abstract class DefaultState extends PalletizingState
{
   
   protected boolean scan_processed_by_preceding_state = false;
   protected Carton carton;
   protected Pallet pallet;
   public Long last_carton_scanned;
   public static Properties settings = MidwareConfig.getInstance().getSettings();
   public static String order_qty_to_address = "";
   public static String order_qty_from_address = "";
   public static String bulk_labeling_address = "";
   
   
   static
   {
        order_qty_to_address = (String)settings.get("order_qty_to_address");
        order_qty_from_address = (String)settings.get("order_qty_from_address");
        bulk_labeling_address = (String)settings.get("bulk_labeling_address");
        
        //System.out.println("to address: " + order_qty_to_address);
        //System.out.println("from address: " + order_qty_from_address);
       
   }
   
   /** Creates a new instance of ConfirmRemoveState */
   public DefaultState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent,
	  boolean scan_processed_by_preceding_state)
   {
	  super(button_pressed,carton_num,skip_ip,bay_num,parent);
	  this.scan_processed_by_preceding_state = scan_processed_by_preceding_state;
   }
   
   
   public boolean isCartonOfCompletedPallet(Carton carton) throws Exception
   {
	 
	  if(this instanceof ReturnToBayState)
		  return false;
	  
	  boolean is_invalid = false;
	  Pallet pallet = null;
	  if(carton.getPallet_id() != null)
	  {
		 pallet = ProductLabelingDAO.getPallet(carton.getPallet_id());
		 if(pallet != null)
			if(pallet.getProcess_status() != null && pallet.getProcess_status().toUpperCase().equals("PALLETIZED"))
			   is_invalid = true;
	  }
	  
	  //this.pallet = pallet;
	  return is_invalid;
   }
   
   public boolean isCartonOfOtherIncompletedPallet(Carton carton) throws Exception
   {
	  
	  boolean is_incomplete_pallet = false;
	  Pallet pallet = null;
	  if(carton.getPallet_id() != null)
	  {
		 pallet = ProductLabelingDAO.getPallet(carton.getPallet_id());
		 if(pallet != null)
			if(pallet.getProcess_status()== null||(pallet.getProcess_status()!= null && !pallet.getProcess_status().toUpperCase().equals("PALLETIZED")))
			   is_incomplete_pallet = true;
	  }
	  
	  
	  return is_incomplete_pallet;
   }
   
   public boolean isCartonAlreadyOnPallet(Carton carton) throws Exception
   {
	  
	  boolean is_carton_of_this_pallet = false;
	  if(carton.getPallet_id() != null)
	  {
		 Bay bay = getBay();
		 if(bay != null && bay.getState_pallet() != null && bay.getState_pallet().getId().equals(carton.getPallet_id()))
		 {
			is_carton_of_this_pallet = true;
		 }
		 
	  }
	  
	  
	  return is_carton_of_this_pallet;
   }
   
   
    @SuppressWarnings("static-access")
   protected String build_wrong_button_screen(String LCD2_message)
   {
	  
	  String msg = "";
	  if(LCD2_message != null)
		 msg = LCD2_message;
	  
	  this.is_error_screen = true;
	  return  parent.createWarningRobotScreen("INV BTN(" + this.button.toString() + ") FOR CTN",msg);
	  
	  
   }
   
   
    @SuppressWarnings("static-access")
   protected String build_wrong_button_screen(String LCD1_message,String LCD2_message)
   {
	  this.is_error_screen = true;
	  return  parent.createWarningRobotScreen(LCD1_message,LCD2_message);
	  
	  
   }
   
   
   
   protected Bay getBay() throws Exception
   {
	  return (Bay)MidwareCache.getDevicesCache().getDeviceState(this.skip_ip,this.bay_num);
   }
   
   protected boolean isBayEmpty() throws Exception
   {
	  Bay bay = getBay();
	  if(bay== null||(bay != null && (bay.getState_pallet()== null && bay.getStatePallet_template()== null)))
		 return true;
	  else
		 return false;
	  //return parent.isBayEmpty(this.skip_ip,this.bay_num);
   }
   
   
   //======================================================================================================
   //RULES AND CHECKS
   //Deafault implementations for button press interfaces are for 'non-transactional states'
   //Transactional states need to override these default responses to button presses, if they do not
   //want interference or if they require slightly differnet implementation
   //======================================================================================================
   
   
   //--------------------------------------------------------------------------------------------
   //NORMAL TRANSITION RULES FOR RETURN_TO_BAY OR RETURN QC SAMPLE
   //
   //1) Bay must be empty or an inspection carton must have been scanned out
   //--------------------------------------------------------------------------------------------
   protected boolean isValidButton1Context() throws Exception
   {
	  boolean can_proceed = false;
	  
	  if(isBayEmpty())
		 can_proceed = true;
	  else
	  {
		 String qc_state = "";
		 
		 if(getBay().getState_pallet()!= null)
		 {
			 qc_state = getBay().getState_pallet().getQc_status_code().toUpperCase();
			 if(qc_state.equals("INSPECTING")||qc_state.equals("PASSED")||qc_state.equals("FAILED"))
				can_proceed = true;
		 }
			
	  }
	  
	  return can_proceed;
   }
   
   
    @SuppressWarnings("static-access")
   protected String isValidButton3Context() throws Exception
   {
	  String msg = "";
	  
	  if(!isBayEmpty())
	  {
		 if(getBay().getState_pallet()== null)
			msg = this.parent.NO_PALLET_IN_BAY_MSG;
		 else if(getBay().getState_pallet().getQc_status_code().toUpperCase().equals("PASSED")||getBay().getState_pallet().getQc_status_code().toUpperCase().equals("FAILED"))
			msg = this.parent.PALLET_ALREADY_INSPECTED_MSG;
		 else if(getBay().getState_pallet().getQc_status_code().toUpperCase().equals("INSPECTING"))
			msg = this.parent.PALLET_BEING_INSPECTED_MSG;
		 
	  }
	  else
		 msg = this.parent.EMPTY_BAY_MSG;
	  
	  return msg;
	  
   }
   
   //-----------------------------------------------------------------
   //NORMAL TRANSITION RULES FOR COMPLETE PALLET
   //1) Bay must not be empty
   //2) Pallet must exist
   //3) Pallet qc_inspection status must be 'INSPECTED'
   //-----------------------------------------------------------------
   
   protected String isValidButton2Context() throws Exception
   {
	  return ""; //TODO MAKE SURE ABOUT THIS: PALLET CAN BE COMPLETED REGARDLESS OF INSPECTION STATUS
//		String msg = this.parent.PALLET_COMPLETE_NOT_APPLICABLE_MSG;
//		if(!isBayEmpty())
//		{
//			Bay bay = getBay();
//			if(bay != null)
//			{
//				Pallet pallet = getBay().getState_pallet();
//				if(pallet != null)
//				{
//					if(pallet.getQc_status_code().toUpperCase().equals("INSPECTED"))
//						msg = "";
//					else
//						msg = this.parent.PALLET_NOT_INSPECTED_MSG;
//				}
//			}
//
//		}
//
//
//		return msg;
	  
   }
   
   
   //====================================
   //RETURN TO BAY OR PPECB RETURN BUTTON
   //====================================
   protected void button_1_pressed() throws Exception
   {
	  if(isValidButton1Context())
	  {
		 if(isBayEmpty())
			this.parent.active_state = new ReturnToBayState(button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
		 else
		 {
			this.parent.active_state = new ReturnInspectionCtnState(button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
			
		 }
	  }//this.parent.active_state = new ReturnToBayState(button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
	  else
		 this.robot_screen = build_wrong_button_screen(null);
	  
   }
   



   //======================
   //COMPLETE PALLET BUTTON
   //======================
   protected void button_2_pressed() throws Exception
   {
	  String invalid_context = isValidButton2Context();
	  if(!invalid_context.equals(""))
		 this.robot_screen = build_wrong_button_screen(invalid_context);
	  else
	  {//context is valid: determine if user want to create a partial pallet
		 Bay bay = getBay();
		 Integer cpp = bay.getState_cpp();
		 Integer actual_count = bay.getState_pallet().getCarton_quantity_actual();
		 if(actual_count < cpp)
		 {
			this.parent.active_state = new ConfirmPartialCompleteState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
			
		 }
		 else if(actual_count > cpp)
		 {
			this.parent.active_state = new ConfirmPartialCompleteState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,true);
		 }

		 else
		 {
			try
			{
                if(this.carton == null)
                this.carton = PalletisingDAO.getLastCartonOnBay(this.bay_num,this.skip_ip);


			   Complete_pallet_transaction(false); //show pallet complete screen

                if( this.carton.getOrder_number()!= null && !this.carton.getOrder_number().toUpperCase().equals("N.A."))
                          this.send_order_quantity_info();

               MidwareCache.getDevicesCache().clearDeviceState(this.skip_ip,this.bay_num);

			   this.parent.active_state = new InitialState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
			}
			
			catch (Exception ex)
			{
			   if(ex instanceof PalletizingException)
				  this.setRemoveCtnState(((PalletizingException)ex).short_msg);
			   else
				  throw ex;
			}
			
		 }
		 
		 
	  }
	  
   }
   
   //-----------------------------------------------------------------
   //NORMAL TRANSITION RULES FOR DRAW INSPECTION CARTON
   //1) Bay must not be empty
   //2) Pallet must exist
   //3) Pallet qc_inspection status must be 'UNINSPECTED'
   //-----------------------------------------------------------------
    @SuppressWarnings("static-access")
   protected void button_3_pressed() throws Exception
   {
	  String invalid_msg = isValidButton3Context();
	  if(invalid_msg.equals(""))
		 this.parent.active_state = new DrawInspectionCartonState(button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
	  else
		 this.robot_screen = build_wrong_button_screen(invalid_msg,this.parent.NO_SAMPLE_REQUIRED_MSG);
	  
	  
   }
   
   protected void button_4_pressed()
   {
	  //ONLY SUBCLASSES CAN IMPLEMENT THIS MEANINGFULLY
	  
   }
   
   protected void button_5_pressed()
   {
	  //ONLY SUBCLASSES CAN IMPLEMENT THIS MEANINGFULLY
	  
   }
   
   
   
   protected Bay cacheBay() throws Exception
   {
	  
	  Bay bay =	getBay();
	  if(bay == null)
	  {
		 bay = PalletisingDAO.getBay(this.bay_num,this.skip_ip);
		  if(bay != null)
		    bay.setActive_bay_transaction(this);
		  else
			  throw new Exception("Bay cannot be found for skip " + this.skip_ip.toString() + " and bay " + this.bay_num.toString());
	  }
	  return bay;
	  
   }
   
   //==================================================================================
   //Business transactions stored in this class, because:
   //1) They need to be accessible to more than one subclass of this class
   //2) They need the command state already stored in this class instance
   //==================================================================================
   
   
   
   protected void Complete_pallet_transaction(boolean transaction_is_open) throws Exception
   {

	     Pallet pallet = getBay().getState_pallet();

         if(pallet == null)
             pallet = PalletisingDAO.getBayPallet(this.bay_num,this.skip_ip);


		 PalletisingDAO.completePallet(pallet,transaction_is_open, this.bay_num, this.skip_ip);
		 //pallet completed successfully, we need to clear the bay from
		 //memory
                 this.parent.provided_screen = this.build_order_qty_status_screen(pallet.getOrder_number(),pallet.getSeason_code(),pallet.getCommodity_code());
		 //MidwareCache.getDevicesCache().clearDeviceState(this.skip_ip,this.bay_num);
	
	  
	  
   }
   
   
   //===================================================
   //Main interface methods
   //===================================================
   
   
   public void button_pressed() throws Exception
   {
	  switch(this.button)
	  {
		 case btn1:
			this.button_1_pressed();
			break;
		 case btn2:
			this.button_2_pressed();
			break;
		 case btn3:
			this.button_3_pressed();
			break;
		 case btn4:
			this.button_4_pressed();
			break;
		 case btn5:
			this.button_5_pressed();
			break;
			
			
	  }
	  
   }
   
   
   //-----------------------------------------------------------------------------
   //Validations are
   //1) Is the carton valid:
   //   a) Does it exists
   //   b) Does it not belong to another completed pallet
   // NB: this method will set the parent's active state to the appropriate
   //     handling transaction if the carton is invalid
   //----------------------------------------------------------------------------
   
   
   public boolean is_carton_valid() throws Exception
   {
	  
	  this.carton = ProductLabelingDAO.getCarton(this.carton_num);
	  Carton carton = this.carton;
	  String reason = "";
	  
          
          if(carton == null)
	  {
		 reason = this.parent.NO_CTN_MSG;
		 
	  }
          else if(carton.getExit_reference()!= null && carton.getExit_reference().toUpperCase().equals("SCRAPPED"))
          {
              reason = this.parent.SCRAPPED_MSG;
          }
          
	  else
	  {
		 if(isCartonOfCompletedPallet(carton))
			reason = this.parent.OTHER_BAY_CARTON_MSG;
	  }
	  
	  if(!reason.equals(""))
	  {
		 setRemoveCtnState(reason);
	  }
	  
	  
	  return(reason.equals(""));
	  
   }
   
   
   protected void setRemoveCtnState(String reason) throws Exception
   {
	  if(this.isBayEmpty())
		 this.parent.active_state = new RemoveForInitialState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,reason,this.last_carton_scanned);
	  else if(getBay().isState_is_mixed_mode())
		 this.parent.active_state = new RemoveForMixedBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,reason,this.last_carton_scanned);
	  else
		 this.parent.active_state = new RemoveForBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,reason,this.last_carton_scanned);
	  
	  
   }
   
   protected void setRemoveCtnState(String	reason,String provided_command) throws Exception
   {
	  if(this.isBayEmpty())
		 this.parent.active_state = new RemoveForInitialState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,reason,this.last_carton_scanned);
	  else if(getBay().isState_is_mixed_mode())
		 this.parent.active_state = new RemoveForMixedBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,reason,this.last_carton_scanned);
	  else
		 this.parent.active_state = new RemoveForBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,reason,this.last_carton_scanned);
	  
	  ((ConfirmRemoveState)this.parent.active_state).provided_command = provided_command;
	  
   }
   
   //=================================================================================
   //What is base state?
   //It is (normal) the state of the bay that persist across individual transactions
   //i.e. pallet building or mixed pallet building, or the initial state
   //=================================================================================
   protected PalletizingState calculateBaseState() throws Exception
   {
	  
	  if(this.isBayEmpty())
		 return new InitialState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
	  else if(getBay().isState_is_mixed_mode())
		 return  new MixedPalletBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
	  else
		 return new PalletBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
	  
	  
   }
   
   //=============================================================================================
   //Default implementation of this method assumes the normal pallet criteria checking
   //mechanism- i.e. not applicable for the mixed building state
   //==============================================================================================
   protected  boolean isCartonValidForPallet() throws Exception
   {
	  if(this.isBayEmpty())
		 return true; //criteria check not needed for first carton scan
	  
	  Bay bay = getBay();
	  PalletizingCriteria criteria = bay.getState_criteria();
	  Pallet pallet = bay.getState_pallet();
	  Carton carton = this.carton;
	  ProductionRun run = bay.getState_run();
	  FgSetup fg_setup = bay.getState_Fg_Setup();
	  Carton carton_setup = bay.getState_carton_setup();
          
          
	  boolean test_failed = false;
	  
	  //FG_CODE: COMPULSORY
	  if(!pallet.getFg_product_code().equals(carton.getFg_product_code())) //fg codes must always match
		 test_failed = true;

		  //SELL BY CODE
	  else if (!fg_setup.getRetailer_sell_by_code().equals(carton.getSell_by_code()))
		  test_failed = true;
          
            //ORG_CODE: COMPULSORY
	  if(!pallet.getOrganization_code().equals(carton.getOrganization_code())) //fg codes must always match
		 test_failed = true;
	  
	  //TARGET MARKET
	  else if (criteria.getTarget_market_code()== true && !(pallet.getTarget_market_code().equals(carton.getTarget_market_code())))
		 test_failed = true;
	  
	  //INVENTORY CODE
	  else if (criteria.getInventory_code()== true && !(pallet.getInventory_code().equals(carton.getInventory_code())))
		 test_failed = true;
	  
	  //MARK CODE
	  else if (criteria.getMark_code()== true && !(pallet.getCarton_mark_code().equals(carton.getCarton_mark_code())))
		 test_failed = true;
	  
	  //FARM CODE
	  else if (criteria.getFarm_code()== true && !(run.getFarm_code().equals(carton.getFarm_code())))
		 test_failed = true;


		  //UNITS PER CARTON
	  else if (criteria.getUnits_per_carton()== true && !(carton_setup.getUnits_per_carton().equals(carton.getUnits_per_carton())))
		 test_failed = true;
	  
	  return !test_failed;
	  
   }
   
   
   
   
    @SuppressWarnings("static-access")
   protected String build_order_qty_status_screen(String order_number,String season,String commodity_code) throws Exception
   {
        String season_code = season + "_" + commodity_code;
        Map schedule_order_details = ProductLabelingDAO.getSeasonOrderQtyDetails(order_number,season_code);
        int req_qty = 0;
        int produced_qty = 0;
        if(schedule_order_details!= null)
        {
            req_qty = (Integer)schedule_order_details.get("quantity_required");
            produced_qty = (Integer)schedule_order_details.get("quantity_produced");
        }
        
        String required = String.valueOf(req_qty);
        String produced = String.valueOf(produced_qty);
        
        String line1 = "ON:" + order_number;
        String line2 = "Rqr:" + required + " Pack:" + produced;
        
        return this.parent.createNormalRobotScreen(line1,line2);
        
   
   }
   
   
   protected void send_order_quantity_info() throws Exception
   {
       
       MailInterface    mailer = null;
       mailer = DeviceScan.getMailer();
       HashMap mail_log = new HashMap();
       String mail_content = new String();
       //----------------------------------------------------------------------------------------------------------
       //Get schedule order details and determine if the order has been fullfilled as result this label's printing'
       // IF SO: set the 'label_message' member variable to: "SCHED ORDER COMPLETED: " <order_quantity_produced>
      //----------------------------------------------------------------------------------------------------------
        String season_code = this.carton.getSeason_code() + "_" + this.carton.getCommodity_code();
        Map schedule_order_details = ProductLabelingDAO.getSeasonOrderQtyDetails(this.carton.getOrder_number(),season_code);
        int req_qty = 0;
        int produced = 0;
        if(schedule_order_details!= null)
        {
            int cpp = getBay().getState_cpp();
            if(cpp == 0)
                throw new Exception("cpp is null");
            
            req_qty = (Integer)schedule_order_details.get("quantity_required");
            produced = (Integer)schedule_order_details.get("quantity_produced");
            produced += 1;
            schedule_order_details.put("quantity_produced",produced);
              //-----------------------------------------------------------------------------
            //Update season order qty for this order and season on season order qty's table
            //-----------------------------------------------------------------------------
            int new_qty = (Integer)schedule_order_details.get("quantity_produced");

            String upc_type = this.carton.getUnit_pack_product_code().substring(0,1).toUpperCase();
            boolean alert_bulk_labeling = !(upc_type.equals("T")||upc_type.equals("J"));
            int n_mails =  alert_bulk_labeling == true ? 2 : 1;


            //ProductLabelingDAO.updateSeasonOrderQty(new_qty,(Integer)schedule_order_details.get("id"));
            
            //System.out.println("ORDER QTY Updated");
            String label_message = null;
            String subject = "CARTON ORDER QUANTITY REACHED ";
            if(req_qty > 0)
            {
               //----------------------------------------------------------------------
               //Send emails if 1) produced = required - 20 and 2) produced = required
               //
               //----------------------------------------------------------------------

               if(produced >= req_qty)
               {
                    label_message = "CUSTOMER ORDER " + this.carton.getOrder_number() + "(required: " + String.valueOf(req_qty) + ", produced: " + ((Integer)schedule_order_details.get("quantity_produced")).toString() + ") FIN";

               }
                   else if((req_qty - produced) <= (20 + cpp))
               {
                  label_message = "CUSTOMER ORDER " + this.carton.getOrder_number() + "(required: " + String.valueOf(req_qty) + ", produced: " + ((Integer)schedule_order_details.get("quantity_produced")).toString() + ") ALMOST FIN";  
                  subject = "CARTON ORDER QUANTITY ALMOST REACHED ";

               }
               
               if (label_message != null)
               {
                String tos[] = new String[n_mails];
                tos[0] = order_qty_to_address;

                if(alert_bulk_labeling == true)
                    tos[1] = bulk_labeling_address;

                String lines[] = new String[5];
                lines[0] = "<strong>" + label_message + "</strong><HR>";
                lines[1] = "Product: " + this.carton.getExtended_fg_code() + "<BR>";
                lines[2] = "Run: " + this.carton.getProduction_run_code() + "<BR>";
                lines[3] = "Target market: " + this.carton.getTarget_market_code() + "<BR>";
                lines[4] = "Inventory code: " + this.carton.getInventory_code()+ "<BR>";
                String mail_err = "";

                try {

                    mailer.mailTransmission(order_qty_from_address,tos,subject,lines);
                }
                    catch(Exception me)
                {
                    me.printStackTrace();
                    mail_err = me.getMessage();
                }


                   mail_content = lines[0] + lines[1] + lines[2] + lines[3] + lines[4];
                   mail_log.put("content",mail_content);
                   mail_log.put("season_order_quantity_id",schedule_order_details.get("id"));
                   String to_two = "";
                   to_two = n_mails == 1?" ":tos[1];
                   mail_log.put("to",tos[0] + "," + to_two);
                   mail_log.put("mail_err",mail_err);
                   PalletisingDAO.logSeasonOrderQtyMail(mail_log);

               }
            }
        }
   }
   
        
   //-----------------------------------------------------------------------------------------------
   //This method can be used to add a carton to a pallet, also for a transfer carton scenario
   //The TransferCarton state needs to provide the donor bay and pallet as input
   //This method needs to check whether, as a result from the addition, the pallet became complete:
   // (i.e. cpp = actual carton count)
   //This method will transition to the appropriate state- can only be pallet building, mixed
   //pallet building or pallet complete state
   //----------------------------------------------------------------------------------------------
    
    @SuppressWarnings("static-access")
   protected void AddCartonToPalletTransaction(Pallet pallet,Pallet pallet_to_decrement,Bay bay_to_decrement,boolean transaction_is_open) throws Exception
   {
	  //try
	  //{
		 boolean is_complete = false;
		 if(pallet == null)
			 throw new Exception("AddCartonToPalletTransaction method cannot be called with a null pallet parameter ");
		 
		 if(!transaction_is_open)
			DataSource.getSqlMapInstance().startTransaction();
		 
		 this.carton = ProductLabelingDAO.getCarton(this.last_carton_scanned);
		 Integer carton_qty_actual = pallet.getCarton_quantity_actual()== null? 1: pallet.getCarton_quantity_actual() +1;
		 PalletisingDAO.updatePalletCartonQuantity(pallet.getPallet_number(),carton_qty_actual);
		 pallet.setCarton_quantity_actual(carton_qty_actual);
		 
		 //decrement transfer donor pallet, if this is a transfer scenario
		 if(pallet_to_decrement != null)
		 {
			PalletisingDAO.updatePalletCartonQuantity(pallet_to_decrement.getPallet_number(),pallet_to_decrement.getCarton_quantity_actual() -1);
			PalletisingDAO.deleteBayCarton(this.carton.getId()); //for a transfer the carton must exist on the donor bay
			
		 }
		 
		 
		 BayCarton bay_carton = new BayCarton();
		 bay_carton.setPallet_id(pallet.getId());
		 bay_carton.setCarton_id(this.carton.getId());
		 bay_carton.setBay_id(getBay().getId());
                 //---------------------------------------------------------------------------------------
                 //Work around: for some reason, a bay carton sometimes exists here- even though it is
                 //deleted during a transfer. As an extra safeguard, we'll first check whether it
                 //exists here, before trying to create it
                 //----------------------------------------------------------------------------------------
		 if(PalletisingDAO.getBayCarton(this.carton.getCarton_number(),this.bay_num,this.skip_ip)== null);
                    PalletisingDAO.createBayCarton(bay_carton);
		 
		 //set pallet ref in the im-memory carton & then update the actual carton in db
		 this.carton.setPallet_id(pallet.getId());
                 //------------------------------------------------------------------------------------
                 //Jan 2009 changes:
                 //Update order qty_produced for first-time palletized cartons with valid order numbers
                 //a Carton in the below given transaction contexts(states) that have no pallet id and
                 //have  a valid order number (not 'n.a.') qualify to have order number quantities incremented
                 //-------------------------------------------------------------------------------------------

//                  if(this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.InitialState")||
//			this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.PreInitialState")||
//			this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.PalletBuildingState")||
//			this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.MixedPalletBuildingState"))
//                  {
////                     if(this.carton.getPallet_number()== null && this.carton.getOrder_number()!= null && !this.carton.getOrder_number().toUpperCase().equals("N.A."))
////                     {
////                       // System.out.println("CARTON: " + this.carton.getCarton_number().toString() + " require order increment");
////
////                        //this.send_order_quantity_info();
////                     }
//                  }
//          
//                 else
//                 {
//                      //System.out.println("CARTON: " + this.carton.getCarton_number().toString() + " DOES NOT require order increment");
//
//                 }
                 this.carton.setPallet_number(pallet.getPallet_number());
		 PalletisingDAO.updatePalletNumOnCarton(this.carton);
		 
		 //check whether pallet has become complete, and if so, complete it
		 if(pallet.getCarton_quantity_actual().equals(pallet.getCpp()))
		 {
			this.Complete_pallet_transaction(true);

                        if( this.carton.getOrder_number()!= null && !this.carton.getOrder_number().toUpperCase().equals("N.A."))
                          this.send_order_quantity_info();

                        MidwareCache.getDevicesCache().clearDeviceState(this.skip_ip,this.bay_num);
                        
			            is_complete = true;
			//this.parent.active_state = new InitialState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
			//----------------------------------------------------------------------------------------------------------------
                        //new code 10/06/2008: transfer to initialstate not clean enough- just remove all state and let it be recalculated
                        //----------------------------------------------------------------------------------------------------------------
                        this.parent.active_state = null;
                        MidwareCache.getDevicesCache().clearDeviceState(skip_ip,bay_num);
		        //----------------
                        //end new code
                        //----------------
                 }
		 else
		 { //transit to pallet building or mixed pallet building state
			getBay().getState_pallet().setCarton_quantity_actual(pallet.getCarton_quantity_actual());
			
			if(!getBay().isState_is_mixed_mode())
			   this.parent.active_state = new PalletBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
			else
			   this.parent.active_state = new MixedPalletBuildingState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
		 }
		 
		 
		 DataSource.getSqlMapInstance().commitTransaction();
		 //update the in-memory carton quantities- NB only after transaction committed
		 
		
		 if(pallet_to_decrement != null)
		 {
			 if(bay_to_decrement.getState_pallet()!= null)
			  bay_to_decrement.getState_pallet().setCarton_quantity_actual(bay_to_decrement.getState_pallet().getCarton_quantity_actual()-1);
			 
			  bay_to_decrement.setState_special_instruction(this.parent.CARTON_TRANSFERRED_MSG);
		 }
		 
		 //if(is_complete == true)
			//DeviceScan.send_integration_record("pallet_completed",pallet.getId().toString(),"Pallet");
		 
	  //}
	  //catch (Exception ex)
	  //{
		 
		// throw new Exception("AddCartonToPalletTransaction failed. Reported exception: " + ex.toString());
		
	  //}
	  
	  //finally{
	   //DataSource.getSqlMapInstance().endTransaction();
	  //}
	  
	  
	  
   }
   
    
   //==============================================================
   //This method creates the first pallet on a bay and then uses
   //the populate bay to store additional state info needed by all
   //subsequent transactions
   //==============================================================
    @SuppressWarnings("static-access")
   protected void createPalletOnBayTransaction() throws Exception
   {
	  
	  try
	  {
		 
		 Bay bay = null;
	     
		 if(this.carton == null && this.last_carton_scanned != null)
		 {
			 this.carton = ProductLabelingDAO.getCarton(this.last_carton_scanned);
		 }
		 bay = parent.populateBay(this.carton,this.bay_num,this.skip_ip); //NB: REMEMBER TO CLEAR THE BAY STATE IF THE TRANSACTION FAILS
		 
		 //---------------------------------------------------------------------------------------------------
		 //NOTE: populate_bay will attempt to find a pallet_setup and template from the carton id. If these
		 //      cannot be found, a  PalletizingException will be thrown
		 //---------------------------------------------------------------------------------------------------
		 if(isBayEmpty())
			throw new Exception("CreatePalletOnBayTransaction cannot proceed, because the bay state was not set in populateBay() ");
		 
		 DataSource.getSqlMapInstance().startTransaction();
		 
		 
		 long pallet_nr = 0;
		 pallet_nr = ProductLabelingDAO.getNextMesObjectId(ProductLabelingDAO.MesObjectTypes.PALLET);
		 
		 //Create the pallet from the pallet template
		 Pallet pallet = bay.getStatePallet_template();
		 
		 //populate additional info- i.e. anything pallet requires that is not available on the pallet template
		 pallet.setBuild_status("PARTIAL");
		 pallet.setQc_status_code("UNINSPECTED");
		 pallet.setProcess_status("PALLETIZING_NORMAL");
		 pallet.setDate_time_created(new java.sql.Timestamp(new java.util.Date().getTime()));
		 pallet.setProduction_run_id(bay.getState_run().getId());
		 pallet.setPallet_number(pallet_nr);
		 pallet.setPallet_template_id(Integer.parseInt(pallet.getId().toString())); //the 'pallet' object is really the pallet template
		 
		 pallet.setPick_reference_code(this.carton.getPick_reference());
		 pallet.setOrder_number(this.carton.getOrder_number());
		 pallet.setCpp(bay.getState_cpp());
		 pallet.setAccount_code(this.carton.getAccount_code());
		 pallet.setIso_week_code(ProductLabelingDAO.getCurrentIsoWeek());
		 pallet.setFg_code_old(this.carton.getFg_code_old());
		 String farm_code  = bay.getState_run().getFarm_code();
		 pallet.setFarm_code(farm_code);
		 PalletisingDAO.createPallet(pallet);
		 //get id allocated by db and update in-memory pallet
		 Long pallet_id = PalletisingDAO.getPalletId(pallet.getPallet_number());
		 pallet.setId(pallet_id);
		 
		 pallet.setCpp(bay.getState_cpp());
		
		 
		 bay.setState_pallet(pallet);
		 //store bay in cache
		 MidwareCache.getDevicesCache().getDevicesCache().setDeviceState(this.skip_ip,this.bay_num,bay);
		 
	  }
	  catch (Exception ex)
	  {
	  	 MidwareCache.getDevicesCache().clearDeviceState(this.skip_ip,this.bay_num);
		 
	 	 if(ex instanceof PalletizingException)
	 		throw ex;
	 	 else
	 		throw new Exception("Pallet could not be created on bay. Reported Exception: " + ex.toString());
	  }
	  
	  finally
	  {
		 
	  }
	  
   }
   
   
   //===========================================================================
   //This method builds the screen used by the pallet building and mixed
   //pallet building states
   //
   //===========================================================================
    @SuppressWarnings("static-access")
   protected void build_palletizing_screen() throws Exception
   {
	  try
	  {
		 
		 //---------------------------------------------------------------------------
		 //It is possible that an 'invalid_screen' was created by some transaction
		 //earlier- in which case this method needs to leave things as they are
		 //
		 //---------------------------------------------------------------------------
		 if(this.is_error_screen == true)
		 {
			return;
		 }
		 
		 
		 //some validations
		 if(getBay() == null)
			throw new Exception("Bay is null");
		 
		 if(this.pallet == null)
		 {
			Bay bay = (Bay)MidwareCache.getDevicesCache().getDeviceState(skip_ip,bay_num);
			this.pallet = bay.getState_pallet();
		 }
		 
		 
		 this.carton_num = this.carton_num == null?this.last_carton_scanned : this.carton_num;
		 
		 if(this.pallet == null)
			throw new Exception("Palletizing screen cannot be build. Reason: pallet is null");
		 
		 else if(this.pallet.getPallet_number() == null||this.pallet.getPallet_number().equals(0))
			throw new Exception("Palletizing screen cannot be build. Reason: pallet number of pallet is null or zero");
		 //else if (this.carton_num == null ||this.carton_num.equals(0))
		 //  throw new Exception("Palletizing screen cannot be build. Reason: carton number of pallet is null or zero");
		 else if(getBay().getState_cpp() == null||getBay().getState_cpp().equals(0))
			throw new Exception("Palletizing screen cannot be build. Reason: cartons per pallet is null or zero");
		 
		 String pallet_num = getBay().getState_pallet().getPallet_number().toString();
		 String carton_num = "";
		 if(this.carton!= null)
			carton_num = this.carton_num.toString();
		 
		 String cpp = getBay().getState_cpp().toString();
		 if(getBay().getState_pallet().getCarton_quantity_actual()== null)
			throw new Exception("carton_quantity_actual is null of pallet with id: " + getBay().getState_pallet().getId().toString());
		 
		 String carton_count = getBay().getState_pallet().getCarton_quantity_actual().toString();
		 
		 String lcd2 = "C:" + carton_count + "/" + cpp;
		 
		 if(lcd2.length() > 20)
			lcd2 = carton_count + "/" + cpp;
		 
		 
		 this.robot_screen = parent.createNormalRobotScreen(getQcStatusChar() + pallet_num,lcd2);
		 
	  }
	  catch (Exception ex)
	  {
		 throw new Exception("Palletizing screen could not be built. Reported exception: " + ex.toString());
	  }
	  
	  
   }
   
   
   
   private String getQcStatusChar() throws Exception
   {
	  try
	  {
		 
		 String qc_status = this.pallet.getQc_status_code().toUpperCase();
		 if(qc_status.equals("UNINSPECTED"))
			return "PU";
		 else if (qc_status.equals("INSPECTING"))
			return "P*";
		 else if(qc_status.equals("INSPECTED"))
		 {
			if(pallet.getQc_result_status().equals("PASSED"))
			   return "P+";
			else if(pallet.getQc_result_status().equals("FAILED"))
			   return "P-";
			else
			   throw new Exception("Inspection char could not be calculated. Unknown qc result status. Qc status is: " + qc_status + ". Qc result status is: " + pallet.getQc_result_status().toString());
			
		 }
		 else
			throw new Exception("Inspection char could not be calculated. Qc status is: " + qc_status + ". Qc result status is: " + pallet.getQc_result_status().toString());
		 
	  }
	  catch (Exception ex)
	  {
		 throw new Exception("Inspection char could not be calculated. Reported exception: " + ex.toString());
	  }
	  
	  
   }
   
   
   //==============================================================================================
   //This method needs to be implemented by subclasses to 'handle' the
   //transaction appropriately
   //The default implementation will work for any of the normal states, i.e.
   //  pallet building state or mixed pallet building or initial
   //  The confirm transfer state need to override this method
   //  This method is trying to be too clever- need refactoring in future
   //===============================================================================================
   
   
   public void HandleAddCartonToPalletTransaction() throws Exception,PalletizingException
   {
	  //try
	  //{
		 Pallet pallet = null;
		 
		 boolean trans_is_open = false;
		 
		 //-----------------------------------------------------------------------------------
		 //Why i'm using 'this.getClass()':
		 //1) 'this' keyword alone points to the superclass- I'm looking for the subclass'
		 //2) More correct implementation will be to let the subclass decide
		 //   whether to participate in steps of this algorythm, because
		 //   of 'dependency inversion' principle (i.e. a superclass should
		 //   not have to know about it's subclasses in order to function)'
		 //   But I want to make default implementation of transactions
		 //   complete (too complete I guess) do not want to scatter business logic too much
		 //
		 //------------------------------------------------------------------------------------
		 if(this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.InitialState")||this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.PreInitialState"))
		 {
			createPalletOnBayTransaction();
			trans_is_open = true;
			
		 }
		 Bay bay = getBay();
		 if(bay != null)
			pallet = bay.getState_pallet();
		 
		 if(this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.InitialState")||
			this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.PreInitialState")||
			this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.PalletBuildingState")||
			this.getClass().getName().equals("za.co.multitier.midware.sys.mwpt.MixedPalletBuildingState"))
                 {
                        
  
			 AddCartonToPalletTransaction(pallet,null,null,trans_is_open);
                 }
		 
		 
//	  }
//	  catch (Exception ex)
//	  {
//	 	 if(ex instanceof PalletizingException)
//	 		throw ex;
//	 	 else
//	 		throw new Exception("Add carton to pallet transaction failed unexpectedly. Reported Exception: " + ex.toString());
//		 
//	  }
   }
   
   
   public void carton_scanned()throws Exception
   {
	  if(this.scan_processed_by_preceding_state) //some preceding state already processed the scan in a relevant transaction, so this state must wait one round
		 return;
	  
	  this.last_carton_scanned = this.carton_num;
	  if(is_carton_valid())
		 if(isCartonAlreadyOnPallet(this.carton))
		 {
			this.setRemoveCtnState(PalletizingAction.CARTON_ALREADY_ON_PALLET_MSG);
		 ((ConfirmRemoveState)this.parent.active_state).provided_command = PalletizingAction.PUT_CTB_BACK_MSG;
		 }
		 else if(this.isCartonValidForPallet())
		 {
		 //----------------------------------------------------------------------------------------------------------
		 //last thing to check is whether this carton belongs to another pallet that is incomplete, in which case
		 //we need to transit to the Confirm transfer state
		 //----------------------------------------------------------------------------------------------------------
		 
			if(isCartonOfOtherIncompletedPallet(this.carton))
			{
				if(this.carton.isIs_inspection_carton()!= null && this.carton.isIs_inspection_carton().equals(true))
					this.setRemoveCtnState(PalletizingAction.INSPECT_CTN_OTHER_PLT);
				else
				 this.parent.active_state = new ConfirmTransferState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent,false);
			}
			else
			{
				try
				{
					this.HandleAddCartonToPalletTransaction(); //if this is the last carton, the add_carton transaction will transition to
					//the pallet complete state
					//else it would transition to either pallet huilding or mixed pallet building state
				}
				catch (PalletizingException ex)
				{
					this.setRemoveCtnState(ex.short_msg);
			   
				}
			}
		 }
		 else //carton failed criteria check
		 {
			this.setRemoveCtnState(PalletizingAction.CRITERIA_FAIL_MSG);
		 }
	  
   }
   
   
}
