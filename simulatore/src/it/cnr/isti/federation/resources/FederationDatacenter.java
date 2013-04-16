package it.cnr.isti.federation.resources;

import java.util.List;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;

public class FederationDatacenter extends Datacenter
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
}
