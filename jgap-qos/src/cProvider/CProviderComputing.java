package cProvider;

import java.util.HashMap;

import cProviderIface.ICProviderComputing;

public class CProviderComputing implements ICProviderComputing {

	private int ID;
	/*
	private String place;
	private double cost;
	private int ramAmount;
	private double mips;
	*/
	//testing
	private HashMap<String, Object> characteristic;
	
	public CProviderComputing(){
		new CProviderComputing(-1, new HashMap<String, Object>());
	}
	
	public CProviderComputing(int id, HashMap<String, Object> characteristic){
		this.ID = id;
		this.characteristic = characteristic;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o ==null)
			return 1;
		CProviderComputing provC = (CProviderComputing) o;
		return ID - provC.ID;
	}
	
	public Object clone(){
		return new CProviderComputing(ID, characteristic);
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

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.ID = id;
		
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
/*
	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() >0 )
			this.place = place;
		
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

	@Override
	public void setRam(int size) {
		// TODO Auto-generated method stub
		if(size >0)
			ramAmount = size;
		
	}

	@Override
	public int getRam() {
		// TODO Auto-generated method stub
		return ramAmount;
	}

	@Override
	public void setMips(double mips) {
		// TODO Auto-generated method stub
		this.mips = mips;
		
	}

	@Override
	public double getMips() {
		// TODO Auto-generated method stub
		return mips;
	}
*/

	


}
