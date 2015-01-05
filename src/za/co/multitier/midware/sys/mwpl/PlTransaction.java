/** ***************************************************************************
 * Class: 		PlTransaction
 * Description:	System J&J Mesware product labelling transaction
 *
 * @author: 	Dr. J. Fourie & ...
 * @version	 	Rev 1.00
 *
 * Date: 		July 2009
 */
package za.co.multitier.midware.sys.mwpl;

import java.io.File;
import java.util.Properties;
import za.co.multitier.sys.SysInterface;

import za.co.multitier.mesware.messages.MessageInterface;


import za.co.multitier.mesware.services.email.MailInterface;

import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.mesware.util.TransactionData;
import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.Logger;
import za.co.multitier.midware.sys.appservices.MidwareCache;
import za.co.multitier.midware.sys.appservices.MidwareConfig;
import za.co.multitier.midware.sys.appservices.PdtSymbol6800;

public class PlTransaction {
    //
    // Constructor handle
    //

    public PlTransaction srv = null;
    //
    // Interfaces
    //
    private SysInterface trn = null;
    private static MessageInterface msg = null; // Implies msg must be available!!
    private static MailInterface mail = null; // Implies mail must be available!!

    private boolean do_db_commit = false;
    public static Properties settings = MidwareConfig.getInstance().getSettings();
    public static String root_log_path = null;
    public static String log_transactions = "on";
    public static String order_qty_to_address = "";
    public static String order_qty_from_address = "";
    private String ip = "";
    private String station = "";
    private ProductLabelScan labeling_scan = null;
    private String temp_mode = "";
    private String temp_mass = "";
    private String temp_scancode1 = "";
    private String temp_scancode2 = "";

    static {

        root_log_path = (String) settings.get("labeling_log_root");
        log_transactions = (String) settings.get("log_label_transactions");

        order_qty_to_address = (String) settings.get("order_qty_to_address");
        order_qty_from_address = (String) settings.get("order_qty_from_address");

        //System.out.println("to address: " + order_qty_to_address);
        //System.out.println("from address: " + order_qty_from_address);
    }

    public void set_do_db_transactio(boolean do_db_commit) {
        this.do_db_commit = do_db_commit;
    }
    private TransactionData trData = null;

  
    public PlTransaction() {
        this.srv = this;
    }


    public PlTransaction(SysInterface trn, TransactionData trData) {
        this();
        //hello
        this.trn = trn;
        this.trData = trData;

        this.msg = trData.msg;
        this.mail = trData.mailClient;

    }


    public synchronized boolean commitTransaction(boolean commit) {
        //System.out.println("enter commit: " + String.valueOf(commit));
        boolean success = false;
        try {
            if (commit == true) {
                if (this.do_db_commit == true) {
                    //System.out.println("about to commit");

                       za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().commitTransaction();
                    //System.out.println("about to commit");
                    
                    success = true;
                } else {
                    success = true;
                }
            } else {
                success = true;
            }
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                DeviceScan.handle_exception(msg, "Product Scan exception occurred for ip:" + this.ip + " and station code " + this.station,
                        ex.toString(), this.getClass().getName() + ".processLabelScan()", "LABELING", 0, "", ex.getStackTrace());
            } catch (Exception le) {
                le.printStackTrace();
            }
            success = false;
        } finally {
            try {
                za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().endTransaction();
                if (this.temp_mode.equals("5")) {
                    MidwareCache.getDevicesCache().rebinTransactionDone(this.temp_scancode1, this.temp_scancode2);
                }
                try {
                    this.LogCommitTransaction(this.ip, this.temp_scancode1, this.temp_scancode1, this.temp_mode, this.temp_mass, String.valueOf(commit));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return success;
    }

    public static String getLabelingFileName(String ip) throws Exception {
        //derive the folder name and create if non existing and derive and return filename
        String folder_name = root_log_path + "/label_station_" + ip;
        File folder = new File(folder_name);
        if (folder.exists() == false) {
            synchronized (folder) {

                if (folder.mkdir() == false) {
                    throw new Exception("folder: " + folder.getPath() + " could not be created");
                }
            }
        }

        //derive filename
        String file_name = folder_name + "/";
        file_name += "Day_" + Logger.getFormattedTodayDate() + ".txt";
        return file_name;

    }

    public synchronized static void LogBeginTransaction(String ip, String scancode1, String scancode2, String mode, String mass) throws Exception {
        try {
            if (!log_transactions.equals("on")) {
                return;
            }
            //get file_name, then append a line to the file as follows:
            //<time>:INPUT:SCANNED(<SCANCODE>),BUTTON_PRESSED(<BUTTON>)
            String file_name = getLabelingFileName(ip);
            String line = Logger.getFormattedTime() + " " + "INPUT: scanned1(" + scancode1 + ") scanned2(" + scancode2 + ") mode(" + mode + ") mass(" + mass + ")";
            Logger.appendToFile(file_name, line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public synchronized static void LogCommitTransaction(String ip, String scancode1, String scancode2, String mode, String mass, String commit) throws Exception {
        try {
            if (!log_transactions.equals("on")) {
                return;
            }
            //get file_name, then append a line to the file as follows:
            //<time>:INPUT:SCANNED(<SCANCODE>),BUTTON_PRESSED(<BUTTON>)
            String file_name = getLabelingFileName(ip);
            String line = Logger.getFormattedTime() + " " + "COMMIT: " + commit + "  scanned1(" + scancode1 + ") scanned2(" + scancode2 + ") mode(" + mode + ") mass(" + mass + ")";
            Logger.appendToFile(file_name, line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public synchronized static void LogEndTransaction(String ip, String scancode1, String scancode2, String result) throws Exception {
        try {
            if (!log_transactions.equals("on")) {
                return;
            }
            //get file_name, then append a line to the file as follows:
            //<time>:INPUT:SCANNED(<SCANCODE>),BUTTON_PRESSED(<BUTTON>)
            String file_name = getLabelingFileName(ip);
            String line = Logger.getFormattedTime() + " " + "OUTPUT: scanned1(" + scancode1 + ") scanned2(" + scancode2 + ")";
            line += "  print_data: " + result;
            Logger.appendToFile(file_name, line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Description: Processes incoming data for this particular application... 	<BR>
     * 				Called by mwserver after receiving incoming scans & data
     * @return      String (result string)
     * @exception   NullPointerException
     */
    public synchronized boolean processTransaction() {
        String destinationIP = trData.destAddr;
        String operator = trData.operator;
        String supervisor = trData.supervisor;
        String modeStr = trData.mode;
        String codeCollection[] = trData.codeCollection;
        String mass = trData.mass;

        String resultStr = "";
        String scancode1 = codeCollection[0];
        String scancode2 = "";
        boolean rebin_trans_executed = true;
        String bin_num = trData.binNumber;


        try {


            if (codeCollection.length >= 2) {
                scancode2 = codeCollection[1];
            } else {
                if (codeCollection[0].indexOf("_") > -1) {
                    String[] vals = codeCollection[0].split("_");
                    scancode2 = vals[1];

                }
            }

            LogBeginTransaction(destinationIP, scancode1, scancode2, modeStr, mass);
            this.ip = destinationIP;
            this.station = scancode1;
            this.temp_scancode1 = scancode1;
            this.temp_scancode2 = scancode2;
            this.temp_mode = modeStr;

            int mode = Integer.parseInt(modeStr);



            switch (mode) {
                case 5:
                    ;
                    // resultStr = processTransactionMode_5(codeCollection, mass);
                    //
                    // Inserting demo string for now...
                    //
                    if (MidwareCache.getDevicesCache().otherBusyRebinTransaction(scancode1, scancode2) == false)
					{
                        this.labeling_scan = new RebinLabelScan(destinationIP, mass, codeCollection, msg,operator);
                        this.labeling_scan.set_transaction(this);
                        resultStr = labeling_scan.processLabelScan();
                    } else
					{
                        this.set_do_db_transactio(false);
                        rebin_trans_executed = false;
                        resultStr = SysProtocol.TMSG + "Status=\"true\" Red=\"true\" Yellow=\"false\" Green=\"false\" Msg=\"Transaction busy. PLease wait...\" LCD1=\"\" LCD2=\"\" />";

                    }



                    // CartonLabelScan labeling_scan = new CartonLabelScan(destinationIP,mass,codeCollection,msg);
                    //resultStr = labeling_scan.processLabelScan ();


                    break;

                case 6:
                    ;


                    //System.out.println("CARTON LABEL INPUT: Mode: " + trData.mode + " IP: " + trData.destAddr + " Bin: " + trData.binNumber + " Scan1: " + scancode1 + " Scan2: " + scancode2);
                    msg.sysMsg("CARTON LABEL INPUT: Mode: " + trData.mode + " IP: " + trData.destAddr+ " Bin: " + trData.binNumber + " Scan1: " + scancode1 + " Scan2: " + scancode2);


                    this.labeling_scan = new CartonLabelScan(trData.destAddr, mass, codeCollection, msg, this.mail, order_qty_from_address, order_qty_to_address,bin_num);
                    this.labeling_scan.set_transaction(this);

                    resultStr = labeling_scan.processLabelScan();

                    break;

                case 10:
                    ;


                    //System.out.println("CARTON LABEL INPUT: Mode: " + trData.mode + " IP: " + trData.moduleAlias + " Bin: " + trData.binNumber + " Scan1: " + scancode1 + " Scan2: " + scancode2);
                    msg.sysMsg("CARTON LABEL INPUT: Mode: " + trData.mode + " IP: " + trData.moduleAlias + " Bin: " + trData.binNumber + " Scan1: " + scancode1 + " Scan2: " + scancode2);



                    this.labeling_scan = new CartonLabelScan(trData.moduleAlias, mass, codeCollection, msg, this.mail, order_qty_from_address, order_qty_to_address,bin_num);
                    this.labeling_scan.set_transaction(this);

                    resultStr = labeling_scan.processLabelScan();

                    break;




                case 7:
                    ;
                    // resultStr = processTransactionMode_7(codeCollection, mass);
                    //
                    // Inserting demo string for now...
                    //
//					resultStr =
//						SysProtocol.TPRODUCTLABEL + "Status=\"true\" RunNumber=\"KRM-E4-44-44\" Code=\"" +
//						codeCollection[0] +
//						"\" F0=\"E4\" F1=\"GRANNYS RA\" F2=\"GSG\" F3=\"COUNTS\" F4=\"MIXED\" F5=\"8\" F6=\"20A\" F7=\"20060526693885\" F8=\"53 kg\" F9=\"1\" F10=\"26 May 2006\" F11=\"DFGSD\" Msg=\"Carton " +
//						codeCollection[0] +
//						" Ok...\" />";


                    this.labeling_scan = new RebinLabelScan(destinationIP, mass, codeCollection, msg,operator);
                    this.labeling_scan.set_transaction(this);
                    resultStr = labeling_scan.processLabelScan();


                    break;

                case 8:
                    ;
                    //
                    // Inserting demo string for now...
                    //
                    resultStr = PdtSymbol6800.handle_request("3", "ProductLabel", codeCollection[0], null, null, destinationIP,null);

                    //SysProtocol.TPRODUCTLABEL + "Status=\"true\" LCD1=\"Line 1\" LCD2=\"Line 2\" LCD3=\"Line 3\" LCD4=\"Line 4\" LCD5=\"Line 5\" LCD6=\"QC out ok\" />";
                    break;

                case 9:
                    Long carton_num = null;
                    Double carton_weight = null;
                    try {
                        scancode1 = DeviceScan.removeCharsFromRightPt(scancode1,1);
                        carton_num = Long.parseLong(scancode1);
                        carton_weight = Double.parseDouble(mass);


                    } catch (NumberFormatException nfe) {
                        String num_err = "mass: " + mass.toString() + ". ctn_num: " + scancode1.toString();
                        resultStr = SysProtocol.TMSG + "Status=\"false\" Red=\"true\" Yellow=\"false\" Green=\"false\" Msg=\"Number format error: " + num_err + "\" />";
                        throw nfe;
                    }

                    za.co.multitier.midware.sys.datasource.ProductLabelingDAO.updateCartonWeight(carton_num, carton_weight);
                    resultStr = SysProtocol.TMSG + "Status=\"true\" Red=\"false\" Yellow=\"false\" Green=\"true\" Msg=\"Carton updated\" />";
                    break;


                default:
                    ;
                    break;
            }


        } catch (Exception ex) {
            System.out.println("unexpected exception: " + ex);
            if (resultStr.equals(""))
			{
                resultStr = SysProtocol.TMSG + "Status=\"false\" Red=\"true\" Yellow=\"false\" Green=\"false\" Msg=\"" + ex.toString() + "\" />";
            }

        } finally {
            //System.out.println("finally clause reached...");
            trData.addRecordToReturnData(resultStr);
            //System.out.println("FINALLY: " + resultStr);



            try {



                LogEndTransaction(destinationIP, scancode1, scancode2, resultStr);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return true;
        }
    }


    public synchronized String processTransactionMode_5(String[] codeCollection, String mass) {
        return "";
    }


    public synchronized String processTransactionMode_6(String[] codeCollection, String mass) {
        return "";
    }


    public synchronized String processTransactionMode_7(String[] codeCollection, String mass) {
        return "";
    }
} // PlTransaction

