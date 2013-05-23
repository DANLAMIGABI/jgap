package it.cnr.isti.federation.metascheduler.iface;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.metascheduler.resources.MSApplication;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationComputing;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNetwork;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationStorage;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



import org.cloudbus.cloudsim.Vm;

public class MSApplicationUtility {
	
	private static MSApplicationNode vmToMSApplicationNode(Vm vm, Set<ApplicationEdge> edges, HashMap<String, Object> appParam){
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
		HashMap<String, Object> nodeCar = new HashMap<String, Object>();
		nodeCar.put(Constant.BUDGET, appParam.get(Constant.BUDGET));
		nodeCar.put(Constant.PLACE,appParam.get(Constant.PLACE));
		appNode.setCharacteristic(nodeCar);
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
	
	public static IMSApplication getMSApplication(Application app, HashMap<String, Object> appParam){
		MSApplication newApp = new MSApplication();
		HashMap<String, Object> appCharacteristic = new HashMap<String, Object>();
		List<Vm> vmList = app.getAllVms();
		
		List<MSApplicationNode> nodeList = new ArrayList<MSApplicationNode>();
		for(int i=0; i<vmList.size(); i++){
			nodeList.add(vmToMSApplicationNode(vmList.get(i), app.edgesOf(app.getVertexForVm(vmList.get(i))), appParam));
			
		}
		appCharacteristic.put(Constant.PLACE, appParam.get(Constant.PLACE));
		appCharacteristic.put(Constant.BUDGET, appParam.get(Constant.BUDGET));
		
		newApp.setNodes(nodeList);
		newApp.setCharacteristic(appCharacteristic);
		
		return newApp;
	}

}
