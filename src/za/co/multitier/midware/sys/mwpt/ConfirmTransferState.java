/*
 * ConfirmRemoveState.java
 *
 * Created on February 20, 2007, 4:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpt;

import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.appservices.MidwareCache;
import za.co.multitier.midware.sys.datasource.Bay;
import za.co.multitier.midware.sys.datasource.Carton;
import za.co.multitier.midware.sys.datasource.Pallet;
import za.co.multitier.midware.sys.datasource.PalletisingDAO;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;

/**
 *
 * @author Administrator
 */

//========================================================================================
//This class manages the confirmation of transferring a carton from another pallet/bay
//to this bay. It needs to do the following:
//1) Manage yes/no confirmation
//2) If no- transfer back to the calling state- but prevent it from processing the carton
//          by setting the constructor variable: 'scan_processed_by_preceding_state' to true
//3) If yes:
//          a) open a transaction and
//              1) do CartonMatchPalletTransaction
//              2) subtract a carton from the actual count of the donor bay
//              3) commit transaction
//          b) leave a message on line 1 or 2 of the donor bay's next transaction
//          c) transfer back to the calling state, but allow it to process the carton
//          by setting the constructor variable: 'scan_processed_by_preceding_state' to true
//========================================================================================


public class ConfirmTransferState extends DefaultState
{
	
	
	public String getRobotScreen()
	{
		return parent.createWarningRobotScreen(this.parent.TRANSFER_PROMPT,"F4(Yes) or F5(no)");
	}
	
	
	
	/** Creates a new instance of ConfirmRemoveState */
	public ConfirmTransferState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,
		                      PalletizingAction parent,boolean scan_processed)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,scan_processed);
		this.last_carton_scanned = carton_num;
		
	}
	
	
  
	
	
	private void transferCartonTransaction() throws Exception
	{
		boolean open_trans = false;
		if(this.calculateBaseState() instanceof InitialState)
			{
				this.createPalletOnBayTransaction();
			    open_trans = true;
			}
			
			Pallet this_pallet = getBay().getState_pallet();
			this.carton_num = this.last_carton_scanned;
			Carton carton = ProductLabelingDAO.getCarton(this.last_carton_scanned);
			Pallet donor_pallet = ProductLabelingDAO.getPallet(carton.getPallet_id());
			Bay donor_bay = PalletisingDAO.getBayForPallet(donor_pallet.getId());
			//Try and use the in-memory bay, so we can send the carton transferred message in the next transaction on that bay
			Bay cached_donor_bay = (Bay)MidwareCache.getDevicesCache().getDeviceState(donor_bay.getSkip_ip(),donor_bay.getBay_code());
			if(cached_donor_bay != null)
				donor_bay = cached_donor_bay;
			
			
			this.AddCartonToPalletTransaction(this_pallet,donor_pallet,donor_bay,open_trans);
			
			
	
	}
   
	//-----------------------------------------------------------------------------------------------
	//Overridden method from base state class- the only difference being that the carton number
	//is not set to the latest scan. This is needed for scenario where the user scans another carton
	//instead of pressing F4 or F4- resulting in the original carton number being replaced by the
	//latest one- which is incorrect. The original carton scanned with transfer intention must
	//always be the one transferred- all subsequent scans (before user pressed F4) must be discarded
	//-----------------------------------------------------------------------------------------------
	public void update_command_state(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		this.button = button_pressed;
		this.skip_ip =  skip_ip;
		this.bay_num = bay_num;
		this.parent = parent;
	}
	
	
	public void button_pressed() throws Exception
	{
		
		if(this.button == DeviceCommands.ROBOT_KEYPAD.btn4)//YES
			transferCartonTransaction();
		else  if(this.button == DeviceCommands.ROBOT_KEYPAD.btn5)
			this.parent.active_state = this.calculateBaseState();
		
		
	}
	
	
	
	
	public void carton_scanned()
	{
		
	}
	
	
}














