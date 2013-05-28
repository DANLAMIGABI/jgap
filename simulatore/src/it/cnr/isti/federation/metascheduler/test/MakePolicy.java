package it.cnr.isti.federation.metascheduler.test;


import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.metascheduler.MSPolicy;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationStorage;
import it.cnr.isti.federation.metascheduler.resources.MSProviderStorage;
import it.cnr.isti.federation.metascheduler.resources.iface.*;

import java.util.HashMap;

import org.jgap.xml.ImproperXMLException;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;



public class MakePolicy {
	
	public static MSPolicy makeCostPolicy(double weight){
		if( weight < 0 )
			return null;
		return new MSPolicy(weight, Constant.DESCENDENT_TYPE, Constant.LOCAL_CONSTRAIN) {

			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				
				return 0;
			}

			@Override
			public double evaluateLocalPolicy(MSApplicationNode node,
					IMSProvider prov) {
				// TODO Auto-generated method stub
				HashMap<String ,Object> nodeTratis = node.getCharacteristic();
				HashMap<String, Object> providerTraits = prov.getCharacteristic();
				Double budget = (Double) nodeTratis.get(Constant.BUDGET);
				Double cost = (Double) providerTraits.get(Constant.COST_SEC);

				double constrain = cost - budget;
				return (constrain >0) ? (constrain +1)*getWeight() : (constrain -1)*getWeight();
			}
		};
	}
	
	public static MSPolicy makePlacePolicy(double weight){
		if( weight < 0 )
			return null;
		return new MSPolicy(weight,Constant.EQUAL_TYPE, Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				
				return 0;
			}

			@Override
			public double evaluateLocalPolicy(MSApplicationNode node,
					IMSProvider prov) {
				// TODO Auto-generated method stub
				HashMap<String ,Object> nodeTratis = node.getCharacteristic();
				HashMap<String, Object> providerTraits = prov.getCharacteristic();
				
				String nodePlace = (String) nodeTratis.get(Constant.PLACE);
				String providerPlace = (String) providerTraits.get(Constant.PLACE);
				
				double constrain = Math.abs(nodePlace.compareToIgnoreCase(providerPlace));
				
				double ret = (constrain - 0.9) >0 ? getWeight() : -getWeight();
//				System.out.println("MakePolicy.EqualPolicy.Place nodePlace " + nodePlace + "    ProvPlace " 
//				+ providerPlace + "   compare: " + constrain + "    ret: " + ret);
				return ret;
			}
		};
	}
	
	
	public static MSPolicy makeStoragePolicy(double weight){
		if( weight < 0 )
			return null;
		return new MSPolicy(weight,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				
				MSApplicationStorage nodeStorage = node.getStorage();
				MSProviderStorage provStorage = prov.getStorage();
				HashMap<String ,Object> storageTratis = nodeStorage.getCharacteristic();
				HashMap<String, Object> providerTraits = provStorage.getCharacteristic();
				
				long nodeStore = (long)storageTratis.get(Constant.STORE);
				long provStore = (long) providerTraits.get(Constant.STORE);
				double constrain =  (nodeStore/1024) - (provStore/1024);

				double ret = constrain >0 ? (constrain +1) * getWeight() : (constrain -1)*getWeight();

				return ret;
				
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	
	public static MSPolicy makeTest1(double weight){
		if( weight <0)
			return null;
		return new MSPolicy(weight, Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				
				MSApplicationStorage nodeStorage = node.getStorage();
				MSProviderStorage provStorage = prov.getStorage();
				HashMap<String ,Object> storageTratis = nodeStorage.getCharacteristic();
				HashMap<String, Object> providerTraits = provStorage.getCharacteristic();
				
				long nodeStore = (long)storageTratis.get(Constant.STORE);
				long provStore = (long) providerTraits.get(Constant.STORE);
				double constrain =  (nodeStore/1024) - (provStore/1024);

				double ret = constrain >0 ? (constrain +1) * getWeight() : (constrain -1)*getWeight();

				return ret;
			}
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	public static MSPolicy makeStoreCost(double weight){
		if( weight <0)
			return null;
		return new MSPolicy(weight, Constant.DESCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				
				MSApplicationStorage nodeStorage = node.getStorage();
				MSProviderStorage provStorage = prov.getStorage();
				HashMap<String ,Object> storageTratis = nodeStorage.getCharacteristic();
				HashMap<String, Object> providerTraits = provStorage.getCharacteristic();
				
				long nodeStore = (long)storageTratis.get(Constant.STORE);
				double nodeBudget = (double) node.getCharacteristic().get(Constant.BUDGET);
				
				double provCost = (double) providerTraits.get(Constant.COST_STORAGE);
				
				double constrain =  (nodeStore/1024)*provCost - nodeBudget;
//System.out.println("aaa " + constrain);
				double ret = constrain >0 ? (constrain +1) * getWeight() : (constrain -1)*getWeight();

				return ret;
			}
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
		
		public static MSPolicy fedStore(double weight){
			if( weight <0)
				return null;
			return new MSPolicy(weight, Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
				
				@Override
				public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
					
					MSApplicationStorage nodeStorage = node.getStorage();
					MSProviderStorage provStorage = prov.getStorage();
					HashMap<String ,Object> storageTratis = nodeStorage.getCharacteristic();
					HashMap<String, Object> providerTraits = provStorage.getCharacteristic();
					
					long nodeStore = (long)storageTratis.get(Constant.STORE);
					long provStore = (long) providerTraits.get(Constant.STORE);
					double freeSpace = (provStore/1024) - (nodeStore/1024);
//					System.out.println("sfajfafj;afjfjdkfjfjfdsjfklfjjf; " + freeSpace);
					double constrain = (freeSpace/(provStore/1024)) -1;
					
					
					double ret = constrain >0 ? (constrain +1) * getWeight() : (constrain -1)*getWeight();
					System.out.println("node: " + node.getID() + " prov: " + prov.getID() + " const: " +ret);
					return ret;
				}
				@Override
				public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
					// TODO Auto-generated method stub
					return 0;
				}
				
			};
	}

}
