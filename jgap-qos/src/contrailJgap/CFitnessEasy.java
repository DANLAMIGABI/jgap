package contrailJgap;

import java.security.Policy;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
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
	
	static private ICApplication application;
	static private ICProvider[] providerList;
	static private List<CPolicy> policy;
	
	public CFitnessEasy(double tollerance,ICApplication app, ICProvider[] provList, List<CPolicy> policy){
		CFitnessEasy.tollerance = tollerance;
		CFitnessEasy.application = app;
		providerList = provList;
		CFitnessEasy.policy = policy;
	}
	
	public static void printHashMap(HashMap< Integer, CApplicationNode> appNode){
		Iterator<Integer> keys = appNode.keySet().iterator();
		Integer index;
		System.out.println("HASH SIZE: " + appNode.size());
		while(keys.hasNext()){
			index = keys.next();
			System.out.println("provID: " + providerList[index].getID() + " appID: " + ((CApplicationNode) appNode.get(index)).getID() 
					+ " dudget: " + ((CApplicationNode) appNode.get(index)).getNodeBudget());
		}
	}
	
	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		
		double fitness=0;
		HashMap<Integer, CApplicationNode> applicationMap = getApplicationMap(arg0);
		CFitParameter param = evaluatePolicy(applicationMap);

		if (param.violation != 0) {
			double ret = 1 / param.violation;
			 System.out.println("PENALITY " +ret);// + " count: " + param.tmpCounter);
			return ret;
		}
		printHashMap(applicationMap);
		// Respect Equal
		fitness += param.equal;

		// Maximize ascendent
		fitness += param.ascendent;

		// Minimize descendent
		fitness += param.descendent;

		// fitness += Math.abs(penality * param.tmpCounter);
		System.out.println("FITNESS: " + fitness
				+ "                          Ascendent: " + param.ascendent
				+ " Descendent: " + param.descendent + " Equal: " + param.equal
				+ " Count: " + param.tmpCounter);
		return Math.abs(fitness);
	}
	
	public HashMap<Integer, CApplicationNode> getApplicationMap(IChromosome ch){
		CFitParameter param =  new CFitParameter();
		HashMap<Integer, CApplicationNode> tab = new HashMap<Integer, CApplicationNode>();
		List<CApplicationNode> nodes = application.getNodes();
		for(int i=0; i<ch.size(); i++){
			Integer index = (Integer) ch.getGene(i).getAllele();
			updateHashMap(tab, nodes.get(i), index);
		}
		return tab;
	}
	
	public CFitParameter evaluatePolicy(HashMap<Integer, CApplicationNode> applicationMap){
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
					if( (val = policy.get(j).evaluateGlobalPolicy(application, providerList[providerId])) > 0){ // violazione
						param.violation += val;
					}
					//System.out.println("GLOBLA CONSTRAIN counter" + param.tmpCounter );
				}
				else{ //LOCAL CONSTRAIN
					if ( (val = policy.get(j).evaluateLocalPolicy(node, providerList[providerId])) >0 ){ //violazione
						param.violation += val;
					}
				}
				if(val <0 )
					updateParameter(policy.get(j).getType(), val, param);
			}
		}
		return param;
	}
	
	public void updateParameter(char constrainType, double val, CFitParameter param){
		switch (constrainType) {
		case Constant.ASCENDENT_TYPE: param.ascendent += val; break;
		case Constant.DESCENDENT_TYPE: param.descendent += val; break;
		case Constant.EQUAL_TYPE: param.equal += val; break;
		default:
			break;
		}
	}

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
	
	
	
	

	
	
	
	
	
}
