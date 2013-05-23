package it.cnr.isti.federation.metascheduler.iface;



import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.metascheduler.BestSolution;
import it.cnr.isti.federation.metascheduler.JGAPMapping;
import it.cnr.isti.federation.metascheduler.MSPolicy;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;
import it.cnr.isti.federation.metascheduler.test.FederationPowerDatacenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.power.PowerHost;

public class Metascheduler {
	
	
	public static BestSolution getMapping(Application application, List<MSPolicy> policy, List<FederationPowerDatacenter> dclist){
		List<IMSProvider> providerList = new ArrayList<>();
		
		for(int i=0; i<dclist.size(); i++){
			providerList.add(MSProviderUtility.datacenterToMSProvider(dclist.get(i)));
		}
		
		IMSApplication msApplication = MSApplicationUtility.getMSApplication(application);
		
		return JGAPMapping.startMapping(msApplication, providerList, policy, new ArrayList<String>());
		
		
	}
	

	
	

}
