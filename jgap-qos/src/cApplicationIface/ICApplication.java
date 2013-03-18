package cApplicationIface;

import org.jgap.IApplicationData;

import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationStorage;

public interface ICApplication extends IApplicationData {
	
	public void setBudget(double budget);
	public void setPlace(String place);	
	
	public void setNetwork(CApplicationNetwork network);
	public void setComputing(CApplicationComputing computing);
	public void setStorage(CApplicationStorage storage);
	
	public double getBudget();
	public String getPlace(); 
	public int getID();
	
	public CApplicationComputing getComputing();
	public CApplicationNetwork getNetwork();
	public CApplicationStorage getStorage();


}
