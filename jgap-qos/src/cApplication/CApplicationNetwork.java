package cApplication;

import cApplicationIface.ICAppNetwork;

public class CApplicationNetwork implements ICAppNetwork {

	//private static int NEXT_ID=0;
	
	private int ID;
	private int bandwidth;
	private double budget;
	private String place;
	
	
	public CApplicationNetwork(){
		new CApplicationNetwork(0, 0, null,-1);
	}
	public CApplicationNetwork(int bandwidth, double badget, String place,int id){
		this.budget = badget;
		this.bandwidth = bandwidth;
		this.place = place;
		this.ID = id;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return 1;
		CApplicationNetwork appN = (CApplicationNetwork)o;
		if (ID == appN.ID 
				&& place.equalsIgnoreCase(appN.place)
				&& bandwidth == appN.bandwidth 
				&& budget == appN.budget)
			return 0;
		return ID - appN.ID;
		
	}

	@Override
	public void setBandwidth(int bandwidth) {
		// TODO Auto-generated method stub
		if(bandwidth >0)
			this.bandwidth = bandwidth;
		
	}

	@Override
	public void setBadget(double badget) {
		// TODO Auto-generated method stub
		if(badget >0 )
			this.budget = badget;
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
	public double getBadger() {
		// TODO Auto-generated method stub
		return budget;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}
	public Object clone(){
		CApplicationNetwork appN = new CApplicationNetwork(bandwidth, budget, place, ID);
		return appN;
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	@Override
	public void setID(int ID) {
		// TODO Auto-generated method stub
		this.ID = ID;
	}

	
}
