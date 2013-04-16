package cProviderIface;

import java.util.HashMap;

import org.jgap.IApplicationData;

public interface ICProviderStorage extends IApplicationData {
	
	/* disabled
	public void setAmount(int amount);
	public void setUnitCost(double unitCost);
	public void setPlace(String place);
	public int getAmount();
	public double getUnitCost();
	public String getPlace();
	
	*/
	
	
	//testing
	public void setCharacteristic(HashMap<String, String> characteristic);
	public HashMap<String, String> getCharacteristic();
	
	public void setID(int id);
	public int getID();
	

}
