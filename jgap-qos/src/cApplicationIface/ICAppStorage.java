package cApplicationIface;

import org.jgap.IApplicationData;

import cApplication.CApplicationStorage;

public interface ICAppStorage extends IApplicationData {

	public void setPlace(String place);
	public void setAmount(int amount);
	public void setBadget(double unitCost);
	public void setID(int ID);
	
	public void merge(CApplicationStorage store);
	
	public int getID();
	public int getAmount();
	public double getBadget();
	public String getPlace();
}
