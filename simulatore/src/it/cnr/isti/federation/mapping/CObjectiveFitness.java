package it.cnr.isti.federation.mapping;

import it.cnr.isti.TestJgapExampleUtility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import contrailJgap.ConfigurationJGAPQos;

import cApplication.CApplicationNode;
import cApplicationIface.ICApplication;
import cPolicy.CPolicy;
import cProviderIface.ICProvider;

public class CObjectiveFitness extends FitnessFunction{
	
	static private ICApplication application;
	static private List<ICProvider> providerList;
	static private List<CPolicy> policy;
	
	static private List<String> aggregationParam;
	
	
	public CObjectiveFitness(ConfigurationFitness conf){
		application = conf.getApplication();
		providerList = conf.getProviders();
		policy = conf.getConstrains();
		aggregationParam = conf.getAggregationParams();
		System.out.println("------------------------------- " + policy.size());
		
	}
	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		System.out.println("                     " + arg0.getGene(0).getAllele());
		double fitness=0;
		HashMap<Integer, CApplicationNode> applicationMap = getApplicationMapping(arg0);
		CFitParameter param = evaluatePolicy(applicationMap);

		if (param.violation != 0) {
			double ret = 1 / param.violation;
			 System.out.println("PENALITY " +ret);// + " count: " + param.tmpCounter);
			return ret;
		}
//		 printHashMap(applicationMap); 
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
	
	private HashMap<Integer, CApplicationNode> getApplicationMapping(IChromosome ch){
		CFitParameter param =  new CFitParameter();
		HashMap<Integer, CApplicationNode> applicationTable = new HashMap<Integer, CApplicationNode>();
		List<CApplicationNode> nodes = application.getNodes();
		for(int i=0; i<ch.size(); i++){
			Integer index = (Integer) ch.getGene(i).getAllele();
			aggregateNode(applicationTable, nodes.get(i), index);
		}
		return applicationTable;
	}
	
	private void aggregateNode(HashMap<Integer, CApplicationNode> applicationTab, CApplicationNode node, Integer key){
		CApplicationNode tempNode;
		if( !applicationTab.containsKey(key) ){
			tempNode = node.clone();
			applicationTab.put(key, tempNode);
			return;
		}
		tempNode = (CApplicationNode)applicationTab.get(key);
		for(int i=0; i<aggregationParam.size(); i++){
			aggregateCharacteristic(aggregationParam.get(i), tempNode.getComputing().getCharacteristic(), node.getComputing().getCharacteristic());
			aggregateCharacteristic(aggregationParam.get(i), tempNode.getNetwork().getCharacteristic(), node.getNetwork().getCharacteristic());
			aggregateCharacteristic(aggregationParam.get(i), tempNode.getStorage().getCharacteristic(), node.getStorage().getCharacteristic());
		}
		applicationTab.put(key, tempNode);
		
	}
	
	private void aggregateCharacteristic(String aggregationKey, HashMap<String, Object> map1, HashMap<String, Object> map2){
		
		Object obj1 = map1.get(aggregationKey);
		Object obj2 = map2.get(aggregationKey);
		
		if(obj1 instanceof Integer){
			obj1 = (Integer) obj1 + (Integer) obj2;
		}else if( obj1 instanceof Long ){
			obj1 = (Long) obj1 + (Long) obj2;
		}else if( obj1 instanceof Double){
			obj1 = (Double)obj1 + (Double) obj2;
		}
		map1.put(aggregationKey, obj1);
		
	}
	
	private CFitParameter evaluatePolicy(HashMap<Integer, CApplicationNode> applicationMap){
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
					if( (val = policy.get(j).evaluateGlobalPolicy(application, providerList.get(providerId))) > 0){ // violazione
						param.violation += val;
					}
					//System.out.println("GLOBLA CONSTRAIN counter" + param.tmpCounter );
				}
				else{ //LOCAL CONSTRAIN
					if ( (val = policy.get(j).evaluateLocalPolicy(node, providerList.get(providerId))) >0 ){ //violazione
						param.violation += val;
					}
				}
				if(val <0 )
					updateParameter(policy.get(j).getType(), val, param);
			}
		}
		return param;
	}
	
	private void updateParameter(char constrainType, double val, CFitParameter param){
		switch (constrainType) {
		case Constant.ASCENDENT_TYPE: param.ascendent += val; break;
		case Constant.DESCENDENT_TYPE: param.descendent += val; break;
		case Constant.EQUAL_TYPE: param.equal += val; break;
		default:
			break;
		}
	}
/*
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
	
	*/
	
	

	
	
	
	
	
}
