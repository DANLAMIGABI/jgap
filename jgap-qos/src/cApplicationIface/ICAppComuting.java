package cApplicationIface;

import org.jgap.IApplicationData;

import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationStorage;

public interface ICAppComuting extends IApplicationData {
	
	public void setPlace(String place);
	public void setBadget(double badget);
	public void setID(int ID);
	
	
	public String getPlace();
	public double getBadget();
	public int getID();
	

}
