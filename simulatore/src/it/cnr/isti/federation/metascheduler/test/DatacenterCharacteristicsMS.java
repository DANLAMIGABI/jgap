package it.cnr.isti.federation.metascheduler.test;

import java.util.List;

import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;

public class DatacenterCharacteristicsMS extends DatacenterCharacteristics {

	private String place;
		
	public DatacenterCharacteristicsMS(String place,String architecture, String os,
			String vmm, List<? extends Host> hostList, double timeZone,
			double costPerSec, double costPerMem, double costPerStorage,
			double costPerBw) {
		super(architecture, os, vmm, hostList, timeZone, costPerSec, costPerMem,
				costPerStorage, costPerBw);
		this.place = place;
		
	}
	
	public String getPlace(){
		return place;
	}
	

}
