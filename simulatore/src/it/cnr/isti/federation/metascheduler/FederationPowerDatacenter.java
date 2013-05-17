package it.cnr.isti.federation.metascheduler;

import java.util.List;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;
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
	
	@Override
	 protected void processOtherEvent(SimEvent ev) {
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
	
}
