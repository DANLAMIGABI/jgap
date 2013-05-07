package it.cnr.isti.federation.mapping;

import java.util.HashMap;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;

import cApplication.CApplicationNode;
import cApplicationIface.ICAppStorage;
import cApplicationIface.ICApplication;
import cPolicy.CPolicy;
import cProviderIface.ICProvider;
import cProviderIface.ICProviderStorage;

public class MakePolicy {
	
	public static CPolicy makeCostPolicy(double weight){
		if( weight < 0 )
			return null;
		return new CPolicy(weight, Constant.DESCENDENT_TYPE, Constant.LOCAL_CONSTRAIN) {

			@Override
			public double evaluateGlobalPolicy(ICApplication app, ICProvider prov) {
				// TODO Auto-generated method stub
				
				return 0;
			}

			@Override
			public double evaluateLocalPolicy(CApplicationNode node,
					ICProvider prov) {
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
	
	public static CPolicy makePlacePolicy(double weight){
		if( weight < 0 )
			return null;
		return new CPolicy(weight,Constant.EQUAL_TYPE, Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateGlobalPolicy(ICApplication app, ICProvider prov) {
				// TODO Auto-generated method stub
				
				return 0;
			}

			@Override
			public double evaluateLocalPolicy(CApplicationNode node,
					ICProvider prov) {
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
	public static CPolicy makeStoragePolicy(double weight){
		if( weight < 0 )
			return null;
		return new CPolicy(weight,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(CApplicationNode node, ICProvider prov) {
				// TODO Auto-generated method stub
				ICAppStorage astore = node.getStorage();
				ICProviderStorage pstore = prov.getStorage();
				double constrain = astore.getAmount() - pstore.getAmount();
				return constrain >0 ? (constrain +1) * getWeight() : (constrain -1)*getWeight();
				
			}
			
			@Override
			public double evaluateGlobalPolicy(ICApplication app, ICProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
*/
}
