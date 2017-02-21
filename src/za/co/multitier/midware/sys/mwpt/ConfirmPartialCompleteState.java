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
import za.co.multitier.midware.sys.datasource.PalletisingDAO;

/**
 *
 * @author Administrator
 */
public  class ConfirmPartialCompleteState extends DefaultState
{
	
	
	protected boolean transit_to_normal_building = true;
	protected boolean is_more_than_req = false;
	
	/** Creates a new instance of ConfirmRemoveState */
	public ConfirmPartialCompleteState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,
		                      PalletizingAction parent) throws Exception
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
		
		if(!getBay().isState_is_mixed_mode())
		  this.transit_to_normal_building = true;
	}
	
	public ConfirmPartialCompleteState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,
		                      PalletizingAction parent,boolean is_more_than_req) throws Exception
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
		
		this.is_more_than_req = is_more_than_req;
		if(!getBay().isState_is_mixed_mode())
		  this.transit_to_normal_building = true;
	}
	
	
	public String  getRobotScreen()
	{
		if(this.robot_screen == null)
		{
			if(this.is_more_than_req)
				 return  PalletizingAction.createWarningRobotScreen(this.parent.TOO_MANY_CARTONS_MSG,this.parent.PARTIAL_PALLET_PROMPT);
			else
				return  PalletizingAction.createWarningRobotScreen(this.parent.TOO_FEW_CARTONS_MSG,this.parent.PARTIAL_PALLET_PROMPT);
		}
		else
			return this.robot_screen;
	}

	
	public void button_pressed() throws Exception
	{
	   if(this.button == DeviceCommands.ROBOT_KEYPAD.btn4)
	   {

         if(this.carton == null)
              this.carton = PalletisingDAO.getLastCartonOnBay(this.bay_num,this.skip_ip);
           
		       Complete_pallet_transaction(false);

                 if( this.carton.getOrder_number()!= null && !this.carton.getOrder_number().toUpperCase().equals("N.A."))
                          this.send_order_quantity_info();

           MidwareCache.getDevicesCache().clearDeviceState(this.skip_ip,this.bay_num);
                 
		 this.parent.active_state = new  InitialState(this.button,this.carton_num,this.skip_ip,this.bay_num,this.parent);
	   }
	   else if(this.button == DeviceCommands.ROBOT_KEYPAD.btn5)
	   {
		  this.parent.active_state = this.calculateBaseState();
	   }
		  
		
	}
	
	
	public void carton_scanned()
	{
		//just do nothing: In this state we're waiting for yes/no: if user scan carton, this method is
		//called- in which we do nothing- which will result in the prompt screen being recreated when
		//the main transaction calls our getRobotScreen method
	}
	
	
}














