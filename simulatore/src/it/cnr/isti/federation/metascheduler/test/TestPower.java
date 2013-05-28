package it.cnr.isti.federation.metascheduler.test;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.test.DataSette;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

public class TestPower {
	private static DatacenterBroker broker;
	private static List<FederationDatacenter> dcList;
    private static List<Application> applications;
    
    private static List<Cloudlet> cloudletList;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// First step: Initialize the CloudSim package. It should be called
		// before creating any entities.
		int num_user = 1; // number of cloud users
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false; // mean trace events
		// Initialize the CloudSim library
		CloudSim.init(num_user, calendar, trace_flag);
		// Initialize the CloudSim library
		CloudSim.init(num_user, calendar, trace_flag);
		broker = createBroker();
		int brokerId = broker.getId();		
		
		dcList = DataSette.generateDatanceters(10, 10, 4, 8);
		
		//Applications
		applications = new ArrayList<>();
		Application testApp = ApplicationUtility.getFederationApplication(brokerId, 1);
		applications.add(testApp);
		
//		System.out.println(testApp.getAllVms().get(0).getRam());
//		System.err.println(testApp.getAllVms().get(0).getMips());
//		System.out.println(testApp.getAllVms().get(0).getSize());
		
		// broker.submitVmList(applications.get(0).getAllVms());

		List<Vm> vmList = new ArrayList<>();
		vmList.add(applications.get(0).getAllVms().get(0));
		broker.submitVmList(vmList);

		// Fifth step: Create one Cloudlet
		cloudletList = new ArrayList<Cloudlet>();
		cloudletList.add(applications.get(0).getAllCloudlets().get(0));
		
		broker.submitCloudletList(cloudletList);

		CloudSim.send(brokerId, dcList.get(0).getId(), 0, 10, dcList.get(0));
		CloudSim.startSimulation();

		CloudSim.stopSimulation();
		List<Cloudlet> newList = broker.getCloudletReceivedList();
		SimulationUtility.printCloudletList(newList);

	}
	
	private static DatacenterBroker createBroker() {
        DatacenterBroker broker = null;
        try {
                broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
                e.printStackTrace();
                return null;
        }
        return broker;
}

}
