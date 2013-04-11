package newTest;

import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationNode;
import cApplication.CApplicationStorage;
import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProvider.CProviderNetwork;
import cProvider.CProviderStorage;


public class CFitnessEasyTest {
	
//	static String[] provPlaces = new String[]{
//		"Italia","Svezia","Italia","Italia","Svezia","Italia"
//	};
//	static Integer[] costs = new Integer[]{
//		85,65,150,80,120,75
//		
//	};
//	static Integer[] ram = new Integer[]{
//		1000,5000,6000,2000,1000,3000
//	};
//	static Integer[] bandwidth = new Integer[]{
//		1000,1000,100000,2000,10000,1000
//	};
//	static Integer[] storeSize = new Integer[]{
//		20,30,100,50,80,35
//	};
// 	
//	static String[] appPlaces = new String[]{
//		"Italia","Italia","Svezia"
//	};
//	
//	static double[] budgets = {
//		75,85,85
//	};
	
//	public static CApplicationNode[] makeNodes(int size){
//		
//		CApplicationNode[] nodes = new CApplicationNode[size];
//		
//		for(int i=0; i< nodes.length; i++){
//			nodes[i] = new CApplicationNode();
//			nodes[i].setID(i);
//			nodes[i].setNodeBudget(budgets[i%size]);
//			nodes[i].setComputing(new CApplicationComputing(null, 0, -1, ram[i%size]));
//			nodes[i].setNetwork(new CApplicationNetwork(bandwidth[i%size], 0, null, -1));
//			nodes[i].setStorage(new  CApplicationStorage(0, storeSize[i%size], 0, null));
//		}
//		return nodes;
//	}
	
	public static CProvider[] makeProviderList(String[] places, Integer[] costs, Integer[] ram, Integer[] bandwidth, Integer[] storeSize ){
		CProvider[] provlist = new CProvider[places.length];
		CProviderComputing comp; 
		CProviderNetwork net; 
		CProviderStorage store; 
		for(int i=0; i<provlist.length; i++){
			comp = new CProviderComputing();
			net = new CProviderNetwork();
			store =  new CProviderStorage();
			comp.setRam(ram[i%ram.length]);
			net.setBandwidth(bandwidth[i%bandwidth.length]);
			store.setAmount(storeSize[i%storeSize.length]);
			provlist[i] = new CProvider(places[i%places.length], costs[i%costs.length], 101+i, comp, store, net);
			
		}
		return provlist;	
	}

}
