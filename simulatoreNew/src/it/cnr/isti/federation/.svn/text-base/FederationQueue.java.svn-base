package it.cnr.isti.federation;

import it.cnr.isti.federation.application.Application;

import java.util.LinkedList;

import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;

public class FederationQueue extends SimEntity
{
	private Federation federation;
	private Object[] applicationsAndTimestamps;
	private LinkedList<Application> applications = new LinkedList<Application>();
	
	public FederationQueue(Federation federation, Object[] applicationsAndTimestamps)
	{
		super("Federation_Queue");
		
		this.federation = federation;
		//this.applications = applications;
		
		this.applicationsAndTimestamps = applicationsAndTimestamps;
		
		// schedule the events
		this.scheduleEvents();
	}
	
	
	private void scheduleEvents()
	{
		
		long[] longs = (long[]) applicationsAndTimestamps[1];
		Application[] applications = (Application[]) applicationsAndTimestamps[0];
		
		// schedule an event for each application
		for (int i=0; i<applications.length;i++)
		{
			CloudSim.send(this.getId(), this.getId(), longs[i], FederationTags.APPLICATION_IN_QUEUE, applications[i]);
		}
	}


	@Override
	public void processEvent(SimEvent ev)
	{
		switch (ev.getTag())
		{
		case FederationTags.APPLICATION_IN_QUEUE:
			Application app = (Application) ev.getData();
			applications.add(app);
			CloudSim.send(this.getId(), federation.getId(), 0, FederationTags.APPLICATION_IN_QUEUE, applications);
			break;
		}
	}


	@Override
	public void shutdownEntity() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startEntity() 
	{
		Log.printLine("FederationQueue is starting...");
	}
	
	
	
}
