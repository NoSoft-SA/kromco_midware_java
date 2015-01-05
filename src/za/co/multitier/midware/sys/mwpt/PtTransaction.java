/** ***************************************************************************
 * Class: 		PtTransaction
 * Description:	System J&J Mesware palletizing transaction
 *
 * @author: 	Dr. J. Fourie & ...
 * @version	 	Rev 1.00
 *
 * Date: 		July 2009
 */
package	za.co.multitier.midware.sys.mwpt;

import java.io.File;
import java.util.Properties;

import za.co.multitier.sys.SysInterface;

import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.mesware.services.email.MailInterface;


import za.co.multitier.mesware.util.TransactionData;

import za.co.multitier.mesware.factories.*;

import za.co.multitier.midware.sys.appservices.DeviceCommands.ROBOT_KEYPAD;
import za.co.multitier.midware.sys.appservices.DeviceScan;
import za.co.multitier.midware.sys.appservices.Logger;
import za.co.multitier.midware.sys.appservices.MidwareCache;
import za.co.multitier.midware.sys.appservices.MidwareConfig;
import za.co.multitier.midware.sys.protocol.SysProtocol;

public class PtTransaction
{
	//
	// Constructor handle
	//
	public	PtTransaction				srv				= null;
	//
	// Interfaces
	//
	private SysInterface				trn				= null;

	private static MessageInterface     msg             = null; // Implies msg must be available!!
	private static MailInterface        mail            = null; // Implies mail must be available!!


	private TransactionData				trData			= null;

	private FactoryInterface			mwptMap			= null;

    public static Properties settings = MidwareConfig.getInstance().getSettings();
    public static String root_log_path = null;
    public static String log_transactions = "on";

    static{

        root_log_path = (String)settings.get("palletizing_log_root");
        log_transactions = (String)settings.get("log_transactions");

    }


	public PtTransaction()
	{
        this.srv 	= this;
	}


	public PtTransaction(SysInterface trn, TransactionData trData)
	{
		this();

        this.trn 	= trn;
		this.trData = trData;

        this.msg 	= trData.msg;
		this.mail	= trData.mailClient;

        DeviceScan.setMailer(trn.getMailHandle());
	}

     public static String getPalletBayFileName(String ip,String bay) throws Exception {
        //derive the folder name and create if non existing and derive and return filename
        String folder_name = root_log_path + "/skip_" + ip;
        File folder = new File(folder_name);
        if(folder.exists() == false) {
            synchronized(folder) {

                if(folder.mkdir()== false)
                    throw new Exception("folder: " + folder.getPath() + " could not be created");
            }
        }

        //derive filename
        String file_name = folder_name + "/";
        file_name += "Bay_" + bay + "#" + Logger.getFormattedTodayDate() + ".txt";
        return file_name;

    }

    public synchronized static void LogBayTransaction(String ip,String bay,String scancode,String button) throws Exception {
        try {
            if(!log_transactions.equals("on"))
                return;
            //get file_name, then append a line to the file as follows:
            //<time>:INPUT:SCANNED(<SCANCODE>),BUTTON_PRESSED(<BUTTON>)
            String file_name = getPalletBayFileName(ip,bay);
            String line = Logger.getFormattedTime() + " " + "INPUT: scanned(" + scancode + "), button pressed(" + button.toString() + ")";
            Logger.appendToFile(file_name,line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public synchronized static void LogBayTransaction(String ip,String bay,String scancode,String button,String output) throws Exception {
        try {
            if(!log_transactions.equals("on"))
                return;
            //get file_name, then append a line to the file as follows:
            //<time>:INPUT:SCANNED(<SCANCODE>),BUTTON_PRESSED(<BUTTON>)
            String file_name = getPalletBayFileName(ip,bay);
            String line = Logger.getFormattedTime() + " " + "OUTPUT: scanned(" + scancode + "), button pressed(" + button.toString() + ")";
            line += "  SCREEN: " + output;
            Logger.appendToFile(file_name,line);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
	

	 public synchronized boolean commitTransaction(boolean commit)
	 {
		 //
		 // Commit code goes here....
		 //
		 return true; // Gee die MES net solank 'n indikasie of jou commit OK deurgeloop het
	 }
	 
	/**
	 * Description: Processes incoming data for this particular application...
	 * 				This method is called by Mesware after receiving incoming scan
	 * @return      String (scan code)
	 */
	public synchronized boolean processTransaction()
	{
	int mode = -1;
        String destinationIP = trData.destAddr;
        String operator = trData.operator;
        String supervisor = trData.supervisor;
        String bay = trData.bayNumber;
        String button = trData.buttonNumber;
        String scanCode = "";

        if(trData.codeCollection != null && trData.codeCollection.length > 0)
            scanCode = trData.codeCollection[0];

		String resultStr = "";
        boolean transaction_executed = true;

        //System.out.println("Called..." + bay + " " + scanCode + " ip:" + destinationIP + " button: " + button.toString() );

        String busyStr = SysProtocol.TPALLETIZE + "Status=\"true\" Red=\"false\" Yellow=\"true\" Green=\"false\" LCD1=\"Transaction busy\" LCD2=\"Please Wait...\" />";

        try
		{
            LogBayTransaction(destinationIP,bay,scanCode,button);
            if(MidwareCache.getDevicesCache().otherBusyTransaction(destinationIP,bay))
             {
                resultStr = busyStr;
                transaction_executed = false; //meaning this transaction could not execute, because another one is still busy
                
             }

            int bayNumber = Integer.parseInt(bay);
            int bayButton = Integer.parseInt(button);
            ROBOT_KEYPAD button_pressed = ROBOT_KEYPAD.none;

            scanCode = DeviceScan.removeCharsFromRightPt(scanCode,1);


            if(bayButton > 0)
                switch(bayButton) {
                    case 1:
                        button_pressed = ROBOT_KEYPAD.btn1;
                        break;
                    case 2:
                        button_pressed = ROBOT_KEYPAD.btn2;
                        break;
                    case 3:
                        button_pressed = ROBOT_KEYPAD.btn3;
                        break;
                    case 4:
                        button_pressed = ROBOT_KEYPAD.btn4;
                        break;
                    case 5:
                        button_pressed = ROBOT_KEYPAD.btn5;
                        break;
                }


                resultStr = new PalletizingAction(msg).process_action(destinationIP,bay,button_pressed,scanCode);

                //System.out.println(resultStr);


        } catch(NumberFormatException nfe) {} catch(NullPointerException npe) {} catch(Exception ex) {
            //System.out.println("Exception: " + ex.getMessage().toString());
            //System.out.println(resultStr);
        } finally {//HZ addition
            trData.addRecordToReturnData(resultStr);
            try {
                
                za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().endTransaction();
                if(transaction_executed == true)
                    MidwareCache.getDevicesCache().transactionDone(destinationIP,bay);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {

                LogBayTransaction(destinationIP,bay,scanCode,button,resultStr);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }

        
    }
	

	public synchronized String processTransactionData(int bayNumber, String scanCode)
	{
	    return "";
	}


	public synchronized String processTransactionButton(int buttonNumber)
	{
	    return "";
	}	
} // PtTransaction
