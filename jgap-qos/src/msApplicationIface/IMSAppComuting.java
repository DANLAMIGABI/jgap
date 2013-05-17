package msApplicationIface;

import java.util.HashMap;

import msApplication.MSApplicationComputing;
import msApplication.MSApplicationNetwork;
import msApplication.MSApplicationStorage;

import org.jgap.IApplicationData;


public interface IMSAppComuting extends IApplicationData {
	
	/*disabled 
	public void setPlace(String place);
	public void setBudget(double budget);
	public void setRam(int size);
	public int getRam();
	public String getPlace();
	public double getBudget();
	public void merge(CApplicationComputing computing);
	*/
	
	public void setID(int ID);
	public int getID();
	
	
	
	//testing
	public void setCharacteristic(HashMap<String, Object> characteristic);
	public HashMap<String, Object> getCharacteristic();


}
