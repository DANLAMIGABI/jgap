package it.cnr.isti.federation.resources;

import it.cnr.isti.federation.resources.HostProfile.HostParams;

import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;

public class HostProvider 
{
	private static int ID_COUNTER = 0;
	
	private static HostDynamicWorkload createHost(HostProfile profile, List<Pe> pes)
	{
		RamProvisioner ramP = null;
		BwProvisioner bwP = null;
		VmScheduler vmSched = null;
		
		try
		{
			int ram = Integer.parseInt(profile.get(HostParams.RAM_AMOUNT_MB));
			ramP = (RamProvisioner)Class.forName(profile.get(HostParams.RAM_PROVISIONER))
					.getDeclaredConstructor(int.class).newInstance(ram);
			
			long bw = Long.parseLong(profile.get(HostParams.BW_AMOUNT));
			bwP = (BwProvisioner)Class.forName(profile.get(HostParams.BW_PROVISIONER))
					.getDeclaredConstructor(long.class).newInstance(bw);
			
			vmSched = (VmScheduler)Class.forName(profile.get(HostParams.VM_SCHEDULER))
					.getDeclaredConstructor(List.class).newInstance(pes);
		}
		catch (Exception e)
		{
			// TODO: log the error
			e.printStackTrace();
		}

		
		HostDynamicWorkload host = new HostDynamicWorkload(ID_COUNTER++, 
				ramP, bwP, 
				Long.parseLong(profile.get(HostParams.STORAGE_MB)),
				pes, 
				vmSched);
		
		return host;
	}
	
	public static HostDynamicWorkload getDefault(List<Pe> pes)
	{
		return createHost(HostProfile.getDefault(), pes);
	}
	
	public static HostDynamicWorkload get(HostProfile profile, List<Pe> pes)
	{
		return createHost(profile, pes);
	}
}
