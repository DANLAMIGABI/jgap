package it.cnr.isti.federation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cloudbus.cloudsim.Vm;

public class AllocationSession 
{
	public enum MappingStatus
	{
		SUCCESS,
		FAILED,
		PENDING;
	}
	
	private Map<Vm, Integer> vmToDc;
	private Map<Vm, MappingStatus> vmToStatus;
	
	public AllocationSession(Map<Vm, Integer> vmToDc)
	{
		this.vmToDc = vmToDc;
		
		vmToStatus = new HashMap<Vm, AllocationSession.MappingStatus>();
		for (Vm vm: vmToDc.keySet())
		{
			vmToStatus.put(vm, MappingStatus.PENDING);
		}
	}
	
	public Set<Vm> getVms()
	{
		return vmToDc.keySet();
	}
	
	public Integer getDc(Vm vm)
	{
		return vmToDc.get(vm);
	}

	public boolean isCompleted()
	{
		for (Vm vm: vmToDc.keySet())
		{
			if (vmToStatus.get(vm) == MappingStatus.PENDING)
				return false;
		}
		return true;
	}
}
