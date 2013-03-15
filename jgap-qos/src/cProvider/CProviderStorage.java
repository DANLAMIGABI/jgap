package cProvider;

import cProviderIface.ICProviderStorage;

public class CProviderStorage implements ICProviderStorage {
	private String place;
	private int ID;
	private int amount;
	private double unitCost;
	
	public CProviderStorage(){
		new CProviderStorage(null, 0, 0, -1);
	}
	
	public CProviderStorage(String place, int amount, double unitCost, int ID){
		this.amount = amount;
		this.place = place;
		this.unitCost = unitCost;
		this.ID = ID;
	}
	

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return 1;
		CProviderStorage provS = (CProviderStorage)o;
		if (ID == provS.ID 
				&& place.equalsIgnoreCase(provS.place)
				&& amount == provS.amount 
				&& unitCost == provS.unitCost)
			return 0;
		return ID - provS.ID;
	}
	
	public Object clone(){
		return new CProviderStorage(place, amount, unitCost, ID);
	}

	@Override
	public void setAmount(int amount) {
		// TODO Auto-generated method stub
		if(amount >0 )
			this.amount = amount;
		
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
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return amount;
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
