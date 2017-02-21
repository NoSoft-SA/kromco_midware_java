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
public class PalletBuildingState extends DefaultState
{
	
	
	public String getRobotScreen() throws Exception
	{
		build_palletizing_screen();
		return this.robot_screen;
	}
	
	/** Creates a new instance of ConfirmRemoveState */
	public PalletBuildingState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
	}
	
	
	//DEFAULT BEHAVIOUR OF SUPERCLASS WILL HANDLE CARTON SCAN AND BUTTON PRESS
	
}
