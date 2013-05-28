package it.cnr.isti.federation.application;

import it.cnr.isti.federation.UtilityPrint;
import it.cnr.isti.networking.SecuritySupport;

import org.cloudbus.cloudsim.Cloudlet;
import org.jgrapht.graph.DefaultEdge;

public class ApplicationEdge extends DefaultEdge
{
	private static final long serialVersionUID = 1423234l;
	
	/* requirements */
	private double bandwidth;
	private SecuritySupport security;
	private double latency;
	
	public ApplicationEdge(double bandwidth, SecuritySupport security, double latency)
	{
		this.bandwidth = bandwidth;
		this.security = security;
		this.latency = latency;
	}
	
	public double getBandwidth()
	{
		return this.bandwidth;
	}

	public SecuritySupport getSecurity()
	{
		return this.security;
	}

	public double getLatency()
	{
		return this.latency;
	}
	
	
}
