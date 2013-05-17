package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.mapping.Constant;
import it.cnr.isti.test.ThreeTierBusinessApplication;
import it.cnr.it.giuseppe.SimulationUtility;
import it.cnr.it.giuseppe.SimulationVmUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHost;

import cApplicationIface.ICApplication;


public class MetaTestSimulation {
	
        /** The cloudlet list. */
        private static List<Cloudlet> cloudletList;
        private static List<FederationPowerDatacenter> dcList;

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
                    
                    testProvider();
                    
                    
                    
                    
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
        
        private static List<FederationPowerDatacenter> makeDatacenter(){
        	List<FederationPowerDatacenter> datacenter = new ArrayList<>();
        	int bumberDatacenters = 1;
    		HashMap<String , Object> paramDatacenter = DatacenterUtility.getDatacenterParam();
            for(int i=0; i<bumberDatacenters; i++){
            	paramDatacenter.put(Constant.ID, i+101+"");
                paramDatacenter.put(Constant.PLACE, "italia");
                datacenter.add(DatacenterUtility.getDatacenter(paramDatacenter, 2, 0));
            }
            return datacenter;
        }
        
        private static void testProvider(){
        	try{
               
        		dcList = makeDatacenter();

        		DatacenterUtility.printFederationDataCenter(dcList);
                // Third step: Create Broker
                DatacenterBroker broker = createBroker();
                int brokerId = broker.getId();
                
//                // Fourth step: Create one virtual machine
//                vmlist = new ArrayList<Vm>();
//
//                // add the VM to the vmList
//                vmlist.add(SimulationVmUtility.getDefaultVm(brokerId));
//
//                // submit vm list to the broker
//                broker.submitVmList(vmlist);
//                
//
//                // Fifth step: Create one Cloudlet
//                cloudletList = new ArrayList<Cloudlet>();
//                
//                // add the cloudlet to the list
//                int pesNumber = 1;
//                cloudletList.add(SimulationCloudletUtility.getDefautlCloudlet(brokerId, pesNumber, vmlist.get(0).getId()));
                        
              //Creazione applicazione 
                List<Application> applications = new ArrayList<Application>();
                applications.add(getApplication(1));
              //Conversione Applicazione in GAapplication
                ICApplication testApplication = ApplicationUtility.applicationToCApplication(applications.get(0),"italia", "1000.02");
                System.out.println(ApplicationUtility.toStringCApplication(testApplication));
                
                
                List<Vm> vmList = new ArrayList<>();
                vmList.add(applications.get(0).getAllVms().get(0));
                broker.submitVmList(vmList);
                // add the cloudlet to the list
                cloudletList = new ArrayList<>();
                cloudletList.add(applications.get(0).getAllCloudlets().get(0));
                
                System.out.println("###### ST: " + applications.get(0).getAllVms().get(0).getMips());
                
                
                // submit cloudlet list to the broker
                broker.submitCloudletList(cloudletList);
                CloudSim.terminateSimulation(100);
                CloudSim.send(brokerId, dcList.get(0).getId(), 3, 10, dcList.get(0) );
                // Sixth step: Starts the simulation
                CloudSim.startSimulation();

                CloudSim.stopSimulation();

                //Final step: Print results when simulation is over
                List<Cloudlet> newList = broker.getCloudletReceivedList();
                SimulationUtility.printCloudletList(newList);
                
                PowerHost dynamicData = (PowerHost)dcList.get(0).getHostList().get(0);
                System.out.println(dynamicData.getRam());

                Log.printLine("CloudSimExample1 finished!");
                } catch (Exception e) {
                	e.printStackTrace();
                	Log.printLine("Unwanted errors happen");
                }
        	}
        
        private static Application getApplication(int numberOfCloudlets)
    	{
    		Double number = new Double(numberOfCloudlets);
    		if (number < 3)
    			number = 3d;
    		
    		int frontend = new Double(Math.ceil(number * 20 / 100)).intValue();
    		int database = new Double(Math.ceil(number * 20 / 100)).intValue();
    		int appserver = number.intValue() - frontend - database;
    		
    		return new ThreeTierBusinessApplication(frontend, appserver, database);
    	}
}
