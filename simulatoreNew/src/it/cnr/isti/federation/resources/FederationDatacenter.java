package it.cnr.isti.federation.resources;

import it.cnr.isti.federation.Allocation;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.metascheduler.test.DatacenterCharacteristicsMS;

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

public class FederationDatacenter extends  Datacenter//PowerDatacenterNonPowerAware
{

	public FederationDatacenter(String name, DatacenterCharacteristics characteristics, 
			VmAllocationPolicy vmAllocationPolicy, List<Storage> storageList, double schedulingInterval)
			throws Exception 
	{
		super(name, characteristics, vmAllocationPolicy, storageList, schedulingInterval);
	}

	@Override
	public String toString() {
		return "FederationDatacenter ["+this.getName()+"]";
	}
	
	
	@Override
	protected void processOtherEvent(SimEvent ev){
		
		String str ="";
		List<HostDynamicWorkload> hlist = getHostList();
		HostDynamicWorkload host = hlist.get(0) ;
		
		str += "dc_ID :          " + getId()+ "\n";
		str += "dc_Size:         " + hlist.size() +"\n";
		str += "cost_mem:        " + getMSCharacteristics().getCostPerMem()+ "\n";
		str += "cost_storage:    " + getMSCharacteristics().getCostPerStorage()+ "\n";
		str += "cost_sec:        " + getMSCharacteristics().getCostPerSecond()+ "\n";
		str += "   host_id:      " + host.getId()+ "\n";
		str += "   host_ram:     " + (host.getRam() - host.getUtilizationOfRam())+ "\n";
		str += "   host_store:   " + (host.getStorage())+ "\n";
		str += "   host_mips:    " + (host.getTotalMips() - host.getUtilizationMips())+ "\n";
		str += "   host_net:     " + host.getUtilizationOfBw()+ "\n";
		str += "   host_net_tot: " + host.getBw()+ "\n";
		System.out.println(str);
		
	
}

	public DatacenterCharacteristicsMS getMSCharacteristics(){
		return (DatacenterCharacteristicsMS)super.getCharacteristics();
	}
////    public DatacenterCharacteristics getCharacteristics(){
////		return (DatacenterCharacteristicsMS)super.getCharacteristics();
////	}
//	
//	
//	protected void processVmCreate(SimEvent ev)
//	{
//		System.out.println("askdjfkdsfdfjdsfjdsklfjds;fkkkkkkkjdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
//		
//		Application app = (Application) ev.getData();
//		int vmId = app.getAllVms().get(2).getId();
//		
//		
//		int datacenterId = getId();
//		
//		Allocation allocation = new Allocation(app);
//		int result = 1;
//		
//		if (result == CloudSimTags.TRUE) 
//		{
//			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
//					+ " has been created in Datacenter #" + datacenterId);
//		
//			allocation.setRunning(app.getAllVms().get(2), datacenterId);
//			
//			if (allocation.isCompleted())
//			{
//				startCloudlets(allocation);
//			}
//	//		else
//	//		{
//	//			continueAllocation(allocation);
//	//		}
//		} 
//		else 
//		{
//			Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
//					+ " creation failed in Datacenter #" + datacenterId);
//			
//			allocation.failedMapping(app.getAllVms().get(2), datacenterId);
//	//		continueAllocation(allocation);
//		}
//		
//	}
//	
//	private void startCloudlets(Allocation allocation)
//	{
//		Application app = allocation.getApplication();
//		for (Vm vm: app.getAllVms())
//		{
//			Cloudlet cloudlet = app.getVertexForVm(vm).getAssociatedcloudlet(vm);
//			cloudlet.setVmId(vm.getId());
//			cloudlet.setUserId(this.getId());		
//			sendNow(allocation.getAllocatedDatacenter(vm), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
//		}
//	}
}
