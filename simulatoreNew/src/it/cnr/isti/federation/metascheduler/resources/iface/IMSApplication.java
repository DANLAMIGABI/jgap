package it.cnr.isti.federation.metascheduler.resources.iface;

import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;

import java.util.HashMap;
import java.util.List;




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
