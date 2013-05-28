package it.cnr.isti.federation.resources;

import it.cnr.isti.federation.resources.HostProfile.HostParams;

import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.VmScheduler;
import org.cloudbus.cloudsim.VmSchedulerTimeSharedOverSubscription;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G4Xeon3040;
import org.cloudbus.cloudsim.provisioners.BwProvisioner;
import org.cloudbus.cloudsim.provisioners.RamProvisioner;

public class HostProvider 
{
	private static int ID_COUNTER = 0;
	
	private static PowerHost createHost(HostProfile profile, List<Pe> pes)
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
			//giuseppe
//			vmSched = (VmScheduler)Class.forName(profile.get(HostParams.VM_SCHEDULER))
//					.getDeclaredConstructor(List.class).newInstance(pes);
			vmSched = new VmSchedulerTimeSharedOverSubscription(pes);
		}
		catch (Exception e)
		{
			// TODO: log the error
			e.printStackTrace();
		}

		
//		PowerHost host = new PowerHost(ID_COUNTER++, 
//				ramP, bwP, 
//				Long.parseLong(profile.get(HostParams.STORAGE_MB)),
//				pes, 
//				vmSched);
		PowerHostUtilizationHistory host = new PowerHostUtilizationHistory(ID_COUNTER++, 
				ramP, bwP, 
				Long.parseLong(profile.get(HostParams.STORAGE_MB)),
				pes, 
				vmSched, new PowerModelSpecPowerHpProLiantMl110G4Xeon3040());
		
		return host;
	}
	
	public static PowerHost getDefault(List<Pe> pes)
	{
		return createHost(HostProfile.getDefault(), pes);
	}
	
	public static PowerHost get(HostProfile profile, List<Pe> pes)
	{
		return createHost(profile, pes);
	}
}
