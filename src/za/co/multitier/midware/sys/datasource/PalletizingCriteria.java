/*
 * PalletizingCriteria.java
 *
 * Created on February 19, 2007, 1:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class PalletizingCriteria
{
	
	/** Creates a new instance of PalletizingCriteria */
	public PalletizingCriteria()
	{
	}

	private Integer id;

	private String carton_setup_code;

	private Integer carton_setup_id;

	private Boolean farm_code;

	private Boolean fg_product_code;

	private Boolean inventory_code;

	private Boolean mark_code;

	private Integer production_run_id;

	private Boolean sell_by_code;

	private Boolean target_market_code;

	private Boolean product_class_code;

	private Boolean grade_code;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getCarton_setup_code()
	{
		return carton_setup_code;
	}

	public void setCarton_setup_code(String carton_setup_code)
	{
		this.carton_setup_code = carton_setup_code;
	}

	public Integer getCarton_setup_id()
	{
		return carton_setup_id;
	}

	public void setCarton_setup_id(Integer carton_setup_id)
	{
		this.carton_setup_id = carton_setup_id;
	}

	public Boolean getFarm_code()
	{
               if(this.farm_code == null)
                   this.farm_code = false;
               
		return farm_code;
	}

	public void setFarm_code(Boolean farm_code)
	{
		this.farm_code = farm_code;
	}

	public Boolean getFg_product_code()
	{ 
                if(this.fg_product_code == null)
                   this.fg_product_code = false;
                
		return fg_product_code;
	}

	public void setFg_product_code(Boolean fg_product_code)
	{
		this.fg_product_code = fg_product_code;
	}

	public Boolean getInventory_code()
	{
                if(this.inventory_code == null)
                   this.inventory_code = false;
                
		return inventory_code;
	}

	public void setInventory_code(Boolean inventory_code)
	{
		this.inventory_code = inventory_code;
	}

	public Boolean getMark_code()
	{
                 if(this.mark_code == null)
                   this.mark_code = false;
                 
		return mark_code;
	}

	public void setMark_code(Boolean mark_code)
	{
		this.mark_code = mark_code;
	}

	public Integer getProduction_run_id()
	{
		return production_run_id;
	}

	public void setProduction_run_id(Integer production_run_id)
	{
		this.production_run_id = production_run_id;
	}

	public Boolean getSell_by_code()
	{
                 if(this.sell_by_code == null)
                   this.sell_by_code = false;
                 
		return sell_by_code;
	}

	public void setSell_by_code(Boolean sell_by_code)
	{
		this.sell_by_code = sell_by_code;
	}

	public Boolean getTarget_market_code()
	{
                if(this.target_market_code == null)
                   this.target_market_code = false;
                
		return target_market_code;
	}

	public void setTarget_market_code(Boolean target_market_code)
	{
		this.target_market_code = target_market_code;
	}

	public Boolean getProduct_class_code()
	{
                if(this.product_class_code == null)
                   this.product_class_code = false;
                
		return product_class_code;
	}

	public void setProduct_class_code(Boolean product_class_code)
	{
		this.product_class_code = product_class_code;
	}

	public Boolean getGrade_code()
	{
                if(this.grade_code == null)
                   this.grade_code = false;
                
		return grade_code;
	}

	public void setGrade_code(Boolean grade_code)
	{
		this.grade_code = grade_code;
	}

    private Boolean units_per_carton;

    public Boolean getUnits_per_carton() {
        
         if(this.units_per_carton == null)
            this.units_per_carton = false;
         
        return units_per_carton;
    }

    public void setUnits_per_carton(Boolean units_per_carton) {
        this.units_per_carton = units_per_carton;
    }

	
	
}
