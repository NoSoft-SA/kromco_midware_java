/*
 * RebinLabelSetup.java
 *
 * Created on February 5, 2007, 4:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class RebinLabelSetup
{
	
	/** Creates a new instance of RebinLabelSetup */
	public RebinLabelSetup()
	{
	}

	private Integer id;

	private String class_code;

	private String size_code;

	private String pc_code;

	private String puc;

	private String bin_id;

	private Integer bin_weight_kg;

	private String line_code;

	private java.sql.Timestamp transaction_date;

	private String user_name;

	private String binfill_station_code;

	private String rebin_label_station_code;

	private Boolean default_mark_boolean;

	private Integer rebin_setup_id;

	private String rmt_description;

	private String rmt_code;

	private String label_code;

	private Integer label_id;

	private String label_type;

	private String printer_format_code;

	private String farm_code;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getClass_code()
	{
		return class_code;
	}

	public void setClass_code(String class_code)
	{
		this.class_code = class_code;
	}

	public String getSize_code()
	{
		return size_code;
	}

	public void setSize_code(String size_code)
	{
		this.size_code = size_code;
	}

	public String getPc_code()
	{
		if(pc_code == null)
			return "";
		else
		return pc_code;
	}

	public void setPc_code(String pc_code)
	{
		this.pc_code = pc_code;
	}

	public String getPuc()
	{
		return puc;
	}

	public void setPuc(String puc)
	{
		this.puc = puc;
	}

	public String getBin_id()
	{
		return bin_id;
	}

	public void setBin_id(String bin_id)
	{
		this.bin_id = bin_id;
	}

	public Integer getBin_weight_kg()
	{
		return bin_weight_kg;
	}

	public void setBin_weight_kg(Integer bin_weight_kg)
	{
		this.bin_weight_kg = bin_weight_kg;
	}

	public String getLine_code()
	{
		return line_code;
	}

	public void setLine_code(String line_code)
	{
		this.line_code = line_code;
	}

	public java.sql.Timestamp getTransaction_date()
	{
		return transaction_date;
	}

	public void setTransaction_date(java.sql.Timestamp transaction_date)
	{
		this.transaction_date = transaction_date;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getBinfill_station_code()
	{
		return binfill_station_code;
	}

	public void setBinfill_station_code(String binfill_station_code)
	{
		this.binfill_station_code = binfill_station_code;
	}

	public String getRebin_label_station_code()
	{
		return rebin_label_station_code;
	}

	public void setRebin_label_station_code(String rebin_label_station_code)
	{
		this.rebin_label_station_code = rebin_label_station_code;
	}

	public Boolean isDefault_mark_boolean()
	{
		return default_mark_boolean;
	}

	public void setDefault_mark_boolean(Boolean default_mark_boolean)
	{
		this.default_mark_boolean = default_mark_boolean;
	}

	public Integer getRebin_setup_id()
	{
		return rebin_setup_id;
	}

	public void setRebin_setup_id(Integer rebin_setup_id)
	{
		this.rebin_setup_id = rebin_setup_id;
	}

	public String getRmt_description()
	{
		return rmt_description;
	}

	public void setRmt_description(String rmt_description)
	{
		this.rmt_description = rmt_description;
	}

	public String getRmt_code()
	{
		return rmt_code;
	}

	public void setRmt_code(String rmt_code)
	{
		this.rmt_code = rmt_code;
	}

	public String getLabel_code()
	{
		return label_code;
	}

	public void setLabel_code(String label_code)
	{
		this.label_code = label_code;
	}

	public Integer getLabel_id()
	{
		return label_id;
	}

	public void setLabel_id(Integer label_id)
	{
		this.label_id = label_id;
	}

	public String getLabel_type()
	{
		return label_type;
	}

	public void setLabel_type(String label_type)
	{
		this.label_type = label_type;
	}

	public String getPrinter_format_code()
	{
		return printer_format_code;
	}

	public void setPrinter_format_code(String printer_format_code)
	{
		this.printer_format_code = printer_format_code;
	}

	public String getFarm_code()
	{
		return farm_code;
	}

	public void setFarm_code(String farm_code)
	{
		this.farm_code = farm_code;
	}
	
}
