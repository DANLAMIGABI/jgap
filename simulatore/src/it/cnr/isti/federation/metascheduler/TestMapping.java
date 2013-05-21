package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.mapping.CObjectiveFitness;
import it.cnr.isti.federation.mapping.ConfigurationFitness;
import it.cnr.isti.federation.mapping.Constant;
import it.cnr.isti.federation.mapping.MakePolicy;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.test.DataSette;
import it.cnr.it.giuseppe.SimulationUtility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHost;
import org.jgap.InvalidConfigurationException;

import msApplication.*;
import msApplicationIface.*;
import msProvider.*;
import msProviderIface.*;

public class TestMapping {
	
	private static List<FederationPowerDatacenter> dcList;
	private static List<IMSProvider> providerList;
	private static List<Cloudlet> cloudletList;
	
	public static void main (String[] args) throws InvalidConfigurationException
	{
		
		int num_user = 1;   // users
        Calendar calendar = Calendar.getInstance();
        boolean trace_flag = true;  // trace events
        
        // Initialize the CloudSim library
        CloudSim.init(num_user, calendar, trace_flag);
        
        int bumberDatacenters = 5;
        providerList = new ArrayList<>();
        String[] places = { "Italia","Spagna","Germania","Francia"};
        												// id, place, costSec,  costMem,  costStore,  costBw
        HashMap<String , Object> paramDatacenter = DatacenterUtility.getDatacenterParam();
        for(int i = 0; i<bumberDatacenters; i++){
        	paramDatacenter.put(Constant.ID, i+101+"");
            paramDatacenter.put(Constant.PLACE, places[i%places.length]);
            providerList.add (DatacenterUtility.getProvider(paramDatacenter, 1, i*10000));
        }
        DatacenterUtility.printProviderList(providerList);
        
        dcList = new ArrayList<>();
        for(int i=0; i<bumberDatacenters; i++){
        	paramDatacenter.put(Constant.ID, i+101+"");
            paramDatacenter.put(Constant.PLACE, places[0]);
            dcList.add(DatacenterUtility.getDatacenter(paramDatacenter, 1, i*1));
        }
//        DatacenterUtility.printFederationDataCenter(dcList);
        
/* Test Applicatione */
        
        //Creazione applicazione 
        List<Application> applications = new ArrayList<Application>();
        applications.add(DataSette.getApplication(1));
//        System.out.println(applications.get(0).getAllCloudlets().get(0).get);
        
        //Conversione Applicazione in GAapplication
        IMSApplication testApplication = ApplicationUtility.applicationToMSApplication(applications.get(0),"italia", "1000.02");
        System.out.println(ApplicationUtility.toStringMSApplication(testApplication));
        
        
        // TEST MAPPING 
        DatacenterBroker broker = createBroker();
        int brokerId = broker.getId();
        
        List<Vm> vmList = new ArrayList<>();
        vmList.add(applications.get(0).getAllVms().get(0));
        broker.submitVmList(vmList);
       
//      Fifth step: Create one Cloudlet
        cloudletList = new ArrayList<Cloudlet>();
        cloudletList.add(applications.get(0).getAllCloudlets().get(0));
        
//      submit cloudlet list to the broker
        broker.submitCloudletList(cloudletList);

        CloudSim.startSimulation();
        
        CloudSim.stopSimulation();
        
        
      //Final step: Print results when simulation is over
        List<Cloudlet> newList = broker.getCloudletReceivedList();
        SimulationUtility.printCloudletList(newList);
        
//        PowerHost dynamicData = (PowerHost)dcList.get(0).getHostList().get(0);
//        System.out.println(dynamicData.getRam());
        
       
  
       // DatacenterUtility.printFederationDataCenter(dcList);
        
        
/*        
        //configurazione parametri algoritmo
        ConfigurationFitness fitConf = new ConfigurationFitness();
        fitConf.addAggregationParams(Constant.RAM);
        fitConf.addAggregationParams(Constant.BW);
        fitConf.addAggregationParams(Constant.STORE);
        fitConf.setApplication(testApplication);
        fitConf.setProviders(providerList);
        List<CPolicy> test = new ArrayList<>();
        test.add(MakePolicy.makeCostPolicy(1));
        test.add(MakePolicy.makePlacePolicy(2));
        fitConf.setConstrains(test);
        
        ConfigurationJGAPQos conf = new ConfigurationJGAPQos();
        conf.setFitnessFunction(new CObjectiveFitness(fitConf));
        conf.setPopulationSize(Constant.POP_SIZE);
        conf.setEvolutionSize(Constant.EVOLUTION_SIZE);
        conf.setConfiguration(testApplication, providerList.size());
        
        conf.application = testApplication;
        conf.providers = providerList;
        
        //Avvio algoritmo
        JGAPMapping.init(conf);
        BestSolution sol = JGAPMapping.startMapping();
        
        MappingOverSim  testMapp = new MappingOverSim("TestMapping"); 
        CloudSim.addEntity(testMapp);
        Iterator<Integer> iterator = sol.getBest().keySet().iterator();
        while(iterator.hasNext()){
        	Integer key = iterator.next();
        	testMapp.applyMapping(dcList.get(0), applications.get(0).getAllVms().get(0));
        }
        
        dcList.get(0).getHostList().get(0).getRam();
        
        //CloudSim.startSimulation();
       
               
        Log.printLine("Soluzione");
        Log.printLine("FIT: " + sol.getFit());
        HashMap<Integer, Integer> map = sol.getBest();
        Iterator<Integer> iter =  map.keySet().iterator();
        while(iter.hasNext()){
        	Integer key = iter.next();
        	Log.printLine("appID: " + key + " -> provID: " + map.get(key));
        }
	*/
	}
	
	
		
		
		
		
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
	
	private static void printCloudletList(List<Cloudlet> list) {
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

	

}
