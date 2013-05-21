package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import msApplication.MSApplicationNode;
import msApplicationIface.IMSAppComuting;
import msApplicationIface.IMSAppNetwork;
import msApplicationIface.IMSApplication;
import msProviderIface.IMSProvider;
import msProviderIface.IMSProviderComputing;
import msProviderIface.IMSProviderNetwork;
import msProviderIface.IMSProviderStorage;

import org.jgap.IChromosome;


public class UtilityJGAP {
	
	private static String characteristicToString(HashMap<String, Object> map, String indent){
		String title ="";
		String value ="";
		String ret = "";
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String next  = keys.next();
			title = next;
			Object obj = map.get(next);
			if( obj instanceof Integer )
				value = (Integer) obj + indent;
			else if( obj instanceof Double)
				value =  (Double) obj + indent;
			else if (obj instanceof Long)
				value = (Long) obj + indent;
			else if(next instanceof String)
				value = (String) obj + indent;
			ret += indent + title + ":  " + value + "\n"; 
		}
		//return ret += title + "\n" + value + "\n";
		return ret;
	}
	
	public static String printChromosome(IChromosome arg, List<IMSProvider> plist, IMSApplication app){
		Integer id;
		String ret="";
		for(int i=0; i<arg.size(); i++){
			id = (Integer)arg.getGene(i).getAllele();
			ret += "appID: "+ app.getID()+"." + i + " -> " + "pId: " + id+ "\n";
		}
		return ret;
	}
	
	public static void printProviders(List<IMSProvider> plist){
		for(int i =0; i<plist.size(); i++){
			IMSProviderComputing comp = plist.get(i).getComputing();
			IMSProviderNetwork net = plist.get(i).getNetwork();
			IMSProviderStorage store = plist.get(i).getStorage();
			System.out.println("pID: " + plist.get(i).getID());
			System.out.println(characteristicToString(plist.get(i).getCharacteristic(), "      "));
			System.out.println("  comp");
			System.out.println(characteristicToString(comp.getCharacteristic(),"      " ));
			System.out.println("  net");
			System.out.println(characteristicToString(net.getCharacteristic(),"      " ));
			System.out.println("  store");
			System.out.println(characteristicToString(store.getCharacteristic(),"      " ));
			
		}
	}
	public static void printICApplication(IMSApplication app){
		List<MSApplicationNode> nodes = app.getNodes();
		for(int i=0; i<nodes.size(); i++){
			System.out.println("AppID: " + app.getID() +"."+i );
			System.out.println("   vklja;dsf " + characteristicToString(nodes.get(i).getCharacteristic(), "    "));
			System.out.println("  comp");
			System.out.println(characteristicToString(nodes.get(i).getComputing().getCharacteristic(), "    "));
			System.out.println("  net");
			System.out.println(characteristicToString(nodes.get(i).getNetwork().getCharacteristic(), "    "));
			System.out.println("  store");
			System.out.println(characteristicToString(nodes.get(i).getStorage().getCharacteristic(), "    "));
			
		}
	}
	

}