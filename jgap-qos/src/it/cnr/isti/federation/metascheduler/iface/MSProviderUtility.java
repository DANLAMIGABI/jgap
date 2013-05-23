package it.cnr.isti.federation.metascheduler.iface;


import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.metascheduler.resources.MSProvider;
import it.cnr.isti.federation.metascheduler.resources.MSProviderComputing;
import it.cnr.isti.federation.metascheduler.resources.MSProviderNetwork;
import it.cnr.isti.federation.metascheduler.resources.MSProviderStorage;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.power.PowerHost;


public class MSProviderUtility {
	
	private static HashMap<String, Object> aggregateHostInfo(List<Host> hostList){
//		System.out.println("### AGGREGATE INFO: DATACENTER_UTILITY");
		HashMap<String, Object> map = new HashMap<String, Object>();
		long storage =0;
		int ram =0;
		long bw =0;
		double mips =0;
		for(int i=0; i<hostList.size(); i++){
			storage += hostList.get(i).getStorage();
			ram += hostList.get(i).getRam();
			bw += hostList.get(i).getBw();
			mips += hostList.get(i).getBw();
		}
		map.put(Constant.STORE, storage);
		map.put(Constant.MIPS, mips);
		map.put(Constant.RAM, ram);
		map.put(Constant.BW, bw);
		
		return map;
	}
	
	private static IMSProvider datacenterToMSProvider(List<Host> hostList, HashMap<String, Object> datacenterParam ){
		MSProvider provider = new MSProvider();
		HashMap<String, Object> providerCharacteristic = new HashMap<String, Object>();
		HashMap<String, Object> networkCharacteristic = new HashMap<String, Object>();
		HashMap<String, Object> computingCharacteristic = new HashMap<String, Object>();
		HashMap<String, Object> storageCharacteristic = new HashMap<String, Object>();
		
		//aggregazione della lista degli host
		HashMap<String, Object> aggregateHost = aggregateHostInfo(hostList);
//		Log.printLine("## AGGREGAZIONE ##");
//		Log.printLine(hashToString(aggregateHost, "   "));
//		Log.printLine("###################");
		
		//computing
		computingCharacteristic.put(Constant.RAM, aggregateHost.get(Constant.RAM));
		computingCharacteristic.put(Constant.MIPS, aggregateHost.get(Constant.MIPS));
		computingCharacteristic.put(Constant.COST_MEM, datacenterParam.get(Constant.COST_MEM) );
		
		//network
		networkCharacteristic.put(Constant.BW, aggregateHost.get(Constant.BW));
		networkCharacteristic.put(Constant.COST_BW, datacenterParam.get(Constant.COST_BW));
		
		//storage
		storageCharacteristic.put(Constant.STORE, aggregateHost.get(Constant.STORE));
		storageCharacteristic.put(Constant.COST_STORAGE, datacenterParam.get(Constant.COST_STORAGE));
		
		//provider
		providerCharacteristic.put(Constant.ID, datacenterParam.get(Constant.ID));
		providerCharacteristic.put(Constant.COST_SEC, datacenterParam.get(Constant.COST_SEC));
		providerCharacteristic.put(Constant.PLACE, datacenterParam.get(Constant.PLACE));
		
		provider.setID(Integer.parseInt((String)datacenterParam.get(Constant.ID)));
		provider.setCharacteristic(providerCharacteristic);
		provider.setComputing(new MSProviderComputing());
		provider.setNetwork(new MSProviderNetwork());
		provider.setStorage(new MSProviderStorage());
		provider.getComputing().setCharacteristic(computingCharacteristic);
		provider.getStorage().setCharacteristic(storageCharacteristic);
		provider.getNetwork().setCharacteristic(networkCharacteristic);
		/* to do 
		 * place ID
		 */
		return provider;
	}
	
	public static List<IMSProvider> getMSProvdierList(List<List<Host>> hostList, List<HashMap<String, Object>> characteristicList){
		List<IMSProvider> providerList = new ArrayList<IMSProvider>();
		for(int i=0; i<hostList.size(); i++){
			providerList.add(datacenterToMSProvider(hostList.get(i), characteristicList.get(i)));
		}
		
		return providerList;
	}
	

}
