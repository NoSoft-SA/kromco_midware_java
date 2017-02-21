/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author hans
 */
public class BinTipCriteria {

   private Integer id;
   private Boolean  farm_code;
   private Boolean  treatment_code;
   private Boolean  commodity_code;
   private Boolean  variety_code;
   private Boolean  class_code;
   private Boolean  pc_code;
   private Boolean  track_indicator_code;
   private Boolean  cold_store_code;
   private Boolean  season_code;
   private Integer  production_schedule_id;

    //pre-sort
    private Boolean ripe_point_code;
    private Boolean rmt_product_type_code;
    private Boolean size_code;

    /**
     * @return the farm_code
     */
    public Boolean getFarm_code() {
        return farm_code;
    }

    /**
     * @param farm_code the farm_code to set
     */
    public void setFarm_code(Boolean farm_code) {
        this.farm_code = farm_code;
    }

    /**
     * @return the treatment_code
     */
    public Boolean getTreatment_code() {
        return treatment_code;
    }

    /**
     * @param treatment_code the treatment_code to set
     */
    public void setTreatment_code(Boolean treatment_code) {
        this.treatment_code = treatment_code;
    }

    /**
     * @return the commodity_code
     */
    public Boolean getCommodity_code() {
        return commodity_code;
    }

    /**
     * @param commodity_code the commodity_code to set
     */
    public void setCommodity_code(Boolean commodity_code) {
        this.commodity_code = commodity_code;
    }

    /**
     * @return the variety_code
     */
    public Boolean getVariety_code() {
        return variety_code;
    }

    /**
     * @param variety_code the variety_code to set
     */
    public void setVariety_code(Boolean variety_code) {
        this.variety_code = variety_code;
    }

    /**
     * @return the class_code
     */
    public Boolean getClass_code() {
        return class_code;
    }

    /**
     * @param class_code the class_code to set
     */
    public void setClass_code(Boolean class_code) {
        this.class_code = class_code;
    }

    /**
     * @return the pc_code
     */
    public Boolean getPc_code() {
        return pc_code;
    }

    /**
     * @param pc_code the pc_code to set
     */
    public void setPc_code(Boolean pc_code) {
        this.pc_code = pc_code;
    }

    /**
     * @return the track_indicator_code
     */
    public Boolean getTrack_indicator_code() {
        return track_indicator_code;
    }

    /**
     * @param track_indicator_code the track_indicator_code to set
     */
    public void setTrack_indicator_code(Boolean track_indicator_code) {
        this.track_indicator_code = track_indicator_code;
    }

    /**
     * @return the cold_store_code
     */
    public Boolean getCold_store_code() {
        return cold_store_code;
    }

    /**
     * @param cold_store_code the cold_store_code to set
     */
    public void setCold_store_code(Boolean cold_store_code) {
        this.cold_store_code = cold_store_code;
    }

    /**
     * @return the season_code
     */
    public Boolean getSeason_code() {
        return season_code;
    }

    /**
     * @param season_code the season_code to set
     */
    public void setSeason_code(Boolean season_code) {
        this.season_code = season_code;
    }

    /**
     * @return the production_schedule_id
     */
    public Integer getProduction_schedule_id() {
        return production_schedule_id;
    }

    /**
     * @param production_schedule_id the production_schedule_id to set
     */
    public void setProduction_schedule_id(Integer production_schedule_id) {
        this.production_schedule_id = production_schedule_id;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }


    public Boolean getRipe_point_code() {
        return ripe_point_code;
    }

    public void setRipe_point_code(Boolean ripe_point_code) {
        this.ripe_point_code = ripe_point_code;
    }

    public Boolean getRmt_product_type_code() {
        return rmt_product_type_code;
    }

    public void setRmt_product_type_code(Boolean rmt_product_type_code) {
        this.rmt_product_type_code = rmt_product_type_code;
    }

    public Boolean getSize_code() {
        return size_code;
    }

    public void setSize_code(Boolean size_code) {
        this.size_code = size_code;
    }
}


