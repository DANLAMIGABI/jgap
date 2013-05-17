package it.cnr.it.giuseppe;

import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

public class SimulationVmUtility {
	
	public static Vm getDefaultVm(int brokerId){
		// VM description
		int vmid = 0;
        int mips = 1000;
        long size = 10000; // image size (MB)
        int ram = 512; // vm memory (MB)
        long bw = 1000;
        int pesNumber = 1; // number of cpus
        String vmm = "Xen"; // VMM name
        
     // create VM
        Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
        return vm;
	}

}
