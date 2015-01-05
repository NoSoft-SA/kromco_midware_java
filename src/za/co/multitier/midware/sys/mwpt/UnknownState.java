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
import za.co.multitier.midware.sys.datasource.PalletizingCriteria;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;
import za.co.multitier.midware.sys.datasource.ProductionRun;

/**
 *PURPOSES OF THIS CLASS
 * 1) To provide default behaviour, in the sense of
 *   a)  The transactional context is unclear and we need to provide sensible behaviour
 *        e.g when our Palletizing cache was cleared unexpectatly
 *   b)  Standard Behaviour that is needed by most Palletizing states
 *
 *  HOW DO WE ENTER INTO THIS STATE?
 *  A) If the main transaction found the in-memory bay to be empty, in which case
 *     the 'special' populate_bay method will be called- which will construct state for
 *     following scenarios:
 *     a) Restoration of bay state due to unexpectec deletion of entite app's memory in process space
 *     b) New pallet creation 
 *  B) A subclass of this class did not override or delegated the main interface methods
 *  
 */
public class UnknownState extends DefaultState
{
	
	
	/** Creates a new instance of ConfirmRemoveState */
	public UnknownState(DeviceCommands.ROBOT_KEYPAD button_pressed,Long carton_num, String skip_ip,String bay_num,PalletizingAction parent)
	{
		super(button_pressed,carton_num,skip_ip,bay_num,parent,false);
	}
	
	
	
	public String getRobotScreen()
	{
		
		return this.parent.createErrorRobotScreen(this.parent.UNKNOWN_STATE,"");
	}
	
	

	
	
}
