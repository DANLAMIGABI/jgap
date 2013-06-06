package it.cnr.isti.federation;

import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.NetEstimator;

import java.util.List;

import org.cloudbus.cloudsim.core.CloudSim;


public class FederationProvider 
{	
	public static Federation getFederation(List<FederationDatacenter> dcs, String allocatorClazz, InternetEstimator netEstimator)
	{
		// create the monitoring
		int schedulingInterval = 1000;
		MonitoringHub monitoring = new MonitoringHub(dcs, schedulingInterval);
		CloudSim.addEntity(monitoring);
		
		// create the allocator
		AbstractAllocator allocator = null;		
		try
		{
			Class clazz = Class.forName(allocatorClazz);	
			allocator = (AbstractAllocator)clazz.getDeclaredConstructor(MonitoringHub.class, InternetEstimator.class).newInstance(
					monitoring, netEstimator);
		}
		catch (Exception e)
		{
			// TODO: log the exception
			e.printStackTrace();
		}
		
		// create the federation
		Federation federation = null;
		try
		{
			federation = new Federation(monitoring, allocator);
		}
		catch (Exception e)
		{
			// TODO: log the exception
			e.printStackTrace();
		}

		return federation;
	}
}
