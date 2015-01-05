/*
 * ItemPackProduct.java
 *
 * Created on February 27, 2007, 6:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class ItemPackProduct
{
	
	/** Creates a new instance of ItemPackProduct */
	public ItemPackProduct()
	{
	}

	private String product_class_code;

	private String commodity_code;

	private String cosmetic_code_name;

	private String grade_code;

	private String marketing_variety_code;

	private Integer actual_count;

	private String unit_pack_product_code;

	private String carton_pack_product_code;

	public String getProduct_class_code()
	{
		return product_class_code;
	}

	public void setProduct_class_code(String product_class_code)
	{
		this.product_class_code = product_class_code;
	}

	public String getCommodity_code()
	{
		return commodity_code;
	}

	public void setCommodity_code(String commodity_code)
	{
		this.commodity_code = commodity_code;
	}

	public String getCosmetic_code_name()
	{
		return cosmetic_code_name;
	}

	public void setCosmetic_code_name(String cosmetic_code_name)
	{
		this.cosmetic_code_name = cosmetic_code_name;
	}

	public String getGrade_code()
	{
		return grade_code;
	}

	public void setGrade_code(String grade_code)
	{
		this.grade_code = grade_code;
	}

	public String getMarketing_variety_code()
	{
		return marketing_variety_code;
	}

	public void setMarketing_variety_code(String marketing_variety_code)
	{
		this.marketing_variety_code = marketing_variety_code;
	}

	public Integer getActual_count()
	{
		return actual_count;
	}

	public void setActual_count(Integer actual_count)
	{
		this.actual_count = actual_count;
	}

	public String getUnit_pack_product_code()
	{
		return unit_pack_product_code;
	}

	public void setUnit_pack_product_code(String upc)
	{
		this.unit_pack_product_code = upc;
	}

	public String getCarton_pack_product_code()
	{
		return carton_pack_product_code;
	}

	public void setCarton_pack_product_code(String cpc)
	{
		this.carton_pack_product_code = cpc;
	}
	
}
