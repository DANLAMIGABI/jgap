package contrailJgap;

import java.security.Policy;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.IApplicationData;
import org.jgap.IChromosome;

import test.Constant;

import cApplication.CApplication;
import cApplication.CApplicationNode;
import cApplicationIface.ICApplication;
import cProvider.CProvider;
import cProviderIface.ICProvider;
import cPolicy.*;

public class CFitnessEasy extends FitnessFunction{
	
	static private double tollerance;
	static private int penalty =10;
	
	static private ICApplication app;
	static private ICProvider[] pList;
	static private Vector<CPolicy> policy;
	
	public CFitnessEasy(double tollerance,ICApplication app, ICProvider[] provList, Vector<CPolicy> policy){
		CFitnessEasy.tollerance = tollerance;
		CFitnessEasy.app = app;
		pList = provList;
		CFitnessEasy.policy = policy;
	}
	
//	@Override
//	protected double evaluate(IChromosome arg0) {
//		// TODO Auto-generated method stub
//		
//		double fitness=0;		
//		CFitParameter param = evaluatePolicy(arg0);
//		
//		if(param.distance != 0){
//			double ret = 1/param.distance;
////			System.out.println("PENALITY " +ret + " count: " + param.tmpCounter);
//			return ret;
//		}
//		// if more one node is allocated at the same provider 
//		// the chromosome is not valid but it is accepted whit penalty 
//		
//		
//		//Respect Equal 
//		fitness += Math.abs(param.equal);
//		
//		//Maximize ascendent
//		fitness += Math.abs(param.ascendent);
//		
//		//Minimize descendent
//		fitness += Math.abs(param.descendent );
//		
//		if( !param.validity )
//			fitness /= param.tmpCounter * penalty;
//		
////		fitness += Math.abs(penality * param.tmpCounter);
//		System.out.println("FITNESS: " + fitness + "                          Ascendent: " + param.ascendent + " Descendent: " + param.descendent + " Equal: " + param.equal + " Count: " + param.tmpCounter);
//		return Math.abs(fitness);
//	}
	
	public static void printHashMap(HashMap< Integer, CApplicationNode> appNode){
		Iterator<Integer> keys = appNode.keySet().iterator();
		Integer index;
		System.out.println("HASH SIZE: " + appNode.size());
		while(keys.hasNext()){
			index = keys.next();
			System.out.println("provID: " + pList[index].getID() + " appID: " + ((CApplicationNode) appNode.get(index)).getID() 
					+ " dudget: " + ((CApplicationNode) appNode.get(index)).getNodeBudget());
		}
	}
	
	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		
		double fitness=0;
		HashMap<Integer, CApplicationNode> applicationMap = getApplicationMap(arg0);
		CFitParameter param = new_evaluatePolicy(applicationMap);

		if (param.distance != 0) {
			double ret = 1 / param.distance;
			 System.out.println("PENALITY " +ret);// + " count: " + param.tmpCounter);
			return ret;
		}
		printHashMap(applicationMap);
		// Respect Equal
		fitness += Math.abs(param.equal);

		// Maximize ascendent
		fitness += Math.abs(param.ascendent);

		// Minimize descendent
		fitness += Math.abs(param.descendent);

		// fitness += Math.abs(penality * param.tmpCounter);
		System.out.println("FITNESS: " + fitness
				+ "                          Ascendent: " + param.ascendent
				+ " Descendent: " + param.descendent + " Equal: " + param.equal
				+ " Count: " + param.tmpCounter);
		return Math.abs(fitness);
	}
	
//	private CFitParameter evaluatePolicy(IChromosome ch){
//		CFitParameter param =  new CFitParameter();
//		CApplicationNode[] nodes = app.getNodes();
//		double val;
//		for(int i=0; i<ch.size(); i++){
//			Integer index = (Integer) ch.getGene(i).getAllele();
//			evalutateValidity(nodes[i], pList[index].getID(), param);
//			for( int j =0; j<policy.size(); j++){
//				if(policy.get(j).getGroup() == Constant.GLOBAL_CONSTRAIN ){	// GLOBAL CONSTRAIN
//					if( (val = policy.get(j).evaluateGlobalPolicy(app, pList[index])) > 0){ // violazione
//						param.distance += val;
//					}
//					//System.out.println("GLOBLA CONSTRAIN counter" + param.tmpCounter );
//				}
//				else{ //LOCAL CONSTRAIN
//					if ( (val = policy.get(j).evaluateLocalPolicy(nodes[i], pList[index])) >0 ){ //violazione
//						param.distance += val;
//					}
//				}
//				if(val <0 )
//					updateParameter(policy.get(j).getType(), val, param);
//				
//				
//			}
//		}
//		return param;
//	}
	
	public void updateParameter(char constrainType, double val, CFitParameter param){
		switch (constrainType) {
		case Constant.ASCENDENT_TYPE: param.ascendent += val; break;
		case Constant.DESCENDENT_TYPE: param.descendent += val; break;
		case Constant.EQUAL_TYPE: param.equal += val; break;
		default:
			break;
		}
	}
	
	
//	public void evalutateValidity(CApplicationNode node, int pID, CFitParameter param){
//		if(param.firstAllele >0 && param.firstAllele == pID){
//			param.validity = false;			
//			param.tmpCounter ++;
//			return;
//		}else
//			param.firstAllele = pID;
//	}
	

	private void updateHashMap(HashMap<Integer, CApplicationNode> tab, CApplicationNode node, Integer key ){
		CApplicationNode newNode;
		if( !tab.containsKey(key) ){
			newNode = node.clone();
			tab.put(key, newNode);
			return;
		}
		newNode = (CApplicationNode)tab.get(key);
		newNode.merge(node);
	}
	
	public HashMap<Integer, CApplicationNode> getApplicationMap(IChromosome ch){
		CFitParameter param =  new CFitParameter();
		HashMap<Integer, CApplicationNode> tab = new HashMap<Integer, CApplicationNode>();
		CApplicationNode[] nodes = app.getNodes();
		for(int i=0; i<ch.size(); i++){
			Integer index = (Integer) ch.getGene(i).getAllele();
			updateHashMap(tab, nodes[i], index);
		}
		return tab;
	}
	
	public CFitParameter new_evaluatePolicy(HashMap<Integer, CApplicationNode> applicationMap){
		CFitParameter param = new CFitParameter();
		Iterator<Integer> keys = applicationMap.keySet().iterator();
		CApplicationNode node;
		while(keys.hasNext()){
			int providerId = keys.next();
			node = applicationMap.get(providerId);
			// valutazione policy per ogni nodo
			
			double val;
			for( int j =0; j<policy.size(); j++){
				if(policy.get(j).getGroup() == Constant.GLOBAL_CONSTRAIN ){	// GLOBAL CONSTRAIN
					if( (val = policy.get(j).evaluateGlobalPolicy(app, pList[providerId])) > 0){ // violazione
						param.distance += val;
					}
					//System.out.println("GLOBLA CONSTRAIN counter" + param.tmpCounter );
				}
				else{ //LOCAL CONSTRAIN
					if ( (val = policy.get(j).evaluateLocalPolicy(node, pList[providerId])) >0 ){ //violazione
						param.distance += val;
						System.out.println("PORCOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO " + val);
					}
				}
				if(val <0 )
					updateParameter(policy.get(j).getType(), val, param);
			}
		}
		return param;
	}

	
	
	
	
	
}
