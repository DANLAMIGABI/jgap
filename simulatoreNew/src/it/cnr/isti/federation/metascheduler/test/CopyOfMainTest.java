package it.cnr.isti.federation.metascheduler.test;

import it.cnr.isti.federation.Federation;
import it.cnr.isti.federation.FederationTestBroker;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.VmProvider;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.SecuritySupport;
import it.cnr.isti.test.DataSette;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.core.CloudSim;

public class CopyOfMainTest {
	private static Federation cloudSim_fed;
	private static FederationTestBroker metascheduler_fed;
	private static List<FederationDatacenter> cloudSim_dcList;
	private static List<Application> cloudSim_applications;
	private static List<FederationDatacenter> metascheduler_dcList;
	private static List<Application> metascheduler_applications;
	
	protected static Properties dc_prop;
	protected static Properties app_prop;
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

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

		cloudSim_dcList = getDatacenterForCloudSim();
		
		for(int i=0; i<2; i++){
//			cloudSim_dcList = getDatacenterForCloudSim();
			Federation fed = new Federation("Federation");
			fed.setDatacenters(cloudSim_dcList);
			
			CloudSim.addEntity(fed);
			VmProvider.userId = fed.getId();
			cloudSim_fed = CloudSimTest.startCloudSimSimulation(cloudSim_dcList, cloudSim_applications, app_prop);
			printCloudSimResults(cloudSim_dcList, cloudSim_fed);
			CloudSim.init(num_user, calendar, trace_flag);
//			fed = cloudSim_fed;
		}
		
		
		
		
		
		
//		metascheduler_dcList = getDatacenterForMetascheduler();
		
		
		 //Test form OVF 
		/* 
		cloudSim_applications = new ArrayList<>();
		cloudSim_applications.add(ApplicationFromOVF.getApplicationFromOVF("ovf-MultipleVM-cnr-ubntServer.ovf"));
		
		System.out.println(cloudSim_applications.get(0).getAllVms().size());
		
		MakeTestUtils.printVmlist(cloudSim_applications.get(0).getAllVms());
		*/
	/*	
		// SHUFFLE
		long seed = Long.parseLong(dc_prop.getProperty("seed"));
		Random rand = new Random(seed);
		Collections.shuffle(cloudSim_dcList, rand);
		*/

		// CloudSim Simulation
		/*
		cloudSim_applications = new ArrayList<>();
		String cloudlets = app_prop.getProperty(Constant.APPLICATION_CLOUDLETS);
		cloudSim_applications.add(MakeTestUtils.getFederationApplication(federation.getId(),Integer.parseInt(cloudlets),app_prop));
		MakeTestUtils.printVmlist(cloudSim_applications.get(0).getAllVms());
		for(FederationDatacenter fed : cloudSim_dcList){
			List<Host> hlist = fed.getHostList();
			for(Host h : hlist){
				MakeTestUtils.printHostInfo(h);
			}
		}
		*/
		
		//Metascheduler Simulation
		
//			metascheduler_fed =MetaschedulerSimulationTest.startMetaschedulerSimulation(metascheduler_dcList, metascheduler_applications, app_prop);
//		printCloudSimResults(metascheduler_dcList, metascheduler_fed);
//		printFinalState(metascheduler_dcList);
//		saveState(cloudSim_dcList);
		
	}

	
//	###############################################################################################################
	
	
	
	
	public static void saveState(List<FederationDatacenter> dclist){
		try{
//			PrintWriter fout = new PrintWriter("abc.txt");
//			fout.println(125.0);
//			fout.close();
			String ret ="";
			String sep = "|";
			double dcRam = 0;
			
			for(FederationDatacenter dc : dclist){
				int dcId = dc.getId();
				int size = dc.getHostList().size();
				String place = dc.getMSCharacteristics().getPlace();
				ret += dcId + sep + "%1$,.2f"+sep+ size + sep + place + sep;
				List<HostDynamicWorkload> hlist = dc.getHostList();
				dcRam = 0;
				for(HostDynamicWorkload h : hlist){
					int id = h.getId();
					double ram = h.getRam() - h.getUtilizationOfRam();
					if(ram > dcRam)
						dcRam = ram;
					ret+= id + sep + ram + sep;
				}
				ret = String.format(ret, dcRam) + "\n";
			}
			
			System.out.println(ret);
//			fout.println(String.format(ret, dcRam));
//			fout.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
	}
	
	
	private static List<FederationDatacenter> getDatacenterForMetascheduler() throws InstantiationException, IllegalAccessException{
		// Make Datacenter Metascheduler
		List<FederationDatacenter> dc = MakeTestUtils.getDatacenterList(dc_prop);
		// for(FederationDatacenter dc : metascheduler_dcList)
		// System.out.println(MakeTestUtils.datacenterStateToString(dc));

		DataSette.net = setInternetEstimator(dc,dc.size());
		return dc;
	}
	
	private static List<FederationDatacenter> getDatacenterForCloudSim(){
		// Make Datacenter cloudSim
		List<FederationDatacenter> dc = MakeTestUtils.getDatacenterList(dc_prop);
		// for(FederationDatacenter dc : cloudSim_dcList)
		// System.out.println(MakeTestUtils.datacenterStateToString(dc));
		DataSette.net = setInternetEstimator(dc,dc.size());
		return dc;
	}
	
	private static void printCloudSimResults(List<FederationDatacenter> dcList, FederationTestBroker fed){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		printFinalState(dcList);
		System.out.println("FederationSim Results");
		HashMap<Integer, Integer> allocatedToDatacenter = fed.getVmToDatacenter();
		Iterator<Integer> keys = allocatedToDatacenter.keySet().iterator();
		while (keys.hasNext()) {
			Integer i = keys.next();
			System.out.println("VM #" + i + " Allocated in datacenter #"+ allocatedToDatacenter.get(i));
		}
	}
	
	
	private static void printCloudSimResults(List<FederationDatacenter> dcList, Federation fed){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		printFinalState(dcList);
		System.out.println("FederationSim Results");
		HashMap<Integer, Integer> allocatedToDatacenter = fed.getVmToDatacenter();
		Iterator<Integer> keys = allocatedToDatacenter.keySet().iterator();
		while (keys.hasNext()) {
			Integer i = keys.next();
			System.out.println("VM #" + i + " Allocated in datacenter #"+ allocatedToDatacenter.get(i));
		}
	}
	
	
	private static void printInitialState(List<FederationDatacenter> dcList){
		System.out.println("##################### Federation Datacenter Initial State #############################");
		for(FederationDatacenter dc : dcList){
			System.out.println(MakeTestUtils.datacenterStateToString(dc));
		}
		System.out.println("#######################################################################################");
	}
	private static void printFinalState(List<FederationDatacenter> dcList){
		System.out.println("##################### Federation Datacenter Final State #############################");
		for(FederationDatacenter dc : dcList){
			System.out.println(MakeTestUtils.datacenterStateToString(dc));
		}
		System.out.println("#######################################################################################");
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

}
