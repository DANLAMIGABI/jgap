package cProvider;

import cProviderIface.ICProviderNetwork;

public class CProviderNetwork implements ICProviderNetwork {
	private String place;
	private int ID;
	private double unitCost;
	private int bandwidth;
	
	
	public CProviderNetwork(){
		new CProviderNetwork(null, 0, 0, -1);
	}
	
	public CProviderNetwork(String place, int bandwidth, double unitCost, int ID){
		this.bandwidth = bandwidth;
		this.place = place;
		this.ID = ID;
		this.unitCost = unitCost;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o ==null)
			return -1;
		CProviderNetwork provN = (CProviderNetwork)o;
		if (ID == provN.ID 
				&& place.equalsIgnoreCase(provN.place)
				&& unitCost == provN.unitCost 
				&& bandwidth == provN.bandwidth)
			return 0;
		return ID - provN.ID;
	}
	
	public Object clone(){
		return new CProviderNetwork(place, bandwidth, unitCost, ID);
	}
	
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
	public int getBandwidth() {
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

}
