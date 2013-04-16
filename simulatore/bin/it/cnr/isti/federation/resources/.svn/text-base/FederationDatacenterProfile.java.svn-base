package it.cnr.isti.federation.resources;

import java.util.HashMap;
import java.util.Map;

public class FederationDatacenterProfile 
{
	
	public enum DatacenterParams
	{
		ARCHITECTURE("x86"),
		OS("Linux"),
		VMM("Xen"),
		TIME_ZONE("1"), // CET (?)
		COST_PER_SEC("3.0"),
		COST_PER_MEM("0.05"),
		COST_PER_STORAGE("0.001"),
		COST_PER_BW("0.0"),
		VM_ALLOCATION_POLICY("org.cloudbus.cloudsim.VmAllocationPolicySimple"),
		SCHEDULING_INTERNAL("0");
		
		private String def;
		
		private DatacenterParams(String def)
		{
             this.def = def;
		}
	}
	
	private  Map<DatacenterParams, String> data;
	
	private FederationDatacenterProfile()
	{
		data = new HashMap<DatacenterParams, String>();
		
		for (DatacenterParams p : DatacenterParams.values())
		{
			data.put(p, p.def);
		}
	}

	public static FederationDatacenterProfile getDefault()
	{
		return new FederationDatacenterProfile();
	}
	
	public String get(DatacenterParams par)
	{
		return data.get(par);
	}
	
	public void set(DatacenterParams par, String value)
	{
		data.put(par, value);
	}
}
