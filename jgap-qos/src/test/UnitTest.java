package test;

import java.util.ArrayList;

import org.junit.Test;

import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProvider.CProviderNetwork;
import cProvider.CProviderStorage;

public class UnitTest {
	
	
	public ArrayList<CProvider> Test1_makeProviderList(){
		String[] providerPlaces = new String[]{ "Italia","Svezia","Italia","Italia","Svezia","Italia"};
		Double[] costs = new Double[]{ 85.00,65.00,150.00,80.00,120.00,75.00 };
		Integer[] ram = new Integer[]{ 1000,5000,6000,2000,1000,3000 };
		Integer[] bandwidth = new Integer[]{ 1000,1000,100000,2000,10000,1000 };
		Integer[] storeSize = new Integer[]{ 20,30,100,50,80,35 };
				
		CProviderComputing computing;
		CProviderNetwork network;
		CProviderStorage storage;
		
		ArrayList<CProvider> providerList = new ArrayList<CProvider>();
		for(int i=0; i<providerPlaces.length; i++){
			computing = new CProviderComputing();
			network = new CProviderNetwork();
			storage =  new CProviderStorage();
			computing.setRam(ram[i]);
			network.setBandwidth(bandwidth[i]);
			storage.setAmount(storeSize[i]);
			providerList.add(new CProvider(providerPlaces[i], costs[i], 101+i, computing, storage, network));
		}
		return providerList;
	}
	
	@Test
	public void Test1(){
		
				
	}

}
