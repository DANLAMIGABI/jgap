package it.cnr.isti.federation;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.networking.InternetEstimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

public class Allocation 
{
	private double startSimTime = 0d;
	private double finishSimTime = 0d;
	private long startRealTime;
	private long finishRealTime; 
	
	private Application application;
	private List<Vm> pending;
	private List<Vm> remaining;
	private List<Vm> running;
	private Map<Vm, Integer> mapping;
	private Map<Vm, List<Integer>> blacklistMap;
	
	private Map<ApplicationVertex, Integer> avToDcid;
		
	public Allocation(Application application)
	{
		this.application = application;
		pending = new ArrayList<Vm>();
		running = new ArrayList<Vm>();
		remaining = new ArrayList<Vm>();
		mapping = new HashMap<Vm, Integer>();
		blacklistMap = new HashMap<Vm, List<Integer>>();
		avToDcid = new HashMap<ApplicationVertex, Integer>();
		
		for (Vm vm: application.getAllVms())
		{
			blacklistMap.put(vm, new ArrayList<Integer>());
		}
		
		remaining.addAll(application.getAllVms());
		
		startSimTime = CloudSim.clock();
		startRealTime = System.currentTimeMillis();
	}
	
	public Application getApplication()
	{
		return this.application;
	}
	
	public void setRunning(Vm vm, Integer datacenterId)
	{
		ApplicationVertex av = application.getVertexForVm(vm);
		avToDcid.put(av, datacenterId);
		
		pending.remove(vm);
		running.add(vm);
		mapping.put(vm, datacenterId);
	}
	
	public void unsetRunning(Vm vm)
	{
		running.remove(vm);
		pending.add(vm);
		Integer datacenterId = mapping.remove(vm);
		
		List<Integer> blacklist = blacklistMap.get(vm);
		blacklist.add(datacenterId);
	}
	
	public void failedMapping(Vm vm, Integer datacenterId)
	{
		List<Integer> blacklist = blacklistMap.get(vm);
		blacklist.add(datacenterId);
		pending.remove(vm);
		remaining.add(vm);
	}
	
	public Vm getNextVm()
	{
		if (isCompleted() == false)
		{
			Vm vm = remaining.remove(0);
			pending.add(vm);
			return vm;
		}
		
		return null;
	}
	
	public boolean isCompleted()
	{
		boolean result = ((remaining.size() == 0) && (pending.size() == 0));
		
		if (result)
		{
			finishSimTime = CloudSim.clock();
			finishRealTime = System.currentTimeMillis();
		}
		
		return result;
	}
	
	public Integer getAllocatedDatacenter(Vm vm)
	{
		return mapping.get(vm);
	}

	public Integer pickDatacenter(Vm vm, List<FederationDatacenter> datacenters, InternetEstimator net)
	{
		// choose a random datacenter
		int dcid = -1;
		
		ApplicationVertex av = application.getVertexForVm(vm);
		Integer candidate = avToDcid.get(av);
		
		int index = 0;
		
		
		if (candidate != null)
		{
			if (blacklistMap.get(vm).contains(candidate) == false)
				return candidate;
			else
			{			
				index = datacenters.indexOf(CloudSim.getEntity(candidate));
			}
		}
		
	
		
		for (int i=index; i<datacenters.size(); i++)
		{
			if (blacklistMap.get(vm).contains(datacenters.get(i).getId()) == false)
			{
				if ((checkInternet(vm, net, datacenters.get(i).getId())) || true)
				{
					dcid = datacenters.get(i).getId();
					break;
				}
			}
		}
		
		return dcid;
	}

	public double getSimDuration()
	{
		return finishSimTime - startSimTime;
	}
	
	public long getRealDuration()
	{
		return finishRealTime - startRealTime;
	}

	private boolean checkInternet(Vm vm, InternetEstimator net, int dcid)
	{
		ApplicationVertex sourceVertex = application.getVertexForVm(vm);
		FederationDatacenter sourceFd = (FederationDatacenter) CloudSim.getEntity(dcid);
		for (Vm v2: running)
		{
			ApplicationVertex destinationVertex = application.getVertexForVm(v2);
			ApplicationEdge edge = application.edgeBetween(sourceVertex, destinationVertex);
			
			if (edge == null) // if no edge in the application, the cloudlet dont have to communicate
				return true;
			
			
			FederationDatacenter destinationFd = (FederationDatacenter) CloudSim.getEntity(mapping.get(v2));
			
			if (sourceFd.getId() != destinationFd.getId()) 
			{
				boolean result = net.allocateLink(sourceFd, destinationFd, edge, application);
				if (result == false)
					return result;
			}
		}
		
		return true;
	}
	
}
