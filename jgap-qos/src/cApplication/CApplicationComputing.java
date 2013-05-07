package cApplication;

import java.util.HashMap;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;

import org.omg.CosNaming.IstringHelper;

import cApplicationIface.ICAppComuting;

public class CApplicationComputing implements ICAppComuting{
	/*
	private String place;
	private double budget;
	private int ramSize;
	*/
	private int ID;
	
	//testing
	private HashMap<String, Object> characteristic;
	
	public CApplicationComputing(){
		new CApplicationComputing(-1, new HashMap<String, Object>());
	}
	
	public CApplicationComputing( int ID,HashMap<String, Object> characteristic ){
		this.ID = ID;
		this.characteristic = characteristic;		
	}
	
	/*
	@Override
	public void merge(CApplicationComputing computing) {
		// TODO Auto-generated method stub
		for(int i=0; i<characteristicToMerge.size(); i++){
			String key = characteristicToMerge.get(i);
			String value = this.characteristic.get(key);
	 obj = computing.getCharacteristic().get(key);
			
		}
		
	}
*/
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return 1;
		CApplicationComputing appc = (CApplicationComputing)o;
		return ID - appc.ID;
	}

	

	@Override
	public void setID(int ID) {
		// TODO Auto-generated method stub
		this.ID = ID;
		
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	public Object clone(){
		CApplicationComputing appC = new CApplicationComputing(ID, characteristic);
		return appC ;
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
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}

	@Override
	public double getBudget() {
		// TODO Auto-generated method stub
		return budget;
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
*/
	
	

}
