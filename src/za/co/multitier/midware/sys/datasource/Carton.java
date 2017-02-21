/*
 * Carton.java
 *
 * Created on February 5, 2007, 9:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class Carton
{
	
	/** Creates a new instance of Carton */
	public Carton()
	{
	}

    private Integer bin_id;
    
    private String brand;

    private String marketing_variety_code;
    
    private String inventory_code_short;
    
    private String gtin_code;
    

	private String account_code;
        
        public boolean need_order_qty_increment = false;
	
	private Integer production_run_id;

        private Integer shift_id;

	private Long pallet_id;

	private String carton_label_station_code;

	private Long carton_number;

	private String erp_station;

	private String erp_pack_point;

	private String commodity_code;

	private String carton_mark_code;

	private String target_market_code;

	private String variety_short_long;

	private String fg_code_old;

	private String quarantine;

	private String inspection_type_code;

	private String carton_label_code;

	private String carton_pack_station_code;

	private String order_number;

	private java.sql.Timestamp pack_date_time;

	private String actual_size_count_code;

	private String grade_code;

	private String old_pack_code;

	private String qc_status_code;

	private String treatment_code;

	private String chemical_status_code;

	private String product_class_code;

	private String erp_cultivar;

	private String track_indicator_code;

	private String pc_code;

	private String cold_store_code;

	private String inventory_code;

	private String farm_code;

	private String spray_program_code;

	private Double carton_fruit_nett_mass;

	private Double cpc_tu_mass;

	private Integer quantity;

	private String pi;

	private String pick_reference;

	private String line_code;

	private String shift_code;

	private String remarks;

	private String organization_code;

	private String quality_group_code;

	private String iso_week_code;

	private String season_code;

	private String puc;

	private String exit_reference;

	private java.sql.Timestamp exit_date_time;

	private Integer pallet_sequence_number;

	private Long pallet_number;

	private String fg_product_code;

	private java.sql.Timestamp date_time_created;

	private java.sql.Timestamp date_time_erp_xmit;

	private String production_run_code;

	private Integer carton_template_id;

	
	//=========================
	//ACCESSORS
	//========================
	public Integer getProduction_run_id()
	{
		return production_run_id;
	}

	public void setProduction_run_id(Integer production_run_id)
	{
		this.production_run_id = production_run_id;
	}

	public Long getPallet_id()
	{
		return pallet_id;
	}

	public void setPallet_id(Long pallet_id)
	{
		this.pallet_id = pallet_id;
	}
	
	public String getOld_pack_code()
	{
		return old_pack_code;
	}

	public void setOld_pack_code(String old_pack_code)
	{
		this.old_pack_code = old_pack_code;
	}

	public String getCarton_label_station_code()
	{
		return carton_label_station_code;
	}

	public void setCarton_label_station_code(String carton_label_station_code)
	{
		this.carton_label_station_code = carton_label_station_code;
	}

	public Long getCarton_number()
	{
		return carton_number;
	}

	public void setCarton_number(Long carton_number)
	{
		this.carton_number = carton_number;
	}

	public String getErp_station()
	{
		return erp_station;
	}

	public void setErp_station(String erp_station)
	{
		this.erp_station = erp_station;
	}

	public String getErp_pack_point()
	{
		return erp_pack_point;
	}

	public void setErp_pack_point(String erp_pack_point)
	{
		this.erp_pack_point = erp_pack_point;
	}

	public String getCommodity_code()
	{
		return commodity_code;
	}

	public void setCommodity_code(String commodity_code)
	{
		this.commodity_code = commodity_code;
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

	public String getVariety_short_long()
	{
		return variety_short_long;
	}

	public void setVariety_short_long(String variety_short_long)
	{
		this.variety_short_long = variety_short_long;
	}

	public String getFg_code_old()
	{
		return fg_code_old;
	}

	public void setFg_code_old(String fg_code_old)
	{
		this.fg_code_old = fg_code_old;
	}

	public String getQuarantine()
	{
		return quarantine;
	}

	public void setQuarantine(String quarantine)
	{
		this.quarantine = quarantine;
	}

	public String getInspection_type_code()
	{
		return inspection_type_code;
	}

	public void setInspection_type_code(String inspection_type_code)
	{
		this.inspection_type_code = inspection_type_code;
	}

	public String getCarton_label_code()
	{
		return carton_label_code;
	}

	public void setCarton_label_code(String carton_label_code)
	{
		this.carton_label_code = carton_label_code;
	}

	public String getCarton_pack_station_code()
	{
		return carton_pack_station_code;
	}

	public void setCarton_pack_station_code(String carton_pack_station_code)
	{
		this.carton_pack_station_code = carton_pack_station_code;
	}

	public String getOrder_number()
	{
		return order_number;
	}

	public void setOrder_number(String order_number)
	{
		this.order_number = order_number;
	}

	public java.sql.Timestamp getPack_date_time()
	{
		return pack_date_time;
	}

	public void setPack_date_time(java.sql.Timestamp pack_date_time)
	{
		this.pack_date_time = pack_date_time;
	}

	public String getActual_size_count_code()
	{
		return actual_size_count_code;
	}

	public void setActual_size_count_code(String actual_size_count_code)
	{
		this.actual_size_count_code = actual_size_count_code;
	}

	public String getGrade_code()
	{
		return grade_code;
	}

	public void setGrade_code(String grade_code)
	{
		this.grade_code = grade_code;
	}
	
	
	public String getAccount_code()
	{
		return account_code;
	}

	public void setAccount_code(String account_code)
	{
		this.account_code = account_code;
	}

	public String getQc_status_code()
	{
		return qc_status_code;
	}

	public void setQc_status_code(String qc_status_code)
	{
		this.qc_status_code = qc_status_code;
	}

	public String getTreatment_code()
	{
		return treatment_code;
	}

	public void setTreatment_code(String treatment_code)
	{
		this.treatment_code = treatment_code;
	}

	public String getChemical_status_code()
	{
		return chemical_status_code;
	}

	public void setChemical_status_code(String chemical_status_code)
	{
		this.chemical_status_code = chemical_status_code;
	}

	public String getProduct_class_code()
	{
		return product_class_code;
	}

	public void setProduct_class_code(String product_class_code)
	{
		this.product_class_code = product_class_code;
	}

	public String getErp_cultivar()
	{
		return erp_cultivar;
	}

	public void setErp_cultivar(String erp_cultivar)
	{
		this.erp_cultivar = erp_cultivar;
	}

	public String getTrack_indicator_code()
	{
		return track_indicator_code;
	}

	public void setTrack_indicator_code(String track_indicator_code)
	{
		this.track_indicator_code = track_indicator_code;
	}

	public String getPc_code()
	{
		return pc_code;
	}

	public void setPc_code(String pc_code)
	{
		this.pc_code = pc_code;
	}

	public String getCold_store_code()
	{
		return cold_store_code;
	}

	public void setCold_store_code(String cold_store_code)
	{
		this.cold_store_code = cold_store_code;
	}

	public String getInventory_code()
	{
		return inventory_code;
	}

	public void setInventory_code(String inventory_code)
	{
		this.inventory_code = inventory_code;
	}

	public String getFarm_code()
	{
		return farm_code;
	}

	public void setFarm_code(String farm_code)
	{
		this.farm_code = farm_code;
	}

	public String getSpray_program_code()
	{
		return spray_program_code;
	}

	public void setSpray_program_code(String spray_program_code)
	{
		this.spray_program_code = spray_program_code;
	}

	public Double getCarton_fruit_nett_mass()
	{
		return carton_fruit_nett_mass;
	}

	public void setCarton_fruit_nett_mass(Double carton_fruit_nett_mass)
	{
		this.carton_fruit_nett_mass = carton_fruit_nett_mass;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public String getPi()
	{
		return pi;
	}

	public void setPi(String pi)
	{
		this.pi = pi;
	}

	public String getPick_reference()
	{
		return pick_reference;
	}

	public void setPick_reference(String pick_reference)
	{
		this.pick_reference = pick_reference;
	}

	public String getLine_code()
	{
		return line_code;
	}

	public void setLine_code(String line_code)
	{
		this.line_code = line_code;
	}

	public String getShift_code()
	{
		return shift_code;
	}

	public void setShift_code(String shift_code)
	{
		this.shift_code = shift_code;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public String getOrganization_code()
	{
		return organization_code;
	}

	public void setOrganization_code(String organization_code)
	{
		this.organization_code = organization_code;
	}

	public String getQuality_group_code()
	{
		return quality_group_code;
	}

	public void setQuality_group_code(String quality_group_code)
	{
		this.quality_group_code = quality_group_code;
	}

	public String getIso_week_code()
	{
		return iso_week_code;
	}

	public void setIso_week_code(String iso_week_code)
	{
		this.iso_week_code = iso_week_code;
	}

	public String getSeason_code()
	{
		return season_code;
	}

	public void setSeason_code(String season_code)
	{
		this.season_code = season_code;
	}

	public String getPuc()
	{
		return puc;
	}

	public void setPuc(String puc)
	{
		this.puc = puc;
	}

	public String getExit_reference()
	{
		return exit_reference;
	}

	public void setExit_reference(String exit_reference)
	{
		this.exit_reference = exit_reference;
	}

	public java.sql.Timestamp getExit_date_time()
	{
		return exit_date_time;
	}

	public void setExit_date_time(java.sql.Timestamp exit_date_time)
	{
		this.exit_date_time = exit_date_time;
	}

	public Integer getPallet_sequence_number()
	{
		return pallet_sequence_number;
	}

	public void setPallet_sequence_number(Integer pallet_sequence_number)
	{
		this.pallet_sequence_number = pallet_sequence_number;
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

	public String getProduction_run_code()
	{
		return production_run_code;
	}

	public void setProduction_run_code(String production_run_code)
	{
		this.production_run_code = production_run_code;
	}

	public Integer getCarton_template_id()
	{
		return carton_template_id;
	}

	public void setCarton_template_id(Integer carton_template_id)
	{
		this.carton_template_id = carton_template_id;
	}

	private String pc_code_num;

	public String getPc_code_num()
	{
		return pc_code_num;
	}

	public void setPc_code_num(String pc_code_num)
	{
		this.pc_code_num = pc_code_num;
	}

	private String packer_number;

	public String getPacker_number()
	{
		return packer_number;
	}

	public void setPacker_number(String packer_number)
	{
		this.packer_number = packer_number;
	}

	private Integer id;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
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

	private String egap;

	public String getEgap()
	{
		return egap;
	}

	public void setEgap(String egap)
	{
		this.egap = egap;
	}

	private Boolean is_inspection_carton;

	

	private String sell_by_code;

	public String getSell_by_code()
	{
		return sell_by_code;
	}

	public void setSell_by_code(String sell_by_code)
	{
		this.sell_by_code = sell_by_code;
	}

	public Boolean isIs_inspection_carton()
	{
		return is_inspection_carton;
	}

	public void setIs_inspection_carton(Boolean is_inspection_carton)
	{
		this.is_inspection_carton = is_inspection_carton;
	}

	private String class_code;

	public String getClass_code()
	{
		return class_code;
	}

	public void setClass_code(String class_code)
	{
		this.class_code = class_code;
	}

	private String treatment_type_code;

	public String getTreatment_type_code()
	{
		return treatment_type_code;
	}

	public void setTreatment_type_code(String treatment_type_code)
	{
		this.treatment_type_code = treatment_type_code;
	}

	private Integer items_per_unit;

	public Integer getItems_per_unit()
	{
		return items_per_unit;
	}

	public void setItems_per_unit(Integer items_per_unit)
	{
		this.items_per_unit = items_per_unit;
	}

	private Integer units_per_carton;

	public Integer getUnits_per_carton()
	{
            if(units_per_carton == null)
                units_per_carton = 0;
            
		return units_per_carton;
	}

	public void setUnits_per_carton(Integer units_per_carton)
	{
		this.units_per_carton = units_per_carton;
	}

	private String fg_mark_code;

	public String getFg_mark_code()
	{
		return fg_mark_code;
	}
 
	public void setFg_mark_code(String fg_mark_code)
	{
		this.fg_mark_code = fg_mark_code;
	}

	private String extended_fg_code;

	public String getExtended_fg_code()
	{
		return extended_fg_code;
	}

	public void setExtended_fg_code(String extended_fg_code)
	{
		this.extended_fg_code = extended_fg_code;
	}

    private String carton_setup_code;

    public String getCarton_setup_code() {
        return carton_setup_code;
    }

    public void setCarton_setup_code(String carton_setup_code) {
        this.carton_setup_code = carton_setup_code;
    }


    private String unit_pack_product_code;

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getUnit_pack_product_code() {
        return unit_pack_product_code;
    }

    public void setUnit_pack_product_code(String unit_pack_product_code) {
        this.unit_pack_product_code = unit_pack_product_code;
    }

    /**
     * @return the shift_id
     */
    public Integer getShift_id() {
        return shift_id;
    }

    /**
     * @param shift_id the shift_id to set
     */
    public void setShift_id(Integer shift_id) {
        this.shift_id = shift_id;
    }

    public String getMarketing_variety_code() {
        String[] vals = this.getVariety_short_long().split("_");
       return vals[0];
    }

    public void setMarketing_variety_code(String marketing_variety_code) {
        this.marketing_variety_code = marketing_variety_code;
    }

    public String getInventory_code_short() {
        String[] vals = this.getInventory_code().split("_");
        return vals[0];
    }

    public void setInventory_code_short(String inventory_code_short) {
        this.inventory_code_short = inventory_code_short;
    }

    public String getGtin_code() {
        return gtin_code;
    }

    public void setGtin_code(String gtin_code) {
        this.gtin_code = gtin_code;
    }
    
    private String gtin;

    public void calc_gtin()
    {
        try {
            String gtin_code = (String) DataSource.getSqlMapInstance().queryForObject("getGtin", this);
            this.setGtin(gtin_code);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getBin_id() {
        return bin_id;
    }

    public void setBin_id(Integer bin_id) {
        this.bin_id = bin_id;
    }

	public Double getCpc_tu_mass()
	{
		return cpc_tu_mass;
	}

	public void setCpc_tu_mass(Double cpc_tu_mass)
	{
		this.cpc_tu_mass = cpc_tu_mass;
	}
}
