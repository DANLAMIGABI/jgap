package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.mapping.ConstantMapping;
import it.cnr.isti.test.ThreeTierBusinessApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudbus.cloudsim.Vm;

import msApplication.*;
import msApplicationIface.*;

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
	
	public static String toStringMSApplication(IMSApplication app){
		HashMap<String, Object> map = app.getCharacteristic();
		String ret = "";
		ret += map.get(ConstantMapping.PLACE);
		ret += hashToString(map, "   ");
		List<MSApplicationNode> nodes = app.getNodes();
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
	
	private static MSApplicationNode vmToMSApplicationNode(Vm vm, Set<ApplicationEdge> edges, String budger, String place){
		MSApplicationNode appNode = new MSApplicationNode();
//		HashMap<String, Object> appNodeCharacteristic = new HashMap<>();
//		appNodeCharacteristic.put(Constant.PLACE, place);
//		appNode.setCharacteristic(appNodeCharacteristic);
		HashMap<String, Object> compParam =  new HashMap<>();
		HashMap<String, Object> netParam = new HashMap<>();
		HashMap<String, Object> storeParam = new HashMap<>();
		
		MSApplicationComputing computing = new MSApplicationComputing();
		MSApplicationNetwork network = new MSApplicationNetwork();
		MSApplicationStorage storage = new MSApplicationStorage();
		
		compParam.put(ConstantMapping.MIPS, vm.getMips());
		compParam.put(ConstantMapping.RAM, vm.getRam());
		storeParam.put(ConstantMapping.STORE, vm.getSize());
		
		computing.setCharacteristic(compParam);
		storage.setCharacteristic(storeParam);
		
		netParam.put(ConstantMapping.BW, aggregateEdgesBW(edges)+"");
		network.setCharacteristic(netParam);
		
		appNode.setComputing(computing);
		appNode.setNetwork(network);
		appNode.setStorage(storage);
		HashMap<String, Object> nodeCar = new HashMap<>();
		nodeCar.put(ConstantMapping.BUDGET, Double.parseDouble(budger));
		nodeCar.put(ConstantMapping.PLACE,place);
		appNode.setCharacteristic(nodeCar);
		return appNode;
	}
	
	
	public static IMSApplication applicationToMSApplication(Application app, String place, String budget){
		MSApplication newApp = new MSApplication();
		HashMap<String, Object> appCharacteristic = new HashMap<>();
		List<Vm> vmList = app.getAllVms();
		
		List<MSApplicationNode> nodeList = new ArrayList<>();
		for(int i=0; i<vmList.size(); i++){
			nodeList.add(vmToMSApplicationNode(vmList.get(i), app.edgesOf(app.getVertexForVm(vmList.get(i))), budget, place));
			
		}
		appCharacteristic.put(ConstantMapping.PLACE, place);
		appCharacteristic.put(ConstantMapping.BUDGET, Double.parseDouble(budget));
		
		newApp.setNodes(nodeList);
		newApp.setCharacteristic(appCharacteristic);
		
		return newApp;
	}
	
	public static Application getApplication(int userID, int numberOfCloudlets)
	{
		Double number = new Double(numberOfCloudlets);
		if (number < 3)
			number = 3d;
		
		int frontend = 1;//new Double(Math.ceil(number * 20 / 100)).intValue();
		int database = 1;//new Double(Math.ceil(number * 20 / 100)).intValue();
		int appserver = 1;//number.intValue() - frontend - database;
		
		return new ThreeTierBusinessApplication(userID,frontend, appserver, database);
	}
	

}
