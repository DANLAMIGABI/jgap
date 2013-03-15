package cProvider;

import cProviderIface.ICProvider;

public class CProvider implements ICProvider {
	//private static int NEXT_ID=1;
	
	private String place;
	private int ID;
	private double cost;
	
	private CProviderComputing provCom;
	private CProviderStorage provSto;
	private CProviderNetwork provNet;
	
	public CProvider(){
		new CProvider(null, 0, -1, null, null, null);
	}
	
	public CProvider(String place, double cost, int id, CProviderComputing comp, 
			CProviderStorage store, CProviderNetwork net ){
		this.place = place;
		this.ID = id;
		provCom = comp;
		provNet = net;
		provSto = store;
		
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
			return 1;
		CProvider prov = (CProvider)o;
		if(ID == prov.ID
				&& place.equalsIgnoreCase(prov.place) 
				&& cost == prov.cost
				&& provCom.compareTo(prov.provCom)==0
				&& provNet.compareTo(prov.provNet) ==0
				&& provSto.compareTo(prov.provSto) == 0)
			return 0;
		return ID -  prov.ID;
	}
	public Object clone(){
		return new CProvider(place, cost, ID, provCom, provSto, provNet);
	}
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

	

}
