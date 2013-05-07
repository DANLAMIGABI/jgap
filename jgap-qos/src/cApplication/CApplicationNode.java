package cApplication;

import java.util.HashMap;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

public class CApplicationNode {
	
	
	private int ID;
	/*
	private double nodeBudget;
	private String nodePlace;
	*/
	private CApplicationComputing appComp;
	private CApplicationNetwork appNet;
	private CApplicationStorage appSto;
	
	private HashMap<String, Object> characteristic;
	
	public CApplicationNode(){
		new CApplicationNode(-1, new HashMap<String, Object>(), new CApplicationComputing(), new CApplicationStorage(), new CApplicationNetwork());
	}
	public CApplicationNode(int ID, HashMap<String, Object> characteristic, CApplicationComputing comp, CApplicationStorage st, CApplicationNetwork net){
		this.ID = ID;
		appComp = comp;
		appSto = st;
		appNet = net;
		this.characteristic = characteristic;
	}
	
	public CApplicationNode clone(){
		return new CApplicationNode(ID, characteristic, appComp, appSto, appNet);
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
	
	public CApplicationStorage getStorage() {
		// TODO Auto-generated method stub
		return appSto;
	}
	
	public void setCharacteristic(HashMap<String, Object> characteristic){
		this.characteristic = characteristic;
	}
	public HashMap<String, Object> getCharacteristic(){
		return characteristic;
	}

	
/*
	public void merge(CApplicationNode node){
//		nodeBudget += node.getNodeBudget();
		appComp.merge(node.getComputing());
		appNet.merge(node.getNetwork());
		appSto.merge(node.getStorage());
	}
	*/
	
/*	
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
	*/
	
	

}
