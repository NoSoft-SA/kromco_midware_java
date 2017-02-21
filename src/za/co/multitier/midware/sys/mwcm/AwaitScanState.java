/*
 * AwaitScanState.java
 *
 * Created on January 30, 2007, 2:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwcm;


import java.util.HashMap;
import za.co.multitier.midware.sys.datasource.*;

/**
 *
 * @author Administrator
 */
public class AwaitScanState extends BinTippingState
{
	
	private Bin bin;
	
	/** Creates a new instance of AwaitScanState */
	public AwaitScanState(String ip,String bin_id,BinTippingScan parent) throws Exception
	{
		super(ip,bin_id,parent);
	}
	
	public AwaitScanState(String ip,String bin_id,BinTippingScan parent,Bin bin) throws Exception
	{
		this(ip,bin_id,parent);
		this.bin = bin;
	}
	
	
	public static boolean is_tipped_bin(String bin_id)throws Exception
	{
		 if(BinTippingDAO.getTippedBin(bin_id)	!= null)
			 return true;
		 else if(BinTippingDAO.getInvalidBin(bin_id)!= null)
			 return true;
		 else
			 return false;
			 	
			 // this.parent.active_state = new AwaitAuthState(this.ip,this.bin_id,this.parent,invalid_msg);
	}
	
	
	//--------------------------------------------------------------------------
	//This method tries to find a bin with scanned-in id in the bins table, if
	//found, it creates a new tipped bin and deletes the bin, as well as create
	// a postBox out record - else TRANSISIONS to InvalidBin
	//--------------------------------------------------------------------------
	public void scanBin() throws Exception
	{ 
		//try
		//{
			
			this.bin = BinTippingDAO.getBin(this.bin_id);
			  
			if (this.bin != null && !is_tipped_bin(this.bin_id) )
			{  
				this.bin.setProduction_run_code(this.parent.active_device.getProduction_run_code());
                                this.bin.setProduction_run_id(this.parent.active_device.getProduction_run_id());
                                this.bin.setLine_code(this.parent.active_device.getLine_code());

                                HashMap shift = ProductLabelingDAO.getShift(this.parent.active_device.getLine_code());
                                if(shift != null)
                                    this.bin.setShift_id((Integer)shift.get("id"));


				this.bin.setTipped_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));

                //System.out.println("is non: " + String.valueOf(this.parent.is_non_presort));

                if(this.parent.is_non_presort == true)
                    completeForNonPresort();
                else
                     completeForPresort();

            }
            //invalid bin
			else if (this.parent.is_non_presort == true)
			{
				this.parent.active_state = new InvalidBinState(this.ip,this.bin_id,this.parent);
				this.parent.active_state.scanBin();
            }
                else    //DP: no authorisation state needed. determine reason for error and set err msg instruction
            {
                Bin tipped_bin = BinTippingDAO.getTippedBin(this.bin_id);
                String invalid_msg = BinTippingScan.BIN_NOT_FOUND_MSG_DP + " (" + this.bin_id + ")"; //#3 (default)
                if(tipped_bin	!= null)
                    if(tipped_bin.is_detroyed == false)
                        invalid_msg = BinTippingScan.TIPPED_BIN_DP + " (" + this.bin_id + ")";
                    else
                        invalid_msg = BinTippingScan.DESTROYED_OR_PRESORT_TIPPED_BIN_DP + " (" + this.bin_id + ")";

                else if(BinTippingDAO.getInvalidBin(this.bin_id)!= null)
                    invalid_msg = BinTippingScan.PV_INVALID_BIN_DP + " (" + this.bin_id + ")";

                this.device_instruction = this.parent.createBintipInstruction(false,false,this.parent.active_device.getProduction_run_code(),
                        false,true,invalid_msg);

			}
		//} catch (Exception ex)
		//{
		//	throw new Exception("AwaitScanState's 'scan_bin()' method failed. Reported exception: " + ex.toString());
		//}
		
	}





    private void completeForNonPresort() throws Exception {

            BinTippingDAO.validBinTransaction(this.bin, this.parent.active_device.getProduction_run_code(),null);
            this.device_instruction = this.parent.createBintipInstruction(true,false,this.parent.active_device.getProduction_run_code(),
                    false,true,BinTippingScan.SCAN_NEXT_BIN_MSG);


    }


    private void completeForPresort() throws Exception {
        //System.out.println("LOT: " + this.parent.lotNumber);

        if(this.parent.lotNumber == null||this.parent.lotNumber.trim().isEmpty())
        {

            this.device_instruction = this.parent.createBintipInstruction(true,false,this.parent.active_device.getProduction_run_code(),
                false,true, BinTippingScan.WAITING_FOR_MAF);
        }
        else
        {
            BinTippingDAO.validBinTransaction(this.bin, this.parent.active_device.getProduction_run_code(), this.parent.lotNumber);
            this.device_instruction = this.parent.createBintipInstruction(true,false,this.parent.active_device.getProduction_run_code(),
                    false,true,BinTippingScan.SCAN_NEXT_BIN_MSG);
        }
    }

}
