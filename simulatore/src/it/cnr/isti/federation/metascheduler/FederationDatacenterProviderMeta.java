package it.cnr.isti.federation.metascheduler;


import it.cnr.isti.federation.metascheduler.FederationDatacenterProfileMeta.DatacenterParams;

import java.util.List;

import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicySimple;

public class FederationDatacenterProviderMeta 
{

	private static int DC_COUNTER = 0;
	
	private static FederationPowerDatacenter createFederationDatacenter(FederationDatacenterProfileMeta profile, List<PowerHost> hosts, List<Storage> storages)
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
		VmAllocationPolicy vmAllocationPolicy = null;//new PowerVmAllocationPolicySimple(hosts);
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
		FederationPowerDatacenter fc = null;
		try
		{
			fc = new FederationPowerDatacenter("datacenter_"+DC_COUNTER++, dc, vmAllocationPolicy, storages, 
					Double.parseDouble(profile.get(DatacenterParams.SCHEDULING_INTERNAL)));
		}
		catch (Exception e)
		{
			// TODO: log the error
			e.printStackTrace();
		}
		
		return fc;
		
	}


	public static FederationPowerDatacenter getDefault(List<PowerHost> hosts, List<Storage> storages)
	{
		return createFederationDatacenter(FederationDatacenterProfileMeta.getDefault(), hosts, storages);
	}
	
	public static FederationPowerDatacenter get(FederationDatacenterProfileMeta profile, List<PowerHost> hosts, List<Storage> storages)
	{
		return createFederationDatacenter(profile, hosts, storages);
	}
}
