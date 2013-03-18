package cApplication;

import cApplicationIface.ICAppComuting;

public class CApplicationComputing implements ICAppComuting{
	
	private String place;
	private double budget;
	private int ID;
	private int ramSize;
	
	public CApplicationComputing(){
		new CApplicationComputing(null, 0,-1,0);
	}
	
	public CApplicationComputing(String place, double badget, int ID, int ramSize ){
		this.budget =  badget;
		this.ID = ID;
		this.place = place;
		this.ramSize = ramSize;
	}
	

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return 1;
		CApplicationComputing appc = (CApplicationComputing)o;
		if(ID == appc.ID 
				&& place.equalsIgnoreCase(appc.place)
				&& budget == appc.budget )
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
	public void setBudget(double budget) {
		// TODO Auto-generated method stub
		if(budget >0 )
			this.budget = budget;
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
		return budget;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	public Object clone(){
		CApplicationComputing appC = new CApplicationComputing(place, budget, ID, ramSize);
		return appC ;
	}

	@Override
	public void setRam(int size) {
		// TODO Auto-generated method stub
		if(size >0)
			ramSize = size;
		
	}

	@Override
	public int getRam() {
		// TODO Auto-generated method stub
		return ramSize;
	}

}
