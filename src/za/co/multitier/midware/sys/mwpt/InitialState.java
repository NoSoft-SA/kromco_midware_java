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
public class InitialState extends DefaultState
{
	
      
	
	public String getRobotScreen()
	{
               
		  return parent.createNormalRobotScreen(this.parent.EMPTY_BAY_MSG,this.parent.WELCOME_MSG); 
                
	}
	

	/** Creates a new instance of ConfirmRemoveState */
	public InitialState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent) throws Exception
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
		//============================================================================================
		//InitialState is created when the bay is empty and a carton is scanned to that bay, EXCLUDING
		//return to bay (which will be preceded with a button 1 key_press)
		//InitialState need to do the following:
		//  1) Make sure the carton is valid
		//  2) call populate bay to collect and store all relevant palletizing state in this bay
		//  3) create the pallet and store it in the bay
		//  4) transit to PalletBuildingState
		//==============================================================================================
		
		//Use the carton_scanned method of superclass to perform the validations
		
		
	}
	
//	public void button_pressed() throws Exception
//	{
//		throw new Exception("Button press cannot be called on InitialState");
//			
//	}
	
	
	public void carton_scanned() throws Exception
	{
		super.carton_scanned();
		
	}
	
	
}
