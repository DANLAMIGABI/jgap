//package it.cnr.isti.federation.metascheduler.test;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Properties;
//import java.util.Random;
//
//import javax.imageio.stream.FileImageInputStream;
//
//import org.cloudbus.cloudsim.Cloudlet;
//import org.cloudbus.cloudsim.DatacenterBroker;
//import org.cloudbus.cloudsim.Host;
//import org.cloudbus.cloudsim.Vm;
//import org.cloudbus.cloudsim.core.CloudSim;
//
//import it.cnr.isti.federation.Federation;
//import it.cnr.isti.federation.FederationQueue;
//import it.cnr.isti.federation.FederationQueueProfile;
//import it.cnr.isti.federation.FederationQueueProvider;
//import it.cnr.isti.federation.application.Application;
//import it.cnr.isti.federation.metascheduler.BestSolution;
//import it.cnr.isti.federation.metascheduler.Constant;
//import it.cnr.isti.federation.metascheduler.MSPolicy;
//import it.cnr.isti.federation.metascheduler.iface.Metascheduler;
//import it.cnr.isti.federation.resources.FederationDatacenter;
//import it.cnr.isti.federation.resources.VmProvider;
//import it.cnr.isti.networking.InternetEstimator;
//import it.cnr.isti.networking.SecuritySupport;
//import it.cnr.isti.test.DataSette;
//
//public class DefinitiveTest {
//
//	private static List<FederationDatacenter> dcList;
//	private static List<Application> applications;
//	protected static Properties dc_prop;
//	protected static Properties app_prop;
//	private static Random rand;
//	
//	/**
//	 * @param args
//	 * @throws IOException 
//	 * @throws FileNotFoundException 
//	 */
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		// TODO Auto-generated method stub
//		
//		// First step: Initialize the CloudSim package. It should be called
//		// before creating any entities.
//		int num_user = 1; // number of cloud users
//		Calendar calendar = Calendar.getInstance();
//		boolean trace_flag = false; // mean trace events
//				
//		// Initialize the CloudSim library
//		CloudSim.init(num_user, calendar, trace_flag);
//		
//		dc_prop = new  Properties();
//		dc_prop.load(new FileInputStream("datacenter.config"));
//		app_prop = new Properties();
//		app_prop.load(new FileInputStream("application.config"));
//		rand = new Random(Long.parseLong(dc_prop.getProperty("seed")));
//		
//		//Datacenter Properties
//		Integer dc_number = Integer.parseInt(dc_prop.getProperty(Constant.DATACENTER_NUMEBER));
//		String[] dc_size = dc_prop.getProperty(Constant.DATACENTER_SIZES).toString().split(",");
//		String[] dc_places = dc_prop.get(Constant.DATACENTER_PLACES).toString().split(",");
//		
//		//Make Datacenter
//		dcList = new ArrayList<>();
//		for(int i=0;i<dc_number;i++)
//			dcList.add(MakeTestUtils.getDatacenter(dc_prop, Integer.parseInt(dc_size[0]), dc_places[i%dc_places.length], 0));
//		DataSette.net = setInternetEstimator(dcList, dcList.size());
//		
//		//SHUFFLE
////		Collections.shuffle(dcList, rand);
////		Collections.shuffle(dcList,rand);
//		
////		for(FederationDatacenter dc: dcList){
////			List<Host> hlist = dc.getHostList();
////			System.out.println("Datac Size: " + hlist.size());
////			for( Host h : hlist)
////				MakeTestUtils.printHostInfo(h);
////			System.out.println();
////		}
//		
////		for(FederationDatacenter dc : dcList)
////			MakeTestUtils.printDatacenter(dc);
//		
//		// Make Federation
//		Federation fed = new Federation("Federation");
//		fed.setDatacenters(dcList);
//		CloudSim.addEntity(fed);
//		VmProvider.userId = fed.getId();
//		
//		// Make application
//		applications = new ArrayList<>();
//		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
//		applications.add(MakeTestUtils.getFederationApplication(fed.getId(),Integer.parseInt(cloudlets),app_prop));
//		
//		
//		
////		ApplicationUtility.vmToString(applications.get(0).getAllVms());
//		// create the queue
//		FederationQueueProfile queueProfile = FederationQueueProfile.getDefault();
//		FederationQueue queue = FederationQueueProvider.getFederationQueue(queueProfile, fed, applications);
//		CloudSim.addEntity(queue);
//		
//		// Datacenter initial/final Status
////		for (FederationDatacenter dc : dcList) {
////			CloudSim.send(fed.getId(), dc.getId(), 0, 9000, dc);
////			CloudSim.send(fed.getId(), dc.getId(), 7, 9000, dc);
////		}
//		/*
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ START SIMULATIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		printInitialState();
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start simulation Metascheduler ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		*/
//		/*
//		List<MSPolicy> constraint = new ArrayList<>();
//		constraint.add(MakePolicy.ramConstrain(1));
////		constraint.add(MakePolicy.locationConstrain(1));
//		BestSolution sol = Metascheduler.getMapping(applications.get(0),constraint, dcList);
//		*/
//	/*	
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start simulation CloudSim ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		CloudSim.terminateSimulation(1000);
//		CloudSim.startSimulation();
//
//		CloudSim.stopSimulation();
//		fed.deleteHostFromDatacenter();
//		List<Cloudlet> newList = fed.getReceivedCloudlet();
//		MakeTestUtils.printCloudletList(newList);
//		
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		printFinalState();
//		System.out.println("FederationSim Results");
//		HashMap<Integer, Integer> allocatedToDatacenter = fed.getVmToDatacenter();
//		Iterator<Integer> keys = allocatedToDatacenter.keySet().iterator();
//		while (keys.hasNext()) {
//			Integer i = keys.next();
//			System.out.println("VM #" + i + " Allocated in datacenter #"
//					+ allocatedToDatacenter.get(i));
//		}
//		*/
//		/*
//		System.out.println("Metascheduler Results");
//		HashMap<Integer, Integer> mappString = sol.getBest();
//		System.out.println("Fitness: " + sol.getFit());
//		if (sol.getFit() < 1)
//			System.out.println("SOLUZIONE NON VALIDA");
//		for (int i = 0; i < mappString.size(); i++) {
//			System.out.println("Node: " + i + " -> " + " Prov: "
//					+ dcList.get(mappString.get(i)).getId() + "     " + mappString.get(i));
//		}
//		*/
//		
//
//	}
//	
//	
//	
//	
//// ###########################################################################################################
//	
//	private static DatacenterBroker createBroker() {
//		DatacenterBroker broker = null;
//		try {
//			broker = new DatacenterBroker("Broker");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		return broker;
//	}
//	
//	private static InternetEstimator setInternetEstimator(List<FederationDatacenter> dcList, int numOfDatacenters){
//		InternetEstimator inetEst = new InternetEstimator(numOfDatacenters);
//		for (FederationDatacenter top: dcList)
//		{
//			for (FederationDatacenter bot: dcList)
//			{
//				inetEst.defineDirectLink(top, bot, 1024*1024*10, 100, SecuritySupport.ADVANCED);
//			}
//		}
//		return inetEst;
//	}
//	
//	private static void printInitialState(){
//		System.out.println("##################### Federation Datacenter Initial State #############################");
//		for(FederationDatacenter dc : dcList){
//			System.out.println(MakeTestUtils.datacenterStateToString(dc));
//		}
//		System.out.println("#######################################################################################");
//	}
//	private static void printFinalState(){
//		System.out.println("##################### Federation Datacenter Final State #############################");
//		for(FederationDatacenter dc : dcList){
//			System.out.println(MakeTestUtils.datacenterStateToString(dc));
//		}
//		System.out.println("#######################################################################################");
//	}
//	
////	paramDatacenter = DatacenterUtility.getDatacenterParam();
////	Iterator< String> iter = paramDatacenter.keySet().iterator();	
////	while(iter.hasNext()){
////		String key = iter.next();
////		prop.setProperty(key.toLowerCase(), (String) paramDatacenter.get(key));
////	}
////	prop.store(new FileOutputStream("datacenter.config"), null);
//	
////	HashMap<String, Object> hostconfig = DatacenterUtility.setHashHostParam(0, "1", "1", "1", "1");
////	Iterator< String> iter = hostconfig.keySet().iterator();	
////	while(iter.hasNext()){
////		String key = iter.next();
////		prop.setProperty(key.toLowerCase(), "");
////	}
////	prop.store(new FileOutputStream("datacenter.config"), null);
//
//}
