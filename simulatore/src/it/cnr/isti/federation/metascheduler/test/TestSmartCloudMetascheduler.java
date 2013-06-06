package it.cnr.isti.federation.metascheduler.test;

import it.cnr.isti.federation.Allocation;
import it.cnr.isti.federation.Federation;
import it.cnr.isti.federation.FederationQueue;
import it.cnr.isti.federation.FederationQueueProfile;
import it.cnr.isti.federation.FederationQueueProvider;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.VmProvider;
import it.cnr.isti.federation.metascheduler.*;
import it.cnr.isti.federation.metascheduler.iface.Metascheduler;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.SecuritySupport;
import it.cnr.isti.test.DataSette;
import it.cnr.isti.test.TestResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.power.PowerHost;

public class TestSmartCloudMetascheduler {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;
	private static List<FederationDatacenter> dcList;
	private static List<Application> applications;
	private static List<IMSProvider> providerList;
	private static DatacenterBroker broker;
	private static HashMap<String, Object> paramDatacenter;

	/** The vmlist. */
	private static List<Vm> vmList;

	private static void submitEntireApp(Application app, int brokerId) {
		// submit application to cloudsim
		submitApplication(app);

		// submit events cloudsim
		CloudSim.terminateSimulation(100);
		for (int i = 0; i < dcList.size(); i++) {
			CloudSim.send(brokerId, dcList.get(i).getId(), 3, 10, dcList.get(i));
		}
		// Starts the simulation

	}

	private static void submitSingleAppNode(int datacenterId, Vm vm,
			double delay, int brokerId) {
		CloudSim.send(brokerId, dcList.get(datacenterId).getId(), delay,
				CloudSimTags.VM_CREATE, vm);
	}

	/**
	 * Creates main() to run this example.
	 * 
	 * @param args
	 *            the args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Log.printLine("Starting CloudSimExample1...");
		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);
			broker = createBroker();
			int brokerId = broker.getId();

			// make Datacenter
			initDatacenter(1);
			DatacenterUtility.printFederationDataCenter(dcList);
			// dcList = DataSette.generateDatanceters(10, 10, 4, 8);
//			MakeTestUtils.saveFederationState("Federation.save", dcList);

			// create the federation
			Federation fed = new Federation("Federation");
			fed.setDatacenters(dcList);
			CloudSim.addEntity(fed);
			VmProvider.userId = fed.getId();

			// Applications
			applications = new ArrayList<>();
			applications.add(ApplicationUtility.getFederationApplication(fed.getId(), 1));

//			ApplicationUtility.vmToString(applications.get(0).getAllVms());

			// create the queue
			FederationQueueProfile queueProfile = FederationQueueProfile.getDefault();
			FederationQueue queue = FederationQueueProvider.getFederationQueue(	queueProfile, fed, applications);
			CloudSim.addEntity(queue);

			// Datacenter initial/final Status
			for (FederationDatacenter dc : dcList) {
				CloudSim.send(fed.getId(), dc.getId(), 0, 9000, dc);
				CloudSim.send(fed.getId(), dc.getId(), 7, 9000, dc);
			}

			// SIMULATION
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start simulation CloudSim ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			CloudSim.terminateSimulation(1000);
			CloudSim.startSimulation();

			CloudSim.stopSimulation();
			List<Cloudlet> newList = fed.getReceivedCloudlet();
			printCloudletList(newList);

			// Print the debt of each user to each datacenter
			// datacenter0.printDebts();

			Log.printLine("\n========== ALLOCATIONS ==========");
			for (Allocation a : fed.getAllocations()) {
				// Log.printLine(a.getDuration());
				TestResult res = new TestResult();
				res.simtime = a.getSimDuration();
				res.realtime = a.getRealDuration();

				if (a.isCompleted() == false)
					System.out.println("Failed");
				else
					System.out.println("Real duration " + a.getRealDuration());

			}
			/*
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start simulation Metascheduler ~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			List<MSPolicy> constraint = new ArrayList<>();
			constraint.add(MakePolicy.makeCostPolicy(100000));
			constraint.add(MakePolicy.makeCostRam(100000));
		
			BestSolution sol = Metascheduler.getMapping(applications.get(0),
					constraint, dcList);
*/
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			System.out.println("FederationSim Results");

			HashMap<Integer, Integer> allocatedToDatacenter = fed
					.getVmToDatacenter();
			Iterator<Integer> keys = allocatedToDatacenter.keySet().iterator();
			while (keys.hasNext()) {
				Integer i = keys.next();
				System.out.println("VM #" + i + " Allocated in datacenter #"
						+ allocatedToDatacenter.get(i));

			}
			MakeTestUtils.printDatacenter(dcList.get(0));
/*
			System.out.println("Metascheduler Results");
			HashMap<Integer, Integer> mappString = sol.getBest();
			System.out.println("Fitness: " + sol.getFit());
			if (sol.getFit() < 1)
				System.out.println("SOLUZIONE NON VALIDA");
			for (int i = 0; i < mappString.size(); i++) {
				System.out.println("Node: " + i + " -> " + " Prov: "
						+ dcList.get(mappString.get(i)).getId() + "     ");
			}
*/
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Unwanted errors happen");
		}

	}

	// We strongly encourage users to develop their own broker policies, to
	// submit vms and cloudlets according
	// to the specific rules of the simulated scenario
	/**
	 * Creates the broker.
	 * 
	 * @return the datacenter broker
	 */
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

	private static void initDatacenter(int size) {
		dcList = new ArrayList<>();
		// providerList = new ArrayList<>();
		FederationDatacenter temp;

		paramDatacenter = DatacenterUtility.getDatacenterParam();
		for (int i = 0; i < size; i++) {
			paramDatacenter.put(Constant.PLACE, "italia");
			paramDatacenter.put(Constant.COST_STORAGE, 70000.0 - i * 2000);
			paramDatacenter.put(Constant.COST_MEM, 1000.0 + i * 200);
			temp = DatacenterUtility.getDatacenter(paramDatacenter, 1,i * 10 + 1);
			dcList.add(temp);
		}
		DataSette.net = setInternetEstimator(dcList, dcList.size());
	}
	
	private static InternetEstimator setInternetEstimator(List<FederationDatacenter> dcList, int numOfDatacenters){
		InternetEstimator inetEst = new InternetEstimator(numOfDatacenters);
		for (FederationDatacenter top: dcList)
		{
			for (FederationDatacenter bot: dcList)
			{
				inetEst.defineDirectLink(top, bot, 1024*1024*10, 100, SecuritySupport.ADVANCED);
			}
		}
		return inetEst;
	}

	private static void submitApplication(Application application) {
		// Vm List
		vmList = new ArrayList<>();
		for (int i = 0; i < application.getAllVms().size(); i++) {
			vmList.add(application.getAllVms().get(i));
		}
		broker.submitVmList(vmList);
		// add the cloudlet to the list
		cloudletList = new ArrayList<>();
		for (int i = 0; i < application.getAllCloudlets().size(); i++) {
			cloudletList.add(application.getAllCloudlets().get(i));
		}

		// submit cloudlet list to the broker
		broker.submitCloudletList(cloudletList);

	}

	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
				+ "Data center ID" + indent + "VM ID" + indent + "Time"
				+ indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getResourceId()
						+ indent + indent + indent + cloudlet.getVmId()
						+ indent + indent
						+ dft.format(cloudlet.getActualCPUTime()) + indent
						+ indent + dft.format(cloudlet.getExecStartTime())
						+ indent + indent
						+ dft.format(cloudlet.getFinishTime()));
			}
		}
	}

	

	private static void printAppInfo(Application app, int brokerId) {
		System.out.println("INFO:");
		System.out.println("  Vm size: " + app.getAllVms().size());
		System.out.println("     VmUser ID: "
				+ app.getAllVms().get(0).getUserId());
		System.out.println("  Cloudlet size: " + app.getAllCloudlets().size());
		System.out.println("  uID: " + app.getAllVms().get(0).getUserId()
				+ " brokerId: " + brokerId);
		System.out.println("  Cloudlet UID: "
				+ app.getAllCloudlets().get(0).getUserId());
	}
	/*
	 * // make Datacenter initDatacenter(5); //
	 * DatacenterUtility.printFederationDataCenter(dcList);
	 * 
	 * VmProvider.userId = brokerId; // Application app =
	 * DataSette.getApplication(10); //
	 * System.out.println(app.getAllVms().size());
	 * 
	 * Application app = new BusinessApplication(brokerId, 5);
	 * app.setBudget(1000.02); app.setPlace("italia");
	 * 
	 * // System.out.println(app.get); List<MSPolicy> test = new ArrayList<>();
	 * // test.add(MakePolicy.makeCostPolicy(1)); //
	 * test.add(MakePolicy.makePlacePolicy(2)); //
	 * test.add(MakePolicy.makeStoragePolicy(1));
	 * test.add(MakePolicy.makeStoreCost(1)); test.add(MakePolicy.makeTest1(1));
	 * test.add(MakePolicy.fedStore(10000)); int count =0; int buone =0; for(int
	 * h=0; h<1; h++){ count =0; System.out.println("#### iterazione: " + h);
	 * BestSolution sol = Metascheduler.getMapping(app, test, dcList);
	 * HashMap<Integer, Integer> mappString = sol.getBest();
	 * System.out.println("                            Fitness: " +
	 * sol.getFit()); if (sol.getFit() < 1)
	 * System.out.println("						SOLUZIONE NON VALIDA"); for (int i = 0; i <
	 * mappString.size(); i++) { System.out.println("Node: " + i + " -> " +
	 * " Prov: " + dcList.get(mappString.get(i)).getId() + "     " ); } }
	 * System.out.println("                 FINE: ");
	 */
	// private static void oldTest(){
	// try{
	//
	// initDatacenter();
	//
	// // DatacenterUtility.printFederationDataCenter(dcList);
	// DatacenterUtility.printProviderList(providerList);
	//
	// // Third step: Create Broker
	// broker = createBroker();
	// int brokerId = broker.getId();
	//
	// //Creazione applicazione
	// VmProvider.userId = brokerId;
	// Application app = ApplicationUtility.getApplication(brokerId,1);
	// List<Application> appList = new ArrayList<Application>();
	// appList.add(app);
	//
	// // dcList.get(0).get
	//
	//
	// //Conversione Applicazione in GAapplication
	// IMSApplication applicationMetaschduler =
	// ApplicationUtility.applicationToMSApplication(app,"italia", "1000.02");
	// System.out.println(ApplicationUtility.toStringMSApplication(applicationMetaschduler));
	//
	// ConfigurationJGAPQos conf = configMetascheduler(applicationMetaschduler);
	//
	//
	// JGAPMapping.init(conf);
	// BestSolution sol = JGAPMapping.startMapping();
	// HashMap<Integer, Integer> mappString = sol.getBest();
	// System.out.println("Fitness: " + sol.getFit());
	// for(int i=0; i<mappString.size(); i++){
	// System.out.println("Node: " + i + " -> " + " Prov: "+
	// dcList.get(mappString.get(i)).getId());
	// }
	//
	// Double ris = new Double(sol.getFit());
	// if(ris <1 )
	// System.out.println("SOLUZIONE NON VALIDA");
	//
	//
	// // CloudSim.send(brokerId, dcList.get(mappString.get(0)).getId(), 3, 10,
	// dcList.get(0) );
	// // submitSingleAppNode(mappString.get(0), app.getAllVms().get(0), 4,
	// brokerId);
	// // CloudSim.send(brokerId, dcList.get(mappString.get(0)).getId(), 5, 10,
	// dcList.get(mappString.get(0)) );
	// // CloudSim.startSimulation();
	//
	// // CloudSim.stopSimulation();
	//
	// /*
	// CloudSim.send(brokerId, dcList.get(0).getId(), 3, 10, dcList.get(0) );
	// // Running Test entire app
	// // submitEntireApp(app, brokerId);
	//
	// // Running test single VM
	// submitSingleAppNode(0, app.getAllVms().get(0), 4, brokerId);
	//
	// CloudSim.send(brokerId, dcList.get(0).getId(), 5, 10, dcList.get(0) );
	//
	// CloudSim.startSimulation();
	//
	// CloudSim.stopSimulation();
	//
	// //Final step: Print results when simulation is over
	// List<Cloudlet> newList = broker.getCloudletReceivedList();
	// SimulationUtility.printCloudletList(newList);
	//
	// Log.printLine("CloudSimExample1 finished!");
	// */
	// } catch (Exception e) {
	// e.printStackTrace();
	// Log.printLine("Unwanted errors happen");
	// }
	// }

	// //#### TEST ####
	// FederationPowerDatacenter datac = dcList.get(0);
	//
	// List<Host> hostList = datac.getHostList();
	// Host host = hostList.get(0);
	// host.getBw();
	// host.getRam();
	// host.getStorage();
	// host.getAvailableMips();
	//
	// Datacenter dc = (Datacenter) dcList.get(0);
	//
	// //#############
}
