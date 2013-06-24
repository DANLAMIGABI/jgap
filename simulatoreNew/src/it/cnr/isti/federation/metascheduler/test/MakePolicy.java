package it.cnr.isti.federation.metascheduler.test;


import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.metascheduler.MSPolicy;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationStorage;
import it.cnr.isti.federation.metascheduler.resources.MSProviderStorage;
import it.cnr.isti.federation.metascheduler.resources.iface.*;

import java.util.HashMap;

import org.jgap.xml.ImproperXMLException;




public class MakePolicy {
	protected static long highStorageValue;
	protected static int highRamValue;
	protected static double highCostValue; 
	
//	public static MSPolicy makeInstancesLimit(double weight){
//		return new MSPolicy(weight,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
//			
//			@Override
//			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
//				// TODO Auto-generated method stub
//				Integer instances =(Integer) node.getCharacteristic().get(Constant.VM_INSTANCES);
//				Integer resources = (Integer) prov.getCharacteristic().get(Constant.VM_INSTANCES);
////				System.out.println("----------------------------instances "+ instances);
////				System.out.println("----------------------------resources " +resources);
//				double constrain = instances - resources; 
//				return (constrain >0) ? (constrain +1)*getWeight() : (constrain -1)*getWeight();
//			}
//			
//			@Override
//			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		};
//	}
	
	public static MSPolicy ramConstrain(double weight){
		return new MSPolicy(weight,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				Integer nodeRam = (Integer) node.getComputing().getCharacteristic().get(Constant.RAM);
				Integer provRam = (Integer) prov.getComputing().getCharacteristic().get(Constant.RAM);
				double constrain = (nodeRam - provRam)/highRamValue;
				
				return (constrain >0) ? (constrain +1)*getWeight() : (constrain -1)*getWeight();
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	
	public static MSPolicy locationConstrain(double weight){
		return new MSPolicy(weight, Constant.EQUAL_TYPE, Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				String nodePlace = (String) node.getCharacteristic().get(Constant.PLACE);
				String provPlace = (String)prov.getCharacteristic().get(Constant.PLACE);
				
				double ret = (nodePlace.toLowerCase().compareTo(provPlace.toLowerCase()) ==0 ) ? -1*getWeight() : 1*getWeight();
//				System.out.println(" makePolicy location:  app: " + nodePlace + " -- prov: " + provPlace + " ~~ " + ret);
				return ret;
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	
	public static MSPolicy costRamConstrain(double weight){
		return new MSPolicy(weight,Constant.DESCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				
				double costPerMem = (double) prov.getCharacteristic().get(Constant.COST_MEM);
				int ram = (int) node.getComputing().getCharacteristic().get(Constant.RAM);
				double budget =(double) node.getCharacteristic().get(Constant.BUDGET);
				double cost = ram * costPerMem;
				double constrain = (cost - budget)/highRamValue;
							
				return (constrain >0) ? (constrain+1)*getWeight() : (constrain-1)*getWeight();
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	public static MSPolicy storageConstrain(double weight){
		return new MSPolicy(weight, Constant.ASCENDENT_TYPE, Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				long nodeStore = (long)node.getStorage().getCharacteristic().get(Constant.STORE);
				long provStore = (long)prov.getStorage().getCharacteristic().get(Constant.STORE);
				double constrain = (nodeStore - provStore)/highStorageValue;
				return (constrain >0) ? (constrain+1)*getWeight() : (constrain -1)*getWeight();
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
	}
	
	

}
