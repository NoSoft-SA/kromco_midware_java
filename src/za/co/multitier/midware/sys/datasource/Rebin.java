/*
 * Rebin.java
 *
 * Created on February 5, 2007, 3:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class Rebin {

    /** Creates a new instance of Rebin */
    public Rebin() {
    }
    private Integer id;
    private Integer production_run_id;
    private String class_code;
    private String farm_id;
    private String pc_code;
    private String production_run_code;
    private String rebin_number;
    private String rebin_status;
    private String size_code;
    private String username;
    private Double weight;
    private Double bin_weight;
    private String binfill_station_code;
    private java.sql.Timestamp rebin_date_time;
    private Integer shift_id;
    private String track_indicator_description;
    private String pack_material_product_code;
    private java.sql.Timestamp created_on;
    private String ripe_point_code;

    
    public String getTrack_indicator_description() {
        return track_indicator_description;
    }

    /**
     * @param track_indicator_description the track_indicator_description to set
     */
    public void setTrack_indicator_description(String track_indicator_description) {
        this.track_indicator_description = track_indicator_description;
    }
    //======================
    //ACCESSORS
    //======================
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(String farm_id) {
        this.farm_id = farm_id;
    }

    public String getPc_code() {
        return pc_code;
    }

    public void setPc_code(String pc_code) {
        this.pc_code = pc_code;
    }

    public String getProduction_run_code() {
        return production_run_code;
    }

    public void setProduction_run_code(String production_run_code) {
        this.production_run_code = production_run_code;
    }

    public String getRebin_number() {
        return rebin_number;
    }

    public void setRebin_number(String rebin_number) {
        this.rebin_number = rebin_number;
    }

    public String getRebin_status() {
        return rebin_status;
    }

    public void setRebin_status(String rebin_status) {
        this.rebin_status = rebin_status;
    }

    public String getSize_code() {
        return size_code;
    }

    public void setSize_code(String size_code) {
        this.size_code = size_code;
    }

 

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getProduction_run_id() {
        return production_run_id;
    }

    public void setProduction_run_id(Integer production_run_id) {
        this.production_run_id = production_run_id;
    }
  

    public Double getBin_weight() {
        return bin_weight;
    }

    public void setBin_weight(Double bin_weight) {
        this.bin_weight = bin_weight;
    }
    private String rmt_description;

    public String getRmt_description() {
        return rmt_description;
    }

    public void setRmt_description(String rmt_description) {
        this.rmt_description = rmt_description;
    }
    private String rmt_code;

    public String getRmt_code() {
        return rmt_code;
    }

    public void setRmt_code(String rmt_code) {
        this.rmt_code = rmt_code;
    }
    private String print_number;

    public String getPrint_number() {
        return print_number;
    }

    public void setPrint_number(String print_number) {
        this.print_number = print_number;
    }
    private String line_code;

    public String getLine_code() {
        return line_code;
    }

    public void setLine_code(String line_code) {
        this.line_code = line_code;
    }

    /**
     * @return the binfill_station_code
     */
    public String getBinfill_station_code() {
        return binfill_station_code;
    }

    /**
     * @param binfill_station_code the binfill_station_code to set
     */
    public void setBinfill_station_code(String binfill_station_code) {
        this.binfill_station_code = binfill_station_code;
    }

    /**
     * @return the rebin_date_time
     */
    public java.sql.Timestamp getRebin_date_time() {
        return rebin_date_time;
    }

    /**
     * @param rebin_date_time the rebin_date_time to set
     */
    public void setRebin_date_time(java.sql.Timestamp rebin_date_time) {
        this.rebin_date_time = rebin_date_time;
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

    /**
     * @return the pack_material_product_code
     */
    public String getPack_material_product_code() {
        return pack_material_product_code;
    }

    /**
     * @param pack_material_product_code the pack_material_product_code to set
     */
    public void setPack_material_product_code(String pack_material_product_code) {
        this.pack_material_product_code = pack_material_product_code;
    }

    /**
     * @return the created_on
     */
    public java.sql.Timestamp getCreated_on() {
        return created_on;
    }

    /**
     * @param created_on the created_on to set
     */
    public void setCreated_on(java.sql.Timestamp created_on) {
        this.created_on = created_on;
    }

    public String getRipe_point_code() {
        return ripe_point_code;
    }

    public void setRipe_point_code(String ripe_point_code) {
        this.ripe_point_code = ripe_point_code;
    }
}
