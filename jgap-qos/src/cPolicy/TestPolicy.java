package cPolicy;

import cApplication.CApplication;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationNode;
import cApplicationIface.ICAppComuting;
import cApplicationIface.ICAppNetwork;
import cApplicationIface.ICAppStorage;
import cApplicationIface.ICApplication;
import cProviderIface.ICProvider;
import cProviderIface.ICProviderComputing;
import cProviderIface.ICProviderNetwork;
import cProviderIface.ICProviderStorage;

public class TestPolicy {

//GLOBAL_POLICY	
	// equal
	public static int applicationPlacePolicy(ICApplication app, ICProvider prov){
		return app.getPlace().compareToIgnoreCase(prov.getPlace());
	}
	
	public static int applicationAllocation(CApplicationNode node, ICProvider prov){
		return 0;
	}
	// Descendent
	public static double applicationCost(ICApplication app, ICProvider prov){
		
		return prov.getCost() - app.getBudget();
	}
//END_GLOBAL
	
//LOCAL_POLICY
	// Ascendent
	public static double networkPolicy(CApplicationNode app, ICProvider prov){
		ICAppNetwork  anet = app.getNetwork();
		ICProviderNetwork  pnet = prov.getNetwork();
		
		return anet.getBandwidth() - pnet.getBandwidth();
	}
	
	public static double storagePolicy(CApplicationNode app, ICProvider prov){
		ICAppStorage astore = app.getStorage();
		ICProviderStorage pstore = prov.getStorage();
		return astore.getAmount() - pstore.getAmount();
	}
	
	public static double computingPolicy(CApplicationNode app, ICProvider prov){
		ICAppComuting acomp = app.getComputing();
		ICProviderComputing pcomp = prov.getComputing();
		
		return acomp.getRam() - pcomp.getRam();
	}
//END_LOCAL_POLICY

}
