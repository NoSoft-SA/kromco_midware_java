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

/**
 *
 * @author Administrator
 */
public class PreInitialState extends DefaultState
{
	
	
	/** Creates a new instance of ConfirmRemoveState */
	public PreInitialState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
	}
	

	
	public String getRobotScreen()
	{
	   return this.parent.WELCOME_SCREEN;
	}
	
	
	
	
	
}
