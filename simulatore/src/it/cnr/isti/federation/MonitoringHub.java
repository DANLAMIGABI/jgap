package it.cnr.isti.federation;

import it.cnr.isti.federation.resources.FederationDatacenter;

import java.util.List;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;

import com.rits.cloning.Cloner;

/**
 * @author carlini
 * This class manages the monitoring abstraction for the federation.
 * The task of the monitoring hub is to get allocation and other data from the 
 * providers of the federation.This implementation works as follows.
 * Upon reception of the MONITOR_UPDATE event, this class updates its internal view
 * by cloning the datacenter state. At the same time, it exposes as public the view
 * taken at the previous step.
 */
public class MonitoringHub extends SimEntity
{
	private List<FederationDatacenter> public_view;
	private List<FederationDatacenter> internal_view;
	private List<FederationDatacenter> dcs;
	private int schedulingInterval;
	
	public MonitoringHub(List<FederationDatacenter> dcs, int schedulingInterval)
	{
		super("Monitoring_Hub");
		this.dcs = dcs;
		this.schedulingInterval = schedulingInterval;
		
		// schedule the event and prepare the views
		CloudSim.send(this.getId(), this.getId(), schedulingInterval, FederationTags.MONITOR_UPDATE, null);
		internal_view = cloneList(dcs);
		public_view = internal_view;
	}
	
	public List<FederationDatacenter> getView()
	{
		return public_view;
	}

	@Override
	public void processEvent(SimEvent event) 
	{
		if (event.getTag() == FederationTags.MONITOR_UPDATE)
		{
			// FederationLog.timeLog("Received "+ FederationEvent.MONITOR_UPDATE.tag());
			
			// update the view
			public_view = internal_view;
			internal_view = cloneList(dcs);
			
			// reschedule the next monitoring update event
			CloudSim.send(this.getId(), this.getId(), schedulingInterval, FederationTags.MONITOR_UPDATE, null);
		}
	}

	@Override
	public void shutdownEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startEntity() {
		// TODO Auto-generated method stub
		
	}
	
	private List<FederationDatacenter> cloneList(List<FederationDatacenter> list)
	{
		Cloner cloner = new Cloner();
		return cloner.deepClone(list);
	}
}
