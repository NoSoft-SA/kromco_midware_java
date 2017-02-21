/*
 * ActiveDevice.java
 *
 * Created on January 25, 2007, 12:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class ActiveDevice
{
	
	/** Creates a new instance of ActiveDevice */
	public ActiveDevice()
	{
	}
	
	//----------------
	//MEMBER VARIABLES
	//----------------
	private int id;
	
	private String active_device_code;
	
	private int device_type_id;
	
	private String device_type_code;

	private String production_run_code;

	private java.util.Date production_run_start;

	private String day_line_batch_number;

	private String line_code;

	private int production_run_id;
	
	//----------------------------------
	//ACCESSORS
	//----------------------------------
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getActive_device_code()
	{
		return active_device_code;
	}

	public void setActive_device_code(String active_device_code)
	{
		this.active_device_code = active_device_code;
	}

	public int getDevice_type_id()
	{
		return device_type_id;
	}

	public void setDevice_type_id(int device_type_id)
	{
		this.device_type_id = device_type_id;
	}

	public String getDevice_type_code()
	{
		return device_type_code;
	}

	public void setDevice_type_code(String device_type_code)
	{
		this.device_type_code = device_type_code;
	}

	public String getProduction_run_code()
	{
		return production_run_code;
	}

	public void setProduction_run_code(String production_run_code)
	{
		this.production_run_code = production_run_code;
	}

	public java.util.Date getProduction_run_start()
	{
		return production_run_start;
	}

	public void setProduction_run_start(java.util.Date production_run_start)
	{
		this.production_run_start = production_run_start;
	}

	public String getDay_line_batch_number()
	{
		return day_line_batch_number;
	}

	public void setDay_line_batch_number(String day_line_batch_number)
	{
		this.day_line_batch_number = day_line_batch_number;
	}

	public String getLine_code()
	{
		return line_code;
	}

	public void setLine_code(String line_code)
	{
		this.line_code = line_code;
	}

	public int getProduction_run_id()
	{
		return production_run_id;
	}

	public void setProduction_run_id(int production_run_id)
	{
		this.production_run_id = production_run_id;
	}

	
	

	

	

	
}
