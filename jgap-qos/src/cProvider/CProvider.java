package cProvider;

import java.util.HashMap;

import cProviderIface.ICProvider;

public class CProvider implements ICProvider {
	//private static int NEXT_ID=1;
	
	
	private int ID;
	/*
	private double cost;
	private String place;
	*/
	private CProviderComputing provCom;
	private CProviderStorage provSto;
	private CProviderNetwork provNet;
	
	private HashMap<String, Object> characteristic;
	
	public CProvider(){
		CProviderComputing comp =  new CProviderComputing();
		CProviderNetwork net = new CProviderNetwork();
		CProviderStorage store = new CProviderStorage();
		new CProvider(-1, new HashMap<String, Object>(), comp ,store , net);
	}
	
	public CProvider(int id, HashMap<String, Object> characteristic, CProviderComputing comp, 
			CProviderStorage store, CProviderNetwork net ){
		this.ID = id;
		this.characteristic = characteristic;
		this.provCom = comp;
		this.provNet = net;
		this.provSto = store;
		
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
			return 1;
		CProvider prov = (CProvider)o;
		return ID -  prov.ID;
	}
	public Object clone(){
		return new CProvider(ID, characteristic, provCom, provSto, provNet);
	}
	
	
	
	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		ID = id;
		
	}
	@Override
	public void setNetwork(CProviderNetwork network) {
		// TODO Auto-generated method stub
		if(network != null)
			this.provNet = network;
		
	}
	@Override
	public void setComputing(CProviderComputing computing) {
		// TODO Auto-generated method stub
		if(computing != null)
			provCom = computing;
		
		
	}
	@Override
	public void setStorage(CProviderStorage storage) {
		// TODO Auto-generated method stub
		if(storage != null)
			provSto = storage;
		
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public CProviderComputing getComputing() {
		// TODO Auto-generated method stub
		return provCom;
	}
	@Override
	public CProviderStorage getStorage() {
		// TODO Auto-generated method stub
		return provSto;
	}
	@Override
	public CProviderNetwork getNetwork() {
		// TODO Auto-generated method stub
		return provNet;
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
	public void setCost(double cost) {
		// TODO Auto-generated method stub
		if(cost > 0)
			this.cost = cost;
		
	}
	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() >0)
			this.place = place;
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
*/
}
