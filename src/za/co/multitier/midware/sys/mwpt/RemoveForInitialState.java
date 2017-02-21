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
import za.co.multitier.midware.sys.mwcm.*;

/**
 *
 * @author Administrator
 */
public  class RemoveForInitialState extends ConfirmRemoveState
{
	
	
	/** Creates a new instance of ConfirmRemoveState */
	public RemoveForInitialState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,
		                      PalletizingAction parent,String reason,Long last_carton_scanned)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,reason,last_carton_scanned);
		
	}
	
    
//	public String getRobotScreen()
//	{
//		if(this.reason == null)
//			this.reason = "";
//		
//		return this.parent.createErrorRobotScreen(this.parent.REMOVE_CARTON_MSG,this.reason);
//	}
	
	

	
	protected PalletizingState state_to_transit_to()
	{
		
		return new PreInitialState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
		
		
	}
	
	
	
}














