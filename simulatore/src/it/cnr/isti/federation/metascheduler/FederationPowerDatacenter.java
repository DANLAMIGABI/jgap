package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.Allocation;
import it.cnr.isti.federation.application.Application;

import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.power.PowerDatacenterNonPowerAware;

public class FederationPowerDatacenter extends PowerDatacenterNonPowerAware
{

	public FederationPowerDatacenter(String name, DatacenterCharacteristics characteristics, 
			VmAllocationPolicy vmAllocationPolicy, List<Storage> storageList, double schedulingInterval)
			throws Exception 
	{
		super(name, characteristics, vmAllocationPolicy, storageList, schedulingInterval);
	}
	
	@Override
	public String toString() {
		return "FederationDatacenter ["+this.getName()+"]";
	}
//	@Override
//	protected void setId(int id) {
//		 this.setId(id);
//	}
	
	protected void processStats(SimEvent ev){
		long ram=0;
		long net=0;
		long mips=0;
		long storage =0;
		List<HostDynamicWorkload> hostlist = getHostList();
		for(int i = 0; i<hostlist.size(); i++){
			ram += hostlist.get(i).getRam() - hostlist.get(i).getUtilizationOfRam();
			net += hostlist.get(i).getBw() - hostlist.get(i).getUtilizationOfBw();
			storage += hostlist.get(i).getStorage() ;
			mips += hostlist.get(i).getRam() - hostlist.get(i).getUtilizationMips();
			
		}
		System.out.println("########################## Stato Datacenter: " + getId() + " ##################");
		System.out.println("RAM: " + ram);
		System.out.println("NET: " + net);
		System.out.println("STORAGE: " + storage);
		System.out.println("MIPS: " + mips);
	}
	
	@Override
	 protected void processOtherEvent(SimEvent ev) {
		if(ev.getTag() == 1)
			processVmCreate(ev);
		else
			processStats(ev);
		
	}
	
	protected void processVmCreate(SimEvent ev)
	{
		
		
		Application app = (Application) ev.getData();
		int vmId = app.getAllVms().get(2).getId();
		
		
		int datacenterId = getId();
		
		Allocation allocation = new Allocation(app);
		int result = 1;
		
		if (result == CloudSimTags.TRUE) 
		{
			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
					+ " has been created in Datacenter #" + datacenterId);
		
			allocation.setRunning(app.getAllVms().get(2), datacenterId);
			
			if (allocation.isCompleted())
			{
				startCloudlets(allocation);
			}
//			else
//			{
//				continueAllocation(allocation);
//			}
		} 
		else 
		{
			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
					+ " creation failed in Datacenter #" + datacenterId);
			
			allocation.failedMapping(app.getAllVms().get(2), datacenterId);
//			continueAllocation(allocation);
		}
		
	}
	
	private void startCloudlets(Allocation allocation)
	{
		Application app = allocation.getApplication();
		for (Vm vm: app.getAllVms())
		{
			System.out.println("boooooooooooooooooooooooooooooooooooooooooooooooo");
			Cloudlet cloudlet = app.getVertexForVm(vm).getAssociatedcloudlet(vm);
			cloudlet.setVmId(vm.getId());
			cloudlet.setUserId(this.getId());		
			sendNow(allocation.getAllocatedDatacenter(vm), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
		}
	}
	
}
