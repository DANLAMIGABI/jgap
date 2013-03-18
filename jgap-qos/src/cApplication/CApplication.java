package cApplication;

import cApplicationIface.ICApplication;

public class CApplication implements ICApplication {
	
	private int ID;
	private String place;
	private double budget;
	
	private CApplicationComputing appComp;
	private CApplicationNetwork appNet;
	private CApplicationStorage appSto;
	
	public CApplication(){
		new CApplication(-1, null, 0, null, null, null);
	}
	
	public CApplication(int ID, String place, double badget, CApplicationComputing appc, 
			CApplicationNetwork appn, CApplicationStorage apps){
		this.ID = ID;
		this.place = place;
		this.budget = badget;
		appComp = appc;
		appNet = appn;
		appSto = apps;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
			return 1;
		CApplication app = (CApplication)o;
		if (ID == app.ID && place.equalsIgnoreCase(app.place)
				&& budget == app.budget 
				&& appComp.compareTo(app.appComp) == 0
				&& appNet.compareTo(app.appNet) == 0
				&& appSto.compareTo(app.appSto) == 0)
			return 0;
		return ID = app.ID;
	}
	
	public Object clone(){
		return new CApplication(ID, place, budget, appComp, appNet, appSto);
	}
	
	@Override
	public void setBudget(double badget) {
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
	public void setNetwork(CApplicationNetwork network) {
		// TODO Auto-generated method stub
		if(network != null)
			appNet = network;
		
	}
	@Override
	public void setComputing(CApplicationComputing computing) {
		// TODO Auto-generated method stub
		if(computing != null)
			appComp = computing;
		
	}
	@Override
	public void setStorage(CApplicationStorage storage) {
		// TODO Auto-generated method stub
		if(storage != null)
			appSto = storage;
		
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
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	@Override
	public CApplicationComputing getComputing() {
		// TODO Auto-generated method stub
		return appComp;
	}
	@Override
	public CApplicationNetwork getNetwork() {
		// TODO Auto-generated method stub
		return appNet;
	}
	@Override
	public CApplicationStorage getStorage() {
		// TODO Auto-generated method stub
		return appSto;
	}
	
	

}
