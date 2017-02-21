/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author hans
 */
public class RmtSetup {

     //vals needed for bintip criteria check
        private String farm_code;
        
        private String commodity_code;
        private String variety_code;
        private String class_code;
        private String treatment_code;

        private String pc_code;
        private String track_indicator_code;
        private String cold_store_code;
        private String season_code;

        //pres-sort additions
        private String ripe_point_code;
        private String rmt_product_type_code;
        private String size_code;



    /**
     * @return the farm_code
     */
    public String getFarm_code() {
        return farm_code;
    }

    /**
     * @param farm_code the farm_code to set
     */
    public void setFarm_code(String farm_code) {
        this.farm_code = farm_code;
    }

    /**
     * @return the commodity_code
     */
    public String getCommodity_code() {
        return commodity_code;
    }

    /**
     * @param commodity_code the commodity_code to set
     */
    public void setCommodity_code(String commodity_code) {
        this.commodity_code = commodity_code;
    }

    /**
     * @return the variety_code
     */
    public String getVariety_code() {
        return variety_code;
    }

    /**
     * @param variety_code the variety_code to set
     */
    public void setVariety_code(String variety_code) {
        this.variety_code = variety_code;
    }

    /**
     * @return the class_code
     */
    public String getClass_code() {
        return class_code;
    }

    /**
     * @param class_code the class_code to set
     */
    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    /**
     * @return the treatment_code
     */
    public String getTreatment_code() {
        return treatment_code;
    }

    /**
     * @param treatment_code the treatment_code to set
     */
    public void setTreatment_code(String treatment_code) {
        this.treatment_code = treatment_code;
    }

    /**
     * @return the pc_code
     */
    public String getPc_code() {
        return pc_code;
    }

    /**
     * @param pc_code the pc_code to set
     */
    public void setPc_code(String pc_code) {
        this.pc_code = pc_code;
    }

    /**
     * @return the track_indicator_code
     */
    public String getTrack_indicator_code() {
        return track_indicator_code;
    }

    /**
     * @param track_indicator_code the track_indicator_code to set
     */
    public void setTrack_indicator_code(String track_indicator_code) {
        this.track_indicator_code = track_indicator_code;
    }

    /**
     * @return the cold_store_code
     */
    public String getCold_store_code() {
        return cold_store_code;
    }

    /**
     * @param cold_store_code the cold_store_code to set
     */
    public void setCold_store_code(String cold_store_code) {
        this.cold_store_code = cold_store_code;
    }

    /**
     * @return the season_code
     */
    public String getSeason_code() {
        return season_code;
    }

    /**
     * @param season_code the season_code to set
     */
    public void setSeason_code(String season_code) {
        this.season_code = season_code;
    }


    public String getRipe_point_code() {
        return ripe_point_code;
    }

    public void setRipe_point_code(String ripe_point_code) {
        this.ripe_point_code = ripe_point_code;
    }

    public String getRmt_product_type_code() {
        return rmt_product_type_code;
    }

    public void setRmt_product_type_code(String rmt_product_type_code) {
        this.rmt_product_type_code = rmt_product_type_code;
    }

    public String getSize_code() {
        return size_code;
    }

    public void setSize_code(String size_code) {
        this.size_code = size_code;
    }
}
