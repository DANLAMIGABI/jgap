package it.cnr.isti.federation.metascheduler.test;

import it.cnr.isti.federation.Federation;
import it.cnr.isti.federation.FederationTestBroker;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.SecuritySupport;
import it.cnr.isti.test.DataSette;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.cloudbus.cloudsim.core.CloudSim;

public class MainTest {
	private static Federation clodSim_fed;
	private static FederationTestBroker metascheduler_fed;
	private static List<FederationDatacenter> cloudSim_dcList;
	private static List<Application> clouSim_applications;
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
		
/*				
		// Make Datacenter cloudSim
		cloudSim_dcList = MakeTestUtils.getDatacenterList(dc_prop);
//		for(FederationDatacenter dc : cloudSim_dcList)
//			System.out.println(MakeTestUtils.datacenterStateToString(dc));
		DataSette.net = setInternetEstimator(cloudSim_dcList, cloudSim_dcList.size());
		
	*/
		//Make Datacenter Metascheduler
		metascheduler_dcList = MakeTestUtils.getDatacenterList(dc_prop);
//		for(FederationDatacenter dc : metascheduler_dcList)
//			System.out.println(MakeTestUtils.datacenterStateToString(dc));

		DataSette.net = setInternetEstimator(metascheduler_dcList, metascheduler_dcList.size());
	/*	
		// SHUFFLE
		long seed = Long.parseLong(dc_prop.getProperty("seed"));
		Random rand = new Random(seed);
		Collections.shuffle(cloudSim_dcList, rand);
		*/
/*
		// CloudSim Simulation
		clodSim_fed = CloudSimTest.startCloudSimSimulation(cloudSim_dcList, clouSim_applications, app_prop);
//		printCloudSimResults(cloudSim_dcList, clodSim_fed);
	*/	
		//Metascheduler Simulation
		metascheduler_fed =MetaschedulerSimulationTest.startMetaschedulerSimulation(metascheduler_dcList, metascheduler_applications, app_prop);
//		printSimulationResults(metascheduler_dcList, metascheduler_fed);
		
		
	}

	
	
	
	
	
//	###############################################################################################################
	
	private static void printSimulationResults(List<FederationDatacenter> dcList, FederationTestBroker fed){
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
