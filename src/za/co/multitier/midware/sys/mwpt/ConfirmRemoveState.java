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
public abstract class ConfirmRemoveState extends PalletizingState
{
	
	protected String reason = "";
	public String provided_command;
	public Long last_carton_scanned;
	
	
	public String getRobotScreen() throws Exception
	{
	   
	   create_remove_screen();
	   return this.robot_screen;
	   
	}
	
	/** Creates a new instance of ConfirmRemoveState */
	public ConfirmRemoveState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,
		                      PalletizingAction parent,Long last_carton_scanned)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent);
		this.last_carton_scanned = last_carton_scanned;
		
		
	}
	
	
	
	public ConfirmRemoveState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,
		                      PalletizingAction parent,String reason,Long last_carton_scanned)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent);
		this.reason = reason;
		this.last_carton_scanned = last_carton_scanned;
		
	}
	
	
	private void create_remove_screen()
	{
		if(this.provided_command == null)
			this.robot_screen = PalletizingAction.createErrorRobotScreen(PalletizingAction.REMOVE_CARTON_MSG,this.reason);
		else
			this.robot_screen = PalletizingAction.createErrorRobotScreen(this.provided_command,this.reason);
	}
	
	
	public void button_pressed()
	{
		if(this.transaction_cycle == 0)
		{
			create_remove_screen();
			return;
		}
			
		if(this.button == DeviceCommands.ROBOT_KEYPAD.btn4)
			
				this.parent.active_state = state_to_transit_to();
			
		else
			create_remove_screen();
	}
	
	
	protected abstract PalletizingState state_to_transit_to();
	
	
	public void carton_scanned()
	{
		create_remove_screen();
	}
	
	
}














