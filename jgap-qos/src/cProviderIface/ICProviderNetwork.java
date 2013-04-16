package cProviderIface;

import java.util.HashMap;

import org.jgap.IApplicationData;

public interface ICProviderNetwork extends IApplicationData{

	/*disabled
	public void setBandwidth(int bandwidth);
	public void setUnitCost(double unitCost);
	public void setPlace(String place);
	public long getBandwidth();
	public double getUnitCost();
	public String getPlace();

	*/
		
	//testing
	public void setCharacteristic(HashMap<String, String> characteristic);
	public HashMap<String, String> getCharacteristic();
	
	
	
}
