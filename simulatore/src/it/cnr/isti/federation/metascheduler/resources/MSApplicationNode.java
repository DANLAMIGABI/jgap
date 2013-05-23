package it.cnr.isti.federation.metascheduler.resources;

import java.util.HashMap;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

public class MSApplicationNode implements Cloneable{
	
	
	private int ID;
	/*
	private double nodeBudget;
	private String nodePlace;
	*/
	private MSApplicationComputing appComp;
	private MSApplicationNetwork appNet;
	private MSApplicationStorage appSto;
	
	private HashMap<String, Object> characteristic;
	
	public MSApplicationNode(){
		new MSApplicationNode(-1, new HashMap<String, Object>(), new MSApplicationComputing(), new MSApplicationStorage(), new MSApplicationNetwork());
	}
	public MSApplicationNode(int ID, HashMap<String, Object> characteristic, MSApplicationComputing comp, MSApplicationStorage st, MSApplicationNetwork net){
		this.ID = ID;
		appComp = comp;
		appSto = st;
		appNet = net;
		this.characteristic = characteristic;
	}
	
	@Override
	public Object clone() {
		MSApplicationNode node = null;
		try {
			node = (MSApplicationNode) super.clone();
			node.appComp = (MSApplicationComputing) appComp.clone();
			node.appNet = (MSApplicationNetwork) appNet.clone();
			node.appSto = (MSApplicationStorage) appSto.clone();
			node.characteristic = (HashMap<String, Object>) characteristic.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
		
	}
	
	public void setID(int id){
		ID = id;
	}
	public int getID(){
		return ID;
	}

	public MSApplicationComputing getComputing() {
		// TODO Auto-generated method stub
		return appComp;
	}
	public MSApplicationNetwork getNetwork() {
		// TODO Auto-generated method stub
		return appNet;
	}
	
	public void setNetwork(MSApplicationNetwork network) {
		// TODO Auto-generated method stub
		if(network != null)
			appNet = network;
		
	}

	public void setComputing(MSApplicationComputing computing) {
		// TODO Auto-generated method stub
		if(computing != null)
			appComp = computing;
		
	}

	public void setStorage(MSApplicationStorage storage) {
		// TODO Auto-generated method stub
		if(storage != null)
			appSto = storage;
		
	}
	
	public MSApplicationStorage getStorage() {
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
