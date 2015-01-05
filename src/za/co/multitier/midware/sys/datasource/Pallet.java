/*
 * Pallet.java
 *
 * Created on February 9, 2007, 9:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class Pallet
{
	
	/** Creates a new instance of Pallet */
	public Pallet()
	{
	}

	private Long id;

        private Long ppecb_inspection_id;

	private Long pallet_number;

	private String fg_product_code;

	private String build_status;

	private String quarantine;

	private String inspection_code;

	private String consigment_note_number;

	private String final_status_code;

        private Integer load_detail_id;


	private java.sql.Timestamp oldest_pack_date_time;

	private java.sql.Timestamp print_status;

	private String size_count_code;

	private String carton_mark_code;

	private String target_market_code;

	private String grade_code;

	private String marketing_variety_code;

	private String old_pack_code;

	private String thermocouple;

	private String pallet_label_code;

	private String qc_status_code;

	private Integer carton_quantity_actual;

	private String pi;

	private String country_origin_code;

	private String inventory_code;

	private String pick_reference_code;

	private String pc_code;

	private String commodity_code;

	private String pallet_format_product_code;

	private String organization_code;

	private String label_standard_code;

	private String inspect_type_code;

	private String cold_store_code;

	private String group_id;

	private String erp_cultivar;

	private String quality_group_code;

	private String class_code;

	private java.sql.Timestamp date_time_created;
	
	private java.sql.Timestamp date_time_completed;

	private java.sql.Timestamp date_time_erp_xmit;

	private Integer pallet_type_id;

	private Integer pallet_format_product_id;

	private Integer production_run_id;

	private Integer pallet_label_setup_id;

	private Integer pallet_template_id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getPallet_number()
	{
		return pallet_number;
	}

	public void setPallet_number(Long pallet_number)
	{
		this.pallet_number = pallet_number;
	}

	public String getFg_product_code()
	{
		return fg_product_code;
	}

	public void setFg_product_code(String fg_product_code)
	{
		this.fg_product_code = fg_product_code;
	}

	public String getBuild_status()
	{
		return build_status;
	}

	public void setBuild_status(String build_status)
	{
		this.build_status = build_status;
	}

	public String getQuarantine()
	{
		return quarantine;
	}

	public void setQuarantine(String quarantine)
	{
		this.quarantine = quarantine;
	}

	public String getInspection_code()
	{
		return inspection_code;
	}

	public void setInspection_code(String inspection_code)
	{
		this.inspection_code = inspection_code;
	}

	public String getConsigment_note_number()
	{
		return consigment_note_number;
	}

	public void setConsigment_note_number(String consigment_note_number)
	{
		this.consigment_note_number = consigment_note_number;
	}

	public String getFinal_status_code()
	{
		return final_status_code;
	}

	public void setFinal_status_code(String final_status_code)
	{
		this.final_status_code = final_status_code;
	}

	

	public java.sql.Timestamp getOldest_pack_date_time()
	{
		return oldest_pack_date_time;
	}

	public void setOldest_pack_date_time(java.sql.Timestamp oldest_pack_date_time)
	{
		this.oldest_pack_date_time = oldest_pack_date_time;
	}

	public java.sql.Timestamp getPrint_status()
	{
		return print_status;
	}

	public void setPrint_status(java.sql.Timestamp print_status)
	{
		this.print_status = print_status;
	}

	public String getSize_count_code()
	{
		return size_count_code;
	}

	public void setSize_count_code(String size_count_code)
	{
		this.size_count_code = size_count_code;
	}

	public String getCarton_mark_code()
	{
		return carton_mark_code;
	}

	public void setCarton_mark_code(String carton_mark_code)
	{
		this.carton_mark_code = carton_mark_code;
	}

	public String getTarget_market_code()
	{
		return target_market_code;
	}

	public void setTarget_market_code(String target_market_code)
	{
		this.target_market_code = target_market_code;
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

	public String getOld_pack_code()
	{
		return old_pack_code;
	}

	public void setOld_pack_code(String old_pack_code)
	{
		this.old_pack_code = old_pack_code;
	}

	public String getThermocouple()
	{
		return thermocouple;
	}

	public void setThermocouple(String thermocouple)
	{
		this.thermocouple = thermocouple;
	}

	public String getPallet_label_code()
	{
		return pallet_label_code;
	}

	public void setPallet_label_code(String pallet_label_code)
	{
		this.pallet_label_code = pallet_label_code;
	}

	public String getQc_status_code()
	{
		return qc_status_code;
	}

	public void setQc_status_code(String qc_status_code)
	{
		this.qc_status_code = qc_status_code;
	}

	public Integer getCarton_quantity_actual()
	{
		return carton_quantity_actual;
	}

	public void setCarton_quantity_actual(Integer carton_quantity_actual)
	{
		this.carton_quantity_actual = carton_quantity_actual;
	}

	public String getPi()
	{
		return pi;
	}

	public void setPi(String pi)
	{
		this.pi = pi;
	}

	public String getCountry_origin_code()
	{
		return country_origin_code;
	}

	public void setCountry_origin_code(String country_origin_code)
	{
		this.country_origin_code = country_origin_code;
	}

	public String getInventory_code()
	{
		return inventory_code;
	}

	public void setInventory_code(String inventory_code)
	{
		this.inventory_code = inventory_code;
	}

	public String getPick_reference_code()
	{
		return pick_reference_code;
	}

	public void setPick_reference_code(String pick_reference_code)
	{
		this.pick_reference_code = pick_reference_code;
	}

	public String getPc_code()
	{
		return pc_code;
	}

	public void setPc_code(String pc_code)
	{
		this.pc_code = pc_code;
	}

	public String getCommodity_code()
	{
		return commodity_code;
	}

	public void setCommodity_code(String commodity_code)
	{
		this.commodity_code = commodity_code;
	}

	public String getPallet_format_product_code()
	{
		return pallet_format_product_code;
	}

	public void setPallet_format_product_code(String pallet_format_product_code)
	{
		this.pallet_format_product_code = pallet_format_product_code;
	}

	public String getOrganization_code()
	{
		return organization_code;
	}

	public void setOrganization_code(String organization_code)
	{
		this.organization_code = organization_code;
	}

	public String getLabel_standard_code()
	{
		return label_standard_code;
	}

	public void setLabel_standard_code(String label_standard_code)
	{
		this.label_standard_code = label_standard_code;
	}

	public String getInspect_type_code()
	{
		return inspect_type_code;
	}

	public void setInspect_type_code(String inspect_type_code)
	{
		this.inspect_type_code = inspect_type_code;
	}

	public String getGroup_id()
	{
		return group_id;
	}

	public void setGroup_id(String group_id)
	{
		this.group_id = group_id;
	}

	public String getErp_cultivar()
	{
		return erp_cultivar;
	}

	public void setErp_cultivar(String erp_cultivar)
	{
		this.erp_cultivar = erp_cultivar;
	}

	public String getQuality_group_code()
	{
		return quality_group_code;
	}

	public void setQuality_group_code(String quality_group_code)
	{
		this.quality_group_code = quality_group_code;
	}

	public String getClass_code()
	{
		return class_code;
	}

	public void setClass_code(String class_code)
	{
		this.class_code = class_code;
	}

	public java.sql.Timestamp getDate_time_created()
	{
		return date_time_created;
	}

	public void setDate_time_created(java.sql.Timestamp date_time_created)
	{
		this.date_time_created = date_time_created;
	}

	public java.sql.Timestamp getDate_time_erp_xmit()
	{
		return date_time_erp_xmit;
	}

	public void setDate_time_erp_xmit(java.sql.Timestamp date_time_erp_xmit)
	{
		this.date_time_erp_xmit = date_time_erp_xmit;
	}

	public Integer getPallet_type_id()
	{
		return pallet_type_id;
	}

	public void setPallet_type_id(Integer pallet_type_id)
	{
		this.pallet_type_id = pallet_type_id;
	}

	public Integer getPallet_format_product_id()
	{
		return pallet_format_product_id;
	}

	public void setPallet_format_product_id(Integer pallet_format_product_id)
	{
		this.pallet_format_product_id = pallet_format_product_id;
	}

	public Integer getProduction_run_id()
	{
		return production_run_id;
	}

	public void setProduction_run_id(Integer production_run_id)
	{
		this.production_run_id = production_run_id;
	}

	public Integer getPallet_label_setup_id()
	{
		return pallet_label_setup_id;
	}

	public void setPallet_label_setup_id(Integer pallet_label_setup_id)
	{
		this.pallet_label_setup_id = pallet_label_setup_id;
	}

	public Integer getPallet_template_id()
	{
		return pallet_template_id;
	}

	public void setPallet_template_id(Integer pallet_template_id)
	{
		this.pallet_template_id = pallet_template_id;
	}

	private Integer cpp;

	public Integer getCpp()
	{
		return cpp;
	}

	public void setCpp(Integer cpp)
	{
		this.cpp = cpp;
	}

	private String process_status;

	public String getProcess_status()
	{
		return process_status;
	}

	public void setProcess_status(String process_status)
	{
		this.process_status = process_status;
	}

	private String qc_result_status;

	public String getQc_result_status()
	{
		return qc_result_status;
	}

	public void setQc_result_status(String qc_result_status)
	{
		this.qc_result_status = qc_result_status;
	}

	private String ca_cold_room_code;

	public String getCa_cold_room_code()
	{
		return ca_cold_room_code;
	}

	public void setCa_cold_room_code(String ca_cold_room_code)
	{
		this.ca_cold_room_code = ca_cold_room_code;
	}

	public String getCold_store_code()
	{
		return cold_store_code;
	}

	public void setCold_store_code(String cold_store_code)
	{
		this.cold_store_code = cold_store_code;
	}

	private String farm_code;

	public String getFarm_code()
	{
		return farm_code;
	}

	public void setFarm_code(String farm_code)
	{
		this.farm_code = farm_code;
	}

	private Integer carton_setup_id;

	public Integer getCarton_setup_id()
	{
		return carton_setup_id;
	}

	public void setCarton_setup_id(Integer carton_setup_id)
	{
		this.carton_setup_id = carton_setup_id;
	}

	private String store_type_code;

	public String getStore_type_code()
	{
		return store_type_code;
	}

	public void setStore_type_code(String store_type_code)
	{
		this.store_type_code = store_type_code;
	}

	private String party_name;

	public String getParty_name()
	{
		return party_name;
	}

	public void setParty_name(String party_name)
	{
		this.party_name = party_name;
	}

    private String fg_code_old;

    private String iso_week_code;

    private String season_code;

    public String getFg_code_old() {
        return fg_code_old;
    }

    public void setFg_code_old(String fg_code_old) {
        this.fg_code_old = fg_code_old;
    }

    public String getIso_week_code() {
        return iso_week_code;
    }

    public void setIso_week_code(String iso_week_code) {
        this.iso_week_code = iso_week_code;
    }

    public String getSeason_code() {
        return season_code;
    }

    public void setSeason_code(String season_code) {
        this.season_code = season_code;
    }

    private String order_number;

    private String account_code;

    public String getAccount_code() {
        return account_code;
    }

    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

	public java.sql.Timestamp getDate_time_completed() {
		return date_time_completed;
	}

	public void setDate_time_completed(java.sql.Timestamp date_time_completed) {
		this.date_time_completed = date_time_completed;
	}

    /**
     * @return the load_detail_id
     */
    public Integer getLoad_detail_id() {
        return load_detail_id;
    }

    /**
     * @param load_detail_id the load_detail_id to set
     */
    public void setLoad_detail_id(Integer load_detail_id) {
        this.load_detail_id = load_detail_id;
    }

    /**
     * @return the ppecb_inspection_id
     */
    public Long getPpecb_inspection_id() {
        return ppecb_inspection_id;
    }

    /**
     * @param ppecb_inspection_id the ppecb_inspection_id to set
     */
    public void setPpecb_inspection_id(Long ppecb_inspection_id) {
        this.ppecb_inspection_id = ppecb_inspection_id;
    }

	
}
