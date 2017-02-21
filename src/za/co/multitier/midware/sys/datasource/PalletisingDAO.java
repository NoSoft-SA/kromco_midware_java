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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.co.multitier.midware.sys.appservices.DeviceScan;

public class PalletisingDAO {


    public static Pallet getPalletSetup(Integer carton_template_id) throws Exception {
        try {


            Pallet pallet = (Pallet) DataSource.getSqlMapInstance().queryForObject("getPalletSetup", carton_template_id);
            return pallet;
        } catch (SQLException ex) {
            throw new Exception("Pallet could not be fetched. Reported exception: " + ex);
        }

    }

    public static Integer getBayCartonCount(String skip_ip, String bay_number) throws Exception {
        try {

            HashMap params = new HashMap();
            params.put("skip_ip", skip_ip);
            params.put("bay_number", bay_number);
            Integer count = (Integer) DataSource.getSqlMapInstance().queryForObject("getBayCartonCount", params);
            return count;
        } catch (SQLException ex) {
            throw new Exception("Bay carton count could not read. Reported exception: " + ex);
        }

    }

    public static Pallet getPalletTemplateForCartonSetup(Integer carton_setup_id) throws Exception {
        try {


            Pallet pallet = (Pallet) DataSource.getSqlMapInstance().queryForObject("getPalletTemplateForCartonSetup", carton_setup_id);
            return pallet;
        } catch (SQLException ex) {
            throw new Exception("Pallet template could not be fetched. Reported exception: " + ex);
        }

    }

    public static FgSetup getFgSetup(Integer carton_setup_id) throws Exception {
        try {


            FgSetup fgSetup = (FgSetup) DataSource.getSqlMapInstance().queryForObject("getFgSetup", carton_setup_id);
            return fgSetup;
        } catch (SQLException ex) {
            throw new Exception("Fg setup could not be fetched. Reported exception: " + ex);
        }

    }


    public static PalletizingCriteria getRunPalletizingCriteria(Integer carton_setup_id, Integer production_run_id) throws Exception {
        try {
            HashMap params = new HashMap();
            params.put("run_id", production_run_id);
            params.put("carton_setup_id", carton_setup_id);


            PalletizingCriteria criteria = (PalletizingCriteria) DataSource.getSqlMapInstance().queryForObject("getRunPalletizingCriteria", params);
            return criteria;
        } catch (SQLException ex) {
            throw new Exception("Run palletizing criteria could not be fetched. Reported exception: " + ex);
        }

    }


    public static BayCarton getBayCarton(Long carton_number, String bay_number, String skip_ip) throws Exception {
        try {
            HashMap params = new HashMap();
            params.put("bay_number", bay_number);
            params.put("carton_number", carton_number);
            params.put("skip_ip", skip_ip);

            BayCarton bay_carton = (BayCarton) DataSource.getSqlMapInstance().queryForObject("getBayCarton", params);
            return bay_carton;
        } catch (SQLException ex) {
            throw new Exception("Bay carton could not be fetched. Reported exception: " + ex);
        }

    }


    public static PalletizingCriteria getRelevantPalletizingCriteria(Integer carton_setup_id, Integer production_run_id, Integer schedule_id) throws Exception {
        try {

            //Try to find pallet criteria from bottom upwards
            PalletizingCriteria criteria = null;
            criteria = PalletisingDAO.getRunPalletizingCriteria(carton_setup_id, production_run_id);
            if (criteria == null)
                criteria = PalletisingDAO.getCartonSetupPalletizingCriteria(carton_setup_id);
            if (criteria == null)
                criteria = PalletisingDAO.getSchedulePalletizingCriteria(schedule_id);

            return criteria;
        } catch (SQLException ex) {
            throw new Exception("Relevant palletizing criteria could not be fetched. Reported exception: " + ex);
        }

    }

    public static PalletizingCriteria getCartonSetupPalletizingCriteria(Integer carton_setup_id) throws Exception {
        try {


            PalletizingCriteria criteria = (PalletizingCriteria) DataSource.getSqlMapInstance().queryForObject("getCartonSetupPalletizingCriteria", carton_setup_id);
            return criteria;
        } catch (SQLException ex) {
            throw new Exception("Carton setup palletizing criteria could not be fetched. Reported exception: " + ex);
        }

    }


    public static PalletizingCriteria getSchedulePalletizingCriteria(Integer schedule_id) throws Exception {
        try {


            PalletizingCriteria criteria = (PalletizingCriteria) DataSource.getSqlMapInstance().queryForObject("getSchedulePalletizingCriteria", schedule_id);
            return criteria;
        } catch (SQLException ex) {
            throw new Exception("Schedule criteria could not be fetched. Reported exception: " + ex);
        }

    }


    public static PalletizingCriteria getMixedPalletCriteria(Long pallet_id) throws Exception {
        try {


            PalletizingCriteria criteria = (PalletizingCriteria) DataSource.getSqlMapInstance().queryForObject("getMixedPalletCriteria", pallet_id);
            return criteria;
        } catch (SQLException ex) {
            throw new Exception("Mixed pallet crieteria for pallet criteria could not be fetched. Reported exception: " + ex);
        }

    }

    public static Pallet getPalletByNumber(Long pallet_number) throws Exception {
        try {

            Pallet pallet = (Pallet) DataSource.getSqlMapInstance().queryForObject("getPalletByNumber", pallet_number);
            return pallet;
        } catch (SQLException ex) {
            throw new Exception("Pallet could not be fetched. Reported exception: " + ex);
        }

    }

    public static Integer getCartonsPerPallet(Integer carton_setup_id) throws Exception {
        try {

            Integer cpp = (Integer) DataSource.getSqlMapInstance().queryForObject("getCartonsPerPalletForCartonSetup", carton_setup_id);
            return cpp;
        } catch (SQLException ex) {
            throw new Exception("Pallet could not be fetched. Reported exception: " + ex);
        }

    }


    public static Carton getLastCartonOnBay(String bay_code, String skip_ip) throws Exception {
        try {
            HashMap params = new HashMap();
            params.put("bay_number", bay_code);
            params.put("skip_ip", skip_ip);
            Carton carton = (Carton) DataSource.getSqlMapInstance().queryForObject("getLastCartonOnBay", params);
            return carton;
        } catch (SQLException ex) {
            throw new Exception("Last carton on bay. Reported exception: " + ex);
        }

    }

    public static Bay getBay(String bay_code, String skip_ip) throws Exception {
        try {
            HashMap params = new HashMap();
            params.put("bay_code", bay_code);
            params.put("skip_ip", skip_ip);
            Bay bay = (Bay) DataSource.getSqlMapInstance().queryForObject("getBayBySkipIpAndBayNum", params);
            return bay;
        } catch (SQLException ex) {
            throw new Exception("Bay could not be fetched. Reported exception: " + ex);
        }

    }


    public static List getPalletOrders(Long pallet_id) throws Exception {
        try {

            List pallet_orders = DataSource.getSqlMapInstance().queryForList("getPalletOrders", pallet_id);

            return pallet_orders;
        } catch (SQLException ex) {
            throw new Exception("Pallet orders. Reported exception: " + ex);
        }

    }

    public static List getPalletCartons(Long pallet_id) throws Exception {
        try {

            List cartons = DataSource.getSqlMapInstance().queryForList("getPalletCartons", pallet_id);
            return cartons;
        } catch (SQLException ex) {
            throw new Exception("Cartons for pallet could not be fetched. Reported exception: " + ex);
        }

    }


    public static void createBayCartons(Long pallet_id, Integer bay_id) throws Exception {
        try {

            List<Carton> cartons = getPalletCartons(pallet_id);
            for (Carton carton : cartons) {
                BayCarton bay_ctn = new BayCarton();
                bay_ctn.setCarton_id(carton.getId());
                bay_ctn.setPallet_id(pallet_id);
                bay_ctn.setBay_id(bay_id);
                createBayCarton(bay_ctn);
            }


        } catch (SQLException ex) {
            throw new Exception("Bay carton records could not be created. Reported exception: " + ex);
        }

    }


    public static Bay getBayForPallet(Long pallet_id) throws Exception {
        try {

            Bay bay = (Bay) DataSource.getSqlMapInstance().queryForObject("getBayForPallet", pallet_id);
            return bay;
        } catch (SQLException ex) {
            throw new Exception("Bay could not be fetched from pallet id. Reported exception: " + ex);
        }

    }

    public static Long getPalletId(Long pallet_number) throws Exception {
        try {

            return (Long) DataSource.getSqlMapInstance().queryForObject("getPalletId", pallet_number);

        } catch (SQLException ex) {
            throw new Exception("Pallet id could not be fetched. Reported exception: " + ex);
        }

    }

    public static ItemPackProduct getCartonItemPackDetails(Long carton_number) throws Exception {
        try {

            ItemPackProduct ipc = (ItemPackProduct) DataSource.getSqlMapInstance().queryForObject("getCartonItemPackDetails", carton_number);
            return ipc;
        } catch (SQLException ex) {
            throw new Exception("Item pack details could not be fetched. Reported exception: " + ex);
        }

    }

    public static ItemPackProduct getPalletItemPackDetails(Long pallet_number) throws Exception {
        try {

            ItemPackProduct ipc = (ItemPackProduct) DataSource.getSqlMapInstance().queryForObject("getPalletItemPackDetails", pallet_number);
            return ipc;
        } catch (SQLException ex) {
            throw new Exception("Item pack details could not be fetched. Reported exception: " + ex);
        }

    }

    public static Pallet getBayPallet(String bay_code, String skip_ip) throws Exception {
        try {
            HashMap params = new HashMap();
            params.put("bay_number", bay_code);
            params.put("skip_ip", skip_ip);
            Pallet pallet = (Pallet) DataSource.getSqlMapInstance().queryForObject("getBayPallet", params);
            return pallet;
        } catch (SQLException ex) {
            throw new Exception("Pallet could not be fetched from bay. Reported exception: " + ex);
        }

    }


    public static void createPallet(Pallet new_pallet) throws Exception {
        try {

            String check_digit = DeviceScan.calc_checkdigitSSCC(new_pallet.getPallet_number().toString());
            Long final_pallet_num = Long.parseLong(new_pallet.getPallet_number().toString() + check_digit);
            new_pallet.setPallet_number(final_pallet_num);
            DataSource.getSqlMapInstance().insert("createPallet", new_pallet);


        } catch (SQLException ex) {
            throw new Exception("Pallet could not be created. Reported exception: " + ex);
        }

    }

    public static void createBayCarton(BayCarton bay_carton) throws Exception {
        try {

            DataSource.getSqlMapInstance().insert("createBayCarton", bay_carton);


        } catch (SQLException ex) {
            throw new Exception("Bay carton could not be created. Reported exception: " + ex);
        }

    }


    public static void logSeasonOrderQtyMail(HashMap mail_details) throws Exception {
        try {

            DataSource.getSqlMapInstance().insert("logSeasonOrderQtyMail", mail_details);


        } catch (SQLException ex) {
            throw new Exception("SeasonOrderQtyMail log entry could not be created. Reported exception: " + ex);
        }

    }

    public static void deleteBayCarton(Integer carton_id) throws Exception {
        try {

            DataSource.getSqlMapInstance().delete("deleteBayCarton", carton_id);


        } catch (SQLException ex) {
            throw new Exception("Bay carton could not be deleted. Reported exception: " + ex);
        }

    }


    public static void setCartonInspectionDateTime(Integer carton_id, boolean is_in_datetime) throws Exception {
        try {
            java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime());
            String method = is_in_datetime ? "updateCartonInspectionTimeIn" : "updateCartonInspectionTimeOut";
            HashMap params = new HashMap();
            params.put("qc_datetime", now);
            params.put("carton_id", carton_id);

            DataSource.getSqlMapInstance().update(method, params);


        } catch (SQLException ex) {
            throw new Exception("Inspection datetime could not be set. Reported exception: " + ex);
        }

    }

    public static void setCartonInspectionFlag(Integer carton_id, boolean is_inspection_carton) throws Exception {
        try {

            HashMap params = new HashMap();
            params.put("is_inspection_carton", is_inspection_carton);
            params.put("carton_id", carton_id);

            DataSource.getSqlMapInstance().update("updateCartonInspectionFlag", params);


        } catch (SQLException ex) {
            throw new Exception("Inspection datetime could not be set. Reported exception: " + ex);
        }

    }

    public static void setPalletQcStatus(Long pallet_number, String qc_status) throws Exception {
        try {

            HashMap params = new HashMap();
            params.put("qc_status_code", qc_status);
            params.put("pallet_number", pallet_number);

            DataSource.getSqlMapInstance().update("updatePalletQcStatus", params);


        } catch (SQLException ex) {
            throw new Exception("Inspection datetime could not be set. Reported exception: " + ex);
        }

    }


    public static void updatePalletNumOnCarton(Carton carton) throws Exception {
        try {

            DataSource.getSqlMapInstance().update("updateCartonPalletNum", carton);


        } catch (SQLException ex) {
            throw new Exception("carton could not be created. Reported exception: " + ex);
        }

    }

    public static void batchUpdateBayNumbersOfBayCartons(Integer pallet_id, Integer bay_id) throws Exception {
        try {

            HashMap params = new HashMap();
            params.put("bay_id", bay_id);
            params.put("pallet_ip", pallet_id);
            DataSource.getSqlMapInstance().update("batchUpdateBayNumbersOfBayCartons", params);


        } catch (SQLException ex) {
            throw new Exception("carton could not be created. Reported exception: " + ex);
        }

    }

    public static void updatePalletProcessStatus(Long pallet_number, String new_process_status) throws Exception {
        try {

            HashMap params = new HashMap();
            params.put("pallet_number", pallet_number);
            params.put("new_status", new_process_status);
            DataSource.getSqlMapInstance().update("updatePalletProcessStatus", params);


        } catch (SQLException ex) {
            throw new Exception("Pallet process status could not be updated. Reported exception: " + ex);
        }

    }

    public static void updatePalletRunStats(Pallet pallet) throws Exception {

        DataSource.getSqlMapInstance().update("incrementPalletsCompleted", pallet);
    }


    public static void setPalletRtbOrderVals(Pallet pallet, String bay_num, String skip_ip) throws Exception {


        java.util.ArrayList log_entries = new java.util.ArrayList();


        //get summarised list of season_order records on pallet
        List<HashMap> season_orders = getPalletOrders(pallet.getId());


        for (HashMap season_order : season_orders) {

            if (season_order.get("customer_order_number") != null && !((String) season_order.get("customer_order_number")).toUpperCase().equals("N.A.")) {
                decreaseOrder(season_order);
                java.util.HashMap log_entry = new java.util.HashMap();
                log_entry.put("customer_order_number", season_order.get("customer_order_number"));
                log_entry.put("season_code", season_order.get("season_code"));
                log_entry.put("season_order_quantity_id", season_order.get("id"));
                log_entry.put("n_cartons_of_pallet", season_order.get("n_cartons_on_order"));
                log_entry.put("last_carton_on_bay", 1);
                log_entry.put("pallet_number", pallet.getPallet_number());
                log_entry.put("quantity_added_to_order", 0);
                log_entry.put("quantity_removed_from_order", season_order.get("n_cartons_on_order"));
                log_entry.put("skip_ip", skip_ip);
                log_entry.put("bay_num", bay_num);
                log_entries.add(log_entry);


            }
        }

        //updateCartonOrderNums
        HashMap params = new HashMap();
        params.put("new_order_num", null);
        params.put("pallet_id", pallet.getId());
        DataSource.getSqlMapInstance().update("updateCartonOrderNums", params);
        logOrderAdjustments(log_entries);


    }


    private static void set_pallet_account(Pallet pallet) throws SQLException {

        HashMap acc_details = (HashMap) DataSource.getSqlMapInstance().queryForObject("getPalletAccounts", pallet);
        long n_accs = (Long) acc_details.get("count");
        String account_code = (String) acc_details.get("account_code");

        if (n_accs > 1)
            pallet.setAccount_code("6512");
        else
            pallet.setAccount_code(account_code);


    }


    private static void setPalletOrderVals(Pallet pallet, String bay_num, String skip_ip) throws Exception {

        //get last carton
        Carton last_carton = getLastCartonOnBay(bay_num, skip_ip);
        String carton_season_code = last_carton.getSeason_code() + "_" + last_carton.getCommodity_code();
        HashMap last_carton_order = null;

        java.util.ArrayList log_entries = new java.util.ArrayList();


        //get summarised list of season_order records on pallet
        List<HashMap> season_orders = getPalletOrders(pallet.getId());

        if (season_orders.size() == 0)
            return;

        for (HashMap season_order : season_orders) {
            if (!(season_order.get("customer_order_number").equals(last_carton.getOrder_number()) && season_order.get("season_code").equals(carton_season_code))) {
                //decrement
                if (season_order.get("customer_order_number") != null && !((String) season_order.get("customer_order_number")).toUpperCase().equals("N.A.")) {
                    decreaseOrder(season_order);
                    java.util.HashMap log_entry = new java.util.HashMap();
                    log_entry.put("customer_order_number", season_order.get("customer_order_number"));
                    log_entry.put("season_code", season_order.get("season_code"));
                    log_entry.put("season_order_quantity_id", season_order.get("id"));
                    log_entry.put("n_cartons_of_pallet", season_order.get("n_cartons_on_order"));
                    log_entry.put("last_carton_on_bay", last_carton.getCarton_number());
                    log_entry.put("pallet_number", last_carton.getPallet_number());
                    log_entry.put("quantity_added_to_order", 0);
                    log_entry.put("quantity_removed_from_order", season_order.get("n_cartons_on_order"));
                    log_entry.put("skip_ip", skip_ip);
                    log_entry.put("bay_num", bay_num);
                    log_entries.add(log_entry);


                }
            } else {
                last_carton_order = season_order;
            }
        }

        //increment the order of the last carton
        if (last_carton.getOrder_number() != null && !last_carton.getOrder_number().toUpperCase().equals("N.A.")) {
            increaseOrder(pallet.getCarton_quantity_actual().longValue(), (Integer) last_carton_order.get("id"));

            java.util.HashMap log_entry = new java.util.HashMap();
            log_entry.put("customer_order_number", last_carton_order.get("customer_order_number"));
            log_entry.put("season_code", last_carton_order.get("season_code"));
            log_entry.put("season_order_quantity_id", last_carton_order.get("id"));
            log_entry.put("n_cartons_of_pallet", last_carton_order.get("n_cartons_on_order"));
            log_entry.put("last_carton_on_bay", last_carton.getCarton_number());
            log_entry.put("pallet_number", last_carton.getPallet_number());
            log_entry.put("quantity_added_to_order", pallet.getCarton_quantity_actual());
            log_entry.put("quantity_removed_from_order", 0);
            log_entry.put("skip_ip", skip_ip);
            log_entry.put("bay_num", bay_num);
            log_entries.add(log_entry);
        }

        //updateCartonOrderNums
        HashMap params = new HashMap();
        params.put("new_order_num", last_carton.getOrder_number());
        params.put("pallet_id", last_carton.getPallet_id());
        DataSource.getSqlMapInstance().update("updateCartonOrderNums", params);
        logOrderAdjustments(log_entries);


    }

    public static void logOrderAdjustments(ArrayList<HashMap> log_entries) throws Exception {
        try {

            for (HashMap log_entry : log_entries) {

                DataSource.getSqlMapInstance().insert("logOrderAdjustments", log_entry);
            }


        } catch (SQLException ex) {
            throw new Exception("Order adjustments could not be logged. Reported exception: " + ex);
        }

    }


    public static void completePallet(Pallet pallet, boolean transaction_is_open, String bay_num, String skip_ip) throws Exception {
        try {
            if (!transaction_is_open)
                DataSource.getSqlMapInstance().startTransaction();


            Integer actual_qty = (Integer) DataSource.getSqlMapInstance().queryForObject("getPalletCartonCount", pallet.getPallet_number());
            pallet.setCarton_quantity_actual(actual_qty);

            // Integer actual_qty = pallet.getCarton_quantity_actual();

            if (actual_qty > 0) {
                Carton last_carton = getLastCartonOnBay(bay_num, skip_ip);
                pallet.setOrder_number(last_carton.getOrder_number());
            }


            java.sql.Timestamp oldest_pack_time = (java.sql.Timestamp) DataSource.getSqlMapInstance().queryForObject("getOldestPackDateTime", pallet.getId());
            pallet.setOldest_pack_date_time(oldest_pack_time);
            pallet.setProcess_status("PALLETIZED");
            pallet.setDate_time_completed(new java.sql.Timestamp(new java.util.Date().getTime()));



            if (actual_qty == null)
                throw new Exception("Actual quantity of in-memory pallet is null");

            Integer req_qty = pallet.getCpp();
            if (req_qty == null)
                throw new Exception("Required quantity of in-memory pallet is null");

            String build_status = "";
            if (actual_qty < req_qty || actual_qty > req_qty)
                build_status = "PARTIAL";
            else
                build_status = "FULL";
            pallet.setBuild_status(build_status);

            //---------------------------------------------------------------------------------------------------
            //Find the current qc values of pallet- this is correct on pallet record in db, since it was updated
            //by the PPECB web program- the in-memory pallet's qc values is out of sync
            //---------------------------------------------------------------------------------------------------
            Pallet qc_pallet = getPalletByNumber(pallet.getPallet_number());
            if (qc_pallet.getQc_result_status() != null)
                pallet.setQc_result_status(qc_pallet.getQc_result_status());

            if (qc_pallet.getPpecb_inspection_id() != null)
                pallet.setPpecb_inspection_id(qc_pallet.getPpecb_inspection_id());

            if (qc_pallet.getQc_status_code() != null)
                pallet.setQc_status_code(qc_pallet.getQc_status_code());
            else
                pallet.setQc_status_code(null);

            if (actual_qty > 0)
            set_pallet_account(pallet);

            DataSource.getSqlMapInstance().update("completePallet", pallet);
            DataSource.getSqlMapInstance().update("propagatePalletQcVals", pallet);

            //DataSource.getSqlMapInstance().update("incrementPalletsCompleted",pallet);
            ProductLabelingDAO.updateRunStats(null, null, null, pallet);
            //updatePalletRunStats(pallet);
            //remove all cartons from bay
            if (actual_qty > 0)
               setPalletOrderVals(pallet, bay_num, skip_ip);

            PalletisingDAO.clearPalletFromBay(pallet.getId());


            if (!transaction_is_open)//will only be false from confirmPartialComplateState call
            {
                DataSource.getSqlMapInstance().commitTransaction();
                //               if(actual_qty > 0)
                //DeviceScan.send_integration_record("pallet_completed",pallet.getId().toString(),"Pallet");
            }


        } catch (SQLException ex) {
            throw new Exception("Complete pallet transaction. Reported exception: " + ex);
        } finally {
            //if(!transaction_is_open)
            //	DataSource.getSqlMapInstance().endTransaction();
        }
    }

    public static void clearPalletFromBay(Long pallet_id) throws Exception {
        try {


            DataSource.getSqlMapInstance().delete("clearPalletFromBay", pallet_id);


        } catch (SQLException ex) {
            throw new Exception("Pallet's cartons could not be cleared from bay. Reported exception: " + ex);
        }

    }

    public static void decreaseOrder(HashMap cartons_per_order) throws Exception {
        try {

            DataSource.getSqlMapInstance().update("decreaseOrder", cartons_per_order);


        } catch (SQLException ex) {
            throw new Exception("Order could not be decreased. Reported exception: " + ex);
        }

    }


    public static void increaseOrder(Long amount_to_add, Integer new_id) throws Exception {
        try {
            HashMap params = new HashMap();
            params.put("amount_to_add", amount_to_add);
            params.put("new_id", new_id);
            DataSource.getSqlMapInstance().update("increaseOrder", params);


        } catch (SQLException ex) {
            throw new Exception("Order could not be decreased. Reported exception: " + ex);
        }

    }

    public static void updatePalletCartonQuantity(Long pallet_number, Integer new_quantity) throws Exception {
        try {

            HashMap params = new HashMap();
            params.put("pallet_number", pallet_number);
            params.put("new_quantity", new_quantity);
            DataSource.getSqlMapInstance().update("updatePalletCartonCount", params);


        } catch (SQLException ex) {
            throw new Exception("Pallet's carton quantity could not be updated. Reported exception: " + ex);
        }

    }


}
