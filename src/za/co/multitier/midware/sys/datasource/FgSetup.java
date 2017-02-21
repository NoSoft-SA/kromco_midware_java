/*
 * FgSetup.java
 *
 * Created on February 19, 2007, 1:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class FgSetup
{
	
	/** Creates a new instance of FgSetup */
	public FgSetup()
	{
	}

	private Integer id;

	private String retailer_sell_by_code;

	private String inventory_code;

	private String fg_product_code;

	private String target_market;

	private String gtin;

	private String remarks;

	private Integer carton_setup_id;

	private Integer fg_product_id;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getRetailer_sell_by_code()
	{
		return retailer_sell_by_code;
	}

	public void setRetailer_sell_by_code(String retailer_sell_by_code)
	{
		this.retailer_sell_by_code = retailer_sell_by_code;
	}

	public String getInventory_code()
	{
		return inventory_code;
	}

	public void setInventory_code(String inventory_code)
	{
		this.inventory_code = inventory_code;
	}

	public String getFg_product_code()
	{
		return fg_product_code;
	}

	public void setFg_product_code(String fg_product_code)
	{
		this.fg_product_code = fg_product_code;
	}

	public String getTarget_market()
	{
		return target_market;
	}

	public void setTarget_market(String target_market)
	{
		this.target_market = target_market;
	}

	public String getGtin()
	{
		return gtin;
	}

	public void setGtin(String gtin)
	{
		this.gtin = gtin;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public Integer getCarton_setup_id()
	{
		return carton_setup_id;
	}

	public void setCarton_setup_id(Integer carton_setup_id)
	{
		this.carton_setup_id = carton_setup_id;
	}

	public Integer getFg_product_id()
	{
		return fg_product_id;
	}

	public void setFg_product_id(Integer fg_product_id)
	{
		this.fg_product_id = fg_product_id;
	}
	
}
