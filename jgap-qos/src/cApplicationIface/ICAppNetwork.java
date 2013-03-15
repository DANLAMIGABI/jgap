package cApplicationIface;

import org.jgap.IApplicationData;

public interface ICAppNetwork extends IApplicationData {
	
	
	public void setBandwidth(int bandwidth);
	public void setBadget(double badget);
	public void setPlace(String place);
	public void setID(int ID);
	
	public int getBandwidth();
	public double getBadger();
	public String getPlace();
	public int getID();
	
}
