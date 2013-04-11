package cApplicationIface;

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
	public void setPlace(String place);
	public void setNodes(CApplicationNode[] nodes);
	

	public String getPlace(); 
	public int getID();
	public CApplicationNode[] getNodes();


}
