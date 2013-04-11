package cApplicationIface;

import org.jgap.IApplicationData;

import cApplication.CApplicationNetwork;

public interface ICAppNetwork extends IApplicationData {
	
	
	public void setBandwidth(int bandwidth);
	public void setBadget(double badget);
	public void setPlace(String place);
	public void setID(int ID);
	
	public void merge(CApplicationNetwork network);
	
	public int getBandwidth();
	public double getBudget();
	public String getPlace();
	public int getID();
	
}
