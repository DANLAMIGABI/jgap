package msProviderIface;

import java.util.HashMap;

import msProvider.MSProviderComputing;
import msProvider.MSProviderNetwork;
import msProvider.MSProviderStorage;

import org.jgap.IApplicationData;


public interface IMSProvider extends IApplicationData{

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
		
	public void setNetwork(MSProviderNetwork network);
	public void setComputing(MSProviderComputing computing);
	public void setStorage(MSProviderStorage storage);
	
	public MSProviderComputing getComputing();
	public MSProviderStorage getStorage();
	public MSProviderNetwork getNetwork();
}
