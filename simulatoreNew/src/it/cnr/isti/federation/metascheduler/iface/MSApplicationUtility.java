package it.cnr.isti.federation.metascheduler.iface;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.metascheduler.resources.MSApplication;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationComputing;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNetwork;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationStorage;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.network.datacenter.AppCloudlet;

public class MSApplicationUtility {
	public static String hashToString(HashMap<String, Object> map, String indent){
		String ret = "";
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String next  = keys.next();
			Object value = map.get(next);
			next = next.toLowerCase();
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
	
	public static String toStringMSApplication(IMSApplication app){
		HashMap<String, Object> map = app.getCharacteristic();
		String indent = "    ";
		String ret = "";
		
		List<MSApplicationNode> nodes = app.getNodes();
		for(int i=0; i<nodes.size(); i++){
			ret += " Node." + nodes.get(i).getID() + "\n";
//			ret += hashToString(map, indent);
//			ret += indent + "Place: " + nodes.get(i).getCharacteristic().get(Constant.PLACE) + "\n";
//			ret += indent + "Budget: " + nodes.get(i).getCharacteristic().get(Constant.BUDGET) + "\n";
			ret += hashToString(nodes.get(i).getCharacteristic(), indent);
			ret += hashToString(nodes.get(i).getComputing().getCharacteristic(), indent);
			ret += hashToString(nodes.get(i).getNetwork().getCharacteristic(), indent);
			ret += hashToString(nodes.get(i).getStorage().getCharacteristic(), indent);
		}
		return ret;
	}
	
	
	private static MSApplicationNode vmToMSApplicationNode(Vm vm, Set<ApplicationEdge> edges, String place, double budget){
		MSApplicationNode appNode = new MSApplicationNode();
//		HashMap<String, Object> appNodeCharacteristic = new HashMap<>();
//		appNodeCharacteristic.put(Constant.PLACE, place);
//		appNode.setCharacteristic(appNodeCharacteristic);
		HashMap<String, Object> compParam =  new HashMap<String, Object>();
		HashMap<String, Object> netParam = new HashMap<String, Object>();
		HashMap<String, Object> storeParam = new HashMap<String, Object>();
		
		MSApplicationComputing computing = new MSApplicationComputing();
		MSApplicationNetwork network = new MSApplicationNetwork();
		MSApplicationStorage storage = new MSApplicationStorage();
		
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
		HashMap<String, Object> nodeCharacteristic = new HashMap<String, Object>();
		nodeCharacteristic.put(Constant.BUDGET, budget);
		nodeCharacteristic.put(Constant.PLACE,place);
//		nodeCharacteristic.put(Constant.VM_INSTANCES, new Integer(1));
		appNode.setCharacteristic(nodeCharacteristic);
		appNode.setID(vm.getId());
		return appNode;
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
	
	public static IMSApplication getMSApplication(Application app){
		MSApplication newApp = new MSApplication();
//		HashMap<String, Object> appCharacteristic = new HashMap<String, Object>();
		ApplicationVertex vertex ;
		List<Vm> vmList = app.getAllVms();
		
		List<MSApplicationNode> nodeList = new ArrayList<MSApplicationNode>();
		for(int i=0; i<vmList.size(); i++){
			vertex = app.getVertexForVm(vmList.get(i));
			nodeList.add(vmToMSApplicationNode(vmList.get(i), app.edgesOf(vertex), vertex.getPlace(), vertex.getBudget()));
			
		}
//		appCharacteristic.put(Constant.PLACE, .getPlace());
//		appCharacteristic.put(Constant.BUDGET, app.getBudget());
//		appCharacteristic.put("count", new Integer(0));
		
		newApp.setNodes(nodeList);
//		newApp.setCharacteristic(appCharacteristic);
		
		return newApp;
	}

}
