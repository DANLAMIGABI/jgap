package cProviderIface;

import java.util.HashMap;

import org.jgap.IApplicationData;

import cProvider.CProviderNetwork;
import cProvider.CProviderComputing;
import cProvider.CProviderStorage;

public interface ICProvider extends IApplicationData{

	/* disabled
	public void setCost(double cost);
	public void setPlace(String place);	
	public double getCost();
	public String getPlace(); 
	*/
	
	public void setID(int id);
	public int getID();

	//testing
	public void setCharacteristic(HashMap<String, Object> characteristic);
	public HashMap<String, Object> getCharacteristic();
		
	public void setNetwork(CProviderNetwork network);
	public void setComputing(CProviderComputing computing);
	public void setStorage(CProviderStorage storage);
	
	public CProviderComputing getComputing();
	public CProviderStorage getStorage();
	public CProviderNetwork getNetwork();
}
