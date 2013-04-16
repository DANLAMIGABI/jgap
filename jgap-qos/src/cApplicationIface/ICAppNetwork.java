package cApplicationIface;

import java.util.HashMap;

import org.jgap.IApplicationData;

import cApplication.CApplicationNetwork;

public interface ICAppNetwork extends IApplicationData {
	
	/*disabled
	public void setBandwidth(int bandwidth);
	public void setBadget(double badget);
	public void setPlace(String place);
	public int getBandwidth();
	public double getBudget();
	public String getPlace();
	*/
	
	public void setID(int ID);
	public int getID();

	public void merge(CApplicationNetwork network);

	//testing
	public void setCharacteristic(HashMap<String, String> characteristic);
	public HashMap<String, String> getCharacteristic();

}
