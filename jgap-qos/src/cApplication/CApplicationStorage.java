package cApplication;

import java.util.HashMap;

import cApplicationIface.ICAppStorage;

public class CApplicationStorage implements ICAppStorage {
	//private static int NEXT_ID = 1;
	/* disabled
	private double budget;
	private String place;
	private int amount;
	private char unit;
	*/
	private int ID;
	
	//testing
	private HashMap<String, Object> characteristic;
	
	
	public CApplicationStorage(){
		new CApplicationStorage(-1, new HashMap<String, Object>());
	}
	
	public CApplicationStorage(int ID, HashMap<String, Object> characteristic){
		this.ID = ID;
		this.characteristic = characteristic;
	}
	/*
	@Override
	public void merge(CApplicationStorage store) {
		// TODO Auto-generated method stub
//		budget += store.getBadget();
		amount += store.getAmount();
	}
	*/
	
	public int getID(){
		return ID;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		/* compare solo sull'ID */
		if( o == null)
			return 1;
		CApplicationStorage appStore = (CApplicationStorage)o;
		
		return ID - appStore.getID();
	}
	
	public Object clone(){
		CApplicationStorage appStore = new CApplicationStorage(ID, characteristic);
		return appStore;
	}

	@Override
	public void setID(int ID) {
		// TODO Auto-generated method stub
		
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
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return null;
	}
*/
	

	


}
