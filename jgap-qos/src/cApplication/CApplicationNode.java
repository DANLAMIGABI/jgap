package cApplication;

public class CApplicationNode {
	
	
	private int ID;
	private double nodeBudget;
	private String nodePlace;
	private CApplicationComputing appComp;
	private CApplicationNetwork appNet;
	private CApplicationStorage appSto;
	
	public CApplicationNode(){
		new CApplicationNode(0, null, 0,null, null, null);
	}
	public CApplicationNode(int ID, String nodePlace, double nodeBudget, CApplicationComputing comp, CApplicationStorage st, CApplicationNetwork net){
		this.ID = ID;
		this.nodeBudget = nodeBudget;
		this.nodePlace = nodePlace;
		appComp = comp;
		appSto = st;
		appNet = net;
	}
	
	public CApplicationNode clone(){
		return new CApplicationNode(ID, nodePlace, nodeBudget, appComp, appSto, appNet);
	}

	public void merge(CApplicationNode node){
//		nodeBudget += node.getNodeBudget();
		appComp.merge(node.getComputing());
		appNet.merge(node.getNetwork());
		appSto.merge(node.getStorage());
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
	public void setNodeBudget(double nodeBudget){
		this.nodeBudget = nodeBudget;
	}
	
	public void setNodePlace(String nodePlace){
		this.nodePlace = nodePlace;
	}
	
	public String getNodePlace(){
		return nodePlace;
	}
	
	public double getNodeBudget(){
		return this.nodeBudget;
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
