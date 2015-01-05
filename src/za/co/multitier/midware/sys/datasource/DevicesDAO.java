/*
 * DevicesDAO.java
 *
 * Created on January 25, 2007, 1:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

import java.sql.SQLException;
import java.util.HashMap;

public class DevicesDAO
{
	
   //getDeviceByCodeAndSequence
	public static ActiveDevice getActiveDevice(String device_code,String day_line_sequence) throws Exception
	{
		try
		{
			if(device_code == null)
			   throw new Exception("device code is empty");
			
			if(day_line_sequence == null)
			   throw new Exception("day_line_sequence");
			
			HashMap params = new  HashMap();
			params.put("active_device_code",device_code);
			params.put("day_line_batch_number",day_line_sequence);
			
			ActiveDevice device = (ActiveDevice) DataSource.getSqlMapInstance().queryForObject("getDeviceByCodeAndSequence", params);
			return device;
		} catch (SQLException ex)
		{
			throw new Exception("Active device could not be fetched. Reported exception: " + ex);
		}
		
	}
	
	
	public static ActiveDevice getActiveDevice(String device_code) throws Exception
	{
		try
		{
			
			ActiveDevice device = (ActiveDevice) DataSource.getSqlMapInstance().queryForObject("getDevice", device_code);
			return device;
		} catch (Exception ex)
		{
			throw new Exception("Active device could not be fetched. Reported exception: " + ex);
		}
		
	}
	
}
