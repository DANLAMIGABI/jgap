package it.cnr.isti;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.mapping.CObjectiveFitness;
import it.cnr.isti.federation.mapping.ConfigurationFitness;
import it.cnr.isti.federation.mapping.Constant;
import it.cnr.isti.federation.mapping.MakePolicy;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.HostProfile.HostParams;
import it.cnr.isti.test.DataSette;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.lists.VmList;
import org.jgap.FitnessFunction;
import org.jgap.InvalidConfigurationException;

import contrailJgap.BestSolution;
import contrailJgap.ConfigurationJGAPQos;
import contrailJgap.JGAPMapping;

import sun.util.logging.resources.logging;

import cApplicationIface.ICApplication;
import cPolicy.CPolicy;
import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProviderIface.ICProvider;

public class TestMapping {
	
	private static List<FederationDatacenter> dcList;
	private static List<ICProvider> providerList;
	
	private static HashMap<String, Object> setHashHostParam(String storage, String ram, String bw, String mips ){
		HashMap<String, Object> param = new HashMap<>();
		param.put(Constant.BW, Long.parseLong(bw));
		param.put(Constant.MIPS, Double.parseDouble(mips));
		param.put(Constant.RAM, Integer.parseInt(ram));
		param.put(Constant.STORE, Long.parseLong(storage));
		return param;
	}
	
	private static HashMap<String, Object> setHashDataCenterParam(String id,String place,String costSec, String costMem, String costStore, String costBw){
		HashMap<String, Object> param = new HashMap<>();
		param.put(Constant.COST_BW, Double.parseDouble(costBw));
		param.put(Constant.COST_MEM, Double.parseDouble(costMem));
		param.put(Constant.COST_SEC, Double.parseDouble(costSec));
		param.put(Constant.COST_STORAGE, Double.parseDouble(costStore));
		param.put(Constant.PLACE, place);
		param.put(Constant.ID, id);
		return param;
	}
	
	public static List<Host> getHostList(List<HashMap<String, Object>> hostsParam){
		List<Host> hostList = new ArrayList<>();
        for(int i=0; i<hostsParam.size(); i++){
	        hostList.add(TestJgapExampleUtility.createHost(hostsParam.get(i)));
        }
        return hostList;
	}
	
	public static List<HashMap<String, Object>> getHostParamDatacenter1(){
		List<HashMap<String, Object>> hostParamDatacenter1 = new ArrayList<>();
        hostParamDatacenter1.add(setHashHostParam("7000", "1024", "500", "2500"));
        hostParamDatacenter1.add(setHashHostParam("8000", "512", "500", "3500"));
        hostParamDatacenter1.add(setHashHostParam("148840", "204", "24", "4500"));
        return hostParamDatacenter1;
	}
	
	public static List<HashMap<String, Object>> getHostParamDatacenter2(){
		List<HashMap<String, Object>> hostParamDatacenter2 = new ArrayList<>();
												// storage,  ram,  bw,  mips
        hostParamDatacenter2.add(setHashHostParam("800", "2024", "2000", "3500"));
        hostParamDatacenter2.add(setHashHostParam("900", "3024", "3000", "4500"));
        hostParamDatacenter2.add(setHashHostParam("1000", "4024", "4000", "5500"));
        return hostParamDatacenter2;
	}
	
	
	/**
	 * @param args
	 * @throws InvalidConfigurationException
	 */
	/**
	 * @param args
	 * @throws InvalidConfigurationException
	 */
	public static void main (String[] args) throws InvalidConfigurationException
	{
		
		int cloudlet = 1;
		
		int num_user = 1;   // users
        Calendar calendar = Calendar.getInstance();
        boolean trace_flag = true;  // trace events
        
        int hostPerDatacenter = 1 * cloudlet;

        // Initialize the CloudSim library
        CloudSim.init(num_user, calendar, trace_flag);
        
/*Test provider */
        
        //Host Param per datacenter
        List<HashMap<String, Object>> hostParamDatacenter1 = getHostParamDatacenter1();
        List<HashMap<String, Object>> hostParamDatacenter2 = getHostParamDatacenter2();
        
        //host list per datacenter
        List<Host> hostListA = getHostList(hostParamDatacenter1);
        List<Host> hostListB = getHostList(hostParamDatacenter2);
        
        //Data Center param
        																// id, place, costSec,  costMem,  costStore,  costBw
        HashMap<String , Object> paramDatacenter1 = setHashDataCenterParam("101","Italia", "1", "1", "1", "1");
        HashMap<String , Object> paramDatacenter2 = setHashDataCenterParam("102","Svezia","1000.02", "1", "1", "1");
        
        // create federation datacenter
        dcList = new ArrayList<>();
        dcList.add(TestJgapExampleUtility.createDatacenter(paramDatacenter1, hostListA));
//        dcList.add(TestJgapExampleUtility.createDatacenter(paramDatacenter2, hostListB));
        
        // print datacenter
//        TestJgapExampleUtility.printFederationDataCenter(dcList);
        
        providerList = new ArrayList<>();
        providerList.add(TestJgapExampleUtility.datacenterToCProvider(paramDatacenter1, hostParamDatacenter1));
        providerList.add(TestJgapExampleUtility.datacenterToCProvider(paramDatacenter2, hostParamDatacenter1));
       // providerList.add(TestJgapExampleUtility.datacenterToCProvider(paramDatacenter2, hostParamDatacenter2));
        
        TestJgapExampleUtility.printProvider(providerList);
        
/* Test Applicatione */
        
        List<Application> applications = new ArrayList<Application>();
        applications.add(DataSette.getApplication(1));
        
        ICApplication testApplication = TestJgapExampleUtility.applicationToCApplication(applications.get(0),"svezia", "1000.02");
//        Log.printLine(TestJgapExampleUtility.toStringCApplication(testApplication));
        
        
/* Tert JGAP */
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
        
//        System.err.println("size: " + fitConf.getConstrains().size());
//        System.out.println("prov: " + fitConf.getProviders().size());
        
        ConfigurationJGAPQos conf = new ConfigurationJGAPQos();
        conf.setFitnessFunction(new CObjectiveFitness(fitConf));
        conf.setPopulationSize(Constant.POP_SIZE);
        conf.setEvolutionSize(Constant.EVOLUTION_SIZE);
        conf.setConfiguration(testApplication, providerList.size());
        
        //TEST
        conf.application = testApplication;
        conf.providers = providerList;
        
        
        
        JGAPMapping.init(conf);
        BestSolution sol = JGAPMapping.startMapping();
        /*
        Log.printLine("Soluzione");
        HashMap<Integer, Integer> map = sol.getBest();
        Iterator<Integer> iter =  map.keySet().iterator();
        while(iter.hasNext()){
        	Integer key = iter.next();
        	Log.printLine("appID: " + key + " -> provID: " + map.get(key));
        }
        	
        */
        
        
        
	}
	
}
