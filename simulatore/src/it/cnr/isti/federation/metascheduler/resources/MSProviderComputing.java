package it.cnr.isti.federation.metascheduler.resources;

import it.cnr.isti.federation.metascheduler.resources.iface.IMSProviderComputing;

import java.util.HashMap;




public class MSProviderComputing implements IMSProviderComputing, Cloneable {

	private int ID;
	/*
	private String place;
	private double cost;
	private int ramAmount;
	private double mips;
	*/
	//testing
	private HashMap<String, Object> characteristic;
	
	public MSProviderComputing(){
		new MSProviderComputing(-1, new HashMap<String, Object>());
	}
	
	public MSProviderComputing(int id, HashMap<String, Object> characteristic){
		this.ID = id;
		this.characteristic = characteristic;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o ==null)
			return 1;
		MSProviderComputing provC = (MSProviderComputing) o;
		return ID - provC.ID;
	}
	
	public Object clone(){
		MSProviderComputing pComp = null;
		try {
			pComp = (MSProviderComputing) super.clone();
			pComp.characteristic = (HashMap<String, Object>) characteristic.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pComp;
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
