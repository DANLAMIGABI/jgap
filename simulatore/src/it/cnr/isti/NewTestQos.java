package it.cnr.isti;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
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

import sun.util.logging.resources.logging;

import cApplicationIface.ICApplication;
import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProviderIface.ICProvider;

public class NewTestQos {
	
	private static List<FederationDatacenter> dcList;
	private static List<ICProvider> providerList;
	
	private static HashMap<String, String> setHashHostParam(String storage, String ram, String bw, String mips ){
		HashMap<String, String> param = new HashMap<>();
		param.put(ConstantTest.BW, bw);
		param.put(ConstantTest.MIPS, mips);
		param.put(ConstantTest.RAM, ram);
		param.put(ConstantTest.STORE, storage);
		return param;
	}
	
	private static HashMap<String, String> setHashDataCenterParam(String id,String place,String costSec, String costMem, String costStore, String costBw){
		HashMap<String, String> param = new HashMap<>();
		param.put(ConstantTest.COST_BW, costBw);
		param.put(ConstantTest.COST_MEM, costMem);
		param.put(ConstantTest.COST_SEC, costSec);
		param.put(ConstantTest.COST_STORAGE, costStore);
		param.put(ConstantTest.PLACE, place);
		param.put(ConstantTest.ID, id);
		return param;
	}
	
	public static List<Host> getHostList(List<HashMap<String, String>> hostsParam){
		List<Host> hostList = new ArrayList<>();
        for(int i=0; i<hostsParam.size(); i++){
	        hostList.add(TestJgapExampleUtility.createHost(hostsParam.get(i)));
        }
        return hostList;
	}
	
	public static List<HashMap<String, String>> getHostParamDatacenter1(){
		List<HashMap<String, String>> hostParamDatacenter1 = new ArrayList<>();
        hostParamDatacenter1.add(setHashHostParam("700", "1024", "1000", "2500"));
        hostParamDatacenter1.add(setHashHostParam("800", "2024", "2000", "3500"));
        hostParamDatacenter1.add(setHashHostParam("900", "2024", "3000", "4500"));
        return hostParamDatacenter1;
	}
	
	public static List<HashMap<String, String>> getHostParamDatacenter2(){
		List<HashMap<String, String>> hostParamDatacenter2 = new ArrayList<>();
        hostParamDatacenter2.add(setHashHostParam("800", "2024", "2000", "3500"));
        hostParamDatacenter2.add(setHashHostParam("900", "3024", "3000", "4500"));
        hostParamDatacenter2.add(setHashHostParam("1000", "4024", "4000", "5500"));
        return hostParamDatacenter2;
	}
	
	
	public static void main (String[] args)
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
        List<HashMap<String, String>> hostParamDatacenter1 = getHostParamDatacenter1();
        List<HashMap<String, String>> hostParamDatacenter2 = getHostParamDatacenter2();
        
        //host list per datacenter
        List<Host> hostListA = getHostList(hostParamDatacenter1);
        List<Host> hostListB = getHostList(hostParamDatacenter2);
        
        //Data Center param
        HashMap<String , String> paramDatacenter1 = setHashDataCenterParam("101","Italia", "12", "12", "12", "12");
        HashMap<String , String> paramDatacenter2 = setHashDataCenterParam("102","Svezia","13", "13", "13", "13");
        
        // create federation datacenter
        dcList = new ArrayList<>();
        dcList.add(TestJgapExampleUtility.createDatacenter(paramDatacenter1, hostListA));
        dcList.add(TestJgapExampleUtility.createDatacenter(paramDatacenter2, hostListB));
        
        // print datacenter
        TestJgapExampleUtility.printFederationDataCenter(dcList);
        
        providerList = new ArrayList<>();
        providerList.add(TestJgapExampleUtility.datacenterToCProvider(paramDatacenter1, hostParamDatacenter1));
        providerList.add(TestJgapExampleUtility.datacenterToCProvider(paramDatacenter2, hostParamDatacenter2));
        
        TestJgapExampleUtility.printProvider(providerList);
        
/* Test Applicatione */
        
        List<Application> applications = new ArrayList<Application>();
        applications.add(DataSette.getApplication(1));
        
        ICApplication cApp = TestJgapExampleUtility.applicationToCApplication(applications.get(0),"Pisa");
        Log.printLine(TestJgapExampleUtility.toStringCApplication(cApp));
        
        
/* Tert JGAP */
        
	}
	
}
