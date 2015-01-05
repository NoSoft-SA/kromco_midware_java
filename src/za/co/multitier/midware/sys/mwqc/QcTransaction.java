/** ***************************************************************************
 * Class: 		QcTransaction
 * Description:	System J&J Mesware quality control transaction
 *
 * @author: 	Dr. J. Fourie & ...
 * @version	 	Rev 1.00
 *
 * Date: 		June 2006
 */
package	za.co.multitier.midware.sys.mwqc;


import java.sql.SQLException;
import za.co.multitier.sys.SysInterface;


import za.co.multitier.mesware.messages.MessageInterface;
import za.co.multitier.mesware.services.email.MailInterface;


import za.co.multitier.mesware.util.TransactionData;

import za.co.multitier.midware.sys.appservices.*;
import za.co.multitier.midware.sys.protocol.SysProtocol;

public class QcTransaction
{
	//
	// Constructor handle
	//
	public	QcTransaction               srv             = null;
	//
	// Interfaces
	//
	private static MessageInterface     msg             = null; // Implies msg must be available!!
	private static MailInterface        mail            = null; // Implies mail must be available!!

	
	private SysInterface				trn				= null;

	private TransactionData				trData			= null;

	// *************************************************************************
	// Constructor methods....
	// *************************************************************************
	/**
	 * Description: QcTransaction base class constructor

	 * @return      void

	 */
	public QcTransaction()
	{
        this.srv 	= this;
	}

	/**
	 * Description: QcTransaction base class constructor

	 * @return      void

	 */
	public QcTransaction(SysInterface trn, TransactionData trData)
	{
		this();

        this.trn 	= trn;
		this.trData = trData;

        this.msg 	= trData.msg;
		this.mail	= trData.mailClient;

	}

    // *************************************************************************
	// Processing methods...
	// *************************************************************************
	/**
	 * Method:		commitTransaction
	 * Description: Commit after MES layer has determined...
	 * 				This method is called by Mesware after the business transaction has been processed (but not committed)

	 * @return      Boolean (True=Commit successful)

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

	 * @return      String (scan code)
	 * @exception   NullPointerException
	 */
	public synchronized boolean processTransaction()
	{
		String destinationIP = trData.destAddr;
		String operator = trData.operator;
		String supervisor = trData.supervisor;
		String modeStr = trData.mode;
		String scanCode1 = trData.codeCollection[0];
		String scanCode2 = trData.codeCollection[1];
		String mass = trData.mass;

	    String resultStr = "";

		try
		{
			int mode = Integer.parseInt(modeStr);

			switch(mode)
			{
				case 1 : ;										// Trigger qc OUT transactions
					//
					// Scancode 1 will contain this scan
					// Scancode 2 will be blank
					//
					resultStr = PdtSymbol6800.handle_request("1","QualityControl",scanCode1,null,null,destinationIP,null);

                   //SysProtocol.TQUALITYCONTROL + "Status=\"true\" LCD1=\"Line 1\" LCD2=\"Line 2\" LCD3=\"Line 3\" LCD4=\"Line 4\" LCD5=\"Line 5\" LCD6=\"QC out ok\" />";

				break;

				case 2 : ;										// Trigger qc IN transactions
					//
					// Scancode1 = 1st code scanned
					// Scancode2 = 2nd code scanned
					//
					// Note: The actions of the operator is not defined - he can scan code 1 & 2 in any order
					//
					resultStr = PdtSymbol6800.handle_request("2","QualityControl",scanCode1,scanCode2,null,destinationIP,null);

                    //SysProtocol.TQUALITYCONTROL + "Status=\"true\" LCD1=\"Line 1\" LCD2=\"Line 2\" LCD3=\"Line 3\" LCD4=\"Line 4\" LCD5=\"Line 5\" LCD6=\"QC out ok\" />";

					break;

				case 3 : ;										// Operator or supervisor response = YES
					// Demo only
					// ('Yes')
					//
					//resultStr = SysProtocol.TQUALITYCONTROL + "Status=\"true\" RunNumber=\"KRM-12-23-34\" Red=\"false\" Yellow=\"false\" Green=\"true\" Msg=\"Yes ok\" />";
				break;

				case 4 : ; 										// Operator or supervisor response = NO
					// Demo only
					// ('No')'
					//
					//resultStr = SysProtocol.TQUALITYCONTROL + "Status=\"false\" RunNumber=\"KRM-12-23-34\" Red=\"false\" Yellow=\"false\" Green=\"true\" Msg=\"Mo ok\" />";
					break;
			} // switch
		}
		catch(NullPointerException npe) {}
		catch(Exception ex) {
		  int i = 3;
		}

		finally
		{
            trData.addRecordToReturnData(resultStr);
			msg.sysMsg("QC STR: " + resultStr); // HANS...
                         try {//HZ

                           za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().endTransaction();
                         } catch (SQLException ex) {
                          ex.printStackTrace();
                        }
			return true;
		}
    }
} // QcTransaction
