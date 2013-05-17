package it.cnr.it.giuseppe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.power.PowerDatacenterNonPowerAware;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicySimple;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;


public class TestSimulation {
	
        /** The cloudlet list. */
        private static List<Cloudlet> cloudletList;

        /** The vmlist. */
        private static List<Vm> vmlist;

        /**
         * Creates main() to run this example.
         *
         * @param args the args
         */
        @SuppressWarnings("unused")
        public static void main(String[] args) {

                Log.printLine("Starting CloudSimExample1...");
        		try {
                    // First step: Initialize the CloudSim package. It should be called
                    // before creating any entities.
                    int num_user = 1; // number of cloud users
                    Calendar calendar = Calendar.getInstance();
                    boolean trace_flag = false; // mean trace events

                    // Initialize the CloudSim library
                    CloudSim.init(num_user, calendar, trace_flag);
                    
                    SimpleExample();
                    
                    
                    
                    
        		}catch (Exception e) {
                        e.printStackTrace();
                        Log.printLine("Unwanted errors happen");
                }
                
        }
        
        // We strongly encourage users to develop their own broker policies, to
        // submit vms and cloudlets according
        // to the specific rules of the simulated scenario
        /**
         * Creates the broker.
         *
         * @return the datacenter broker
         */
        private static DatacenterBroker createBroker() {
                DatacenterBroker broker = null;
                try {
                        broker = new DatacenterBroker("Broker");
                } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                }
                return broker;
        }
        
        private static void SimpleExample(){
        	
        	try{
                    
                    List<PowerHost> powerHostList = SimulationUtility.createPowerHostList(1);
                    Datacenter datacenter = Helper.createDatacenter(
                            "Datacenter",
                            PowerDatacenterEvento.class,
                            powerHostList,
                            new PowerVmAllocationPolicySimple(powerHostList));

                    System.out.println("HOST SIZE: " + datacenter.getHostList().get(0).getPeList().get(1).getMips());
                    

                    // Second step: Create Datacenters
                    // Datacenters are the resource providers in CloudSim. We need at
                    // list one of them to run a CloudSim simulation
//                    Datacenter datacenter0 = SimulationUtility.createDatacenter("Datacenter_0");

                    // Third step: Create Broker
                    DatacenterBroker broker = createBroker();
                    int brokerId = broker.getId();
                    
                    // Fourth step: Create one virtual machine
                    vmlist = new ArrayList<Vm>();

                    // add the VM to the vmList
                    vmlist.add(SimulationVmUtility.getDefaultVm(brokerId));

                    // submit vm list to the broker
                    broker.submitVmList(vmlist);
                    

                    // Fifth step: Create one Cloudlet
                    cloudletList = new ArrayList<Cloudlet>();
                    
                    // add the cloudlet to the list
                    int pesNumber = 1;
                    cloudletList.add(SimulationCloudletUtility.getDefautlCloudlet(brokerId, pesNumber, vmlist.get(0).getId()));
                    
                    
                    // submit cloudlet list to the broker
                    broker.submitCloudletList(cloudletList);
                    CloudSim.terminateSimulation(100);
                    CloudSim.send(brokerId, datacenter.getId(), 3, 10, datacenter );
                    // Sixth step: Starts the simulation
                    CloudSim.startSimulation();

                    CloudSim.stopSimulation();

                    //Final step: Print results when simulation is over
                    List<Cloudlet> newList = broker.getCloudletReceivedList();
                    SimulationUtility.printCloudletList(newList);
                    
                    PowerHost dynamicData = (PowerHost)datacenter.getHostList().get(0);
                    System.out.println(dynamicData.getRam());

                    Log.printLine("CloudSimExample1 finished!");
            } catch (Exception e) {
                    e.printStackTrace();
                    Log.printLine("Unwanted errors happen");
            }
        		
        		
        }

        


}
