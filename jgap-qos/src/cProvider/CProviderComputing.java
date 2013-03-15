package cProvider;

import cProviderIface.ICProviderComputing;

public class CProviderComputing implements ICProviderComputing {

	private int ID;
	private String place;
	private double cost;
	
	public CProviderComputing(){
		new CProviderComputing(null, 0, -1);
	}
	
	public CProviderComputing(String place, double cost, int id){
		this.cost = cost;
		this.place = place;
		this.ID = id;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o ==null)
			return 1;
		CProviderComputing provC = (CProviderComputing) o;
		if (ID == provC.ID 
				&& place.equalsIgnoreCase(provC.place)
				&& cost == provC.cost)
			return 0;
		return ID - provC.ID;
	}
	
	public Object clone(){
		return new CProviderComputing(place, cost, ID);
	}

	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() >0 )
			this.place = place;
		
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.ID = id;
		
	}

	@Override
	public void setCost(double cost) {
		// TODO Auto-generated method stub
		if(cost >0 )
			this.cost = cost;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		return cost;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}

}
