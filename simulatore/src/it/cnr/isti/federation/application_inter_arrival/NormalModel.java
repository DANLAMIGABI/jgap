package it.cnr.isti.federation.application_inter_arrival;

import it.cnr.isti.federation.application.Application;

public class NormalModel implements InterArrivalModelItf {

	private final long intervalAmongJobs = 100;
	
	@Override
	public Object[] getSchedulingTime(Application[] applications, String ...params) {
		
		long interval;
		
		if (params.length != 0){
			interval = Long.parseLong(params[0]);
		} else {
			interval = intervalAmongJobs;
		}
		
		long[] timestamps = new long[applications.length];
		long currentTimestamp = 0;
		for(int i=0;i<applications.length;i++){
			
			timestamps[i] = currentTimestamp;
			currentTimestamp += interval;
			
		}
		
		Object[] result =  new Object[2];
		result[0] = applications;
		result[1] = timestamps;
		
		return result;
	}

}
