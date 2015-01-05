/*
 * ConfirmRemoveState.java
 *
 * Created on February 20, 2007, 4:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.mwpt;

import java.sql.SQLException;
import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.datasource.Bay;
import za.co.multitier.midware.sys.datasource.DataSource;
import za.co.multitier.midware.sys.datasource.Pallet;
import za.co.multitier.midware.sys.datasource.PalletisingDAO;
import za.co.multitier.midware.sys.datasource.PalletizingCriteria;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;

/**
 *
 * @author Administrator
 */



public class DrawInspectionCartonState extends DefaultState
{
	
	
	private Long scanned_carton;
	private boolean is_valid_rtb_carton;
	private Pallet pallet;
	private boolean await_rtb_answer = false;
	
	public String getRobotScreen()
	{
		if(this.robot_screen == null)
		 return this.parent.createWarningRobotScreen(this.parent.SCAN_INSPECTION_CTN_PROMPT,"");
		else
			return this.robot_screen;
	}
	
	
	/** Creates a new instance of ConfirmRemoveState */
	public DrawInspectionCartonState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
	}
	
	
	//==================================================================
	//RTB Validation rules:
	//1) carton must exist
	//2) bay must not be empty
	//2) cannot belong to other completed bay
	//3) must belong to the pallet on this bay
	//4) carton must exist on this bay
	//5) pallet's qc status must not be 'inspected' or 'inspecting'
	//===================================================================
	private boolean isValidInspectionDraw() throws Exception
	{
		String invalid_reason = "";
		
			if(this.is_carton_valid()) //this method will set the state to remove by itself if invalid
			{
				if(this.isBayEmpty())
		 			invalid_reason = this.parent.EMPTY_BAY_MSG;
				
				else if(this.carton.getPallet_id()== null)
					invalid_reason = this.parent.NO_PALLET_FOR_CARTON_MSG;
		
				else
				{
					
					this.pallet = PalletisingDAO.getBayPallet(this.bay_num,this.skip_ip);
					if(!this.pallet.getId().equals(this.carton.getPallet_id()))
						invalid_reason = this.parent.CARTON_OF_OTHER_PALLET;
					else
					{ 
						if(this.pallet.getQc_status_code()== null)
						   throw new Exception("qc_status_code of pallet is null!");
						
						String qc_status = this.pallet.getQc_status_code().toUpperCase();
						if(qc_status.equals("INSPECTING"))
							invalid_reason = this.parent.PALLET_BEING_INSPECTED_MSG;
						else if(qc_status.equals("INSPECTED"))
							invalid_reason = this.parent.PALLET_ALREADY_INSPECTED_MSG;
						else if(PalletisingDAO.getBayCarton(this.carton.getCarton_number(),this.bay_num,this.skip_ip)== null)
							invalid_reason = this.parent.CARTON_NOT_LINKED_TO_BAY_MSG;
						
						
					}
				
					
				}
				
				   if(!invalid_reason.equals(""))	
						this.setRemoveCtnState(invalid_reason,this.parent.REMOVE_CARTON_MSG);
				
					return (invalid_reason.equals(""));
			}
			else
				return false;
			
	}
	
	
	private void inspectionDrawTransaction() throws Exception
	{
		try
		{
			DataSource.getSqlMapInstance().startTransaction();
		    PalletisingDAO.setCartonInspectionDateTime(this.carton.getId(),false);
			PalletisingDAO.setCartonInspectionFlag(this.carton.getId(),true);
			PalletisingDAO.setPalletQcStatus(this.pallet.getPallet_number(),"INSPECTING");
		   //TO DO : QC RECORD CREATION
			DataSource.getSqlMapInstance().commitTransaction();
			
			//now update the in-memory pallet with these details
			getBay().getState_pallet().setQc_status_code("INSPECTING");
			
				
		} catch (Exception ex)
		{
			
			throw new Exception("Inspection carton return failed. Reported exception: " + ex.toString());
		}
		
		finally
		{
			//DataSource.getSqlMapInstance().endTransaction();
		}
		
	}
	
	
	public void button_pressed() throws Exception
	{
		
		//NO IMPLEMENTATION NEEDED- OTHER THAN OVERRIDING THE DAFAULT STATE'S BEHAVIOUR WITH NOTHING- RESULTING
		//IN THE DEFAULT SCREEN JUST BEING RE-RENDERED (IF ANY BUTTON IS PRESSED WHILE THIS STATE IS ACTIVE)
			
	}
	
	
	public void carton_scanned() throws Exception
	{
		this.scanned_carton = this.carton_num;
	    if(this.transaction_cycle > 0)
		{
			if(isValidInspectionDraw()) //this method will create(transition to) the appropriate error state itself
			{
				this.inspectionDrawTransaction();
				this.parent.active_state = this.calculateBaseState();
			}
		}
		

	
	}
	
	
}
