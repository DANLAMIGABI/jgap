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
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.SecuritySupport;
import it.cnr.isti.test.DataSette;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.swing.text.StyledEditorKit.ItalicAction;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;

public class MetaschedulerSimulationTest {
	protected static Properties dc_prop;
	protected static Properties app_prop;
	
	
	public static void main(String[] args) throws Exception {
		int num_user = 1; // number of cloud users
		Calendar calendar = Calendar.getInstance();
		boolean trace_flag = false; // mean trace events

		// Initialize the CloudSim library
		CloudSim.init(num_user, calendar, trace_flag);
		
		dc_prop = new  Properties();
		dc_prop.load(new FileInputStream("datacenter.config"));
		app_prop = new Properties();
		app_prop.load(new FileInputStream("application.config"));
		
		long seed = Long.parseLong(dc_prop.getProperty("seed"));
		MakeTestUtils.rand = new Random(seed);
		FileWriter out = new FileWriter("/home/tirocinante/metascheduler.txt");
		
		
		
		// DATACENTER
		List<FederationDatacenter> dcList = getDatacenterForMetascheduler();
		
//		dcList.get(5).getMSCharacteristics().setCostPerMem(new Double(0.0001));
		HashMap<Integer, FederationDatacenter> datacenterHashTable = getFederationDatacenterHashMap(dcList);
		Collections.sort(dcList);
		String str ="";
		for(FederationDatacenter dc : dcList){
			str+= MakeTestUtils.datacenterStateToString(dc)+"\n";
		}
		out.write(str);
		out.close();
/*		
		// Make application
		List<Application> applications = new ArrayList<>();
		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
		applications.add(MakeTestUtils.getFederationApplication(0,Integer.parseInt(cloudlets), app_prop));
		
		MakeTestUtils.printVmlist(applications.get(0));
	
		MakePolicy.highCostValue = 0.9991834307905937;
		MakePolicy.highStorageValue = 65536;
		MakePolicy.highRamValue = 2048;
		
//		MakePolicy.highCostValue = 0.9677559094241207;
//		MakePolicy.highStorageValue = 32768;
//		MakePolicy.highRamValue = 2048;
		
		
		
		String log = "highCostValue: " + MakePolicy.highCostValue + "\nhighStorageValue: " + MakePolicy.highStorageValue +
				"\nhighRamValue: " + MakePolicy.highRamValue + "\n";
		
		List<MSPolicy> constraint = new ArrayList<>();
		constraint.add(MakePolicy.ramConstrain(     0.0000000005));
		constraint.add(MakePolicy.storageConstrain( 0.00000000025));
		constraint.add(MakePolicy.locationConstrain(0.00000000025));
		constraint.add(MakePolicy.costRamConstrain( 0.999999999));
		
		int iteration =1;
		log += String.format("Datacentere Numbers: %s\nDatacentere size: %s%nApplication size: %s%nPopolazione: %d%nIterazioni: %d%n" , 
				dc_prop.getProperty("datacenter_number"), dc_prop.getProperty("datacenter_size"), app_prop.getProperty("application_cloudlets"),Constant.POP_SIZE,iteration);
		for(int k=0;k<iteration; k++){
			log += "########################################### ITERAZIONE " + k+ " ################################################\n";
			System.out.println("########################################### ITERAZIONE " + k+ " ################################################\n");;
			BestSolution sol = Metascheduler.getMapping(applications.get(0),constraint, dcList);
	
			System.out.println("Metascheduler Results");
			HashMap<Integer, Integer> mappString = sol.getBest();
			
			System.out.println("Fitness: " + sol.getFit());
			if (sol.getFit() < 1){
				System.out.println("\nSOLUZIONE NON VALIDA\n");
				log += "\nSOLUZIONE NON VALIDA\n";
			}
			else{
				String str2="";
				for (int i = 0; i < mappString.size(); i++) {
					str2 +="Node: " + i + " -> " + " Prov: "
							+ dcList.get(mappString.get(i)).getId() + "     "
							+ mappString.get(i)+"\n";
				}
				System.out.println(str);
				log += str2 + "\n";
			}
			List<String> message = sol.getMessages();
			
			for( String s : message){
				log += s + "\n";
				System.out.println(s);
			}
			
	//		saveAssociationMap(applications.get(0), fed, datacenterHashTable);
	//		printCloudSimResults(dcList,fed);
			
			log += "COSTO SOLUZIONE: " +  MakeTestUtils.getSolutionCost(mappString, dcList, applications.get(0)) + "\n";
			out.write(log);
			log = "";
		}
		out.close();
		*/
	}
	
	//#############################################################################################################################
	
	protected static void printCloudSimResults(List<FederationDatacenter> dcList, FederationTestBroker fed){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~ " + fed.getVmToDatacenter().size() );
		
		System.out.println("FederationSim Results");
		HashMap<Integer, Integer> allocatedToDatacenter = fed.getVmToDatacenter();
		Iterator<Integer> keys = allocatedToDatacenter.keySet().iterator();
		while (keys.hasNext()) {
			Integer i = keys.next();
			System.out.println("VM #" + i + " Allocated in datacenter #"+ allocatedToDatacenter.get(i));
		}
	}
	
	
	
	protected static void saveAssociationMap(Application app,FederationTestBroker fed, HashMap<Integer, FederationDatacenter> dcHash ){
		System.out.println(fed.getVmToDatacenter().size());
//		HashMap<Integer, Integer> vmIdToDatacenterId = fed.getVmToDatacenter();
//		Integer idprovider;
//		String str = "vmId : vmRam : vmStore : dcID : dcRamPrice : dcPlace : dcRamSize : dcStoreSize : dcSize : hId : hRam : hStore\n";
//		String sep=" : ";
//		List<Vm> vmlist = app.getAllVms();
//		for(Vm vm : vmlist){
//			idprovider = vmIdToDatacenterId.get(vm.getId());
//			System.out.println("                                                                                                    " + vm.getId());
////			str += vm.getId() + sep + vm.getRam() + sep + vm.getSize() + sep + getDcInfo(dcHash.get(idprovider)); 
//		}
//		System.out.println(str);
	}
	
	
	protected static String getDcInfo(FederationDatacenter dc){
		String ret="";
		String sep=" : ";
		int dcId = dc.getId();
		int size = dc.getHostList().size();
		String place = dc.getMSCharacteristics().getPlace();
		double ramPrice = dc.getMSCharacteristics().getCostPerMem();
		ret += dcId + sep + ramPrice+ sep + place + sep + " %.2f "+sep+ " %d " + sep + size+sep ;

		List<HostDynamicWorkload> hlist = dc.getHostList();
		double dcRam = 0;
		long store=0;
		for(HostDynamicWorkload h : hlist){
			int id = h.getId();
			System.out.println("                                                                                                                       PORCO HOST ID " + id);
			double ram = h.getRam() - h.getUtilizationOfRam();
			long hstore = h.getStorage();
			if(ram > dcRam)
				dcRam = ram;
			if(hstore > store)
				store = hstore;
			ret+= id + sep + ram + sep + hstore + sep;
		}
		ret = String.format(ret, dcRam,store) + "\n";
		return ret;
		
	}
	
	
	
	protected static List<FederationDatacenter> getDatacenterForMetascheduler() throws InstantiationException, IllegalAccessException{
		// Make Datacenter Metascheduler
		List<FederationDatacenter> dc = MakeTestUtils.getDatacenterList(dc_prop);
		// for(FederationDatacenter dc : metascheduler_dcList)
		// System.out.println(MakeTestUtils.datacenterStateToString(dc));

		DataSette.net = setInternetEstimator(dc,dc.size());
		return dc;
	}
	protected static InternetEstimator setInternetEstimator(List<FederationDatacenter> dcList, int numOfDatacenters){
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
	protected static HashMap<Integer, FederationDatacenter> getFederationDatacenterHashMap(List<FederationDatacenter> dclist){
		HashMap<Integer, FederationDatacenter> dcHash = new HashMap<>();
		for(FederationDatacenter dc : dclist){
			dcHash.put(dc.getId(), dc);
		}
		return dcHash;
	}
	

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
//				CloudSim.send(fed.getId(), dcList.get(providerId).getId(), 100,-1, vm);
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
	
	/*
	FederationTestBroker fed = new FederationTestBroker("FederationBroker");
	fed.setDatacenters(dcList);
	CloudSim.addEntity(fed);
	VmProvider.userId = fed.getId();
	*/
	
	/*
	if (sol.getFit() >= 1) {
		List<Vm> allVm = applications.get(0).getAllVms();
		int delay = 4;
		for (Vm vm : allVm) {
			Integer providerId = mappString.get(vm.getId());
			System.err.println("SENDING: vm " +vm.getId() +" TO dc: " +dcList.get(providerId).getId() );
			CloudSim.send(fed.getId(), dcList.get(providerId).getId(), delay++,CloudSimTags.VM_CREATE, vm);
//			CloudSim.send(fed.getId(), dcList.get(providerId).getId(), 100,-1, vm);
		}
//		CloudSim.terminateSimulation(1000);
		CloudSim.startSimulation();

		CloudSim.stopSimulation();
		// fed.deleteHostFromDatacenter();
		List<Cloudlet> newList = fed.getReceivedCloudlet();
		MakeTestUtils.printCloudletList(newList);
	}
	*/
	
	

}
