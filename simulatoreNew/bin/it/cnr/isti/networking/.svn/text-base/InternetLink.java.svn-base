package it.cnr.isti.networking;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InternetLink
{
	private long bandwidth; // bps
	private int latency; //ms
	private SecuritySupport security;
	
	private Map<Application, List<ApplicationEdge>> mappings;
	
	public InternetLink(long bandwidth, int latency, SecuritySupport security)
	{
		this.bandwidth = bandwidth;
		this.latency = latency;
		this.security = security;
		
		mappings = new HashMap<Application, List<ApplicationEdge>>();
	}
	
	public boolean mapEdge(Application application, ApplicationEdge edge)
	{
		// Admission control
		if ((edge.getBandwidth() <= this.getBandwidth()) &&
				(edge.getLatency() >= this.getLatency()) &&
				(edge.getSecurity() == this.getSecurity()))
		{		
			List<ApplicationEdge> edges = mappings.get(application);
			if (edges == null)
				edges = new ArrayList<ApplicationEdge>();
			
			if (edges.contains(edge)) // do nothing
				return true;
			
			edges.add(edge);
			mappings.put(application, edges);
			return true;
			
			// TODO: im not doing any resource reduction here
		}
		else
		{
			return false;
		}
	}
	
	public void unmapEdge(Application application, ApplicationEdge edge)
	{
		List<ApplicationEdge> edges = mappings.get(application);
		if (edges != null)
			edges.remove(edge);
	}
	
	public List<ApplicationEdge> getEdges(Application application)
	{
		return mappings.get(application);
	}

	public long getBandwidth()
	{
		return bandwidth;
	}

	public void setBandwidth(long bandwidth)
	{
		this.bandwidth = bandwidth;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency)
	{
		this.latency = latency;
	}

	public SecuritySupport getSecurity()
	{
		return security;
	}

	public void setSecurity(SecuritySupport security)
	{
		this.security = security;
	}
	
	
	
}
