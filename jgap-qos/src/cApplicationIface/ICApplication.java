package cApplicationIface;

import org.jgap.IApplicationData;

import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationStorage;

public interface ICApplication extends IApplicationData {
	
	public void setBadget(double badget);
	public void setPlace(String place);	
	
	public void setNetwork(CApplicationNetwork network);
	public void setComputing(CApplicationComputing computing);
	public void setStorage(CApplicationStorage storage);
	
	public double getBadget();
	public String getPlace(); 
	public int getID();
	
	public CApplicationComputing getComputing();
	public CApplicationNetwork getNetwork();
	public CApplicationStorage getStorage();


}
