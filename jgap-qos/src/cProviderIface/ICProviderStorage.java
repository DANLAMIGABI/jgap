package cProviderIface;

import org.jgap.IApplicationData;

public interface ICProviderStorage extends IApplicationData {
	public void setAmount(int amount);
	public void setUnitCost(double unitCost);
	public void setPlace(String place);
	
	public int getID();
	public int getAmount();
	public double getUnitCost();
	public String getPlace();

}
