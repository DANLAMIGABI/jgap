package it.cnr.isti.federation.metascheduler.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import it.cnr.isti.federation.Federation;
import it.cnr.isti.federation.FederationQueue;
import it.cnr.isti.federation.FederationQueueProfile;
import it.cnr.isti.federation.FederationQueueProvider;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.VmProvider;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.core.CloudSim;

public class CloudSimTest {
	
	
	public static Federation startCloudSimSimulation(List<FederationDatacenter> dcList, List<Application> applications, Properties app_prop){
		
		
		
		// Make Federation
		Federation fed = new Federation("Federation");
		fed.setDatacenters(dcList);
		CloudSim.addEntity(fed);
		VmProvider.userId = fed.getId();
		
		// Make application
		applications = new ArrayList<>();
		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
		applications.add(MakeTestUtils.getFederationApplication(fed.getId(),Integer.parseInt(cloudlets),app_prop));

		// create the queue
		FederationQueueProfile queueProfile = FederationQueueProfile.getDefault();
		FederationQueue queue = FederationQueueProvider.getFederationQueue(queueProfile, fed, applications);
		CloudSim.addEntity(queue);
		long start = System.currentTimeMillis();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start simulation CloudSim ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		CloudSim.terminateSimulation(1000);
		CloudSim.startSimulation();
		
		CloudSim.stopSimulation();
		System.out.println("TEMPO ESECUZIONE " + (System.currentTimeMillis()-start)/new Double(1000));
//		fed.deleteHostFromDatacenter();
		List<Cloudlet> newList = fed.getReceivedCloudlet();
		MakeTestUtils.printCloudletList(newList);
		return fed;
	}

}
