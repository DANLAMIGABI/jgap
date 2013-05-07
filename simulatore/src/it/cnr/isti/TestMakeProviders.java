package it.cnr.isti;

import it.cnr.isti.federation.mapping.Constant;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.FederationDatacenterProfile;
import it.cnr.isti.federation.resources.FederationDatacenterProvider;
import it.cnr.isti.federation.resources.HostProfile;
import it.cnr.isti.federation.resources.HostProvider;
import it.cnr.isti.federation.resources.FederationDatacenterProfile.DatacenterParams;
import it.cnr.isti.federation.resources.HostProfile.HostParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.lists.HostList;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProvider.CProviderNetwork;
import cProvider.CProviderStorage;
import cProviderIface.ICProvider;

public class TestMakeProviders {
	
	private static HashMap<String, Object> setHashHostParam(int raiseFactor,String storage, String ram, String bw, String mips ){
		HashMap<String, Object> param = new HashMap<>();
		param.put(Constant.BW, raiseFactor+ Long.parseLong(bw));
		param.put(Constant.MIPS, raiseFactor+ Double.parseDouble(mips));
		param.put(Constant.RAM, raiseFactor+ Integer.parseInt(ram));
		param.put(Constant.STORE, raiseFactor+ Long.parseLong(storage));
		return param;
	}
	
	private static List<HashMap<String, Object>> getHostConfigurations(int numHost, int raiseFactor){
		List<HashMap<String, Object>> hostParam = new ArrayList<>();
		for(int i=0; i<numHost; i++){
			hostParam.add(setHashHostParam(i*raiseFactor, "7000", "1024", "500", "2500"));
		}
        return hostParam;
	}
	
	private static List<Host> getHostList(List<HashMap<String, Object>> hostDatacenter){
		List<Host> hostList = new ArrayList<>();
        for(int i=0; i<hostDatacenter.size(); i++){
	        hostList.add(createHost(hostDatacenter.get(i)));
        }
        return hostList;
	}
	
	private static Host createHost(HashMap<String, Object> param){
		
		List<Pe> peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple((Double)param.get(Constant.MIPS))));
		HostProfile prof = HostProfile.getDefault();
		prof.set(HostParams.STORAGE_MB, (Long)param.get(Constant.STORE)+"");
		prof.set(HostParams.RAM_AMOUNT_MB,(Integer)param.get(Constant.RAM)+"");
		prof.set(HostParams.BW_AMOUNT, (Long)param.get(Constant.BW)+"");
		return HostProvider.get(prof, peList);
	}
	
	private static HashMap<String, Object> aggregateHost(List<HashMap<String, Object>> hostListParam){
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
	
	private static CProvider datacenterToCProvider(HashMap<String, Object> datacenterParam, List<HashMap<String, Object>> hostListParam){
		CProvider provider = new CProvider();
		HashMap<String, Object> providerCharacteristic = new HashMap<>();
		HashMap<String, Object> networkCharacteristic = new HashMap<>();
		HashMap<String, Object> computingCharacteristic = new HashMap<>();
		HashMap<String, Object> storageCharacteristic = new HashMap<>();
		
		//aggregazione della lista degli host
		HashMap<String, Object> aggregateHost = aggregateHost(hostListParam);
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
		providerCharacteristic.put(Constant.COST_SEC, datacenterParam.get(Constant.COST_SEC));
		providerCharacteristic.put(Constant.PLACE, datacenterParam.get(Constant.PLACE));
		
		provider.setID(Integer.parseInt((String)datacenterParam.get(Constant.ID)));
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
	
	public static List<ICProvider> getProviderList(HashMap<String, Object> param, int listSize, int datacenterSize, int raiseFactor){
		List<HashMap<String, Object>> hostsConf = getHostConfigurations(datacenterSize,raiseFactor);
		List<ICProvider> providerList = new ArrayList<>();
		for(int i=0; i<listSize; i++){
			providerList.add(datacenterToCProvider(param, hostsConf));
		}
		return providerList;
	}
	
	
	public static FederationDatacenter getDatacenter(HashMap<String, Object> param, int datacenterSize, int raiseFactor){
		FederationDatacenterProfile prof = FederationDatacenterProfile.getDefault();
		prof.set(DatacenterParams.COST_PER_BW, (Double)param.get(Constant.COST_BW)+"");
		prof.set(DatacenterParams.COST_PER_MEM, (Double)param.get(Constant.COST_MEM)+"");
		prof.set(DatacenterParams.COST_PER_SEC, (Double)param.get(Constant.COST_SEC)+"");
		prof.set(DatacenterParams.COST_PER_STORAGE, (Double)param.get(Constant.COST_STORAGE)+"");
		
		//make datacenter
		List<HashMap<String, Object>> hostsDatacenter = getHostConfigurations(datacenterSize, raiseFactor);
		List<Host> hostList = getHostList(hostsDatacenter);
		
		List<Storage> storageList = new ArrayList<Storage>();
		return FederationDatacenterProvider.get(prof, hostList, storageList);
	}

}
