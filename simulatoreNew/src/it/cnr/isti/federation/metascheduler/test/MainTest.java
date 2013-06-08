package it.cnr.isti.federation.metascheduler.test;

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
import java.util.List;
import java.util.Properties;

import org.cloudbus.cloudsim.core.CloudSim;

public class MainTest {

	private static List<FederationDatacenter> cloudSim_dcList;
	private static List<Application> clouSim_applications;
	private static List<FederationDatacenter> metascheduler_dcList;
//	private static List<Application> metascheduler_applications;
	
	protected static Properties dc_prop;
	protected static Properties app_prop;
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
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
		
		// Datacenter Properties
		Integer dc_number = Integer.parseInt(dc_prop.getProperty(Constant.DATACENTER_NUMEBER));
		String[] dc_size = dc_prop.getProperty(Constant.DATACENTER_SIZES).toString().split(",");
		String[] dc_places = dc_prop.get(Constant.DATACENTER_PLACES).toString().split(",");
		
		// Make Datacenter
		cloudSim_dcList = new ArrayList<>();
		for (int i = 0; i < dc_number; i++)
			cloudSim_dcList.add(MakeTestUtils.getDatacenter(dc_prop, Integer.parseInt(dc_size[0]), dc_places[i% dc_places.length], 0));
		DataSette.net = setInternetEstimator(cloudSim_dcList, cloudSim_dcList.size());
		
		CloudSimTest.startCloudSimSimulation(cloudSim_dcList, clouSim_applications, app_prop);
		
	}
	
//	###############################################################################################################
	
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
