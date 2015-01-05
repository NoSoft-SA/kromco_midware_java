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
import za.co.multitier.midware.sys.datasource.Bay;
import za.co.multitier.midware.sys.datasource.Carton;
import za.co.multitier.midware.sys.datasource.FgSetup;
import za.co.multitier.midware.sys.datasource.ItemPackProduct;
import za.co.multitier.midware.sys.datasource.Pallet;
import za.co.multitier.midware.sys.datasource.PalletisingDAO;
import za.co.multitier.midware.sys.datasource.PalletizingCriteria;
import za.co.multitier.midware.sys.datasource.ProductionRun;

/**
 *
 * @author Administrator
 */
public class MixedPalletBuildingState extends DefaultState
{
	
	 
	public String getRobotScreen() throws Exception
	{
		build_palletizing_screen();
		return this.robot_screen;
	}
	
	//==============================================================================================
	//The criteria check differs for a mixed pallet, in the following ways:
	//1) fields class and grade are added and, because of this
	//2) (if either of them are checked) the fg_code cannot be used, but rather it's'
	//    composing fields (that is, all the fields of the item pack product, inc class and grade)
	//   and the UPC and CPC
	//===============================================================================================
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
		ItemPackProduct pallet_ipc = bay.getState_item_pack();
		if(pallet_ipc == null)
		{
		   pallet_ipc = PalletisingDAO.getPalletItemPackDetails(pallet.getPallet_number());
		   bay.setState_item_pack(pallet_ipc);
		}
		
		boolean test_failed = false;
		
		if((criteria.getGrade_code() != null && criteria.getProduct_class_code() != null) &&(criteria.getGrade_code()||criteria.getProduct_class_code())) //If either of these 2 is on, we need to decompose fg setup and check it's 'elements' indivudually
		{
			
			//get ipc details for carton
			ItemPackProduct carton_ipc = PalletisingDAO.getCartonItemPackDetails(this.carton_num);
			if(carton_ipc == null)
				throw new Exception("Item pack details for carton(" + String.valueOf(this.carton_num) + ") could not be obtained");
			
			
			//CLASS: OPTIONAL
			
			else if(criteria.getProduct_class_code()== true && !(pallet_ipc.getProduct_class_code().equals(carton_ipc.getProduct_class_code())))
				test_failed = true;
			
			//GRADE: OPTIONAL
			else if(criteria.getGrade_code()== true && !(pallet_ipc.getGrade_code().equals(carton_ipc.getGrade_code()))) 
				test_failed = true;
			
			//COMMODITY: COMPULSORY
			else if(!pallet_ipc.getCommodity_code().equals(carton_ipc.getCommodity_code())) 
				test_failed = true;
			
			//MARKETING VARIETY: COMPULSORY
			else if(!pallet_ipc.getMarketing_variety_code().equals(carton_ipc.getMarketing_variety_code())) 
				test_failed = true;
			
			//ACTUAL COUNT: COMPULSORY
			else if(!pallet_ipc.getActual_count().equals(carton_ipc.getActual_count())) 
				test_failed = true;
			
			//COSMETIC CODE: COMPULSORY
			else if(!pallet_ipc.getCosmetic_code_name().equals(carton_ipc.getCosmetic_code_name())) 
				test_failed = true;
			
			//UPC: COMPULSORY
			else if(!pallet_ipc.getUnit_pack_product_code().equals(carton_ipc.getUnit_pack_product_code())) 
				test_failed = true;
			
			//CPC: COMPULSORY
			else if(!pallet_ipc.getCarton_pack_product_code().equals(carton_ipc.getCarton_pack_product_code())) 
				test_failed = true;
		}
		else
		{
		//FG_CODE: COMPULSORY, IF WE DID NOT HAVE TO DECOMPOSE FG SETUP (NEEDED IF GRADE OR CLASS MIXING IS ALLOWED)
			if(!pallet.getFg_product_code().equals(carton.getFg_product_code())) //fg codes must always match
				test_failed = true;
		}
                
                if(!pallet.getOrganization_code().equals(carton.getOrganization_code())) //fg codes must always match
		 test_failed = true;
		
		//TARGET MARKET
		if (criteria.getTarget_market_code()== true && !(pallet.getTarget_market_code().equals(carton.getTarget_market_code())))
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
		
		//SELL BY CODE
		else if (criteria.getSell_by_code()== true && !(fg_setup.getRetailer_sell_by_code().equals(carton.getSell_by_code())))
			test_failed = true;
		
                  
          //UNITS PER CARTON
	  else if (criteria.getUnits_per_carton()== true && !(carton_setup.getUnits_per_carton().equals(carton.getUnits_per_carton())))
		 test_failed = true;
                
		return !test_failed;
		
	}
	
	/** Creates a new instance of ConfirmRemoveState */
	public MixedPalletBuildingState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
	}
	
	
	//DEFAULT BEHAVIOUR OF SUPERCLASS WILL HANDLE CARTON SCAN AND BUTTON PRESS
	
	
}
