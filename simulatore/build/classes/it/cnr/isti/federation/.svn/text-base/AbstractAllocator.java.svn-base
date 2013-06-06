package it.cnr.isti.federation;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.NetEstimator;

import java.util.HashMap;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;

public abstract class AbstractAllocator 
{
	protected HashMap<String, String> persistentStorage;
	protected MonitoringHub monitoring;
	protected InternetEstimator netEstimator;
	
	public AbstractAllocator(MonitoringHub monitoring, InternetEstimator netEstimator)
	{
		persistentStorage = new HashMap<String, String>();
		this.monitoring = monitoring;
		this.netEstimator = netEstimator;
	}
	
	public HashMap<String, String> getStorage()
	{
		return persistentStorage;
	}
	
	public MonitoringHub getMonitoring()
	{
		return monitoring;
	}
	
	public abstract MappingSolution findAllocation(Application application);
	
	protected Host getSuitableHost(FederationDatacenter dc, Vm vm)
	{
		for (Host h: dc.getHostList())
		{
			if (h.isSuitableForVm(vm))
				return h;
		}
		
		return null;
	}
}
