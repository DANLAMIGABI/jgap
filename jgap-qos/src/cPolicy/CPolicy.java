package cPolicy;

import cApplication.CApplicationNode;
import cApplicationIface.ICApplication;
import cProviderIface.ICProvider;

public abstract class CPolicy  {
	
	private static double weight;
	private static char type;
	private static char group;
	
	public CPolicy(double weight, char type, char group){
		CPolicy.weight = weight;
		CPolicy.type = type;
		CPolicy.group = group;
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
