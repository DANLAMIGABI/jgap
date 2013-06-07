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
	
	public static MSPolicy makeInstancesLimit(double weight){
		return new MSPolicy(weight,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				Integer instances =(Integer) node.getCharacteristic().get(Constant.VM_INSTANCES);
				Integer resources = (Integer) prov.getCharacteristic().get(Constant.VM_INSTANCES);
//				System.out.println("----------------------------instances "+ instances);
//				System.out.println("----------------------------resources " +resources);
				double constrain = instances - resources; 
				return (constrain >0) ? (constrain +1)*getWeight() : (constrain -1)*getWeight();
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	
	public static MSPolicy ramConstrain(double weight){
		return new MSPolicy(weight,Constant.ASCENDENT_TYPE,Constant.LOCAL_CONSTRAIN) {
			
			@Override
			public double evaluateLocalPolicy(MSApplicationNode node, IMSProvider prov) {
				// TODO Auto-generated method stub
				Integer nodeRam = (Integer) node.getComputing().getCharacteristic().get(Constant.RAM);
				Integer provRam = (Integer) prov.getComputing().getCharacteristic().get(Constant.RAM);
				double constrain = nodeRam - provRam;
				return (constrain >0) ? (constrain +1)*getWeight() : (constrain -1)*getWeight();
			}
			
			@Override
			public double evaluateGlobalPolicy(IMSApplication app, IMSProvider prov) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
	
	

}
