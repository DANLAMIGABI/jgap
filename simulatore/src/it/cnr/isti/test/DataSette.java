package it.cnr.isti.test;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.FederationDatacenterProvider;
import it.cnr.isti.federation.resources.HostProfile;
import it.cnr.isti.federation.resources.HostProfile.HostParams;
import it.cnr.isti.federation.resources.HostProvider;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.SecuritySupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

public class DataSette 
{
	private static InternetEstimator net;
	
	public static Application getApplication(int numberOfCloudlets)
	{
		Double number = new Double(numberOfCloudlets);
		if (number < 3)
			number = 3d;
		
		int frontend = new Double(Math.ceil(number * 20 / 100)).intValue();
		int database = new Double(Math.ceil(number * 20 / 100)).intValue();
		int appserver = number.intValue() - frontend - database;
		
		return new ThreeTierBusinessApplication(frontend, appserver, database);
	}
	
	public static List<FederationDatacenter> generateDatanceters(int numOfDatacenters, int numOfHost, int minNumOfCores, int maxNumOfCores)
	{
		Random r =  new Random(13213);
				
		int core_variance = maxNumOfCores - minNumOfCores;
		int delta_cores = core_variance > 0 ? r.nextInt(core_variance) : 0;
		
		if (numOfHost < 1)
			numOfHost = 1;
		
		List<FederationDatacenter> list = new ArrayList<FederationDatacenter>();
		
		for (int i=0; i<numOfDatacenters; i++)
		{
			// create the virtual processor (PE)
			List<Pe> peList = new ArrayList<Pe>();
			int mips = 25000;
			for (int j=0; j<minNumOfCores + delta_cores; j++)
			{
				peList.add(new Pe(j, new PeProvisionerSimple(mips)));
			}
			

			// create the hosts
			List<Host> hostList = new ArrayList<Host>();
			HostProfile prof = HostProfile.getDefault();
			prof.set(HostParams.RAM_AMOUNT_MB, 16*1024+"");

			for (int k=0; k<numOfHost; k++)
			{
				hostList.add(HostProvider.get(prof, peList));
			}
			
			// create the storage
			List<Storage> storageList = new ArrayList<Storage>(); // if empty, no SAN attached
			
			// create the datacenters
			list.add(FederationDatacenterProvider.getDefault(hostList, storageList));
		}
		
		
		net = new InternetEstimator(numOfDatacenters);
		for (FederationDatacenter top: list)
		{
			for (FederationDatacenter bot: list)
			{
				net.defineDirectLink(top, bot, 1024*1024*10, 100, SecuritySupport.ADVANCED);
			}
		}
		
		return list;
	}
	
	public static List<FederationDatacenter> generateDatanceters2(int numOfDatacenters, int numHost, int minNumOfCores, int maxNumOfCores)
	{
		Random r =  new Random(13213);
		
		int core_variance = maxNumOfCores - minNumOfCores;
		int delta_cores = core_variance > 0 ? r.nextInt(core_variance) : 0;
		
		List<FederationDatacenter> list = new ArrayList<FederationDatacenter>();
		NormalDistribution nd = new NormalDistribution(numOfDatacenters / 2d, numOfDatacenters / 4d);
		
		// System.out.println("Aa"+numHost);
		
		for (int i=0; i<numOfDatacenters; i++)
		{
			// create the virtual processor (PE)
			List<Pe> peList = new ArrayList<Pe>();
			int mips = 25000;
			for (int j=0; j<minNumOfCores + delta_cores; j++)
			{
				peList.add(new Pe(j, new PeProvisionerSimple(mips)));
			}
			

			// create the hosts
			List<Host> hostList = new ArrayList<Host>();
			HostProfile prof = HostProfile.getDefault();
			prof.set(HostParams.RAM_AMOUNT_MB, 16*1024+"");

			int num;
			if (numOfDatacenters == 1)
			{
				num = numHost;
			}
			else
			{
				Double value = new Double(nd.density(i)) * numHost;
				System.out.println("bbb " + value);
				num = value.intValue();
			}
			System.out.println("eee"+num);
			if (num < 1)
				num = 1;
			
			 
			
			for (int k=0; k<num; k++)
			{
				hostList.add(HostProvider.get(prof, peList));
			}
			
			// create the storage
			List<Storage> storageList = new ArrayList<Storage>(); // if empty, no SAN attached
			
			// create the datacenters
			list.add(FederationDatacenterProvider.getDefault(hostList, storageList));
		}
		
		
		net = new InternetEstimator(numOfDatacenters);
		for (FederationDatacenter top: list)
		{
			for (FederationDatacenter bot: list)
			{
				net.defineDirectLink(top, bot, 1024*1024*10, 100, SecuritySupport.ADVANCED);
			}
		}
		
		return list;
	}
	
	
	public static InternetEstimator getInternetEstimator()
	{
		return net;
	}
}
