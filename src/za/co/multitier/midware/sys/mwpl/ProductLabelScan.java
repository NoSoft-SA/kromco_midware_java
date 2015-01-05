/*
 * ProductLabelScan.java
 *
 * Created on February 6, 2007, 2:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpl;

import java.util.HashMap;
import java.util.Map;
import za.co.multitier.mesware.messages.MessageInterface;

import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.DeviceTypes;
import za.co.multitier.midware.sys.datasource.ActiveDevice;
import za.co.multitier.midware.sys.datasource.DevicesDAO;

/**
 *
 * @author Administrator
 */
public abstract class ProductLabelScan extends DeviceScan
{
	
	//============================
	//INSTRUCTIONS
	//============================
	private static String LABEL_ERR = 
		SysProtocol.TPRODUCTLABEL +
		"Status=\"false\" Code=\"%s\"" +
		" RunNumber=\"%s\"  Msg=\"%s\" />";
	
	protected String ip;
	protected String mass;
	protected String[] codeCollection;
	protected String run_number;
	protected String template_name = "";
	protected ActiveDevice active_device = null;
	protected String device_type;
    protected String bin_number;
	
	protected Map label_data;
	protected String label_message = "";
	protected boolean cancel_data_send; //known error occurred, only send error message- set by subclass's 'seLabelData()' method
	                                    //to alert this class to not send label data
	
	private PlTransaction pl_transaction;
        
        protected PlTransaction getPltransaction()
        {
            return this.pl_transaction;
        }
        
	protected String createLabelErrString(String msg)
	{
		return String.format(ProductLabelScan.LABEL_ERR,"n.a.",this.run_number,msg);
		
	}

    public static boolean isNumeric(String num)
    {
        try
        {
            Integer.valueOf(num);
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        return true;

    }
        
         public void send_integration_record() throws Exception
        {
            
        }
        
        public void set_transaction(PlTransaction pl_transaction)
        {
            this.pl_transaction = pl_transaction;
        }
	
	public ProductLabelScan(String ip,String mass, String codeCollection[],MessageInterface msg,String bin_num)
	{
		super(msg);
		this.ip = ip;
		this.mass = mass;
        this.bin_number = bin_num;
        
        if(mass.equals(""))
            this.mass = "0.00";

		this.codeCollection = codeCollection;
		
		this.label_data = new HashMap();
		
		String device_class = this.getClass().getName();
		if(device_class.indexOf("CartonLabelScan") > -1)
			this.device_type = DeviceTypes.CARTON_LABELING;
		else if(device_class.indexOf("PalletLabelScan") > -1)
			this.device_type = DeviceTypes.PALLET_LABELING;	
		else if(device_class.indexOf("RebinLabelScan") > -1)
			this.device_type = DeviceTypes.REBIN_LABELING;			
		
		
	}

    public ProductLabelScan(String ip,String mass, String codeCollection[],String bin_num)
    {

        this.ip = ip;
        this.mass = mass;
        this.bin_number = bin_num;

        if(mass.equals(""))
            this.mass = "0.00";

        this.codeCollection = codeCollection;

        this.label_data = new HashMap();

        String device_class = this.getClass().getName();
        if(device_class.indexOf("CartonLabelScan") > -1)
            this.device_type = DeviceTypes.CARTON_LABELING;
        else if(device_class.indexOf("PalletLabelScan") > -1)
            this.device_type = DeviceTypes.PALLET_LABELING;
        else if(device_class.indexOf("RebinLabelScan") > -1)
            this.device_type = DeviceTypes.REBIN_LABELING;


    }
	
	
	protected boolean isDeviceActive() throws Exception
	{
		try
		{
			this.active_device = DevicesDAO.getActiveDevice(this.codeCollection[0]);
			if(this.active_device != null)
			   this.run_number = active_device.getProduction_run_code();
			else
			   this.run_number = "unknown";
			
			return (active_device != null);
		} catch (Exception ex)
		{
			throw new Exception ("isDeviceActive() method failed. Reported Exception: " + ex.toString());
		}
		
	}
	
	protected String build_label() throws Exception
	{
		try
		{
			String message = this.label_message; //set by subclass
			message = message == null? "OK": message;
			Map label_data = this.label_data;  //set by subclass
			
			StringBuilder label_intruction = new StringBuilder();
			label_intruction.append(SysProtocol.TPRODUCTLABEL + "Status=\"true\" Threading=\"true\" RunNumber=\"");
			label_intruction.append(this.run_number + "\" Code=\"");
			label_intruction.append(this.codeCollection[0] + "\" F0=\"" + this.template_name + "\" ");
			
			for(int i = 1; i <= label_data.size(); i ++)
			{
				String key = "F" + new Integer(i).toString();
				String val = "";
				if(label_data.get(key)!= null)
					val = label_data.get(key).toString();
				
				String field = key + "=\"" + val + "\"";
				label_intruction.append(field + " ");
			}
			
			label_intruction.append("Msg=\"" + message + "\" />");
			return label_intruction.toString();
		} catch(Exception e)
		{
			throw new Exception("The 'build_label' method failed. Reported exception: " + e.toString());
		}
		
	}
	
	protected abstract void setLabelData() throws Exception;
	protected abstract void post_labeling_transaction() throws Exception;
	
	
	/*==========================================================================================
	 *This is a template method that controls the server side process of label printing
	 *Label printing sub classes must implement two methods in this algorythm:
	 *1)setLabelData() {fetch data to be printed and create the F1..F(n) series of field-value 
	 *                  pairs as a Map data structure}
	 *2)post_labeling_transaction() {perform any required db transaction on successful labeling}
	 ========================================================================================= */
	public String processLabelScan()
	{
		try
		{
			if(!isDeviceActive())
				return createLabelErrString("Station: " + this.codeCollection[0] + " not linked");
			
			this.setLabelData(); //subclass must fetch and set label data in Map format (and store in 'label_data' member variable)
			if(this.cancel_data_send == true)
				return this.label_message; //fully formatted label message- sent by subclass
			
			if(this.label_data == null||this.label_data.size() == 0)
				throw new Exception("Label data was not set by label processing transaction(subclass)");
			
			String labeling_instruction = this.build_label();
			this.post_labeling_transaction(); //subclass given opportunity to perform any further db transactions
			return labeling_instruction;
				
		} catch (Exception ex)
		{

			DeviceScan.handle_exception(this.midware_console,"Product Scan exception occurred for ip:" + this.ip + " and station code " + this.codeCollection[0],
				                       ex.toString(),this.getClass().getName() + ".processLabelScan()",this.device_type,0,this.run_number,ex.getStackTrace());
		   
		    return this.createLabelErrString("System error occurred");
		}
		
	}
	
	
}
