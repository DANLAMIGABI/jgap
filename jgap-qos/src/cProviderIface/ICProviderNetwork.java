package cProviderIface;

import org.jgap.IApplicationData;

public interface ICProviderNetwork extends IApplicationData{


	public void setBandwidth(int bandwidth);
	public void setUnitCost(double unitCost);
	public void setPlace(String place);
	
	public int getBandwidth();
	public double getUnitCost();
	public String getPlace();
	
}
