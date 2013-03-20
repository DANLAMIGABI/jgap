package cPolicy;

import cApplicationIface.ICApplication;
import cProviderIface.ICProvider;

public abstract class CPolicy  {
	
	private static double weight;
	private static char type;
	
	public CPolicy(double weight, char type){
		CPolicy.weight = weight;
		CPolicy.type = type;
	}

	public abstract double elaluatePolicy(ICApplication app, ICProvider prov);
	
	public void setType(char type){
		CPolicy.type = type;
	}
	public char getType(){
		return type;
	}
	
	public void setWeight(double weight){
		CPolicy.weight = weight;
	}
	public double getWeight(){
		return weight;
	}
}
