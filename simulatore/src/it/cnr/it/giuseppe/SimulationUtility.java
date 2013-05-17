package it.cnr.it.giuseppe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.VmSchedulerTimeSharedOverSubscription;
import org.cloudbus.cloudsim.power.PowerDatacenter;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class SimulationUtility {
	
	public static Datacenter createDatacenter(String name) {

        // Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store
        // our machine
        List<Host> hostList = new ArrayList<Host>();
        
        // 2. A Machine contains one or more PEs or CPUs/Cores.
        // In this example, it will have only one core.
        List<Pe> peList = new ArrayList<Pe>();

        int mips = 1000;

        // 3. Create PEs and add these into a list.
        peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

        // 4. Create Host with its id and list of PEs and add them to the list
        // of machines
        int hostId = 0;
        int ram = 2048; // host memory (MB)
        long storage = 1000000; // host storage
        int bw = 10000;

        hostList.add(
                new Host(
                        hostId,
                        new RamProvisionerSimple(ram),
                        new BwProvisionerSimple(bw),
                        storage,
                        peList,
                        new VmSchedulerTimeShared(peList)
                )
        ); // This is our machine

        // 5. Create a DatacenterCharacteristics object that stores the
        // properties of a data center: architecture, OS, list of
        // Machines, allocation policy: time- or space-shared, time zone
        // and its price (G$/Pe time unit).
        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";
        double time_zone = 10.0; // time zone this resource located
        double cost = 3.0; // the cost of using processing in this resource
        double costPerMem = 0.05; // the cost of using memory in this resource
        double costPerStorage = 0.001; // the cost of using storage in this
                                                                        // resource
        double costPerBw = 0.0; // the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
                                                                                                // devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                        arch, os, vmm, hostList, time_zone, cost, costPerMem,
                        costPerStorage, costPerBw);

        // 6. Finally, we need to create a PowerDatacenter object.
        PowerDatacenter datacenter = null;
        try {
                datacenter = new PowerDatacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
                e.printStackTrace();
        }

        return datacenter;
	}
	
	/**
     * Prints the Cloudlet objects.
     *
     * @param list list of Cloudlets
     */
    public static void printCloudletList(List<Cloudlet> list) {
            int size = list.size();
            Cloudlet cloudlet;

            String indent = "    ";
            Log.printLine();
            Log.printLine("========== OUTPUT ==========");
            Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
                            + "Data center ID" + indent + "VM ID" + indent + "Time" + indent
                            + "Start Time" + indent + "Finish Time");

            DecimalFormat dft = new DecimalFormat("###.##");
            for (int i = 0; i < size; i++) {
                    cloudlet = list.get(i);
                    Log.print(indent + cloudlet.getCloudletId() + indent + indent);

                    if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                            Log.print("SUCCESS");

                            Log.printLine(indent + indent + cloudlet.getResourceId()
                                            + indent + indent + indent + cloudlet.getVmId()
                                            + indent + indent
                                            + dft.format(cloudlet.getActualCPUTime()) + indent
                                            + indent + dft.format(cloudlet.getExecStartTime())
                                            + indent + indent
                                            + dft.format(cloudlet.getFinishTime()));
                    }
            }
    }
    
    
    public static List<PowerHost> createPowerHostList(int hostsNumber) {
        List<PowerHost> hostList = new ArrayList<PowerHost>();
        for (int i = 0; i < hostsNumber; i++) {
                int hostType = i % Constants.HOST_TYPES;

                List<Pe> peList = new ArrayList<Pe>();
                for (int j = 0; j < Constants.HOST_PES[hostType]; j++) {
                        peList.add(new Pe(j, new PeProvisionerSimple(Constants.HOST_MIPS[hostType])));
                }

                hostList.add(new PowerHostUtilizationHistory(
                                i,
                                new RamProvisionerSimple(Constants.HOST_RAM[hostType]),
                                new BwProvisionerSimple(Constants.HOST_BW),
                                Constants.HOST_STORAGE,
                                peList,
                                new VmSchedulerTimeSharedOverSubscription(peList),
                                Constants.HOST_POWER[hostType]));
        }
        return hostList;
}

}
