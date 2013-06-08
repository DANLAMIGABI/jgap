package it.cnr.isti.federation.metascheduler.test;

import it.cnr.isti.federation.Federation;
import it.cnr.isti.federation.FederationQueue;
import it.cnr.isti.federation.FederationQueueProfile;
import it.cnr.isti.federation.FederationQueueProvider;
import it.cnr.isti.federation.FederationTestBroker;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.metascheduler.BestSolution;
import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.metascheduler.MSPolicy;
import it.cnr.isti.federation.metascheduler.iface.Metascheduler;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.VmProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;

public class MetaschedulerSimulationTest {

	public static FederationTestBroker startMetaschedulerSimulation(List<FederationDatacenter> dcList, List<Application> applications,Properties app_prop) throws Exception {
		/*
		// Make Federation Federation
		Federation fed = new Federation("Federation");
		fed.setDatacenters(dcList);
		CloudSim.addEntity(fed);
		VmProvider.userId = fed.getId();

		// Make application applications = new ArrayList<>(); String
		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
		cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
		applications = new ArrayList<>();
		applications.add(MakeTestUtils.getFederationApplication(fed.getId(),
				Integer.parseInt(cloudlets), app_prop));

		// ApplicationUtility.vmToString(applications.get(0).getAllVms()); //
		// create the queue
		FederationQueueProfile queueProfile = FederationQueueProfile.getDefault();
		FederationQueue queue = FederationQueueProvider.getFederationQueue(queueProfile, fed, applications);
		CloudSim.addEntity(queue);
		*/
		 

		FederationTestBroker fed = new FederationTestBroker("FederationBroker");
		fed.setDatacenters(dcList);
		CloudSim.addEntity(fed);
		VmProvider.userId = fed.getId();
		

		// Make application
		applications = new ArrayList<>();
		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
		applications.add(MakeTestUtils.getFederationApplication(fed.getId(),Integer.parseInt(cloudlets), app_prop));
		
		List<MSPolicy> constraint = new ArrayList<>();
		constraint.add(MakePolicy.ramConstrain(1));
		constraint.add(MakePolicy.locationConstrain(1));
		BestSolution sol = Metascheduler.getMapping(applications.get(0),constraint, dcList);

		System.out.println("Metascheduler Results");
		HashMap<Integer, Integer> mappString = sol.getBest();
		
		if (sol.getFit() >= 1) {
			List<Vm> allVm = applications.get(0).getAllVms();
			int delay = 4;
			for (Vm vm : allVm) {
				Integer providerId = mappString.get(vm.getId());
				System.err.println("SENDING: vm " +vm.getId() +" TO dc: " +dcList.get(providerId).getId() );
				CloudSim.send(fed.getId(), dcList.get(providerId).getId(), delay++,CloudSimTags.VM_CREATE, vm);
				CloudSim.send(fed.getId(), dcList.get(providerId).getId(), 100,-1, vm);
			}
			CloudSim.terminateSimulation(1000);
			CloudSim.startSimulation();

			CloudSim.stopSimulation();
			// fed.deleteHostFromDatacenter();
			List<Cloudlet> newList = fed.getReceivedCloudlet();
			MakeTestUtils.printCloudletList(newList);
		}
		
		System.out.println("Fitness: " + sol.getFit());
		if (sol.getFit() < 1)
			System.out.println("SOLUZIONE NON VALIDA");
		for (int i = 0; i < mappString.size(); i++) {
			System.out.println("Node: " + i + " -> " + " Prov: "
					+ dcList.get(mappString.get(i)).getId() + "     "
					+ mappString.get(i));
		}
		List<String> message = sol.getMessages();
		for( String s : message){
			System.out.println(s);
		}
		
		
		return fed;
	}

}
