package it.cnr.isti;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.mapping.Constant;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.FederationDatacenterProfile;
import it.cnr.isti.federation.resources.FederationDatacenterProfile.DatacenterParams;
import it.cnr.isti.federation.resources.FederationDatacenterProvider;
import it.cnr.isti.federation.resources.HostProfile;
import it.cnr.isti.federation.resources.HostProvider;
import it.cnr.isti.federation.resources.HostProfile.HostParams;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.SecuritySupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.text.StyledEditorKit.ItalicAction;

import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;

import sun.util.logging.resources.logging;

import cApplication.CApplication;
import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationNode;
import cApplication.CApplicationStorage;
import cApplicationIface.ICApplication;
import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProvider.CProviderNetwork;
import cProvider.CProviderStorage;
import cProviderIface.ICProvider;

import com.sun.org.apache.bcel.internal.generic.LSTORE;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class TestJgapExampleUtility {
	private static InternetEstimator net;
	
	public static String toStringCApplication(ICApplication app){
		HashMap<String, Object> map = app.getCharacteristic();
		String ret = "";
		ret += map.get(Constant.PLACE);
		ret += hashToString(map, "   ");
//		List<CApplicationNode> nodes = app.getNodes();
//		for(int i=0; i<nodes.size(); i++){
//			ret += nodes.get(i).getComputing().getCharacteristic().get(Constant.PLACE);
//			ret += nodes.get(i).getNetwork().getCharacteristic(), "   ");
//			ret += nodes.get(i).getStorage().getCharacteristic(), "   ");
//		}
		return ret;
	}
	
	
	
	public static String hashToString(HashMap<String, Object> map, String indent){
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
	
	public static void printProvider(List<ICProvider> list){
		String ret = "";
		for(int i=0; i<list.size(); i++){
			ret += hashToString(list.get(i).getCharacteristic(), "");
			ret += hashToString(list.get(i).getComputing().getCharacteristic(), "   ");
			ret += hashToString(list.get(i).getNetwork().getCharacteristic(), "   ");
			ret += hashToString(list.get(i).getStorage().getCharacteristic(), "   ");
		}
		Log.printLine(ret);
	}
	
	public static void printHostInfo(Host host){
		Log.printLine("  hostid:           "+ host.getId());
		Log.printLine("  host ram:         " + host.getRam());
		Log.printLine("  host store:       " + host.getStorage());
		Log.printLine("  host mips:        " + host.getTotalMips());
		Log.printLine("  host pelist size: " + host.getPeList().size());
		Log.printLine();
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
	
	public static Host createHost(HashMap<String, Object> param){
		
		List<Pe> peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple((Double)param.get(Constant.MIPS))));
		HostProfile prof = HostProfile.getDefault();
		prof.set(HostParams.STORAGE_MB, (Long)param.get(Constant.STORE)+"");
		prof.set(HostParams.RAM_AMOUNT_MB,(Integer)param.get(Constant.RAM)+"");
		prof.set(HostParams.BW_AMOUNT, (Long)param.get(Constant.BW)+"");
		return HostProvider.get(prof, peList);
	}
	
	public static FederationDatacenter createDatacenter(HashMap<String, Object> param, List<Host> hostList){
		
		FederationDatacenterProfile prof = FederationDatacenterProfile.getDefault();
		prof.set(DatacenterParams.COST_PER_BW, (Double)param.get(Constant.COST_BW)+"");
		prof.set(DatacenterParams.COST_PER_MEM, (Double)param.get(Constant.COST_MEM)+"");
		prof.set(DatacenterParams.COST_PER_SEC, (Double)param.get(Constant.COST_SEC)+"");
		prof.set(DatacenterParams.COST_PER_STORAGE, (Double)param.get(Constant.COST_STORAGE)+"");
		
		List<Storage> storageList = new ArrayList<Storage>();
		return FederationDatacenterProvider.get(prof, hostList, storageList);
	}
	
	private static HashMap<String, Object> aggregateHostListParam(List<HashMap<String, Object>> hostListParam){
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
	
	public static CProvider datacenterToCProvider(HashMap<String, Object> datacenterParam, List<HashMap<String, Object>> hostListParam){
		CProvider provider = new CProvider();
		HashMap<String, Object> providerCharacteristic = new HashMap<>();
		HashMap<String, Object> networkCharacteristic = new HashMap<>();
		HashMap<String, Object> computingCharacteristic = new HashMap<>();
		HashMap<String, Object> storageCharacteristic = new HashMap<>();
		
		//aggregazione della lista degli host
		HashMap<String, Object> aggregateHost = aggregateHostListParam(hostListParam);
		Log.printLine("## AGGREGAZIONE ##");
		Log.printLine(hashToString(aggregateHost, "   "));
		Log.printLine("###################");
		
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
	
	private static long aggregateEdgesBW(Set<ApplicationEdge> edges){
		long bw =0;

		Iterator<ApplicationEdge> edge = edges.iterator();
		while (edge.hasNext()) {
			ApplicationEdge next = edge.next();
			bw += next.getBandwidth();
		}
		return bw;
	    
	}
	
	private static CApplicationNode vmToCApplicationNode(Vm vm, Set<ApplicationEdge> edges, String budger, String place){
		CApplicationNode appNode = new CApplicationNode();
//		HashMap<String, Object> appNodeCharacteristic = new HashMap<>();
//		appNodeCharacteristic.put(Constant.PLACE, place);
//		appNode.setCharacteristic(appNodeCharacteristic);
		HashMap<String, Object> compParam =  new HashMap<>();
		HashMap<String, Object> netParam = new HashMap<>();
		HashMap<String, Object> storeParam = new HashMap<>();
		
		CApplicationComputing computing = new CApplicationComputing();
		CApplicationNetwork network = new CApplicationNetwork();
		CApplicationStorage storage = new CApplicationStorage();
		
		compParam.put(Constant.MIPS, vm.getMips());
		compParam.put(Constant.RAM, vm.getRam());
		storeParam.put(Constant.STORE, vm.getSize());
		
		computing.setCharacteristic(compParam);
		storage.setCharacteristic(storeParam);
		
		netParam.put(Constant.BW, aggregateEdgesBW(edges)+"");
		network.setCharacteristic(netParam);
		
		appNode.setComputing(computing);
		appNode.setNetwork(network);
		appNode.setStorage(storage);
		HashMap<String, Object> nodeCar = new HashMap<>();
		nodeCar.put(Constant.BUDGET, Double.parseDouble(budger));
		nodeCar.put(Constant.PLACE,place);
		appNode.setCharacteristic(nodeCar);
		return appNode;
	}
	
	
	public static ICApplication applicationToCApplication(Application app, String place, String budget){
		CApplication newApp = new CApplication();
		HashMap<String, Object> appCharacteristic = new HashMap<>();
		List<Vm> vmList = app.getAllVms();
		
		List<CApplicationNode> nodeList = new ArrayList<>();
		for(int i=0; i<vmList.size(); i++){
			nodeList.add(vmToCApplicationNode(vmList.get(i), app.edgesOf(app.getVertexForVm(vmList.get(i))), budget, place));
			
		}
		appCharacteristic.put(Constant.PLACE, place);
		appCharacteristic.put(Constant.BUDGET, Double.parseDouble(budget));
		
		newApp.setNodes(nodeList);
		newApp.setCharacteristic(appCharacteristic);
		
		return newApp;
	}

	
	
}
