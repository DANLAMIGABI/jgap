package cPolicy;

import cApplication.CApplicationNode;
import cApplicationIface.ICApplication;
import cProviderIface.ICProvider;

public abstract class CPolicy  {
	
	private double weight;
	private char type;
	private char group;
	
	public CPolicy(double weight, char type, char group){
		this.weight = weight;
		this.type = type;
		this.group = group;
	}

	public abstract double evaluateGlobalPolicy(ICApplication app, ICProvider prov);
	
	public abstract double evaluateLocalPolicy(CApplicationNode node, ICProvider prov);
	
	public char getType(){
		return type;
	}
	public char getGroup(){
		return group;
	}
	public double getWeight(){
		return weight;
	}
	
//	public void setWeight(double weight){
//		CPolicy.weight = weight;
//	}
//	public void setType(char type){
//	CPolicy.type = type;
//}
	
}
