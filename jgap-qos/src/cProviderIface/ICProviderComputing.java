package cProviderIface;

import org.jgap.IApplicationData;

public interface ICProviderComputing extends IApplicationData {
	
	public void setPlace(String place);
	public void setID(int id);
	public void setCost(double cost);
	
	public int getID();
	public double getCost();
	public String getPlace();

}
