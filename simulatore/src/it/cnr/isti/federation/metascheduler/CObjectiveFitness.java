package it.cnr.isti.federation.metascheduler;


import it.cnr.isti.federation.metascheduler.MSPolicy;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



import org.jgap.FitnessFunction;
import org.jgap.IChromosome;


public class CObjectiveFitness extends FitnessFunction{
	
	  static private IMSApplication application;
	  static private List<IMSProvider> providerList;
	 static private List<MSPolicy> policy;
	
	  static private String[] aggregationParam;
	
	
//	public CObjectiveFitness(ConfigurationFitness conf){
////		application = conf.getApplication();
////		providerList = conf.getProviders();
//		policy = conf.getConstrains();
//		aggregationParam = conf.getAggregationParams();
//		System.out.println("------------------------------- " + policy.size());
//		
//	}
	
	public CObjectiveFitness(IMSApplication app, List<IMSProvider> plist, List<MSPolicy> policyList){
		application = app;
		providerList = plist;
		policy = policyList;
		aggregationParam = Constant.aggregationParam;
	}
	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
//		System.out.println("   Chromosme size " + arg0.size());
		double fitness=0;
		HashMap<Integer, MSApplicationNode> applicationMap = getApplicationMapping(arg0);
		CFitParameter param = evaluatePolicy(applicationMap);

		if (param.violation != 0) {
			double ret = 1 / param.violation;
//			 System.out.println("PENALITY " +ret + " param.violation: " + param.violation);
//			 if(ret <1 )
//				 System.out.println("kfjdkffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
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
//		System.out.println("FITNESS: " + fitness
//				+ "                          Ascendent: " + param.ascendent
//				+ " Descendent: " + param.descendent + " Equal: " + param.equal
//				+ " Count: " + param.tmpCounter);
//		System.out.println("FITNESS: " + fitness);
		return Math.abs(fitness);
	}
	
	private HashMap<Integer, MSApplicationNode> getApplicationMapping(IChromosome ch){
		CFitParameter param =  new CFitParameter();
		HashMap<Integer, MSApplicationNode> applicationTable = new HashMap<Integer, MSApplicationNode>();
		List<MSApplicationNode> nodes = application.getNodes();
		for(int i=0; i<ch.size(); i++){
			Integer index = (Integer) ch.getGene(i).getAllele();
			aggregateNode(applicationTable, nodes.get(i), index);
		}
		return applicationTable;
	}
	
	private void aggregateNode(HashMap<Integer, MSApplicationNode> applicationTab, MSApplicationNode node, Integer key){
//		System.out.println("## LOG: aggregazione nodi fit");
		MSApplicationNode tempNode;
		if( !applicationTab.containsKey(key) ){
			tempNode = (MSApplicationNode)node.clone();
			applicationTab.put(key, tempNode);
			return;
		}
		tempNode = (MSApplicationNode)applicationTab.get(key);
		for(int i=0; i<aggregationParam.length; i++){
			aggregateCharacteristic(aggregationParam[i], tempNode.getComputing().getCharacteristic(), node.getComputing().getCharacteristic());
			aggregateCharacteristic(aggregationParam[i], tempNode.getNetwork().getCharacteristic(), node.getNetwork().getCharacteristic());
			aggregateCharacteristic(aggregationParam[i], tempNode.getStorage().getCharacteristic(), node.getStorage().getCharacteristic());
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
	
	private CFitParameter evaluatePolicy(HashMap<Integer, MSApplicationNode> applicationMap){
		CFitParameter param = new CFitParameter();
		Iterator<Integer> keys = applicationMap.keySet().iterator();
		MSApplicationNode node;
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
	private void updateHashMap(HashMap<Integer, MSApplicationNode> tab, MSApplicationNode node, Integer key ){
		MSApplicationNode newNode;
		if( !tab.containsKey(key) ){
			newNode = node.clone();
			tab.put(key, newNode);
			return;
		}
		newNode = (MSApplicationNode)tab.get(key);
		newNode.merge(node);
	}
	
	*/
	
	

	
	
	
	
	
}
