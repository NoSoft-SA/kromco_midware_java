/** ***************************************************************************
 * Class: 		CmTransaction
 * Description:	System J&J Mesware container move transaction
 *
 * @author: 	Dr. J. Fourie & ...
 * @version	 	Rev 1.00
 *
 * Date: 		June 2006
 */
package	za.co.multitier.midware.sys.mwcm;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

import za.co.multitier.sys.SysInterface;

import za.co.multitier.midware.sys.protocol.SysProtocol;

import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.mesware.services.email.MailInterface;


import za.co.multitier.mesware.util.TransactionData;
import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.appservices.Logger;
import za.co.multitier.midware.sys.appservices.MidwareConfig;

public class CmTransaction
{
	//
	// Constructor handle
	//
	public	CmTransaction               srv             = null;
	//
	// Interfaces
	//
	private static MessageInterface     msg             = null; // Implies msg must be available!!
	private static MailInterface        mail            = null; // Implies mail must be available!!

	
	private SysInterface				trn				= null;

	private TransactionData				trData			= null;

    public static Properties settings = MidwareConfig.getInstance().getSettings();
    public static String root_log_path = null;
    public static String log_transactions = "on";

    static
	{
        root_log_path		= (String)settings.get("bintipping_log_root");
        log_transactions	= (String)settings.get("log_bintip_transactions");
    }


	public CmTransaction()
	{
        this.srv 	= this;
	}


	public CmTransaction(SysInterface trn, TransactionData trData)
	{
		this();

        this.trn 	= trn;
		this.trData = trData;

        this.msg 	= trData.msg;
		this.mail	= trData.mailClient;

	}

     public static String getBintipFileName(String ip) throws Exception {
        //derive the folder name and create if non existing and derive and return filename
        String folder_name = root_log_path + "/bintipper_" + ip;
        File folder = new File(folder_name);
        if(folder.exists() == false) {
            synchronized(folder) {

                if(folder.mkdir()== false)
                    throw new Exception("folder: " + folder.getPath() + " could not be created");
            }
        }

        //derive filename
        String file_name = folder_name + "/";
        file_name += "Day_" + Logger.getFormattedTodayDate() + ".txt";
        return file_name;

    }

    public synchronized static void LogBeginTransaction(String ip,String scancode,String mode) throws Exception {
        try {
            if(!log_transactions.equals("on"))
                return;
            //get file_name, then append a line to the file as follows:
            //<time>:INPUT:SCANNED(<SCANCODE>),BUTTON_PRESSED(<BUTTON>)
            String file_name = getBintipFileName(ip);
            String line = Logger.getFormattedTime() + " " + "INPUT: scan_code(" + scancode + "), mode(" + mode + ")";
            Logger.appendToFile(file_name,line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public synchronized static void LogEndTransaction(String ip,String scancode,String mode,String output) throws Exception {
        try {
            if(!log_transactions.equals("on"))
                return;
            //get file_name, then append a line to the file as follows:
            //<time>:INPUT:SCANNED(<SCANCODE>),BUTTON_PRESSED(<BUTTON>)
            String file_name = getBintipFileName(ip);
            String line = Logger.getFormattedTime() + " " + "OUTPUT: scan_code(" + scancode + "), mode(" + mode + ")";
            line += "  SCREEN: " + output;
            Logger.appendToFile(file_name,line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // *************************************************************************
	// Processing methods...
	// *************************************************************************
	/**
	 * Method:		commitTransaction
	 * Description: Commit after MES layer has determined...
	 * 				This method is called by Mesware after the business transaction has been processed (but not committed)
	 * @param       Boolean (True=OK to commit)
	 * @return      Boolean (True=Commit successful)
	 * @exception   Sit Potensieele MidWare exceptions hier en in die code 
	 */
	 public synchronized boolean commitTransaction(boolean commit)
	 {
		 //
		 // Commit code goes here....
		 //
		 return true; // Gee die MES net 'n indikasie of jou commit OK deurgeloop het
	 }
	 
	/**
	 * Method:		processTransaction
	 * Description: Processes incoming data for this particular application...
	 * 				This method is called by Mesware after receiving incoming scan
	 * @param       String (Calling IP Number)
	 * @return      String (scan code)
	 * @exception   NullPointerException
	 */
	public synchronized boolean processTransaction()
	{
		
        String destinationIP = trData.destAddr;
        String operator = trData.operator;
        String supervisor = trData.supervisor;
        String scanCode = trData.codeCollection[0];
        String resultStr = "";
        String lotNumber = "";




		 try {

             lotNumber = trData.lotNumber;


             msg.sysMsg("BT ENTERED: lot:" + lotNumber + "; BIN: " + scanCode );
             //System.out.println("BT ENTERED: lot:" + lotNumber + "; BIN: " + scanCode );


            LogBeginTransaction(destinationIP,scanCode,trData.mode);
            int mode = Integer.parseInt(trData.mode);
            //F3 = mode

            switch(mode) {
                case 1 : ;										// Trigger bin tipping transactions

                //scanCode = DeviceScan.removeCharsFromRight(scanCode,1);

                resultStr = SysProtocol.TCONTAINERMOVE + "Status=\"true\" RunNumber=\"KRM-12-23-34\" Red=\"false\" Yellow=\"false\" Green=\"true\" Msg=\"Move ok\" />";
                resultStr = BinTippingScan.scanBin(msg,destinationIP,scanCode,DeviceCommands.TRM_KEYPAD.none,BinTippingScan.IS_NON_PRESORT);

                break;

                case 2 : ;										// Trigger carton tipping transactions
                //
                // Demonstrating: DBMS Access for scan data
                //
                // resultStr = processCartonTransactionData(scanCode);
                //
                // Inserting demo string...
                //
                resultStr = SysProtocol.TCONTAINERMOVE + "Status=\"true\" RunNumber=\"KRM-12-23-34\" Red=\"false\" Yellow=\"false\" Green=\"true\" Msg=\"Move ok\" />";
                break;

                case 3 : ;										// Operator or supervisor response = YES
                // F1 ('Yes')

                //resultStr = SysProtocol.TCONTAINERMOVE + "Status=\"true\" RunNumber=\"KRM-12-23-34\" Red=\"false\" Yellow=\"false\" Green=\"true\" Msg=\"Move ok\" />";
                resultStr = BinTippingScan.scanBin(msg,destinationIP,null,DeviceCommands.TRM_KEYPAD.F3,BinTippingScan.IS_NON_PRESORT);
                break;

                case 4 : ; //F2('No')										// Operator or supervisor response = NO

                resultStr = BinTippingScan.scanBin(msg,destinationIP,null,DeviceCommands.TRM_KEYPAD.F4,BinTippingScan.IS_NON_PRESORT);


                case 5 : ; //Dedicated pack	first state							// Operator or supervisor response = NO

                    resultStr = BinTippingScan.scanBin(msg,trData.moduleName,scanCode,DeviceCommands.TRM_KEYPAD.none,null);

                case 6 : ; //Dedicated pack	2nd state- result from MAF							// Operator or supervisor response = NO

                    resultStr = BinTippingScan.scanBin(msg,trData.moduleName,scanCode,DeviceCommands.TRM_KEYPAD.none,lotNumber);

                //resultStr = SysProtocol.TCONTAINERMOVE + "Status=\"false\" RunNumber=\"KRM-12-23-34\" Red=\"false\" Yellow=\"false\" Green=\"true\" Msg=\"Move ok\" />";
                break;
            } // switch


        } catch(NullPointerException npe) {} catch(Exception ex) {
            int i = 3;
        }

        finally {
            try {//HZ

                za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().endTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            msg.sysMsg("BINTIP PROCC STR: " + resultStr);
            //System.out.println(resultStr);
            //System.out.println("Mode: " + trData.mode + "; IP: " + trData.destAddr + " module_name: " + trData.moduleName );
             msg.sysMsg("Mode: " + trData.mode + "; IP: " + trData.destAddr + " module_name: " + trData.moduleName );


            try {
                LogEndTransaction(destinationIP,scanCode,trData.mode,resultStr);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            trData.addRecordToReturnData(resultStr);
            return true;
        }

		
    }
	
	/**
	 * Description: Processes the data collected by the bin transaction
	 * 				This method is called by Mesware after receiving incoming scan
	 * @param		String (scan code)
	 * @return      String (result string)
	 * @exception   NullPointerException, NumberFormatException
	 */
	public synchronized String processBinTransactionData(String scanCode)
	{
//		String		resultStr	= "";
//		ResultSet	rs1			= null;
//		
//		String[] sql1 = 
//		{
//			"SELECT * from containermovements where ContainerID='",
//			scanCode,
//			"';"
//		};
//
//		try
//		{
//			rs1 = trn.processSql(sql1);
//			if (rs1 != null)
//			{	
//				boolean r = true;
//
//				rs1.first();
//
//				String hdr			= SysProtocol.TCONTAINERMOVE + "Status=\"";
//				//
//				// Process out these values from resultsets...
//				//
//				String status		= "true";
//				String runNumber	= "KRM-00-01-02";
//				String red			= "false";
//				String yellow		= "false";
//				String green		= "true";
//				String message		= "Code: " + scanCode;
//
//				if (!r)
//				{
//					red		= "true";
//					green	= "false";
//					message	= "Code: " + scanCode;
//				}
//
//				resultStr =
//					hdr + 
//					status + "\" RunNumber=\"" + runNumber +
//					"\" Red=\"" + red + 
//					"\" Yellow=\"" + yellow + 
//					"\" Green=\"" + green + 
//					"\" Msg=\"" + message + "\" />";
//
//				msg.sysMsg("blue", resultStr);					
//			}
//		}
//		catch(NullPointerException npe) {}
//		catch(Exception ex) {}
//		
//		return resultStr;
	    return "";
	}	

	/**
	 * Description: Processes the data collected by the carton transaction
	 * 				This method is called by Mesware after receiving incoming scan
	 * @param		String (scan code)
	 * @return      String (result string)
	 * @exception   NullPointerException, NumberFormatException
	 */
	public synchronized String processCartonTransactionData(String scanCode)
	{
//		String		resultStr	= "";
//		ResultSet	rs1			= null;
//		
//		String[] sql1 = 
//		{
//			"SELECT * from containermovements where ContainerID='",
//			scanCode,
//			"';"
//		};
//
//		try
//		{
//			rs1 = trn.processSql(sql1);
//			if (rs1 != null)
//			{	
//				boolean r = true;
//
//				rs1.first();
//
//				String hdr			= SysProtocol.TCONTAINERMOVE + "Status=\"";
//				//
//				// Process out these values from resultsets...
//				//
//				String status		= "true";
//				String runNumber	= "KRM-00-01-02";
//				String red			= "false";
//				String yellow		= "false";
//				String green		= "true";
//				String message		= "Code: " + scanCode;
//
//				if (!r)
//				{
//					red		= "true";
//					green	= "false";
//					message	= "Code: " + scanCode;
//				}
//
//				resultStr =
//					hdr + 
//					status + "\" RunNumber=\"" + runNumber +
//					"\" Red=\"" + red + 
//					"\" Yellow=\"" + yellow + 
//					"\" Green=\"" + green + 
//					"\" Msg=\"" + message + "\" />";
//
//				msg.sysMsg("blue", resultStr);					
//			}
//		}
//		catch(NullPointerException npe) {}
//		catch(Exception ex) {}
//		
//		return resultStr;
	    return "";
	}	
	
} // CmTransaction
