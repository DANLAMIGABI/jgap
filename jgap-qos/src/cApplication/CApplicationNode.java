package cApplication;

public class CApplicationNode {
	
	
	private int ID;
	private CApplicationComputing appComp;
	private CApplicationNetwork appNet;
	private CApplicationStorage appSto;
	
	public CApplicationNode(){
		new CApplicationNode(0, null, null, null);
	}
	public CApplicationNode(int ID,CApplicationComputing comp, CApplicationStorage st, CApplicationNetwork net){
		this.ID = ID;
		appComp = comp;
		appSto = st;
		appNet = net;
	}
	

	public void setNetwork(CApplicationNetwork network) {
		// TODO Auto-generated method stub
		if(network != null)
			appNet = network;
		
	}

	public void setComputing(CApplicationComputing computing) {
		// TODO Auto-generated method stub
		if(computing != null)
			appComp = computing;
		
	}

	public void setStorage(CApplicationStorage storage) {
		// TODO Auto-generated method stub
		if(storage != null)
			appSto = storage;
		
	}
	
	public void setID(int id){
		ID = id;
	}
	public int getID(){
		return ID;
	}

	public CApplicationComputing getComputing() {
		// TODO Auto-generated method stub
		return appComp;
	}
	public CApplicationNetwork getNetwork() {
		// TODO Auto-generated method stub
		return appNet;
	}

	public CApplicationStorage getStorage() {
		// TODO Auto-generated method stub
		return appSto;
	}

}
