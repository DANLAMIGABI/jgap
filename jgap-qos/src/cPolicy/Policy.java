package cPolicy;

import cApplication.CApplicationNetwork;
import cApplicationIface.ICAppComuting;
import cApplicationIface.ICAppNetwork;
import cApplicationIface.ICAppStorage;
import cApplicationIface.ICApplication;
import cProviderIface.ICProvider;
import cProviderIface.ICProviderComputing;
import cProviderIface.ICProviderNetwork;
import cProviderIface.ICProviderStorage;

public class Policy {
	
	public static boolean applicationPlacePolicy(ICApplication app, ICProvider prov){
		return app.getPlace().equalsIgnoreCase(app.getPlace());
	}
	
	public static double networkPolicy(ICApplication app, ICProvider prov){
		ICAppNetwork  anet = app.getNetwork();
		ICProviderNetwork  pnet = prov.getNetwork();
		
		return anet.getBandwidth() - pnet.getBandwidth();
	}
	
	public static double storagePolicy(ICApplication app, ICProvider prov){
		ICAppStorage astore = app.getStorage();
		ICProviderStorage pstore = prov.getStorage();
		
		return astore.getAmount() - pstore.getAmount();
	}
	
	public static double computingPolicy(ICApplication app, ICProvider prov){
		ICAppComuting acomp = app.getComputing();
		ICProviderComputing pcomp = prov.getComputing();
		
		return acomp.getRam() - pcomp.getRam();
	}

}
