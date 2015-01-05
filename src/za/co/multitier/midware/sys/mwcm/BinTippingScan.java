/*
 * BinTippingScan.java
 *
 * Created on January 30, 2007, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwcm;

import java.util.HashMap;
import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.midware.sys.appservices.*;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.datasource.*;


/**
 *
 * @author Administrator
 */
public class BinTippingScan extends DeviceScan {
    
    //-------------------
    //Device instructions
    //-------------------
    
//	private static final String BINTIP_NOT_ACTIVE = SysProtocol.TCONTAINERMOVE +  "Status = \"false\" Red = \"false\"" +
//		                                            " Yellow = \"true\" Green = \"false\"> Device not active </ContainerMove>";
    
    private static final String BINTIP_NOT_ACTIVE = SysProtocol.TCONTAINERMOVE + "Status=\"false\" Red=\"false\"" +
        " Yellow=\"true\" Green=\"false\" Msg=\"Dev not active\" />";
    
    private static final String BINTIP_ERROR = SysProtocol.TCONTAINERMOVE + "Status=\"false\" Red=\"true\"" +
        " Yellow=\"false\" Green=\"false\" Msg=\"System error occurred\" />";
    
    
    private static final String SCREEN_ERROR = SysProtocol.TCONTAINERMOVE + "Status=\"false\" Red=\"true\"" +
        " Yellow=\"false\" Green=\"false\" Msg=\"Screen error occurred\" />";
    
    private static final String BINTIP_INSTRUCTION = SysProtocol.TCONTAINERMOVE + "Status=\"%s\" RunNumber=\"%s\" Red=\"%s\"" +
        " Yellow=\"%s\" Green=\"%s\" Msg=\"%s\" />";
    
    //-----------------
    //message constants
    //-----------------
    
    public static  String SCAN_NEXT_BIN_MSG = "SCAN BIN";
    public static  String BIN_NOT_FOUND_MSG = "NOT FND:REQ OV";
    public static  String BIN_NOT_FOUND_MSG_DP = "BIN NOT FOUND";
    public static  String REMOVE_BIN_MSG = "REMOVE BIN";
    public static  String PRESS_F3_F4_MSG = "F3('Y')/F4('N')";
    public static  String TIPPED_BIN_REQ_AUTH = "TIPD BIN:REQ OV";
    public static  String TIPPED_BIN_DP = "BIN ALREADY TIPPED IN MES SYSTEM";
    public static  String DESTROYED_OR_PRESORT_TIPPED_BIN_DP = "BIN TIPPED IN PRESORT PLANT OR DESTROYED ELSEWHERE";
    public static  String INVALID_BIN_REQ_AUTH = "PV INV BIN:REQ OV";
    public static  String PV_INVALID_BIN_DP = "PREVIOUS INVALID BIN";
    public static  String NO_OVERRIDE_AUTH = "OVERRIDE DENIED";
    public static  String IS_CHILD_RUN = "THIS IS A CHILD RUN";
    public static  String WAITING_FOR_MAF = "WAITING FOR MAF...";

    public static  String IS_NON_PRESORT = "IS_NON_PRESORT";
    
    //-------------------------
    //MEMBER VARIABLES
    //--------------------------
    public BinTippingState active_state;
    public ActiveDevice active_device;
    public String authoriser_name;
    public String lotNumber;

    public boolean is_non_presort = false;
    
    public MessageInterface ServerConsole;
    
    DeviceCommands.TRM_KEYPAD key_pressed;



    public boolean HasOverrideAuthorisation = false;
    
    private String getTippedCountForRun(String run_code) throws Exception {
        //get line_id from active_device
        String line_code = this.active_device.getLine_code();
        Integer tipped_count = BinTippingDAO.getTippedBinsCount(line_code,run_code);
        return tipped_count.toString();
    }
    
    
    public String createBintipInstruction(boolean status,boolean red,String run_code,boolean yellow, boolean green,String message)throws Exception {
        //-----------------------------------------------------------------------------------------
        //If the message is the 'SCAN BIN' message(i.e. successfull scan or override took place), we
        //need to extend the message construction in the following way
        // -> get the total tipped count for the run and line and append to 'SCAN BIN MESSAGE'
        // -> replace the run_code with the day_line_batch_code
        //-----------------------------------------------------------------------------------------
        if(message.equals(this.SCAN_NEXT_BIN_MSG)) {
            String day_batch_code = this.active_device.getDay_line_batch_number();
            String tipped_count = getTippedCountForRun(run_code);
            String extended_message = message + "(" + tipped_count + " TPD)";
            return String.format(BinTippingScan.BINTIP_INSTRUCTION,status,day_batch_code,red,yellow,green,extended_message);
        } else
            return String.format(BinTippingScan.BINTIP_INSTRUCTION,status,run_code,red,yellow,green,message);
        
    }
    
    public static String createCustomBintipInstruction(boolean status,boolean red,String run_code,boolean yellow, boolean green,String message)throws Exception {
        
        return String.format(BinTippingScan.BINTIP_INSTRUCTION,status,run_code,red,yellow,green,message);
        
    }
    
    /** Creates a new instance of BinTippingScan */
    
    
    public BinTippingScan(MessageInterface msg) {
        super(msg);
    }

      public BinTippingScan() {

    }
    
    
    
    public String scanBin(String ip,String bin_id,DeviceCommands.TRM_KEYPAD key_pressed,ActiveDevice active_device,String lotNumber) throws Exception {
        try {

            //System.out.println("STEP!: lot: " + lotNumber);
            //System.out.println("is non: " + String.valueOf(this.is_non_presort));

            if (lotNumber != null && lotNumber.trim() != "")
            {

              if(lotNumber.equals(BinTippingScan.IS_NON_PRESORT))
                this.is_non_presort = true;
            }

            //System.out.println("is non: " + String.valueOf(this.is_non_presort));

            if(active_state == null)
                this.active_state = new AwaitScanState(ip,bin_id,this);
            else
                this.active_state.bin_id = bin_id;


              this.lotNumber = lotNumber;
            
            this.active_device = active_device;
            this.key_pressed = key_pressed;
            //-----------------------------------------------------------------------------------------------------------
            //Set the current bin id: it is possible that an active state object that existed before this server
            //roundtrip had an id and that a different id was now scanned-in, in which case the active state
            //object would never get the new id- because they are assigned during creation time in their constructors
            //Some transactions, such as found in awaitauth state, need both ids
            //---------------------------------------------------------------------------------------------------------
            this.active_state.setCurrent_bin_id(bin_id);
            this.active_state.scanBin();
            if(active_state.device_instruction == null||active_state.device_instruction.trim().equals(""))
                throw new Exception("Bin Scan state: " + active_state.getClass().getName() + " did not set a processing instruction! ");
            else
                return active_state.device_instruction;
        } catch (Exception ex) {
            throw ex;
        }
        
        
    }
    
    //-------------------------------------------------------------------------------------------
    //This static method is called from the CM midware component's process_transaction
    //It expects a string as the xml processing instruction to be sent to the bintipper
    //This method main purpose is obtain the correct instance of this class- it could have
    //been stored in cache or it must be created for the first time or it may need to be created
    //from disk(in the event of a power failure)
    //-------------------------------------------------------------------------------------------
    public static String scanBin(MessageInterface msg,String ip,String bin_id,DeviceCommands.TRM_KEYPAD key_pressed,String lotNumber) {
        ActiveDevice active_device = null;
        String bin_fetch_err = "";


        try {



            active_device = DevicesDAO.getActiveDevice(ip);

            
            if(active_device == null)
                return BinTippingScan.BINTIP_NOT_ACTIVE;

             ProductionRun run = (ProductionRun)ProductLabelingDAO.getProductionRun(active_device.getProduction_run_id());
            if(run.getParent_run_code()!= null)
                return createCustomBintipInstruction(false,true,run.getProduction_run_code(),false,false,BinTippingScan.IS_CHILD_RUN);
            
            
            //Now try to get an instance from memory
            BinTippingScan bin_scan = (BinTippingScan)BinTippingCache.getDevicesCache().getDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING);
            if(bin_scan == null)
                bin_scan = new BinTippingScan(msg);
            
            if(key_pressed == DeviceCommands.TRM_KEYPAD.F3||key_pressed == DeviceCommands.TRM_KEYPAD.F4) {
                //In this case no scan took place- we need to get the bin_id from memory
                
                bin_id = bin_scan.active_state.bin_id;
            } else
            {
                 HashMap shift = ProductLabelingDAO.getShift(run.getLine_code());
                if(shift == null)
                    bin_fetch_err = "NO SHIFT FOR LINE: " + run.getLine_code();
                else
                {

                    bin_fetch_err = BinTippingDAO.validate(bin_id, active_device.getProduction_run_id());
                }
            }
            
            if(bin_fetch_err == null){
                String device_instruction = bin_scan.scanBin(ip,bin_id,key_pressed,active_device,lotNumber);
                BinTippingCache.getDevicesCache().setDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING,bin_scan);
                return device_instruction;
            }
            else
            {
               String batch_code = active_device == null?" NA":active_device.getDay_line_batch_number().toString();
                return createCustomBintipInstruction(false,true,batch_code,false,false,bin_fetch_err);
            }
                //bin fetch error occurred
            
            
        } catch (Exception ex) {
            String prod_run_code = "NOT KNOWN IN CONTEXT: ACTIVE DEVICE DOES NOT EXIST IN CONTEXT";
            if(active_device != null)
                prod_run_code = active_device.getProduction_run_code();
            
            DeviceScan.handle_exception(msg,"Bin Scan exception occurred for ip:" + ip + " and bin_id: " + bin_id,
                ex.toString(),"BintippingScan.scan_bin",DeviceTypes.BIN_TIPPING,0,prod_run_code,ex.getStackTrace());
            return BINTIP_ERROR;
        }
        //BinTippingScan bin_scan = BinTippingCache.getDevicesCache().getDeviceState(ip,
        
    }


     public static String scanBin(String ip,String bin_id,DeviceCommands.TRM_KEYPAD key_pressed,String lotNumber) throws Exception {
        ActiveDevice active_device = null;
        String bin_fetch_err = "";

            active_device = DevicesDAO.getActiveDevice(ip);


            if(active_device == null)
                return BinTippingScan.BINTIP_NOT_ACTIVE;

             ProductionRun run = (ProductionRun)ProductLabelingDAO.getProductionRun(active_device.getProduction_run_id());
            if(run.getParent_run_code()!= null)
                return createCustomBintipInstruction(false,true,run.getProduction_run_code(),false,false,BinTippingScan.IS_CHILD_RUN);


            //Now try to get an instance from memory
            BinTippingScan bin_scan = (BinTippingScan)BinTippingCache.getDevicesCache().getDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING);
            if(bin_scan == null)
                bin_scan = new BinTippingScan();

            if(key_pressed == DeviceCommands.TRM_KEYPAD.F3||key_pressed == DeviceCommands.TRM_KEYPAD.F4) {
                //In this case no scan took place- we need to get the bin_id from memory

                bin_id = bin_scan.active_state.bin_id;
            } else
            {
                 HashMap shift = ProductLabelingDAO.getShift(run.getLine_code());
                if(shift == null)
                    bin_fetch_err = "NO SHIFT FOR LINE: " + run.getLine_code();
                else
                {

                    bin_fetch_err =  BinTippingDAO.validate(bin_id, active_device.getProduction_run_id());
                }
            }

            if(bin_fetch_err == null){
                String device_instruction = bin_scan.scanBin(ip,bin_id,key_pressed,active_device,lotNumber);
                BinTippingCache.getDevicesCache().setDeviceState(ip,active_device.getProduction_run_code(),DeviceTypes.BIN_TIPPING,bin_scan);
                return device_instruction;
            }
            else
            {
               String batch_code = active_device == null?" NA":active_device.getDay_line_batch_number().toString();
                return createCustomBintipInstruction(false,true,batch_code,false,false,bin_fetch_err);
            }
                //bin fetch error occurred



        //BinTippingScan bin_scan = BinTippingCache.getDevicesCache().getDeviceState(ip,

    }
    
    
    
}
