package it.cnr.isti;

import it.cnr.isti.federation.mapping.ConstantMapping;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.FederationDatacenterProfile;
import it.cnr.isti.federation.resources.FederationDatacenterProvider;
import it.cnr.isti.federation.resources.HostProviderMetaScheduler;
import it.cnr.isti.federation.resources.HostProfile;
import it.cnr.isti.federation.resources.HostProvider;
import it.cnr.isti.federation.resources.FederationDatacenterProfile.DatacenterParams;
import it.cnr.isti.federation.resources.HostProfile.HostParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.lists.HostList;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProvider.CProviderNetwork;
import cProvider.CProviderStorage;
import cProviderIface.ICProvider;

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
		param.put(ConstantMapping.BW, raiseFactor+ Long.parseLong(bw));
		param.put(ConstantMapping.MIPS, raiseFactor+ Double.parseDouble(mips));
		param.put(ConstantMapping.RAM, raiseFactor+ Integer.parseInt(ram));
		param.put(ConstantMapping.STORE, raiseFactor+ Long.parseLong(storage));
		return param;
	}
	
	private static List<HashMap<String, Object>> getHostConfigurations(int numHost, int raiseFactor){
		List<HashMap<String, Object>> hostParam = new ArrayList<>();
		for(int i=0; i<numHost; i++){
			hostParam.add(setHashHostParam(raiseFactor, "163840", "1740", "1048576", "7000"));
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
	
	private static PowerHost createHost(HashMap<String, Object> param){
		
		List<Pe> peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple((Double)param.get(ConstantMapping.MIPS))));
		HostProfile prof = HostProfile.getDefault();
		prof.set(HostParams.STORAGE_MB, (Long)param.get(ConstantMapping.STORE)+"");
		prof.set(HostParams.RAM_AMOUNT_MB,(Integer)param.get(ConstantMapping.RAM)+"");
		prof.set(HostParams.BW_AMOUNT, (Long)param.get(ConstantMapping.BW)+"");
		//DAM MODIFICARE CON HOSTDYNAMIWORKLOAD
//		return GiuseppeHostProvider.get(prof, peList);
		return HostProvider.get(prof, peList);
	}
	
	private static HashMap<String, Object> aggregateHostInfo(List<HashMap<String, Object>> hostListParam){
		HashMap<String, Object> map = new HashMap<>();
		long storage =0;
		int ram =0;
		long bw =0;
		double mips =0;
		for(int i=0; i<hostListParam.size(); i++){
			HashMap<String, Object> hostParam = hostListParam.get(i);
			storage += (Long)hostParam.get(ConstantMapping.STORE);
			ram += (Integer)hostParam.get(ConstantMapping.RAM);
			bw += (Long)hostParam.get(ConstantMapping.BW);
			mips += (Double)hostParam.get(ConstantMapping.MIPS);
		}
		map.put(ConstantMapping.STORE, storage);
		map.put(ConstantMapping.MIPS, mips);
		map.put(ConstantMapping.RAM, ram);
		map.put(ConstantMapping.BW, bw);
		return map;
	}
	
	private static CProvider datacenterToCProvider(HashMap<String, Object> datacenterParam, List<HashMap<String, Object>> hostListParam){
		CProvider provider = new CProvider();
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
		computingCharacteristic.put(ConstantMapping.RAM, aggregateHost.get(ConstantMapping.RAM));
		computingCharacteristic.put(ConstantMapping.MIPS, aggregateHost.get(ConstantMapping.MIPS));
		computingCharacteristic.put(ConstantMapping.COST_MEM, datacenterParam.get(ConstantMapping.COST_MEM) );
		
		//network
		networkCharacteristic.put(ConstantMapping.BW, aggregateHost.get(ConstantMapping.BW));
		networkCharacteristic.put(ConstantMapping.COST_BW, datacenterParam.get(ConstantMapping.COST_BW));
		
		//storage
		storageCharacteristic.put(ConstantMapping.STORE, aggregateHost.get(ConstantMapping.STORE));
		storageCharacteristic.put(ConstantMapping.COST_STORAGE, datacenterParam.get(ConstantMapping.COST_STORAGE));
		
		//provider
		providerCharacteristic.put(ConstantMapping.ID, datacenterParam.get(ConstantMapping.ID));
		providerCharacteristic.put(ConstantMapping.COST_SEC, datacenterParam.get(ConstantMapping.COST_SEC));
		providerCharacteristic.put(ConstantMapping.PLACE, datacenterParam.get(ConstantMapping.PLACE));
		
		provider.setID(Integer.parseInt((String)datacenterParam.get(ConstantMapping.ID)));
		provider.setCharacteristic(providerCharacteristic);
		provider.setComputing(new CProviderComputing());
		provider.setNetwork(new CProviderNetwork());
		provider.setStorage(new CProviderStorage());
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
	public static void printFederationDataCenter(List<FederationDatacenter> fdata){
		for(int j=0; j<fdata.size(); j++){
			Log.printLine("id: " + fdata.get(j).getId()+ " | name: "+fdata.get(j).getName());
		
			List<Host> list = fdata.get(j).getHostList();
			for(int i=0; i<list.size(); i++){
				printHostInfo(list.get(i));
			}
		}
	}
	
	public static void printProviderList(List<ICProvider> list){
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
		param.put(ConstantMapping.COST_BW, 1.0);
		param.put(ConstantMapping.COST_MEM, 1.0);
		param.put(ConstantMapping.COST_SEC, 1.0);
		param.put(ConstantMapping.COST_STORAGE, 1.0);
		param.put(ConstantMapping.PLACE, null);
		param.put(ConstantMapping.ID, null);
		return param;
	}
	
	public static ICProvider getProvider(HashMap<String, Object> param,int provdierSize, int raiseFactor){
		List<HashMap<String, Object>> hostsConf = getHostConfigurations(provdierSize,raiseFactor); 
		
		return datacenterToCProvider(param, hostsConf);
	}
	
	
	public static FederationDatacenter getDatacenter(HashMap<String, Object> param, int datacenterSize, int raiseFactor){
		FederationDatacenterProfile prof = FederationDatacenterProfile.getDefault();
		prof.set(DatacenterParams.COST_PER_BW, (Double)param.get(ConstantMapping.COST_BW)+"");
		prof.set(DatacenterParams.COST_PER_MEM, (Double)param.get(ConstantMapping.COST_MEM)+"");
		prof.set(DatacenterParams.COST_PER_SEC, (Double)param.get(ConstantMapping.COST_SEC)+"");
		prof.set(DatacenterParams.COST_PER_STORAGE, (Double)param.get(ConstantMapping.COST_STORAGE)+"");
		
		//make datacenter
		List<HashMap<String, Object>> hostsDatacenter = getHostConfigurations(datacenterSize, raiseFactor);
		List<PowerHost> hostList = getHostList(hostsDatacenter);
		
		List<Storage> storageList = new ArrayList<Storage>();
		return FederationDatacenterProvider.get(prof, hostList, storageList);
	}
	

}
