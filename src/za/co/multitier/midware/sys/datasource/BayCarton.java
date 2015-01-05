/*
 * BayCarton.java
 *
 * Created on February 19, 2007, 3:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class BayCarton
{
	
	/** Creates a new instance of BayCarton */
	public BayCarton()
	{
	}

	private Integer id;

	private Integer carton_id;

	private Integer bay_id;

	private Long pallet_id;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getCarton_id()
	{
		return carton_id;
	}

	public void setCarton_id(Integer carton_id)
	{
		this.carton_id = carton_id;
	}

	public Integer getBay_id()
	{
		return bay_id;
	}

	public void setBay_id(Integer bay_id)
	{
		this.bay_id = bay_id;
	}

	public Long getPallet_id()
	{
		return pallet_id;
	}

	public void setPallet_id(Long pallet_id)
	{
		this.pallet_id = pallet_id;
	}
	
}
