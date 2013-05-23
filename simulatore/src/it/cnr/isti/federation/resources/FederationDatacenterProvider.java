package it.cnr.isti.federation.resources;

import it.cnr.isti.federation.resources.FederationDatacenterProfile.DatacenterParams;

import java.util.List;

import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.power.PowerHost;

public class FederationDatacenterProvider 
{

	private static int DC_COUNTER = 0;
	
	private static FederationDatacenter createFederationDatacenter(FederationDatacenterProfile profile, List<HostDynamicWorkload> hosts, List<Storage> storages)
	{
		// create the datacenter characteristics
		DatacenterCharacteristics dc = new DatacenterCharacteristics(profile.get(DatacenterParams.ARCHITECTURE),
				profile.get(DatacenterParams.OS),
				profile.get(DatacenterParams.VMM),
				hosts,
				Double.parseDouble(profile.get(DatacenterParams.TIME_ZONE)),
				Double.parseDouble(profile.get(DatacenterParams.COST_PER_SEC)),
				Double.parseDouble(profile.get(DatacenterParams.COST_PER_MEM)),
				Double.parseDouble(profile.get(DatacenterParams.COST_PER_STORAGE)),
				Double.parseDouble(profile.get(DatacenterParams.COST_PER_BW)));

		// creating vm allocation policy class
		VmAllocationPolicy vmAllocationPolicy = null;
		try
		{
			Class clazz = Class.forName(profile.get(DatacenterParams.VM_ALLOCATION_POLICY));
			vmAllocationPolicy = (VmAllocationPolicy)clazz.getDeclaredConstructor(List.class).newInstance(
					hosts
					);
		}
		catch (Exception e)
		{
			// TODO: log the error
			e.printStackTrace();
		}
		
		// creating the federation datacenter
		FederationDatacenter fc = null;
		try
		{
			fc = new FederationDatacenter("datacenter_"+DC_COUNTER++, dc, vmAllocationPolicy, storages, 
					Double.parseDouble(profile.get(DatacenterParams.SCHEDULING_INTERNAL)));
		}
		catch (Exception e)
		{
			// TODO: log the error
			e.printStackTrace();
		}
		
		return fc;
		
	}


	public static FederationDatacenter getDefault(List<HostDynamicWorkload> hosts, List<Storage> storages)
	{
		return createFederationDatacenter(FederationDatacenterProfile.getDefault(), hosts, storages);
	}
	
	public static FederationDatacenter get(FederationDatacenterProfile profile, List<HostDynamicWorkload> hosts, List<Storage> storages)
	{
		return createFederationDatacenter(profile, hosts, storages);
	}
}
