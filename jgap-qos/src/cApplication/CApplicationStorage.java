package cApplication;

import cApplicationIface.ICAppStorage;

public class CApplicationStorage implements ICAppStorage {
	//private static int NEXT_ID = 1;
	private double badget;
	private String place;
	private int amount;
	private int ID;
	/* not used */
	private char unit;
	
	
	public CApplicationStorage(){
		new CApplicationStorage(0,0,-1,null);
	}
	
	public CApplicationStorage(double unitCost, int amount, int ID, String place){
		this.badget = unitCost;
		this.amount = amount;
		this.ID = ID;
		this.place = place;
		//ApplicationStorage.unit = unit;
	}
	
	public void setBadget(double cost){
		if(cost >0 )
			badget = cost;
	}
	
	public void setAmount(int amount){
		if(amount >0)
			this.amount = amount;
	}
	
	public void setUnit(char unit){
		this.unit = unit;
	}
	
	public double getBadget(){
		return badget;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public char getUnit(){
		return unit;
	}
	public int getID(){
		return ID;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
			return 1;
		CApplicationStorage appStore = (CApplicationStorage)o;
		if (ID == appStore.ID 
				&& place.equalsIgnoreCase(appStore.place)
				&& badget == appStore.badget 
				&& amount == appStore.getAmount())
			return 0;
		return ID - appStore.ID;
	}
	
	public Object clone(){
		CApplicationStorage appStore = new CApplicationStorage(badget, amount, ID, place);
		return appStore;
	}

	@Override
	public void setID(int ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return null;
	}


}
