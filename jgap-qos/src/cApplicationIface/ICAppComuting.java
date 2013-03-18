package cApplicationIface;

import org.jgap.IApplicationData;

import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationStorage;

public interface ICAppComuting extends IApplicationData {
	
	public void setPlace(String place);
	public void setBudget(double budget);
	public void setID(int ID);
	public void setRam(int size);
	
	public int getRam();
	public String getPlace();
	public double getBadget();
	public int getID();
	

}
