package it.cnr.isti.federation;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.test.DataSette;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;

public class Federation extends SimEntity
{

	private List<Integer> datacenterIds;
	private List<FederationDatacenter> datacenters;
	private Map<Integer, DatacenterCharacteristics> datacenterCharacteristicsList;
	
	private Map<Integer, Application> vmToApp; 
	private Map<Integer, Vm> idToVm;
	private Map<Application, Allocation> appToAllocation; 
	
	private Map<Integer, Integer> vmToDatacenter;
	
	private List<Cloudlet> receivedCloudlet;
	
	public Federation(String name)
	{
		super(name);
		
		datacenterIds = CloudSim.getCloudResourceList();
		datacenterCharacteristicsList = new HashMap<Integer, DatacenterCharacteristics>();
		receivedCloudlet = new ArrayList<Cloudlet>();
		vmToApp = new HashMap<Integer, Application>();
		idToVm = new HashMap<Integer, Vm>();
		appToAllocation = new HashMap<Application, Allocation>();
		datacenters = new ArrayList<FederationDatacenter>();
		vmToDatacenter = new HashMap<>();
	}
	
	public void setDatacenters(List<FederationDatacenter> dcs)
	{
		this.datacenters = dcs;
	}
	
	@Override
	public void startEntity() 
	{
		Log.printLine("Federation ("+ this.getId() +") is starting...");	
		schedule(getId(), 0, CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST);
	}
	
	@Override
	public void processEvent(SimEvent ev) 
	{
		FederationLog.timeLog("Event received: "+ev.getTag());
		
		switch (ev.getTag()) {
		// Resource characteristics request
			case CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST:
				processResourceCharacteristicsRequest(ev);
				break;
			// Resource characteristics answer
			case CloudSimTags.RESOURCE_CHARACTERISTICS:
				processResourceCharacteristics(ev);
				break;
			// application submit	
			case FederationTags.APPLICATION_IN_QUEUE:
				processApplicationSubmit(ev);
				break;
			// VM Creation answer
			case CloudSimTags.VM_CREATE_ACK:
				processVmCreate(ev);
				break;
			// A finished cloudlet returned
			case CloudSimTags.CLOUDLET_RETURN:
				processCloudletReturn(ev);
				break;
			// if the simulation finishes
			case CloudSimTags.END_OF_SIMULATION:
				shutdownEntity();
				break;
			// other unknown tags are processed by this method
			default:
				// processOtherEvent(ev);
				break;
		}
	}
	
	// Asks for monitoring to datacenters
	protected void processResourceCharacteristicsRequest(SimEvent ev)
	{
		FederationLog.timeLog("Sending monitoring hook to "+datacenterIds.size()+" datacenters");
		
		for (Integer datacenterId : datacenterIds)
		{
			sendNow(datacenterId, CloudSimTags.RESOURCE_CHARACTERISTICS, getId());
		}
	}

	// Receive monitoring from datacenters
	protected void processResourceCharacteristics(SimEvent ev)
	{
		DatacenterCharacteristics characteristics = (DatacenterCharacteristics) ev.getData();
		datacenterCharacteristicsList.put(characteristics.getId(), characteristics);
		
		FederationLog.timeLog("Received monitoring response from datacenter#"+characteristics.getId());
	}

	// Manage the application submit
	protected void processApplicationSubmit(SimEvent ev)
	{		
		Application ag = ((LinkedList<Application>) ev.getData()).pollFirst();
		FederationLog.timeLog("Received application: "+ag);
		
		Allocation allocation = appToAllocation.get(ag);
		if (allocation == null)
		{
			allocation = new Allocation(ag);
			appToAllocation.put(ag, allocation);
		}
		
		this.continueAllocation(allocation);
	}

	
	private void continueAllocation(Allocation allocation)
	{
		// take the data
		Application app = allocation.getApplication();
		Vm vm = allocation.getNextVm();
				
		int dcid = allocation.pickDatacenter(vm, datacenters, DataSette.getInternetEstimator());
		if (dcid == -1)
		{
			FederationLog.timeLog("Mapping of "+app+" failed.");
			return;
		}
		
		// update some index
		vmToApp.put(vm.getId(), app);
		idToVm.put(vm.getId(), vm);
		
		// sending it
		sendNow(dcid, CloudSimTags.VM_CREATE_ACK, vm);
		FederationLog.timeLog("Sent vm "+vm.getId()+" to datacenter "+dcid);
	}
	
	// Manage the answer to a VM creation
	protected void processVmCreate(SimEvent ev)
	{
		int[] data = (int[]) ev.getData();
		int datacenterId = data[0];
		int vmId = data[1];
		int result = data[2];

		Application app = vmToApp.get(vmId);
		Allocation allocation = appToAllocation.get(app);
		
		if (result == CloudSimTags.TRUE) 
		{
			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
					+ " has been created in Datacenter #" + datacenterId);
			vmToDatacenter.put(new Integer(vmId), new Integer(datacenterId));
			allocation.setRunning(idToVm.get(vmId), datacenterId);
			
			if (allocation.isCompleted())
			{
				startCloudlets(allocation);
			}
			else
			{
				continueAllocation(allocation);
			}
		} 
		else 
		{
			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
					+ " creation failed in Datacenter #" + datacenterId);
			
			allocation.failedMapping(idToVm.get(vmId), datacenterId);
			continueAllocation(allocation);
		}
	}

	private void startCloudlets(Allocation allocation)
	{
		Application app = allocation.getApplication();
		for (Vm vm: app.getAllVms())
		{
			Cloudlet cloudlet = app.getVertexForVm(vm).getAssociatedcloudlet(vm);
			cloudlet.setVmId(vm.getId());
			cloudlet.setUserId(this.getId());		
			sendNow(allocation.getAllocatedDatacenter(vm), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
		}
	}
	
	
	protected void processCloudletReturn(SimEvent ev) 
	{
		Cloudlet cloudlet = (Cloudlet) ev.getData();
		receivedCloudlet.add(cloudlet);
		
		FederationLog.timeLog("Cloudlet" + cloudlet.getCloudletId() +" received");
	}

	public List<Cloudlet> getReceivedCloudlet()
	{
		return this.receivedCloudlet;
	}
	
	public Collection<Allocation> getAllocations()
	{
		return appToAllocation.values();
	}
	public Map<Integer, Integer> getVmToDatacenter(){
		return vmToDatacenter;
	}
	
	@Override
	public void shutdownEntity() 
	{
		Log.printLine(getName() + " is shutting down...");	
	}
}
