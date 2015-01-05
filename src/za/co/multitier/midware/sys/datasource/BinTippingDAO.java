/*
 * DevicesDAO.java
 *
 * Created on January 25, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.datasource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.DeviceTypes;

public class BinTippingDAO {
    //------------
    //BIN METHODS
    //------------

    public static Integer getLineId(String line_code) throws Exception {
        try {

            //HashMap params = new HashMap();
            //params.put("station_code",station_code);
            //params.put("run_id",production_run_id);

            Integer line_id = (Integer) DataSource.getSqlMapInstance().queryForObject("getLineId", line_code);
            return line_id;
        } catch (SQLException ex) {
            throw new Exception("Line id could not be fetched. Reported exception: " + ex);
        }

    }

    private static String criteriaCheckPreSort(Bin bin, Integer run_id) throws Exception {

        RmtSetup setup = (RmtSetup) DataSource.getSqlMapInstance().queryForObject("getRmtSetupPreSort", run_id);
        BinTipCriteria criteria = (BinTipCriteria) DataSource.getSqlMapInstance().queryForObject("getBinTipCriteria", run_id);



        //farm code   (from run)
        if (criteria.getFarm_code() != null && criteria.getFarm_code() == true) {
            if (!(setup.getFarm_code().equals(bin.getFarm_code()))) {
                return "CRIT:FARM";
            }
        }

        //treatment code    (from run)
        if (criteria.getTreatment_code() != null && criteria.getTreatment_code() == true) {
            if (setup.getTreatment_code() == null ||!(setup.getTreatment_code().equals(bin.getTreatment_code()))) {
                return "CRIT FAIL: TREATMENT";
            }
        }

        //commodity code      (from run)
        if (criteria.getCommodity_code() != null && criteria.getCommodity_code() == true) {
            if (setup.getCommodity_code() == null||!(setup.getCommodity_code().equals(bin.getCommodity_code()))) {
                return "CRIT:COMMODITY";
            }
        }

        // variety code    (from run)
        if (criteria.getVariety_code() != null && criteria.getVariety_code() == true) {
            if (setup.getVariety_code()== null||!(setup.getVariety_code().equals(bin.getVariety_code()))) {
                return "CRIT:VARIETY";
            }
        }

        // class code    (from run)
        if (criteria.getClass_code() != null && criteria.getClass_code() == true) {
            if (setup.getClass_code() == null||!(setup.getClass_code().equals(bin.getClass_code()))) {
                return "CRIT:CLASS";
            }
        }

        // PC code    (from run.treatment)
        if (criteria.getPc_code() != null && criteria.getPc_code() == true) {
            if (setup.getPc_code() == null||!(setup.getPc_code().equals(bin.getPc_code_num()))) {
                return "CRIT:PC CODE";
            }
        }

        // track indicator code     (from run)
        if (criteria.getTrack_indicator_code() != null && criteria.getTrack_indicator_code() == true) {
            if (setup.getTrack_indicator_code() == null||!(setup.getTrack_indicator_code().equals(bin.getTrack_indicator_code()))) {
                return "CRIT:TRACK INDIC";
            }
        }

        // cold store code   (from schedule)
        if (criteria.getCold_store_code() != null && criteria.getCold_store_code() == true) {
            if (setup.getCold_store_code()==null||!(setup.getCold_store_code().equals(bin.getCold_store_code()))) {
                return "CRIT:COLD STORE";
            }
        }

        // season code     (from schedule)
        if (criteria.getSeason_code() != null && criteria.getSeason_code() == true) {
            if (setup.getSeason_code() == null||!(setup.getSeason_code().equals(bin.getSeason_code()))) {
                return "CRIT:SEASON";
            }
        }

        // ripe point    (from run)
        if (criteria.getRipe_point_code() != null && criteria.getRipe_point_code() == true) {
            if (setup.getRipe_point_code() == null||!(setup.getRipe_point_code().equals(bin.getRipe_point_code()))) {
                return "CRIT:RIPE POINT";
            }
        }

        // size code    (from run)
        if (criteria.getSize_code() != null && criteria.getSize_code() == true) {
            if (setup.getSize_code()==null||!(setup.getSize_code().equals(bin.getSize_code()))) {
                return "CRIT:SIZE";
            }
        }

        // size code    (from run)
        if (criteria.getRmt_product_type_code() != null && criteria.getRmt_product_type_code() == true) {
            if (setup.getRmt_product_type_code()==null||!(setup.getRmt_product_type_code().equals(bin.getRmt_product_type_code()))) {
                return "CRIT:PRODUCT TYPE";
            }
        }



        return null;

    }

    private static String criteriaCheck(Bin bin, Integer run_id) throws Exception {
        RmtSetup setup = (RmtSetup) DataSource.getSqlMapInstance().queryForObject("getRmtSetup", run_id);
        BinTipCriteria criteria = (BinTipCriteria) DataSource.getSqlMapInstance().queryForObject("getBinTipCriteria", run_id);



        //farm code
        if (criteria.getFarm_code() != null && criteria.getFarm_code() == true) {
            if (!(setup.getFarm_code().equals(bin.getFarm_code()))) {
                return "CRIT:FARM";
            }
        }

        //treatment code
        if (criteria.getTreatment_code() != null && criteria.getTreatment_code() == true) {
            if (!(setup.getTreatment_code().equals(bin.getTreatment_code()))) {
                return "CRIT FAIL: TREATMENT";
            }
        }

        //commodity code
        if (criteria.getCommodity_code() != null && criteria.getCommodity_code() == true) {
            if (!(setup.getCommodity_code().equals(bin.getCommodity_code()))) {
                return "CRIT:COMMODITY";
            }
        }

        // variety code
        if (criteria.getVariety_code() != null && criteria.getVariety_code() == true) {
            if (!(setup.getVariety_code().equals(bin.getVariety_code()))) {
                return "CRIT:VARIETY";
            }
        }

        // class code
        if (criteria.getClass_code() != null && criteria.getClass_code() == true) {
            if (!(setup.getClass_code().equals(bin.getClass_code()))) {
                return "CRIT:CLASS";
            }
        }

        // PC code
        if (criteria.getPc_code() != null && criteria.getPc_code() == true) {
            if (!(setup.getPc_code().equals(bin.getPc_code_num()))) {
                return "CRIT:PC CODE";
            }
        }

        // track indicator code
        if (criteria.getTrack_indicator_code() != null && criteria.getTrack_indicator_code() == true) {
            if (!(setup.getTrack_indicator_code().equals(bin.getTrack_indicator_code()))) {
                return "CRIT:TRACK INDIC";
            }
        }

        // cold store code
        if (criteria.getCold_store_code() != null && criteria.getCold_store_code() == true) {
            if (!(setup.getCold_store_code().equals(bin.getCold_store_code()))) {
                return "CRIT:COLD STORE";
            }
        }

        // season code
        if (criteria.getSeason_code() != null && criteria.getSeason_code() == true) {
            if (!(setup.getSeason_code().equals(bin.getSeason_code()))) {
                return "CRIT:SEASON";
            }
        }



        return null;

    }

    public static String validate(String bin_number, Integer production_run_id) throws Exception {
        try {


        
            Bin bin = getBin(bin_number);
            if (bin == null) {
                return "BIN NOT FOUND";
            }


            if (bin.getBin_weight() == null || bin.getBin_weight() < 1.00) {
                return "BIN NOT WEIGHED";
            } else {
                System.out.println("Bin weighed");
            }


            if (bin.getDelivery_id() != null && bin.isGrower_commitment_required() != null && bin.isGrower_commitment_required() == true) {
                boolean passed = mrlPassed(bin.getDelivery_id());
                if(passed == false)
                  return "MRL NOT DONE|FAILED";

            }

            String failed_criteria = null;
            failed_criteria = criteriaCheckPreSort(bin, production_run_id);
            return failed_criteria;




        } catch (SQLException ex) {
            throw new Exception("validation system failed. Reported exception: " + ex);
        }
    }


   private static boolean mrlPassed(long delivery_id) throws SQLException {

         List <String>results = DataSource.getSqlMapInstance().queryForList("getMrlResults", delivery_id);

       boolean passed = true;

        if (results.size() == 0)
            return false;

        for (String result : results) {
             if(!(result.toUpperCase().equals("PASSED")||result.toUpperCase().equals("PENDING")))
               return false;
        }

       return passed;

    }


    public static Integer getTippedBinsCount(String line_code, String run_code) throws Exception {
        try {
            Integer line_id = getLineId(line_code);
            HashMap params = new HashMap();
            params.put("line_id", line_id);
            params.put("run_code", run_code);

            Integer valid_tipped_count = (Integer) DataSource.getSqlMapInstance().queryForObject("getValidTippedCountForRunAndLine", params);
            Integer invalid_tipped_count = (Integer) DataSource.getSqlMapInstance().queryForObject("getInvalidTippedCountForRunAndLine", params);


            return valid_tipped_count + invalid_tipped_count;



        } catch (SQLException ex) {
            throw new Exception("Line id could not be fetched. Reported exception: " + ex);


        }

    }

    public static void updateBinRunStats(Bin bin) throws Exception {
        if (bin.getWeight() == null) {
            bin.setWeight(0.0);


        }

        DataSource.getSqlMapInstance().update("updateBinsTippedStats", bin);

        //DataSource.getSqlMapInstance().update("addBinWeight",bin);


    }

    public static void validBinTransaction(Bin bin, String production_run_code,String lotNumber) throws Exception {
        try {

            DataSource.getSqlMapInstance().startTransaction();
            bin.setDp_tiplot(lotNumber);
            BinTippingDAO.createTippedBin(bin);


            if (bin.getWeight() == null) {
                bin.setWeight(0.0);


            }
            //DataSource.getSqlMapInstance().update("incrementBinsTipped",bin);
            //DataSource.getSqlMapInstance().update("addBinWeight",bin);
            //updateBinRunStats(bin);
            ProductLabelingDAO.updateRunStats(null, null, bin, null);

            DeviceScan.send_integration_record("bin_tipped", bin.getBin_id().toString(), "Bin");

            DataSource.getSqlMapInstance().commitTransaction();





        } catch (Exception ex) {

            throw new Exception("validBinTransaction failed. Reported exception: " + ex.toString());


        } finally {
            //DataSource.getSqlMapInstance().endTransaction();
        }


    }

    public static void invalidBinAuthorizedTransaction(Bin bin) throws Exception {
        try {
            DataSource.getSqlMapInstance().startTransaction();
            BinTippingDAO.createInvalidBin(bin);
            DataSource.getSqlMapInstance().update("incrementBinsTipped", bin);
            //TO DO: CREATE POSTBOX RECORD
            //TO DO: CREATE BIN ERROR LOG RECORD
            DataSource.getSqlMapInstance().commitTransaction();
            //DeviceScan.send_integration_record("bin_tipped_invalid",bin.getId().toString(),"BinsTippedInvalid");



        } catch (Exception ex) {

            throw new Exception("invalidBinAuthorizedTransaction failed. Reported exception: " + ex.toString());


        } finally {
            //DataSource.getSqlMapInstance().endTransaction();
        }


    }

    public static void createBinErrorLogEntry(Bin bin) throws Exception {
        try {

            MidwareErrorLogEntry error = new MidwareErrorLogEntry();
            error.setAuthorisor_name(bin.getAuthorisor_name());
            error.setError_code(bin.getError_code());
            error.setError_date_time(new java.sql.Timestamp(new java.util.Date().getTime()));
            error.setError_description(bin.getError_description());
            error.setLine_code(bin.getLine_code());
            error.setMw_type(DeviceTypes.BIN_TIPPING);
            error.setObject_id(bin.getBin_id());
            //error.setProduction_run_code(bin.getProduction_run_code());
            //error.setProduction_run_id(bin.getProduction_run_id());
            //error.setProduction_schedule_name(bin.getProduction_schedule_name());

            UtilsDAO.createErrorLogEntry(error);



        } catch (SQLException ex) {
            throw new Exception("Bin could not be created. Reported exception: " + ex);


        }

    }

    public static Bin getBin(String bin_id) throws Exception {
        try {

            Bin bin = (Bin) DataSource.getSqlMapInstance().queryForObject("getBin", bin_id);


            return bin;


        } catch (SQLException ex) {
            throw new Exception("Bin could not be fetched. Reported exception: " + ex);


        }

    }

    //----------
    //TIPPED BIN
    //----------
    public static Bin createTippedBin(Bin bin) throws Exception {
        try {
            //System.out.println("bin receive datetime is: " + bin.getBin_receive_datetime().toString());
            DataSource.getSqlMapInstance().update("createTippedBin", bin);
            //create integration record



            return bin;


        } catch (Exception ex) {
            throw new Exception("Tipped bin could not be created. Reported exception: " + ex);


        }

    }

    public static Bin getTippedBin(String bin_id) throws Exception {
        try {

            Bin bin = (Bin) DataSource.getSqlMapInstance().queryForObject("getTippedBin", bin_id);
            if(bin == null)
            {
                bin = (Bin) DataSource.getSqlMapInstance().queryForObject("getDestroyedBin", bin_id);
                if(bin != null)
                 bin.is_detroyed = true;
            }

            return bin;


        } catch (SQLException ex) {
            throw new Exception("Tipped bin could not be fetched. Reported exception: " + ex);


        }

    }

    //------------
    //INVALID BIN
    //------------
    public static Bin createInvalidBin(Bin bin) throws Exception {
        try {

            DataSource.getSqlMapInstance().insert("createInvalidBin", bin);


            return bin;


        } catch (SQLException ex) {
            throw new Exception("Invalid bin could not be created. Reported exception: " + ex);


        }

    }

    public static Bin getInvalidBin(String bin_id) throws Exception {
        try {

            Bin bin = (Bin) DataSource.getSqlMapInstance().queryForObject("getInvalidBin", bin_id);


            return bin;


        } catch (SQLException ex) {
            throw new Exception("Invalid bin could not be fetched. Reported exception: " + ex);

        }

    }
}
