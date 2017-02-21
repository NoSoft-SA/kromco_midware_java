/*
 * PalletizingState.java
 *
 * Created on February 20, 2007, 4:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpt;

import za.co.multitier.midware.sys.appservices.DeviceCommands;

/**
 *
 * @author Administrator
 */
public abstract class PalletizingState
{
	
	protected String skip_ip;
	protected String bay_num;
	protected DeviceCommands.ROBOT_KEYPAD button;
	protected Long carton_num;
	protected PalletizingAction parent;
	protected String robot_screen;
	public int transaction_cycle = 0;
	public boolean is_error_screen = false;
	
	public void setButtonPressed(DeviceCommands.ROBOT_KEYPAD button)
	{
		this.button = button;
	}
	
	/** Creates a new instance of PalletizingState */
	public PalletizingState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		this.button = button_pressed;
		this.carton_num = carton_num;
		this.skip_ip =  skip_ip;
		this.bay_num = bay_num;
		this.parent = parent;
				
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------
	//This method is needed, because of the way that instances of subclasses of this class
	//(which represent transactions across client-server roundtrips) are managed in memory:
	//E.g: when one state(instance) transitions to another the command_state ( scanned carton, which button was pressed and from which skip and bay)
	//the new state is created with the command state of it's originator. The new state is then stored in memory. On the next roundtrip,
	//the client sends a new command state, but the transaction is still sitting with the old. The transaction controller (PalletizingAction class)
	//will therefore always ensure that the commandstate is updated by calling this method, before calling 'carton_scanned()' or 'button_pressed()'
	//NOTE: the transitioned to state can be asked to do it's thing within the same server instance of a transaction (PalletizingAction) i.e. without
	//the new state having to update it's state- this updating is needed where a transaction spans more than one client-server roundtrip (or putting it
	//another way: for transactions that live ACROSS more than one instance of the main transaction class: PalletizingAction
	//-----------------------------------------------------------------------------------------------------------------------------------------------
	public void update_command_state(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		this.button = button_pressed;
		this.carton_num = carton_num;
		this.skip_ip =  skip_ip;
		this.bay_num = bay_num;
		this.parent = parent;
	}
	
	public String getRobotScreen()throws Exception
	{ 
	   return this.parent.WELCOME_SCREEN;
	}
	
	//It is the responsibility of the calling program to call the correct interface method
	public abstract void button_pressed() throws Exception;
	
	
	public abstract void carton_scanned() throws Exception;
	
}
