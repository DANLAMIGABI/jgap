package it.cnr.isti.federation.metascheduler.resources;

import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;

import java.util.HashMap;




public class MSProvider implements IMSProvider {
	//private static int NEXT_ID=1;
	
	
	private int ID;
	/*
	private double cost;
	private String place;
	*/
	private MSProviderComputing provCom;
	private MSProviderStorage provSto;
	private MSProviderNetwork provNet;
	
	private HashMap<String, Object> characteristic;
	
	public MSProvider(){
		MSProviderComputing comp =  new MSProviderComputing();
		MSProviderNetwork net = new MSProviderNetwork();
		MSProviderStorage store = new MSProviderStorage();
		new MSProvider(-1, new HashMap<String, Object>(), comp ,store , net);
	}
	
	public MSProvider(int id, HashMap<String, Object> characteristic, MSProviderComputing comp, 
			MSProviderStorage store, MSProviderNetwork net ){
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
		MSProvider prov = (MSProvider)o;
		return ID -  prov.ID;
	}
	public Object clone(){
		return new MSProvider(ID, characteristic, provCom, provSto, provNet);
	}
	
	
	
	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		ID = id;
		
	}
	@Override
	public void setNetwork(MSProviderNetwork network) {
		// TODO Auto-generated method stub
		if(network != null)
			this.provNet = network;
		
	}
	@Override
	public void setComputing(MSProviderComputing computing) {
		// TODO Auto-generated method stub
		if(computing != null)
			provCom = computing;
		
		
	}
	@Override
	public void setStorage(MSProviderStorage storage) {
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
	public MSProviderComputing getComputing() {
		// TODO Auto-generated method stub
		return provCom;
	}
	@Override
	public MSProviderStorage getStorage() {
		// TODO Auto-generated method stub
		return provSto;
	}
	@Override
	public MSProviderNetwork getNetwork() {
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
