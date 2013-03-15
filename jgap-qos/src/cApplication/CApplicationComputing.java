package cApplication;

import cApplicationIface.ICAppComuting;

public class CApplicationComputing implements ICAppComuting{
	
	private String place;
	private double badget;
	private int ID;
	
	public CApplicationComputing(){
		new CApplicationComputing(null, 0,-1);
	}
	
	public CApplicationComputing(String place, double badget, int ID ){
		this.badget =  badget;
		this.ID = ID;
		this.place = place;
	}
	

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return 1;
		CApplicationComputing appc = (CApplicationComputing)o;
		if(ID == appc.ID 
				&& place.equalsIgnoreCase(appc.place)
				&& badget == appc.badget )
			return 0;
		return ID - appc.ID;
	}

	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() >0)
			this.place = place;
	}

	@Override
	public void setBadget(double badget) {
		// TODO Auto-generated method stub
		if(badget >0 )
			this.badget = badget;
	}

	@Override
	public void setID(int ID) {
		// TODO Auto-generated method stub
		this.ID = ID;
		
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}

	@Override
	public double getBadget() {
		// TODO Auto-generated method stub
		return badget;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	public Object clone(){
		CApplicationComputing appC = new CApplicationComputing(place, badget, ID);
		return appC ;
	}

}
