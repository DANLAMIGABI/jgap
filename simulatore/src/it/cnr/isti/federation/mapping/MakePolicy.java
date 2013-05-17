package it.cnr.isti.federation.mapping;

import java.util.HashMap;


import metaschedulerJgap.MSPolicy;
import msProviderIface.*;
import msApplication.*;
import msApplicationIface.*;


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
				System.out.println("MakePolicy.EqualPolicy.Place nodePlace " + nodePlace + "    ProvPlace " 
				+ providerPlace + "   compare: " + constrain + "    ret: " + ret);
				return ret;
			}
		};
	}
	/*
	public static MSPolicy makeStoragePolicy(double weight){
		if( weight < 0 )
			return null;
		return new MSPolicy(weight,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				ICAppStorage astore = node.getStorage();
				IMSProviderStorage pstore = prov.getStorage();
				double constrain = astore.getAmount() - pstore.getAmount();
				return constrain >0 ? (constrain +1) * getWeight() : (constrain -1)*getWeight();
				
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
*/
}
