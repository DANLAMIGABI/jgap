package it.cnr.isti.federation.metascheduler.test;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.SecuritySupport;
import it.cnr.isti.test.DataSette;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

public class CloudSimTest {
	protected static Properties dc_prop;
	protected static Properties app_prop;
	
	public static void main(String[] args) throws Exception {
		// First step: Initialize the CloudSim package. It should be called
		// before creating any entities.
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
		
//		List<FederationDatacenter> dclist = getDatacenterForCloudSim();
//		for(FederationDatacenter dc : dclist)
//			System.out.println(MakeTestUtils.datacenterStateToString(dc));
		
		
		
		//DATACENTER
		List<FederationDatacenter> dcList = getDatacenterForCloudSim();
		HashMap<Integer, FederationDatacenter> datacenterHashTable = getFederationDatacenterHashMap(dcList);
		Collections.shuffle(dcList, new Random(System.currentTimeMillis()));
		Collections.shuffle(dcList, new Random(System.currentTimeMillis()));
		PrintWriter out = new PrintWriter("/home/tirocinante/metaschedulerTesting/cloudSimTest/cloudSimTest");
		
		
		for(FederationDatacenter dc : dcList)
			out.println(MakeTestUtils.datacenterStateToString(dc));
//		out.close();
		// Make Federation
		Federation fed = new Federation("Federation");
		fed.setDatacenters(dcList);
		CloudSim.addEntity(fed);
		VmProvider.userId = fed.getId();
		
		// Make application
		List<Application> applications = new ArrayList<>();
		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
		applications.add(MakeTestUtils.getFederationApplication(fed.getId(),Integer.parseInt(cloudlets), app_prop));
		MakeTestUtils.printVmlist(applications.get(0));
		
		// create the queue
		FederationQueueProfile queueProfile = FederationQueueProfile.getDefault();
		FederationQueue queue = FederationQueueProvider.getFederationQueue(
				queueProfile, fed, applications);
		CloudSim.addEntity(queue);
		long start = System.currentTimeMillis();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start simulation CloudSim ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		CloudSim.terminateSimulation(1000);
		CloudSim.startSimulation();

		CloudSim.stopSimulation();
		System.out.println("TEMPO ESECUZIONE "+ (System.currentTimeMillis() - start) / new Double(1000));
		// fed.deleteHostFromDatacenter();
//		List<Cloudlet> newList = fed.getReceivedCloudlet();
//		MakeTestUtils.printCloudletList(newList);
//		saveAssociationMap(applications.get(0), fed, datacenterHashTable );
		String str = printCloudSimResults(datacenterHashTable, applications.get(0), fed);
		System.out.println(str);
		out.write(str);
		out.close();
		
		
	}
	//################################################################################################################
	
	protected static void saveAssociationMap(Application app,Federation fed, HashMap<Integer, FederationDatacenter> dcHash ){
		HashMap<Integer, Integer> vmIdToDatacenterId = fed.getVmToDatacenter();
		Integer idprovider;
		String str = "vmId : vmRam : vmStore : dcID : dcRamPrice : dcPlace : dcRamSize : dcStoreSize : dcSize : hId : hRam : hStore\n";
		String sep=" : ";
		List<Vm> vmlist = app.getAllVms();
		for(Vm vm : vmlist){
			idprovider = vmIdToDatacenterId.get(vm.getId());
			str += vm.getId() + sep + vm.getRam() + sep + vm.getSize() + sep + getDcInfo(dcHash.get(idprovider)); 
		}
		System.out.println(str);
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
	protected static HashMap<Integer, FederationDatacenter> getFederationDatacenterHashMap(List<FederationDatacenter> dclist){
		HashMap<Integer, FederationDatacenter> dcHash = new HashMap<>();
		for(FederationDatacenter dc : dclist){
			dcHash.put(dc.getId(), dc);
		}
		return dcHash;
	}
	
	
	
	
	protected static List<FederationDatacenter> getDatacenterForCloudSim(){
		// Make Datacenter cloudSim
		List<FederationDatacenter> dc = MakeTestUtils.getDatacenterList(dc_prop);
		// for(FederationDatacenter dc : cloudSim_dcList)
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
	
	protected static String printCloudSimResults(HashMap<Integer, FederationDatacenter> dcHash, Application app, Federation fed){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("FederationSim Results");
		double cost=0;
		List<Vm> vmlist = app.getAllVms();
		HashMap<Integer, Integer> allocatedToDatacenter = fed.getVmToDatacenter();
		Iterator<Integer> keys = allocatedToDatacenter.keySet().iterator();
		String sep =" : ";
		String str ="# VmID : vmPrice : vmRam : vmStore : \n";
		while (keys.hasNext()) {
			Integer i = keys.next();
			cost += (vmlist.get(i).getRam()/(1024*1024)) * dcHash.get(allocatedToDatacenter.get(i)).getMSCharacteristics().getCostPerMem();
			str += "VM #" + i + " Allocated in datacenter #"+ allocatedToDatacenter.get(i) + "\n";
		}
//		System.out.println("COST:\t" + cost);
		str += "Cost: " + cost +"\n";
		return str;
	}
	
	
	
	
	public static Federation startCloudSimSimulation(List<FederationDatacenter> dcList, List<Application> applications, Properties app_prop){
		
		
	return null;
		
//		// Make application
//		applications = new ArrayList<>();
//		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
//		applications.add(MakeTestUtils.getFederationApplication(fed.getId(),Integer.parseInt(cloudlets),app_prop));
//		// create the queue
//		FederationQueueProfile queueProfile = FederationQueueProfile.getDefault();
//		FederationQueue queue = FederationQueueProvider.getFederationQueue(queueProfile, fed, applications);
//		CloudSim.addEntity(queue);
//		long start = System.currentTimeMillis();
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start simulation CloudSim ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		CloudSim.terminateSimulation(1000);
//		CloudSim.startSimulation();
//		
//		CloudSim.stopSimulation();
//		System.out.println("TEMPO ESECUZIONE " + (System.currentTimeMillis()-start)/new Double(1000));
////		fed.deleteHostFromDatacenter();
//		List<Cloudlet> newList = fed.getReceivedCloudlet();
//		MakeTestUtils.printCloudletList(newList);
//		return fed;
	}
	
	
	
	
	
	

}
