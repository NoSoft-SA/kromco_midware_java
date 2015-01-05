/*
 * ProductionRun.java
 *
 * Created on February 6, 2007, 5:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class ProductionRun
{
	
	/** Creates a new instance of ProductionRun */
	public ProductionRun()
	{
	}

	private int id;

        private String parent_run_code;

	private int production_schedule_id;

	private String production_schedule_name;

	private int day_line_batch_number;

	private int line_id;

	private String line_code;

    private String track_indicator_code;

	private String newField;

	private java.sql.Timestamp start_date_time;

	private java.sql.Timestamp end_date_time;

	private String production_run_status;

	private int production_run_number;

	private String farm_code;

	private String account_code;

	private String puc_code;

	private String production_run_code;

	private String day_line_batch_code;

	private boolean use_alternate_account;

    private String pc_code;

    private String pc_code_num;


	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getProduction_schedule_id()
	{
		return production_schedule_id;
	}

	public void setProduction_schedule_id(int production_schedule_id)
	{
		this.production_schedule_id = production_schedule_id;
	}

	public String getProduction_schedule_name()
	{
		return production_schedule_name;
	}

	public void setProduction_schedule_name(String production_schedule_name)
	{
		this.production_schedule_name = production_schedule_name;
	}

	public int getDay_line_batch_number()
	{
		return day_line_batch_number;
	}

	public void setDay_line_batch_number(int day_line_batch_number)
	{
		this.day_line_batch_number = day_line_batch_number;
	}

	public int getLine_id()
	{
		return line_id;
	}

	public void setLine_id(int line_id)
	{
		this.line_id = line_id;
	}

	public String getLine_code()
	{
		return line_code;
	}

	public void setLine_code(String line_code)
	{
		this.line_code = line_code;
	}

	

	

	public String getNewField()
	{
		return newField;
	}

	public void setNewField(String newField)
	{
		this.newField = newField;
	}

	public java.sql.Timestamp getStart_date_time()
	{
		return start_date_time;
	}

	public void setStart_date_time(java.sql.Timestamp start_date_time)
	{
		this.start_date_time = start_date_time;
	}

	public java.sql.Timestamp getEnd_date_time()
	{
		return end_date_time;
	}

	public void setEnd_date_time(java.sql.Timestamp end_date_time)
	{
		this.end_date_time = end_date_time;
	}

	public String getProduction_run_status()
	{
		return production_run_status;
	}

	public void setProduction_run_status(String production_run_status)
	{
		this.production_run_status = production_run_status;
	}

	public int getProduction_run_number()
	{
		return production_run_number;
	}

	public void setProduction_run_number(int production_run_number)
	{
		this.production_run_number = production_run_number;
	}

	public String getFarm_code()
	{
		return farm_code;
	}

	public void setFarm_code(String farm_code)
	{
		this.farm_code = farm_code;
	}

	public String getAccount_code()
	{
		return account_code;
	}

	public void setAccount_code(String account_code)
	{
		this.account_code = account_code;
	}

	public String getPuc_code()
	{
		return puc_code;
	}

	public void setPuc_code(String puc_code)
	{
		this.puc_code = puc_code;
	}

	public String getProduction_run_code()
	{
		return production_run_code;
	}

	public void setProduction_run_code(String production_run_code)
	{
		this.production_run_code = production_run_code;
	}

	public String getDay_line_batch_code()
	{
		return day_line_batch_code;
	}

	public void setDay_line_batch_code(String day_line_batch_code)
	{
		this.day_line_batch_code = day_line_batch_code;
	}

	public boolean isUse_alternate_account()
	{
		return use_alternate_account;
	}

	public void setUse_alternate_account(boolean use_alternate_account)
	{
		this.use_alternate_account = use_alternate_account;
	}

	

	

	private String batch_code;

	public String getBatch_code()
	{
		return batch_code;
	}

	public void setBatch_code(String batch_code)
	{
		this.batch_code = batch_code;
	}

    /**
     * @return the parent_run_code
     */
    public String getParent_run_code() {
        return parent_run_code;
    }

    /**
     * @param parent_run_code the parent_run_code to set
     */
    public void setParent_run_code(String parent_run_code) {
        this.parent_run_code = parent_run_code;
    }

    public String getPc_code() {
        return pc_code;
    }

    public void setPc_code(String pc_code) {
        this.pc_code = pc_code;
    }

    public String getTrack_indicator_code() {
        return track_indicator_code;
    }

    public void setTrack_indicator_code(String track_indicator_code) {
        this.track_indicator_code = track_indicator_code;
    }

    public String getPc_code_num() {
        return pc_code_num;
    }

    public void setPc_code_num(String pc_code_num) {
        this.pc_code_num = pc_code_num;
    }
}
