/*
 * CartonLabelScan.java
 *
 * Created on February 6, 2007, 5:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpl;

import java.util.List;
import java.util.Map;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.datasource.Carton;
import za.co.multitier.midware.sys.datasource.DataSource;
import za.co.multitier.midware.sys.datasource.Pallet;
import za.co.multitier.midware.sys.datasource.PalletLabelSetup;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;
import za.co.multitier.midware.sys.datasource.ProductionRun;

/**
 *
 * @author Administrator
 */
public class PalletLabelScan extends ProductLabelScan
{
	
	/** Creates a new instance of CartonLabelScan */
	public PalletLabelScan(String ip,String mass, String codeCollection[],MessageInterface msg)
	{
		super(ip,mass,codeCollection,msg,null);
	}
 
	
	
	PalletLabelSetup label_setup;
	Pallet pallet;
	ProductionRun run;
	Carton carton;
	
	//--------------------------------------------------------------------------------------------------------
	//Override superclass's version, because the device_code alone is not sufficient in rebin_'s case, since
	// (for rebins only) the same stations are kept for many runs- so the run's batch number ensure uniqueness
	//---------------------------------------------------------------------------------------------------------
	protected boolean isDeviceActive() throws Exception
	{
	   return true; //active device doesn't feature with pallet label printing
		
	}
	
	
	
	protected void setLabelData() throws Exception
	{
		try
		{
			
			this.template_name = "E1";
		    carton = ProductLabelingDAO.getCarton(Long.parseLong(this.codeCollection[0]));
			if(carton == null)
			{
				this.cancel_data_send = true;
				this.label_message = this.createLabelErrString("INVALID_CARTON");
			    return;
			}
			
			
			this.pallet = ProductLabelingDAO.getPallet(carton.getPallet_id());
			Pallet pallet = this.pallet;
			
			label_setup = ProductLabelingDAO.getPalletLabelSetup(pallet.getPallet_label_setup_id());
			if(label_setup == null)
				throw new Exception("No pallet label setup was found for station code: " + codeCollection[0]);
			
			run = ProductLabelingDAO.getProductionRun(pallet.getProduction_run_id());
			if(run == null)
			throw new Exception("Production run with id: " + String.valueOf(pallet.getProduction_run_id()) + " does not exist.");
			
			Map data = this.label_data;
			
			//======================================================
			//TODO: WHAT'S THE STORY WITH REPEATING PALLET NUMBER?
			//SHOULD IT BE CHAR FOR CHAR PRINTING?
			//=====================================================
			
			data.put("F1",label_setup.getVariety_plusten_part_1());
			data.put("F2",label_setup.getCarton_mark_code());
			data.put("F3",label_setup.getGrade_code());
			data.put("F4",label_setup.getActual_size_count_code());
			data.put("F5",label_setup.getOld_pack_code()); //belongs to trade unit setup
			data.put("F6",label_setup.getSell_by_code());
			data.put("F7",run.getBatch_code());
			data.put("F8",label_setup.getTarget_market_code());
			data.put("F9",label_setup.getInventory_code());
			data.put("F10",carton.getPick_reference());
			
			List puc_list = ProductLabelingDAO.getPuc_list_for_pallet(pallet.getId());//query appended the eagp to puc
			
			for(int i = 11; i < 19;i ++)
			{
				String puc = "";
				String egap = "";
				if(puc_list.size() > (i - 11))
				{
					
					 puc = (String)puc_list.get(i - 11);
					
				}
				
				data.put("F" + String.valueOf(i),puc);
					
			}
			
			
			data.put("F19",pallet.getPallet_number().toString());
			data.put("F20","(00)" + pallet.getPallet_number().toString());
			data.put("F21","(00)" + pallet.getPallet_number().toString());
			data.put("F22",pallet.getPallet_number().toString());
			data.put("F23","(00)" + pallet.getPallet_number().toString());
			data.put("F24",carton.getOrganization_code());
			data.put("F25",ProductLabelingDAO.getLinePhc(run.getLine_id()));
			data.put("F26",pallet.getPallet_number().toString());
			data.put("F27","(00)" + pallet.getPallet_number().toString());
			
			String puc_egap = "";
			
			if(puc_list.size() > 7)
			{
				puc_egap = (String)puc_list.get(8);
			

			}
				
			data.put("F28",puc_egap);
			data.put("F29","PHC CODE PART 2"); //TODO: WHAT IS THIS?
			data.put("F30",label_setup.getVariety_plusten_part_2());
			data.put("F31",pallet.getId().toString());
			data.put("F32","(00)" + pallet.getPallet_number().toString());
			
			String gtin = ProductLabelingDAO.getSummaryGtin(); //Different gtins can exist on pallet, so we need to
			String gtin_barcode;
			String gtin_readable;
            //get a 'representative' one from a summary table
		
			if(gtin.trim() != "")
			{
				//user batch number
				 gtin_barcode = "01" + gtin + run.getBatch_code();
				 gtin_readable = "(01)" + gtin + run.getBatch_code();
			}
			else
			{
				gtin_barcode = "0110" + run.getBatch_code();
				gtin_readable = "(01)(10)" + run.getBatch_code();
			}
				
			
		
			data.put("F33",gtin_barcode);
			data.put("F34",gtin_readable);
			
			
			
			
		} catch (Exception ex)
		{
			throw new Exception("Label data could not be gathered. Reported exception: " + ex.toString());
		} 
		
	}
	
	
   //==================================================================
	//This method does the following as an atomic transaction;
	//1) create carton
	//2) update order_quantity_produced on carton setup
	//3) update mes_ctl_sequence
	//=================================================================
	
	protected void post_labeling_transaction() throws Exception
	{
		try
		{
			DataSource.getSqlMapInstance().startTransaction();
			ProductLabelingDAO.setPalletXmitDateTime(this.pallet.getId(),new java.sql.Timestamp(new java.util.Date().getTime()));
			
			DataSource.getSqlMapInstance().commitTransaction();
			
			
		} catch (Exception ex)
		{
			throw new Exception("carton label post printing transaction failed. Reported Exception: " + ex.toString());
		}
		
		finally
		{
			//DataSource.getSqlMapInstance().endTransaction();
		}
		
		
	}
	
}
