package test;

import cApplication.CApplicationNode;
import cApplicationIface.ICAppStorage;
import cApplicationIface.ICApplication;
import cPolicy.CPolicy;
import cProviderIface.ICProvider;
import cProviderIface.ICProviderStorage;

public class MakePolicy {
	
	public static CPolicy makeCostPolicy(){
		return new CPolicy(1, Constant.DESCENDENT_TYPE, Constant.LOCAL_CONSTRAIN) {

			@Override
			public double evaluateGlobalPolicy(ICApplication app, ICProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public double evaluateLocalPolicy(CApplicationNode node,
					ICProvider prov) {
				// TODO Auto-generated method stub
				return (prov.getCost() - node.getNodeBudget()) * getWeight();
			}
		};
	}
	
	public static CPolicy makePlacePolicy(){
		return new CPolicy(6,Constant.EQUAL_TYPE, Constant.GLOBAL_CONSTRAIN) {
			
			@Override
			public double evaluateGlobalPolicy(ICApplication app, ICProvider prov) {
				// TODO Auto-generated method stub
				if( app.getPlace().compareToIgnoreCase(prov.getPlace()) == 0 )
					return -1 * getWeight();
				return 1 * getWeight();
			}

			@Override
			public double evaluateLocalPolicy(CApplicationNode node,
					ICProvider prov) {
				// TODO Auto-generated method stub
				if( node.getNodePlace().compareToIgnoreCase(prov.getPlace()) == 0 )
					return -1 * getWeight();
				return 1 * getWeight();
			}
		};
	}
	
	public static CPolicy makeStoragePolicy(){
		return new CPolicy(1,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(CApplicationNode node, ICProvider prov) {
				// TODO Auto-generated method stub
				ICAppStorage astore = node.getStorage();
				ICProviderStorage pstore = prov.getStorage();
				return (astore.getAmount() - pstore.getAmount()) * getWeight() ;
				
			}
			
			@Override
			public double evaluateGlobalPolicy(ICApplication app, ICProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

}
