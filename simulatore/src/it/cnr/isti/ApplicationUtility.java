package it.cnr.isti;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.mapping.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudbus.cloudsim.Vm;

import cApplication.CApplication;
import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationNode;
import cApplication.CApplicationStorage;
import cApplicationIface.ICApplication;

public class ApplicationUtility {
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
	
	public static String toStringCApplication(ICApplication app){
		HashMap<String, Object> map = app.getCharacteristic();
		String ret = "";
		ret += map.get(Constant.PLACE);
		ret += hashToString(map, "   ");
		List<CApplicationNode> nodes = app.getNodes();
		for(int i=0; i<nodes.size(); i++){
			ret +=hashToString( nodes.get(i).getComputing().getCharacteristic(),"    ");
			ret += hashToString(nodes.get(i).getNetwork().getCharacteristic(), "   ");
			ret += hashToString(nodes.get(i).getStorage().getCharacteristic(), "   ");
		}
		return ret;
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
