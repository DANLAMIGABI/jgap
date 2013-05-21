package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.mapping.Constant;
import it.cnr.isti.federation.metascheduler.FederationDatacenterProfileMeta.DatacenterParams;
import it.cnr.isti.federation.resources.HostProfile;
import it.cnr.isti.federation.resources.HostProfile.HostParams;
import it.cnr.isti.federation.resources.HostProviderMetaScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import msProvider.*;
import msProviderIface.*;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;


public class DatacenterUtility {
	
	private static void printHostInfo(Host host){
		Log.printLine("  hostid:           "+ host.getId());
		Log.printLine("  host ram:         " + host.getRam());
		Log.printLine("  host store:       " + host.getStorage());
		Log.printLine("  host mips:        " + host.getTotalMips());
		Log.printLine("  host pelist size: " + host.getPeList().size());
		Log.printLine();
	}
	
	private static HashMap<String, Object> setHashHostParam(int raiseFactor,String storage, String ram, String bw, String mips ){
		HashMap<String, Object> param = new HashMap<>();
		param.put(Constant.BW, (raiseFactor* Long.parseLong(bw)) * 1024*1024);
		param.put(Constant.MIPS, raiseFactor+ Double.parseDouble(mips));
		param.put(Constant.RAM, (raiseFactor* Integer.parseInt(ram))*1024);
		param.put(Constant.STORE, (raiseFactor* Long.parseLong(storage))*1024);
		return param;
	}
	
	private static List<HashMap<String, Object>> getHostConfigurations(int numHost, int raiseFactor){
		List<HashMap<String, Object>> hostParam = new ArrayList<>();
		for(int i=0; i<numHost; i++){
			hostParam.add(setHashHostParam(raiseFactor, "870", "7", "10", "1000"));
		}
        return hostParam;
	}
	
	private static List<PowerHost> getHostList(List<HashMap<String, Object>> hostDatacenter){
		List<PowerHost> hostList = new ArrayList<>();
        for(int i=0; i<hostDatacenter.size(); i++){
	        hostList.add(createHost(hostDatacenter.get(i)));
        }
        return hostList;
	}
	
	private static PowerHostUtilizationHistory createHost(HashMap<String, Object> param){
		
		List<Pe> peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple((Double)param.get(Constant.MIPS))));
		HostProfile prof = HostProfile.getDefault();
		prof.set(HostParams.STORAGE_MB, (Long)param.get(Constant.STORE)+"");
		prof.set(HostParams.RAM_AMOUNT_MB,(Integer)param.get(Constant.RAM)+"");
		prof.set(HostParams.BW_AMOUNT, (Long)param.get(Constant.BW)+"");

		return  HostProviderMetaScheduler.get(prof, peList);
	}
	
	private static HashMap<String, Object> aggregateHostInfo(List<HashMap<String, Object>> hostListParam){
		System.out.println("### AGGREGATE INFO: DATACENTER_UTILITY");
		HashMap<String, Object> map = new HashMap<>();
		long storage =0;
		int ram =0;
		long bw =0;
		double mips =0;
		for(int i=0; i<hostListParam.size(); i++){
			HashMap<String, Object> hostParam = hostListParam.get(i);
			storage += (Long)hostParam.get(Constant.STORE);
			ram += (Integer)hostParam.get(Constant.RAM);
			bw += (Long)hostParam.get(Constant.BW);
			mips += (Double)hostParam.get(Constant.MIPS);
		}
		map.put(Constant.STORE, storage);
		map.put(Constant.MIPS, mips);
		map.put(Constant.RAM, ram);
		map.put(Constant.BW, bw);
		
		return map;
	}
	
	private static MSProvider datacenterToMSProvider(HashMap<String, Object> datacenterParam, List<HashMap<String, Object>> hostListParam){
		MSProvider provider = new MSProvider();
		HashMap<String, Object> providerCharacteristic = new HashMap<>();
		HashMap<String, Object> networkCharacteristic = new HashMap<>();
		HashMap<String, Object> computingCharacteristic = new HashMap<>();
		HashMap<String, Object> storageCharacteristic = new HashMap<>();
		
		//aggregazione della lista degli host
		HashMap<String, Object> aggregateHost = aggregateHostInfo(hostListParam);
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
	
	private static String hashToString(HashMap<String, Object> map, String indent){
		String ret = "";
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String next  = keys.next();
			Object value = map.get(next);
			
			if( value instanceof Integer )
				ret += indent + next + ":  " + (Integer) value + "\n";
			else if( value instanceof Double)
				ret += indent + next + ":  " + (Double) value + "\n";
			else if (value instanceof Long)
				ret += indent + next + ":  " + (Long) value + "\n";
			else if(next instanceof String)
				ret += indent + next + ":  " + (String) value + "\n";
		}
		return ret;
	}
	public static void printFederationDataCenter(List<FederationPowerDatacenter> fdata){
		for(int j=0; j<fdata.size(); j++){
			Log.printLine("id: " + fdata.get(j).getId()+ " | name: "+fdata.get(j).getName());
		
			List<Host> list = fdata.get(j).getHostList();
			for(int i=0; i<list.size(); i++){
				printHostInfo(list.get(i));
			}
		}
	}
	
	public static void printProviderList(List<IMSProvider> list){
		String ret = "";
		for(int i=0; i<list.size(); i++){
			ret += hashToString(list.get(i).getCharacteristic(), "");
			ret += hashToString(list.get(i).getComputing().getCharacteristic(), "   ");
			ret += hashToString(list.get(i).getNetwork().getCharacteristic(), "   ");
			ret += hashToString(list.get(i).getStorage().getCharacteristic(), "   ");
		}
		Log.printLine(ret);
	}
	
	/*
	 * Restituisce i parametri di default per il datacenter, tutti i costi sono impostati ad 1.0
	 * bisonga inizializzare il place e l'id
	 */
	public static HashMap<String, Object> getDatacenterParam(){
		HashMap<String, Object> param = new HashMap<>();
		param.put(Constant.COST_BW, 1.0);
		param.put(Constant.COST_MEM, 1.0);
		param.put(Constant.COST_SEC, 1.0);
		param.put(Constant.COST_STORAGE, 1.0);
		param.put(Constant.PLACE, null);
		param.put(Constant.ID, null);
		return param;
	}
	
	public static IMSProvider getProvider(HashMap<String, Object> param,int provdierSize, int raiseFactor){
		List<HashMap<String, Object>> hostsConf = getHostConfigurations(provdierSize,raiseFactor); 
		
		return datacenterToMSProvider(param, hostsConf);
	}
	
	
	public static FederationPowerDatacenter getDatacenter(HashMap<String, Object> param, int datacenterSize, int raiseFactor){
		FederationDatacenterProfileMeta prof = FederationDatacenterProfileMeta.getDefault();
		prof.set(DatacenterParams.COST_PER_BW, (Double)param.get(Constant.COST_BW)+"");
		prof.set(DatacenterParams.COST_PER_MEM, (Double)param.get(Constant.COST_MEM)+"");
		prof.set(DatacenterParams.COST_PER_SEC, (Double)param.get(Constant.COST_SEC)+"");
		prof.set(DatacenterParams.COST_PER_STORAGE, (Double)param.get(Constant.COST_STORAGE)+"");
		
		//make datacenter
		List<HashMap<String, Object>> hostsDatacenter = getHostConfigurations(datacenterSize, raiseFactor);
		List<PowerHost> hostList = getHostList(hostsDatacenter);
		
		List<Storage> storageList = new ArrayList<Storage>();
		return FederationDatacenterProviderMeta.get(prof, hostList, storageList);
	}
	

}
