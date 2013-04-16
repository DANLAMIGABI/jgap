package cApplicationIface;

import java.util.HashMap;

import org.jgap.IApplicationData;

import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationStorage;

public interface ICAppComuting extends IApplicationData {
	
	/*disabled 
	public void setPlace(String place);
	public void setBudget(double budget);
	public void setRam(int size);
	public int getRam();
	public String getPlace();
	public double getBudget();
	*/
	
	public void setID(int ID);
	public int getID();
	
	public void merge(CApplicationComputing computing);
	
	//testing
	public void setCharacteristic(HashMap<String, String> characteristic);
	public HashMap<String, String> getCharacteristic();


}
