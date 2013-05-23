package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.metascheduler.resources.*;
import it.cnr.isti.federation.metascheduler.resources.iface.*;

public abstract class MSPolicy  {
	
	private double weight;
	private char type;
	private char group;
	
	public MSPolicy(double weight, char type, char group){
		this.weight = weight;
		this.type = type;
		this.group = group;
	}

	public abstract double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov);
	
	public abstract double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov);
	
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
