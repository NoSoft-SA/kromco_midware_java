/*
 * Bay.java
 *
 * Created on February 19, 2007, 3:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

import za.co.multitier.midware.sys.mwpt.PalletizingAction;
import za.co.multitier.midware.sys.mwpt.PalletizingState;

/**
 *
 * @author Administrator
 */
public class Bay
{
	
	/** Creates a new instance of Bay */
	public Bay()
	{
	}

	private Integer id;

	private String skip_code;

	private String line_code;

	private String bay_code;

	private Integer skip_id;
	
	//------------------------
	//State storage variables
	//------------------------
	
	private PalletizingAction state_current_action;
	
	
	private Pallet state_pallet_setup;
	private ProductionRun state_run;
	private FgSetup state_Fg_Setup;
	private PalletizingCriteria state_criteria;
	private Pallet state_pallet;
	private Pallet pallet_template;
	private boolean state_is_mixed_mode;
	private int state_n_actual_cartons;
	private String state_special_instruction;
	private Carton state_carton_setup;
	private ItemPackProduct state_item_pack;
	
	private PalletizingState active_bay_transaction;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getSkip_code()
	{
		return skip_code;
	}

	public void setSkip_code(String skip_code)
	{
		this.skip_code = skip_code;
	}

	public String getLine_code()
	{
		return line_code;
	}

	public void setLine_code(String line_code)
	{
		this.line_code = line_code;
	}

	public String getBay_code()
	{
		return bay_code;
	}

	public void setBay_code(String bay_code)
	{
		this.bay_code = bay_code;
	}

	public Integer getSkip_id()
	{
		return skip_id;
	}

	public void setSkip_id(Integer skip_id)
	{
		this.skip_id = skip_id;
	}

	public PalletizingAction getState_current_action()
	{
		return state_current_action;
	}

	public Pallet getState_pallet_setup()
	{
		return state_pallet_setup;
	}

	public ProductionRun getState_run()
	{
		return state_run;
	}

	public FgSetup getState_Fg_Setup()
	{
		return state_Fg_Setup;
	}
	
	public void setState_Fg_Setup(FgSetup fg_setup)
	{
		this.state_Fg_Setup = fg_setup;
	}
	

	public PalletizingCriteria getState_criteria()
	{
		return state_criteria;
	}

	public Pallet getState_pallet()
	{
		return state_pallet;
	}

	public boolean isState_is_mixed_mode()
	{
		boolean is_mixed = false;
		if(this.getState_pallet()!= null)
			if(this.getState_pallet().getProcess_status().toUpperCase().equals("PALLETIZING_MIXED"))
				is_mixed = true;
		
		return is_mixed;
			
	}

	public int getState_n_actual_cartons()
	{
		return state_pallet.getCarton_quantity_actual();
	}

	public String getState_special_instruction()
	{
		return state_special_instruction;
	}

	public void setState_special_instruction(String state_special_instruction)
	{
		this.state_special_instruction = state_special_instruction;
	}

	public Carton getState_carton_setup()
	{
		return state_carton_setup;
	}

	public void setState_carton_setup(Carton state_carton_setup)
	{
		this.state_carton_setup = state_carton_setup;
	}

	public void setState_run(ProductionRun state_run)
	{
		this.state_run = state_run;
	}

	public Pallet getStatePallet_template()
	{
		return pallet_template;
	}

	public void setStatePallet_template(Pallet pallet_template)
	{
		this.pallet_template = pallet_template;
	}

	public void setState_pallet(Pallet state_pallet)
	{
		this.state_pallet = state_pallet;
	}

	public void setState_pallet_setup(Pallet state_pallet_setup)
	{
		this.state_pallet_setup = state_pallet_setup;
	}

	private Integer state_cpp;

	public Integer getState_cpp()
	{
		
		return state_cpp;
	}

	public void setState_cpp(Integer state_cpp)
	{
		this.state_cpp = state_cpp;
		if(this.state_pallet != null)
			this.state_pallet.setCpp(state_cpp);
		
	}

	public void setState_criteria(PalletizingCriteria state_criteria)
	{
		this.state_criteria = state_criteria;
	}

	public PalletizingState getActive_bay_transaction()
	{
		return active_bay_transaction;
	}

	public void setActive_bay_transaction(PalletizingState active_bay_transaction)
	{
		this.active_bay_transaction = active_bay_transaction;
	}

	private boolean isNoneState;

	public boolean isIsNoneState()
	{
		return isNoneState;
	}

	public void setIsNoneState(boolean isNoneState)
	{
		this.isNoneState = isNoneState;
	}

	private String skip_ip;

	public String getSkip_ip()
	{
		return skip_ip;
	}

	public void setSkip_ip(String skip_ip)
	{
		this.skip_ip = skip_ip;
	}

	public ItemPackProduct getState_item_pack()
	{
		return state_item_pack;
	}

	public void setState_item_pack(ItemPackProduct state_item_pack)
	{
		this.state_item_pack = state_item_pack;
	}

	private boolean populated;

	public boolean isPopulated()
	{
		return populated;
	}

	public void setPopulated(boolean populated)
	{
		this.populated = populated;
	}

	
	
}
