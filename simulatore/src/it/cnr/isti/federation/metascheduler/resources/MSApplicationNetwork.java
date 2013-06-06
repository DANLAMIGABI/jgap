package it.cnr.isti.federation.metascheduler.resources;

import it.cnr.isti.federation.metascheduler.resources.iface.IMSAppNetwork;

import java.io.Serializable;
import java.util.HashMap;



public class MSApplicationNetwork implements IMSAppNetwork, Cloneable, Serializable {

	//private static int NEXT_ID=0;
	
	private int ID;
	/*
	private int bandwidth;
	private double budget;
	private String place;
	*/
	//testing
	private HashMap<String, Object> characteristic;
	
	public MSApplicationNetwork(){
		new MSApplicationNetwork(-1, new HashMap<String, Object>());
	}
	public MSApplicationNetwork(int id, HashMap<String, Object> characteristic){
		this.characteristic = characteristic;
		this.ID = id;
	}
	/*
	@Override
	public void merge(CApplicationNetwork network) {
		// TODO Auto-generated method stub
		bandwidth += network.getBandwidth();
//		budget += network.getBudget();
	}
	*/
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return 1;
		MSApplicationNetwork appN = (MSApplicationNetwork)o;
		return ID - appN.ID;
		
	}
	
	public Object clone(){
		MSApplicationNetwork appN = null;
		try {
			appN = (MSApplicationNetwork) super.clone();
			appN.characteristic = (HashMap<String, Object>) characteristic.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public double getBudget() {
		// TODO Auto-generated method stub
		return budget;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}
	*/
	

	
}
