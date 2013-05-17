package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.mapping.Constant;
import it.cnr.isti.federation.resources.VmProvider;
import it.cnr.isti.test.ThreeTierBusinessApplication;
import it.cnr.it.giuseppe.SimulationCloudletUtility;
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
        private static DatacenterBroker broker;

        /** The vmlist. */
        private static List<Vm> vmList;

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
                    
                    try{
                        
                		dcList = makeDatacenter();

                		DatacenterUtility.printFederationDataCenter(dcList);
                        // Third step: Create Broker
                        broker = createBroker();
                        int brokerId = broker.getId();
                
                      //Creazione applicazione 
                        VmProvider.userId = brokerId;
                        Application app = ApplicationUtility.getApplication(brokerId,1);
                        List<Application> appList = new ArrayList<Application>();
                        appList.add(app);
                      //Conversione Applicazione in GAapplication
                        ICApplication testApplication = ApplicationUtility.applicationToCApplication(app,"italia", "1000.02");
                        System.out.println(ApplicationUtility.toStringCApplication(testApplication));
                        
                        //submit application to cloudsim
                        submitApplication(app);
                        
                        //submit events cloudsim
                        CloudSim.terminateSimulation(100);
                        for(int i=0; i< dcList.size(); i++){
                        	CloudSim.send(brokerId, dcList.get(i).getId(), 3, 10, dcList.get(i) );
                        }
                        // Starts the simulation
                        CloudSim.startSimulation();

                        CloudSim.stopSimulation();

                        //Final step: Print results when simulation is over
                        List<Cloudlet> newList = broker.getCloudletReceivedList();
                        SimulationUtility.printCloudletList(newList);
                        
                        Log.printLine("CloudSimExample1 finished!");
                        } catch (Exception e) {
                        	e.printStackTrace();
                        	Log.printLine("Unwanted errors happen");
                        }
                    
                    
                    
                    
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
                datacenter.add(DatacenterUtility.getDatacenter(paramDatacenter, 5, 20));
//                datacenter.add(DatacenterUtility.getDatacenter(paramDatacenter, 2, 4));
//                datacenter.add(DatacenterUtility.getDatacenter(paramDatacenter, 2, 10));
            }
            return datacenter;
        }
        
        private static void submitApplication(Application application){
        	// Vm List
            vmList = new ArrayList<>();
            for(int i=0; i<application.getAllVms().size(); i++){
            	vmList.add(application.getAllVms().get(i));
            }
            broker.submitVmList(vmList);
            // add the cloudlet to the list
            cloudletList = new ArrayList<>();
            for( int i=0; i< application.getAllCloudlets().size(); i++){
            	cloudletList.add(application.getAllCloudlets().get(i));
            }
            
            // submit cloudlet list to the broker
            broker.submitCloudletList(cloudletList);
        	
        }
        
        
        
        private static void printAppInfo(Application app, int brokerId){
        	System.out.println("INFO:");
            System.out.println("  Vm size: " + app.getAllVms().size());
            System.out.println("     VmUser ID: " + app.getAllVms().get(0).getUserId());
            System.out.println("  Cloudlet size: " + app.getAllCloudlets().size());
            System.out.println("  uID: " + app.getAllVms().get(0).getUserId() + " brokerId: " + brokerId);
            System.out.println("  Cloudlet UID: " + app.getAllCloudlets().get(0).getUserId());
        }
}
