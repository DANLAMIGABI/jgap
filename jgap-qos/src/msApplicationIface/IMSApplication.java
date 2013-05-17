package msApplicationIface;

import java.util.HashMap;
import java.util.List;

import msApplication.MSApplicationComputing;
import msApplication.MSApplicationNetwork;
import msApplication.MSApplicationNode;
import msApplication.MSApplicationStorage;

import org.jgap.IApplicationData;


public interface IMSApplication extends IApplicationData {
	/* moved to application node
		public void setBudget(double budget);
		public double getBudget();
	*/
	
	/*disabled
	 public void setPlace(String place);
	 public String getPlace();
	 
	 */
	
	public void setNodes(List<MSApplicationNode> nodes);
	
	//testing
	public void setCharacteristic(HashMap<String, Object> characteristic);
	public HashMap<String, Object> getCharacteristic();

	public void setID(int id);
	public int getID();
	public List<MSApplicationNode> getNodes();


}
