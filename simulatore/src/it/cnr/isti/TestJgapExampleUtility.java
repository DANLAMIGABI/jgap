package it.cnr.isti;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
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
		HashMap<String, String> map = app.getCharacteristic();
		String ret = "";
		ret = toStringHashMap(map, "");
		List<CApplicationNode> nodes = app.getNodes();
		for(int i=0; i<nodes.size(); i++){
			ret += toStringHashMap(nodes.get(i).getComputing().getCharacteristic(), "   ");
			ret += toStringHashMap(nodes.get(i).getNetwork().getCharacteristic(), "   ");
			ret += toStringHashMap(nodes.get(i).getStorage().getCharacteristic(), "   ");
		}
		return ret;
	}
	
	public static String toStringHashMap(HashMap<String, String> map, String indent){
		String ret = "";
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String next = keys.next();
			ret += indent + next + ":  " + map.get(next) + "\n";
		}
		return ret;
	}
	
	public static void printProvider(List<ICProvider> list){
		String ret = "";
		for(int i=0; i<list.size(); i++){
			ret += toStringHashMap(list.get(i).getCharacteristic(), "");
			ret += toStringHashMap(list.get(i).getComputing().getCharacteristic(), "   ");
			ret += toStringHashMap(list.get(i).getNetwork().getCharacteristic(), "   ");
			ret += toStringHashMap(list.get(i).getStorage().getCharacteristic(), "   ");
		}
		Log.printLine(ret);
	}
	
	public static void printHostInfo(Host host){
		Log.printLine("  hostid:"+ host.getId());
		Log.printLine("  host ram: " + host.getRam());
		Log.printLine("  host store: " + host.getStorage());
		Log.printLine("  host mips: " +host.getTotalMips());
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
	
	public static Host createHost(HashMap<String, String> param){
		
		List<Pe> peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple(Double.parseDouble(param.get(ConstantTest.MIPS)))));
		HostProfile prof = HostProfile.getDefault();
		prof.set(HostParams.STORAGE_MB, param.get(ConstantTest.STORE));
		prof.set(HostParams.RAM_AMOUNT_MB, param.get(ConstantTest.RAM));
		prof.set(HostParams.BW_AMOUNT, param.get(ConstantTest.BW));
		return HostProvider.get(prof, peList);
	}
	
	public static FederationDatacenter createDatacenter(HashMap<String, String> param, List<Host> hostList){
		
		FederationDatacenterProfile prof = FederationDatacenterProfile.getDefault();
		prof.set(DatacenterParams.COST_PER_BW, param.get(ConstantTest.COST_BW));
		prof.set(DatacenterParams.COST_PER_MEM, param.get(ConstantTest.COST_MEM));
		prof.set(DatacenterParams.COST_PER_SEC, param.get(ConstantTest.COST_SEC));
		prof.set(DatacenterParams.COST_PER_STORAGE, param.get(ConstantTest.COST_STORAGE));
		
		List<Storage> storageList = new ArrayList<Storage>();
		return FederationDatacenterProvider.get(prof, hostList, storageList);
	}
	
	private static HashMap<String, String> aggregateHostListParam(List<HashMap<String, String>> hostListParam){
		HashMap<String, String> map = new HashMap<>();
		long storage =0;
		int ram =0;
		long bw =0;
		double mips =0;
		for(int i=0; i<hostListParam.size(); i++){
			HashMap<String, String> hostParam = hostListParam.get(i);
			storage += Double.parseDouble(hostParam.get(ConstantTest.STORE));
			ram += Integer.parseInt(hostParam.get(ConstantTest.RAM));
			bw += Long.parseLong(hostParam.get(ConstantTest.BW));
			mips += Double.parseDouble(hostParam.get(ConstantTest.MIPS));
		}
		map.put(ConstantTest.STORE, storage+"");
		map.put(ConstantTest.MIPS, mips+"");
		map.put(ConstantTest.RAM, ram+"");
		map.put(ConstantTest.BW, bw+"");
		return map;
	}
	
	public static CProvider datacenterToCProvider(HashMap<String, String> datacenterParam, List<HashMap<String, String>> hostListParam){
		CProvider provider = new CProvider();
		HashMap<String, String> providerCharacteristic = new HashMap<>();
		HashMap<String, String> networkCharacteristic = new HashMap<>();
		HashMap<String, String> computingCharacteristic = new HashMap<>();
		HashMap<String, String> storageCharacteristic = new HashMap<>();
		
		//aggregazione della lista degli host
		HashMap<String, String> aggregateHost = aggregateHostListParam(hostListParam);
		Log.printLine("## AGGREGAZIONE ##");
		Log.printLine(toStringHashMap(aggregateHost, "   "));
		Log.printLine("###################");
		
		//computing
		computingCharacteristic.put(ConstantTest.RAM, aggregateHost.get(ConstantTest.RAM));
		computingCharacteristic.put(ConstantTest.MIPS, aggregateHost.get(ConstantTest.MIPS));
		computingCharacteristic.put(ConstantTest.COST_MEM, datacenterParam.get(ConstantTest.COST_MEM) );
		
		//network
		networkCharacteristic.put(ConstantTest.BW, aggregateHost.get(ConstantTest.BW));
		networkCharacteristic.put(ConstantTest.COST_BW, datacenterParam.get(ConstantTest.COST_BW));
		
		//storage
		storageCharacteristic.put(ConstantTest.STORE, aggregateHost.get(ConstantTest.STORE));
		storageCharacteristic.put(ConstantTest.COST_STORAGE, datacenterParam.get(ConstantTest.COST_STORAGE));
		
		//provider
		providerCharacteristic.put(ConstantTest.COST_SEC, datacenterParam.get(ConstantTest.COST_SEC));
		
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
	
	private static CApplicationNode vmToCApplicationNode(Vm vm, Set<ApplicationEdge> edges){
		CApplicationNode appNode = new CApplicationNode();
		HashMap<String, String> compParam =  new HashMap<>();
		HashMap<String, String> netParam = new HashMap<>();
		HashMap<String, String> storeParam = new HashMap<>();
		
		CApplicationComputing computing = new CApplicationComputing();
		CApplicationNetwork network = new CApplicationNetwork();
		CApplicationStorage storage = new CApplicationStorage();
		
		compParam.put(ConstantTest.MIPS, vm.getMips()+"");
		compParam.put(ConstantTest.RAM, vm.getRam()+"");
		storeParam.put(ConstantTest.STORE, vm.getSize()+"");
		
		computing.setCharacteristic(compParam);
		storage.setCharacteristic(storeParam);
		
		netParam.put(ConstantTest.BW, aggregateEdgesBW(edges)+"");
		network.setCharacteristic(netParam);
		
		appNode.setComputing(computing);
		appNode.setNetwork(network);
		appNode.setStorage(storage);
		
		return appNode;
	}
	
	
	public static ICApplication applicationToCApplication(Application app, String place){
		CApplication newApp = new CApplication();
		HashMap<String, String> appCharacteristic = new HashMap<>();
		List<Vm> vmList = app.getAllVms();
		
		List<CApplicationNode> nodeList = new ArrayList<>();
		for(int i=0; i<vmList.size(); i++){
			nodeList.add(vmToCApplicationNode(vmList.get(i), app.edgesOf(app.getVertexForVm(vmList.get(i)))));
			
		}
		appCharacteristic.put(ConstantTest.PLACE, place);
		
		newApp.setNodes(nodeList);
		newApp.setCharacteristic(appCharacteristic);
		
		return newApp;
	}

	
	
}
