/*
 * CartonLabelScan.java
 *
 * Created on February 6, 2007, 5:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
//hello 
package za.co.multitier.midware.sys.mwpl;

import java.util.HashMap;
import java.util.Map;
import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.datasource.DataSource;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;
import za.co.multitier.midware.sys.datasource.ProductionRun;
import za.co.multitier.midware.sys.datasource.Rebin;

/**
 *
 * @author Administrator
 */
public class RebinLabelScan extends ProductLabelScan {

    /** Creates a new instance of CartonLabelScan */
    public RebinLabelScan(String ip, String mass, String codeCollection[], MessageInterface msg,String user) {
        super(ip, mass, codeCollection, msg,null);
        this.username = user;
    }
    private String packer;
    private String rebin_num;
    private String username;
    Rebin rebin;
//        private String packer;
//	private Long rebin_num;
//	private String username;
    //RebinLink rebin_link;
    //RebinLabelSetup label_setup;
    //Rebin rebin_template;
    ProductionRun run;

    protected boolean isDeviceActive() throws Exception {
        try {
            //Code collection now only contains one item, format is:
            //<binfill_station_code>_<6 char unique num>
            String print_num = null;
            if (this.codeCollection.length == 1) {
                String[] vals = this.codeCollection[0].split("_");
                this.codeCollection[0] = vals[0];
                print_num = vals[1];
            } else {
                print_num = this.codeCollection[1];
            }

            this.rebin = ProductLabelingDAO.getRebin(this.codeCollection[0], print_num);
            if (this.rebin != null) {
                this.run_number = rebin.getProduction_run_code();
            }

            return (this.rebin != null);
        } catch (Exception ex) {
            throw new Exception("isDeviceActive() method failed. Reported Exception: " + ex.toString());
        }

    }
    //--------------------------------------------------------------------------------------------------------
    //Override superclass's version, because the device_code alone is not sufficient in rebin_'s case, since
    // (for rebins only) the same stations are kept for many runs- so the run's batch number ensure uniqueness
    //---------------------------------------------------------------------------------------------------------
//	protected boolean isDeviceActive() throws Exception
//	{ 
//		try
//		{
//                       //Code collection now only contains one item, format is:
//                      //<binfill_station_code>_<6 char unique num>
//                     if(this.codeCollection[1].trim().equals(""))
//                     {
//                       String [] vals = this.codeCollection[0].split("_");
//                       this.codeCollection[0] = vals[0];
//                       this.codeCollection[1] = vals[1];
//                     }
//		    
//			this.active_device = DevicesDAO.getActiveDevice(this.codeCollection[0],this.codeCollection[1]);
//	        if(this.active_device != null)
//				this.run_number = active_device.getProduction_run_code();
//			
//			return (active_device != null);
//		} catch (Exception ex)
//		{
//			throw new Exception ("isDeviceActive() method failed. Reported Exception: " + ex.toString());
//		}
//		
//	}

    
    protected void setLabelData() throws Exception {
        try {

            this.template_name = "E3";

            if (this.rebin == null) {
                throw new Exception("No printable rebin was found for station code: " + codeCollection[0]);
            }


            this.rebin_num = this.rebin.getRebin_number();

            //Print title rule
            //if retail unit pack material is of sub type bag, punnet or jumble: no "COUNT:" print title, else print "COUNT:"
            Map data = this.label_data;

            String str_num = String.valueOf(this.rebin_num);

            String farm = this.rebin.getFarm_id();
            if (farm == null || farm.equals("")) {
                farm = this.rebin.getLine_code().toString();
            }
                
            data.put("F1", "");
            data.put("F2", this.rebin.getRmt_description());
            data.put("F3", str_num);
            data.put("F4", str_num);
            data.put("F5", this.rebin.getTrack_indicator_description());
            data.put("F6", farm);
            data.put("F7", str_num);
            data.put("F8", this.rebin.getPack_material_product_code());

            Double gross_weight = Double.parseDouble(this.mass);


            Double bin_mass = this.rebin.getBin_weight();
            this.rebin.setWeight(DeviceScan.round(gross_weight - bin_mass,1));

            data.put("F9", this.rebin.getWeight().toString());
            data.put("F10", this.rebin.getPc_code() + " " + this.rebin.getRipe_point_code());
            data.put("F11", this.rebin.getSize_code());
            data.put("F12", this.rebin.getClass_code());
            data.put("F13", DataSource.toFriendlyDate(this.rebin.getCreated_on()));
            data.put("F14", str_num);
            data.put("F15", this.rebin.getLine_code());
            data.put("F16", this.rebin.getBinfill_station_code());
            data.put("F17", String.valueOf(this.rebin.getProduction_run_id()));
            data.put("F18", farm);
            data.put("F19", this.rebin.getPack_material_product_code());
            data.put("F20", this.rebin.getClass_code());
            data.put("F21", DataSource.toFriendlyDate(this.rebin.getCreated_on()));
            data.put("F22", this.rebin.getWeight().toString());
            data.put("F23", this.rebin.getPc_code()+ " " + this.rebin.getRipe_point_code());
            data.put("F24", this.rebin.getBinfill_station_code());
            data.put("F25", String.valueOf(this.rebin.getProduction_run_id()));
            data.put("F26", this.rebin.getSize_code());
            data.put("F27", this.rebin.getLine_code());
            data.put("F28", str_num);
            data.put("F29", str_num);
            data.put("F30", this.rebin.getRmt_description());
            data.put("F31", farm);
            data.put("F32", this.rebin.getPack_material_product_code());
            data.put("F33", this.rebin.getClass_code());
            data.put("F34", DataSource.toFriendlyDate(this.rebin.getCreated_on()));
            data.put("F35", this.rebin.getWeight().toString());
            data.put("F36", this.rebin.getPc_code()+ " " + this.rebin.getRipe_point_code());
            data.put("F37", this.rebin.getBinfill_station_code());
            data.put("F38", String.valueOf(this.rebin.getProduction_run_id()));
            data.put("F39", this.rebin.getSize_code());
            data.put("F40", this.rebin.getLine_code());
            data.put("F41", this.rebin.getRmt_description());

           // this.username = this.getLoggedOnUsername();
            //data.put("F11", username);


        } catch (Exception ex) {
            throw new Exception("Label data could not be gathered. Reported exception: " + ex.toString());
        }

    }

    protected void setLabelDataOld() throws Exception {
        try {

            this.template_name = "E4";

            if (this.rebin == null) {
                throw new Exception("No printable rebin was found for station code: " + codeCollection[0]);
            }


            this.rebin_num = this.rebin.getRebin_number();

            //Print title rule
            //if retail unit pack material is of sub type bag, punnet or jumble: no "COUNT:" print title, else print "COUNT:"
            Map data = this.label_data;

            String str_num = String.valueOf(this.rebin_num);

            String farm = this.rebin.getFarm_id();
            if (farm == null || farm.equals("")) {
                farm = this.rebin.getLine_code().toString();
            }

            data.put("F1", this.rebin.getRmt_description());
            data.put("F2", this.rebin.getRmt_code());
            data.put("F3", this.rebin.getClass_code());
            data.put("F4", this.rebin.getSize_code());
            data.put("F5", this.rebin.getPc_code()+ " " + this.rebin.getRipe_point_code());
            data.put("F6", farm);
            data.put("F7", this.rebin_num.toString());

            Double gross_weight = Double.parseDouble(this.mass);
            Double bin_mass = this.rebin.getBin_weight();
            this.rebin.setWeight(gross_weight - bin_mass);
            data.put("F8", this.rebin.getWeight().toString());
            data.put("F9", this.rebin.getLine_code());
            data.put("F10", this.getFormattedNowDate());
            this.username = this.username;
            data.put("F11", username);


        } catch (Exception ex) {
            throw new Exception("Label data could not be gathered. Reported exception: " + ex.toString());
        }

    }
   
    public void send_integration_record() throws Exception {
        try {
            DeviceScan.send_integration_record("rebin_new", this.rebin.getRebin_number(), "Rebin");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    protected void post_labeling_transaction() throws Exception {
        try {
            DataSource.getSqlMapInstance().startTransaction();

            //-------------------------------------------------
            //Field we need to update:
            //-> user_name
            //-> transaction_date
            //-> date_time_erp_xmit
            //-> weight
            //-> rebin_status
            //--------------------------------------------------
//			
            this.rebin.setUsername(this.username);

            this.rebin.setRebin_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
            //this.rebin.setDate_time_erp_xmit(new java.sql.Timestamp(new java.util.Date().getTime()));

//            HashMap shift = ProductLabelingDAO.getShift(this.rebin.getProduction_run_code());
//            if (shift != null) {
//                this.rebin.setShift_id((Integer) shift.get("id"));
//            }

            ProductLabelingDAO.updateRebin(this.rebin);
            send_integration_record();

            //DataSource.getSqlMapInstance().commitTransaction();
            


            this.getPltransaction().set_do_db_transactio(true);


        } catch (Exception ex) {
            throw new Exception("rebin label post printing transaction failed. Reported Exception: " + ex.toString());
        } finally {
            //DataSource.getSqlMapInstance().endTransaction();
        }


    }
    //-----------------------------
    //ORIGINAL IMPLEMENTATION
    //-----------------------------
//	protected void setLabelData() throws Exception
//	{
//		try
//		{
//			
//			this.template_name = "E4";
//			rebin_link = ProductLabelingDAO.getRebinLink(this.codeCollection[0],codeCollection[1],this.active_device.getProduction_run_id());
//			if(rebin_link == null)
//				throw new Exception("No rebin link was found for station code: " + codeCollection[0] );
//			
//			if(rebin_link.getCarton_label_setup_id() != null)
//			{
//				this.label_message = "CARTON PRODUCT";
//			    return;
//			}
//			
//			label_setup = ProductLabelingDAO.getRebinLabelSetup(rebin_link.getRebin_label_setup_id());
//			if(label_setup == null)
//				throw new Exception("No rebin label setup was found for station code: " + codeCollection[0]);
//			
//			rebin_template = ProductLabelingDAO.getRebinTemplate(rebin_link.getRebin_template_id());
//			if(rebin_template == null)
//				throw new Exception("No rebin template was found for station code: " + codeCollection[0]);
//			
//			run = ProductLabelingDAO.getProductionRun(rebin_link.getProduction_run_id());
//			if(run == null)
//			throw new Exception("Production run with id: " + String.valueOf(rebin_link.getProduction_run_id()) + " does not exist.");
//			
//			this.rebin_num = ProductLabelingDAO.getNextMesObjectId(ProductLabelingDAO.MesObjectTypes.REBIN);
//			
//			//Print title rule
//			//if retail unit pack material is of sub type bag, punnet or jumble: no "COUNT:" print title, else print "COUNT:"
//			Map data = this.label_data;
//			
//			String str_num = String.valueOf(this.rebin_num);
//			
//			String farm = run.getFarm_code();
//			if(farm == null||farm.equals(""))
//				farm = run.getLine_code().toString();
//			
//			data.put("F1",label_setup.getRmt_description());
//			data.put("F2",label_setup.getRmt_code());
//			data.put("F3",label_setup.getClass_code());
//			data.put("F4",label_setup.getSize_code());
//			data.put("F5",label_setup.getPc_code());
//			data.put("F6",farm);
//			data.put("F7",this.rebin_num.toString());
//			Double gross_weight = Double.parseDouble(this.mass);
//			Double bin_mass = rebin_template.getBin_weight();
//			rebin_template.setWeight(gross_weight - bin_mass);
//			data.put("F8",rebin_template.getWeight().toString());
//			data.put("F9",run.getLine_code());
//			data.put("F10",this.getFormattedNowDate());
//			this.username = this.getLoggedOnUsername();
//			data.put("F11",username);
//			
//		
//		} catch (Exception ex)
//		{
//			throw new Exception("Label data could not be gathered. Reported exception: " + ex.toString());
//		} 
//		
//	}
//	protected void post_labeling_transaction() throws Exception
//	{
//		try
//		{
//			DataSource.getSqlMapInstance().startTransaction();
//			
//			rebin_template.setProduction_run_id(run.getId());
//			rebin_template.setBinfill_station_code(this.codeCollection[0]);
//			rebin_template.setProduction_run_code(run.getProduction_run_code());
//			//rebin_template.setWeight(Double.parseDouble(this.mass));
//			
//			rebin_template.setFarm_id(run.getFarm_code());
//			rebin_template.setRebin_status("complete"); 
//			rebin_template.setErp_bin_type(rebin_template.getProduct_code_pm_bintype());
//			rebin_template.setRebin_number(this.rebin_num);
//			rebin_template.setUsername(this.username);
//			String orchard_code =  run.getFarm_code() + "_" + rebin_template.getTrack_indicator_code ();
//			rebin_template.setOrchard_code(orchard_code);
//			rebin_template.setDate_time_created(new java.sql.Timestamp(new java.util.Date().getTime()));
//			rebin_template.setTransaction_date(new java.sql.Timestamp(new java.util.Date().getTime()));
//			
//                        String iso_week = ProductLabelingDAO.getCurrentIsoWeek();
//                        rebin_template.setIso_week_code(ProductLabelingDAO.getCurrentIsoWeek());       
//			ProductLabelingDAO.createRebin(rebin_template);
//			
//			DataSource.getSqlMapInstance().commitTransaction();
//                        DeviceScan.send_integration_record("rebin_new",rebin_template.getId().toString(),"Rebin");
//			
//			
//			
//			
//			
//		} catch (Exception ex)
//		{
//			throw new Exception("rebin label post printing transaction failed. Reported Exception: " + ex.toString());
//		}
//		
//		finally
//		{
//			DataSource.getSqlMapInstance().endTransaction();
//		}
//		
//		
//	}
//	
}
