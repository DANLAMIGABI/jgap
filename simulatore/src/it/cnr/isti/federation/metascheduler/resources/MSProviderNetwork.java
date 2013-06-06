package it.cnr.isti.federation.metascheduler.resources;

import it.cnr.isti.federation.metascheduler.resources.iface.IMSProviderNetwork;

import java.io.Serializable;
import java.util.HashMap;





public class MSProviderNetwork implements IMSProviderNetwork, Cloneable, Serializable {
	
	private int ID;
	/*
	private double unitCost;
	private int bandwidth;
	private String place;
	*/
	//testing
	private HashMap<String, Object> characteristic;
	
	public MSProviderNetwork(){
		new MSProviderNetwork(-1, new HashMap<String, Object>());
	}
	
	public MSProviderNetwork(int ID, HashMap<String, Object> characteristic){
		this.ID = ID;
		this.characteristic = characteristic;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o ==null)
			return -1;
		MSProviderNetwork provN = (MSProviderNetwork)o;
		return ID - provN.ID;
	}
	
	public Object clone(){
		MSProviderNetwork pNetwork = null;
		try {
			pNetwork = (MSProviderNetwork) super.clone();
			pNetwork.characteristic = (HashMap<String, Object>) characteristic.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pNetwork;
				
	}
	@Override
	public void setCharacteristic(HashMap<String, Object> characteristic) {
		// TODO Auto-generated method stub
		this.characteristic = characteristic;
	}

	@Override
	public HashMap<String, Object> getCharacteristic() {
		// TODO Auto-generated method stub
		return characteristic;
	}
	
/* disabled 	
	@Override
	public void setBandwidth(int bandwidth) {
		// TODO Auto-generated method stub
		if(bandwidth >0)
			this.bandwidth = bandwidth;
		
	}

	@Override
	public void setUnitCost(double unitCost) {
		// TODO Auto-generated method stub
		if(unitCost >0)
			this.unitCost = unitCost;
		
	}

	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() >0)
			this.place = place;
		
	}

	@Override
	public long getBandwidth() {
		// TODO Auto-generated method stub
		return bandwidth;
	}

	@Override
	public double getUnitCost() {
		// TODO Auto-generated method stub
		return unitCost;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}

*/

}
