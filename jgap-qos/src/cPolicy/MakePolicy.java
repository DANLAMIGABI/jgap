package cPolicy;

import test.Constant;
import cApplication.CApplicationNode;
import cApplicationIface.ICAppStorage;
import cApplicationIface.ICApplication;
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
				double constrain = prov.getCost() - node.getNodeBudget();
				return (constrain >0) ? (constrain +1)*getWeight() : (constrain -1)*getWeight();
			}
		};
	}
	
	public static CPolicy makePlacePolicy(double weight){
		if( weight < 0 )
			return null;
		return new CPolicy(weight,Constant.EQUAL_TYPE, Constant.GLOBAL_CONSTRAIN) {
			
			@Override
			public double evaluateGlobalPolicy(ICApplication app, ICProvider prov) {
				// TODO Auto-generated method stub
				double constrain = Math.abs(app.getPlace().compareToIgnoreCase(prov.getPlace()));
				
				return (constrain - 0.9) >0 ? getWeight() : -getWeight();
			}

			@Override
			public double evaluateLocalPolicy(CApplicationNode node,
					ICProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	
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

}
