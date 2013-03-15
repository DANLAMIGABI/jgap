package cProviderIface;

import org.jgap.IApplicationData;

import cProvider.CProviderNetwork;
import cProvider.CProviderComputing;
import cProvider.CProviderStorage;

public interface ICProvider extends IApplicationData{

	public void setCost(double cost);
	public void setPlace(String place);	
	public void setID(int id);
	
	public void setNetwork(CProviderNetwork network);
	public void setComputing(CProviderComputing computing);
	public void setStorage(CProviderStorage storage);
	
	public int getID();
	public double getCost();
	public String getPlace(); 
	
	public CProviderComputing getComputing();
	public CProviderStorage getStorage();
	public CProviderNetwork getNetwork();
}
