/*
 * DevicesDAO.java
 *
 * Created on January 25, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

import com.mindprod.common11.BigDate;
import java.sql.SQLException; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ProductLabelingDAO
{
   
	
	public enum MesObjectTypes {NONE,CARTON,REBIN,PALLET};
	

	public static CartonLink getCartonLink(String station_code,Integer production_run_id) throws Exception
	{
		try
		{

			HashMap params = new HashMap();
			params.put("station_code",station_code);
			params.put("run_id",production_run_id);

			CartonLink link = (CartonLink) DataSource.getSqlMapInstance().queryForObject("getCartonLink", params);
			return link;
		} catch (SQLException ex)
		{
			throw new Exception("Carton link could not be fetched. Reported exception: " + ex);
		}

	}
	
	
	public static HashMap getShift(String line_code) throws Exception
	{
		try
		{
			
			
			HashMap shift = (HashMap) DataSource.getSqlMapInstance().queryForObject("getShift", line_code);
			return shift;
		} catch (SQLException ex)
		{
			throw new Exception("Shift could not be fetched. Reported exception: " + ex);
		}
		
	}
	
        
        public static Rebin getRebin(String station_code,String print_number) throws Exception
	{
		try
		{
			HashMap params = new HashMap();
			params.put("station_code",station_code);
			params.put("print_number",print_number);
			
			Rebin rebin = (Rebin) DataSource.getSqlMapInstance().queryForObject("getRebin", params);
			return rebin;
		} catch (SQLException ex)
		{
			throw new Exception("Rebin could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	public static RebinLink getRebinLink(String station_code,String day_line_seq,Integer production_run_id) throws Exception
	{
		try
		{
			HashMap params = new HashMap();
			params.put("station_code",station_code);
			params.put("run_id",production_run_id);
			params.put("day_line_batch_number",day_line_seq);
			
			RebinLink link = (RebinLink) DataSource.getSqlMapInstance().queryForObject("getRebinLink", params);
			return link;
		} catch (SQLException ex)
		{
			throw new Exception("Rebin link could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	public static Carton getCartonTemplate(int id) throws Exception
	{
		try
		{
			
			Carton template = (Carton) DataSource.getSqlMapInstance().queryForObject("getCartonTemplate", id);
			return template;
		} catch (SQLException ex)
		{
			throw new Exception("Carton template could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	public static Carton getCartonSetup(int id) throws Exception
	{
		try
		{
			
			Carton setup = (Carton) DataSource.getSqlMapInstance().queryForObject("getCartonSetup", id);
			return setup;
		} catch (SQLException ex)
		{
			throw new Exception("Carton setup could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	public static CartonLabelSetup getCartonLabelSetup(int id) throws Exception
	{
		try
		{
			
			CartonLabelSetup label_setup = (CartonLabelSetup) DataSource.getSqlMapInstance().queryForObject("getCartonLabelSetup", id);
			return label_setup;
		} catch (SQLException ex)
		{
			throw new Exception("Carton label setup setup could not be fetched. Reported exception: " + ex);
		}
		
	}
	

	public static String getSummaryGtin()
	{
		
		return "list";
	}
	
	
	public static List getPuc_list_for_pallet(Long pallet_id) throws Exception
	{
		try
		{
			//getTopNinePucs
			return DataSource.getSqlMapInstance().queryForList("getTopNinePucs", pallet_id);
		} catch (SQLException ex)
		{
			throw new Exception("List of top 9 puc's could not be fetched. Reported exception: " + ex);
		}
	}
	
	public static PUC getPUC(String puc_code) throws Exception
	{
		try
		{
			
			return (PUC)DataSource.getSqlMapInstance().queryForObject("getPUC", puc_code);
		} catch (SQLException ex)
		{
			throw new Exception("Eurogap could not be fetched. Reported exception: " + ex);
		}
	}
	
	
	
	public static Carton getCarton(Long carton_number) throws Exception
	{
		try
		{
			
			Carton carton = (Carton) DataSource.getSqlMapInstance().queryForObject("getCarton", carton_number);
			return carton;
		} catch (SQLException ex)
		{
			throw new Exception("Carton  could not be fetched. Reported exception: " + ex);
		}
		
	}
        
        public static Carton getOldestPalletCarton(Long pallet_id) throws Exception
	{
		try
		{
			
			Carton carton = (Carton) DataSource.getSqlMapInstance().queryForObject("getOldestPalletCarton", pallet_id);
			return carton;
		} catch (SQLException ex)
		{
			throw new Exception("Oldest pallet Carton  could not be fetched. Reported exception: " + ex);
		}
		
	}

	//--------
	//PALLET 
	//-------
	public static Pallet getPallet(Long id) throws Exception
	{
		try
		{
			
			Pallet pallet = (Pallet) DataSource.getSqlMapInstance().queryForObject("getPallet", id);
			return pallet;
		} catch (SQLException ex)
		{
			throw new Exception("Pallet could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	public static Pallet getPalletTemplate(int id) throws Exception
	{
		try
		{
			
			Pallet pallet = (Pallet) DataSource.getSqlMapInstance().queryForObject("getPalletTemplate", id);
			return pallet;
		} catch (SQLException ex)
		{
			throw new Exception("Pallet template could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	public static PalletLabelSetup getPalletLabelSetup(int id) throws Exception
	{
		try
		{
			
			PalletLabelSetup label_setup = (PalletLabelSetup) DataSource.getSqlMapInstance().queryForObject("getPalletLabelSetup", id);
			return label_setup;
		} catch (SQLException ex)
		{
			throw new Exception("Pallet label_setup could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	
	public static void setPalletXmitDateTime(Long pallet_id, java.sql.Timestamp curr_date_time) throws Exception
	{
		try
		{
			HashMap params = new HashMap();
			params.put("id",pallet_id);
			params.put("curr_date_time",curr_date_time);
			
			DataSource.getSqlMapInstance().update("setPalletXmitDateTime",params);
			
			
		} catch (SQLException ex)
		{
			throw new Exception("Schedule order quantity could not be updated. Reported exception: " + ex);
		}
		
	}
	
	
	public static String getLinePhc (int line_id) throws Exception
	{
		try
		{
			
			String phc = (String) DataSource.getSqlMapInstance().queryForObject("getLinePhc",line_id);
			return phc;
		} catch (SQLException ex)
		{
			throw new Exception("Phc could not be fetched for line " + String.valueOf(line_id) + ". Reported exception: " + ex);
		}
		
	}
	
	//getPackMatSubtypeForRU
	public static String getPackMatSubtypeForRU (int carton_setup_id) throws Exception
	{
		try
		{
			
			String pm_subtype = (String) DataSource.getSqlMapInstance().queryForObject("getPackMatSubtypeForRU",carton_setup_id);
			return pm_subtype;
		} catch (SQLException ex)
		{
			throw new Exception("Retail unit pack material subtype could not be obtained for carton setup with id: " + String.valueOf(carton_setup_id) + " . Reported exception: " + ex);
		}
		
	}
	
	public static String getRUType (int carton_setup_id) throws Exception
	{
		try
		{
			
			String ru_type = (String) DataSource.getSqlMapInstance().queryForObject("getRUType",carton_setup_id);
			return ru_type;
		} catch (SQLException ex)
		{
			throw new Exception("Retail unit type could not be obtained for carton setup with id: " + String.valueOf(carton_setup_id) + " . Reported exception: " + ex);
		}
		
	}
	
	public static String getCurrentIsoWeek () throws Exception
	{
		try
		{
			BigDate d = new BigDate(new java.util.Date(),TimeZone.getDefault());
		    int iso_week = d.getISOWeekNumber();
			//String iso_week_code = (String) DataSource.getSqlMapInstance().queryForObject("getCurrentIsoWeek");
			return String.valueOf(iso_week);
		} catch (Exception ex)
		{
			throw new Exception("Current iso week could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	
	public static Map getSeasonOrderQtyDetails(String order_number,String season_code) throws Exception
	{
		try
		{
			if(order_number == null || order_number.toUpperCase().equals("N.A."))
                            return null;
                        
            HashMap params = new HashMap();
			params.put("season_code",season_code);
			params.put("order_number",order_number);
                       
			Map order_details = (Map) DataSource.getSqlMapInstance().queryForObject("getSeasonOrderQtyDetails", params);
			return order_details;
		} catch (SQLException ex)
		{
			throw new Exception("Order details could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	
	public synchronized static Long getNextMesObjectId(MesObjectTypes object_type) throws Exception
	{
		MesControlFile ctl = (MesControlFile) DataSource.getSqlMapInstance().queryForObject("getMesCtlSequence", object_type.ordinal());
		ctl.setSequence_number(ctl.getSequence_number()+1);
		DataSource.getSqlMapInstance().update("setMesCtlSequence",ctl);
		return ctl.getSequence_number();
		
	}
	
	
//	public static MesControlFile getMesCtlSequence(MesObjectTypes object_type) throws Exception
//	{
//		try
//		{
//			
//			MesControlFile ctl = (MesControlFile) DataSource.getSqlMapInstance().queryForObject("getMesCtlSequence", object_type.ordinal());
//			return ctl;
//		} catch (SQLException ex)
//		{
//			throw new Exception("Mes control file could not be fetched. Reported exception: " + ex);
//		}
//		
//	}
	
	public static Account getMarketerAccountCodeForFarm(String farm_code,String marketing_org_short_descr) throws Exception
	{
		try
		{
			HashMap params = new HashMap();
			params.put("org_short_description",marketing_org_short_descr);
			params.put("farm_code",farm_code);
		
			
			Account account = (Account) DataSource.getSqlMapInstance().queryForObject("getAccountCodeForFarmAndMarketer", params);
			return account;
		} catch (SQLException ex)
		{
			throw new Exception("Account code could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	//------
	//REBIN
	//------
	

	

	
	

	
	public static ProductionRun getProductionRun (int id) throws Exception
	{
		try
		{
			
			ProductionRun run = (ProductionRun) DataSource.getSqlMapInstance().queryForObject("getProductionRun", id);
			return run;
		} catch (SQLException ex)
		{
			throw new Exception("Production run could not be fetched. Reported exception: " + ex);
		}
		
	}
	


	
//	public synchronized static void setMesCtlSequence(MesControlFile ctl_file) throws Exception
//	{
//		try
//		{
//			
//			DataSource.getSqlMapInstance().update("setMesCtlSequence",ctl_file);
//			
//			
//		} catch (SQLException ex)
//		{
//			throw new Exception("Mes control sequence number could not be updated. Reported exception: " + ex);
//		}
//		
//	}
	
	
        public static void updateCartonRunStats(Carton new_carton) throws Exception
        {
            //-------------------------------------------------------------------------------------
            //Method created to overcome deadlock problem when multiple labeling instances tries to
            //update the production_runs record
            //--------------------------------------------------------------
              DataSource.getSqlMapInstance().update("updateCartonStats",new_carton);
	      //DataSource.getSqlMapInstance().update("addCartonWeight",new_carton);
        }
        
        
	public static void createCarton(Carton new_carton) throws Exception
	{
		//try
		//{
			
			DataSource.getSqlMapInstance().insert("createCarton",new_carton);
                        updateRunStats(new_carton,null,null,null);
                        //updateCartonRunStats(new_carton);
                       // DataSource.getSqlMapInstance().update("incrementCartonsPrinted",new_carton);
		       //DataSource.getSqlMapInstance().update("addCartonWeight",new_carton);
			
		//} catch (SQLException ex)
		//{
		//	throw new Exception("Carton could not be created. Reported exception: " + ex);
		//}
		
	}
	
        public synchronized static void updateRunStats(Carton carton,Rebin rebin,Bin bin,Pallet pallet) throws Exception
        {
            if(carton != null)
                ProductLabelingDAO.updateCartonRunStats(carton);
            else if(rebin != null)
                ProductLabelingDAO.updateRebinRunStats(rebin);
            else if(pallet != null)
                PalletisingDAO.updatePalletRunStats(pallet);
            else if(bin != null)
                BinTippingDAO.updateBinRunStats(bin);
        }
        
	public static void updateRebinRunStats(Rebin new_rebin) throws Exception
        {
              DataSource.getSqlMapInstance().update("updateRebinStats",new_rebin);
	      //DataSource.getSqlMapInstance().update("addRebinWeight",new_rebin);
        }
        
	public static void createRebin(Rebin new_rebin) throws Exception
	{
		try
		{
			
			DataSource.getSqlMapInstance().insert("createRebin",new_rebin);
                        updateRunStats(null,new_rebin,null,null);
                        //updateRebinRunStats(new_rebin);
                        //DataSource.getSqlMapInstance().update("incrementRebinsPrinted",new_rebin);
			//DataSource.getSqlMapInstance().update("addRebinWeight",new_rebin);
                        
			
			
		} catch (SQLException ex)
		{
			throw new Exception("Rebin could not be created. Reported exception: " + ex);
		}
		
	}
        
        public static void updateCartonWeight(Long carton_number,Double weight) throws Exception
	{
		try
		{

                        HashMap params = new HashMap();
			params.put("carton_fruit_nett_mass_actual",weight);
			params.put("carton_number",carton_number);
			DataSource.getSqlMapInstance().update("updateCartonWeight",params);
   

		} catch (SQLException ex)
		{
			throw new Exception("Carton could not be updated. Reported exception: " + ex);
		}

	}

        public static void updateRebin(Rebin rebin) throws Exception
	{
		try
		{
			
			DataSource.getSqlMapInstance().update("updateRebin",rebin);
                         updateRunStats(null,rebin,null,null);
                        //DataSource.getSqlMapInstance().update("incrementRebinsPrinted",rebin);
			//DataSource.getSqlMapInstance().update("addRebinWeight",rebin);
			
			
		} catch (SQLException ex)
		{
			throw new Exception("Rebin could not be updated. Reported exception: " + ex);
		}
		
	}
	
	
	
}
