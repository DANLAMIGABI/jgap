package cApplicationIface;

import java.util.HashMap;

import org.jgap.IApplicationData;

import cApplication.CApplicationStorage;

public interface ICAppStorage extends IApplicationData {
	
	/*disabled
	public void setPlace(String place);
	public void setAmount(int amount);
	public void setBadget(double unitCost);
	public int getAmount();
	public double getBadget();
	public String getPlace();
	*/
	
	public void setID(int ID);
	public int getID();
	
	public void merge(CApplicationStorage store);

	//testing
	public void setCharacteristic(HashMap<String, String> characteristic);
	public HashMap<String, String> getCharacteristic();

	
}
