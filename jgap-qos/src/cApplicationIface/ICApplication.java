package cApplicationIface;

import java.util.HashMap;
import java.util.List;

import org.jgap.IApplicationData;

import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationNode;
import cApplication.CApplicationStorage;

public interface ICApplication extends IApplicationData {
	/* moved to application node
		public void setBudget(double budget);
		public double getBudget();
	*/
	
	/*disabled
	 public void setPlace(String place);
	 public String getPlace();
	 
	 */
	
	public void setNodes(List<CApplicationNode> nodes);
	
	//testing
	public void setCharacteristic(HashMap<String, Object> characteristic);
	public HashMap<String, Object> getCharacteristic();

	public void setID(int id);
	public int getID();
	public List<CApplicationNode> getNodes();


}
