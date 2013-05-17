package msApplicationIface;

import java.util.HashMap;

import msApplication.MSApplicationStorage;

import org.jgap.IApplicationData;


public interface IMSAppStorage extends IApplicationData {
	
	/*disabled
	public void setPlace(String place);
	public void setAmount(int amount);
	public void setBadget(double unitCost);
	public int getAmount();
	public double getBadget();
	public String getPlace();
	public void merge(CApplicationStorage store);
	*/
	
	public void setID(int ID);
	public int getID();
	
	

	//testing
	public void setCharacteristic(HashMap<String, Object> characteristic);
	public HashMap<String, Object> getCharacteristic();

	
}
