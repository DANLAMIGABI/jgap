package it.cnr.isti.federation;

import java.util.List;

import it.cnr.isti.federation.FederationQueueProfile.QueueParams;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application_inter_arrival.InterArrivalModelItf;

public class FederationQueueProvider {

	
	public static FederationQueue createFederationQueue(FederationQueueProfile profile, Federation federation, 
			List<Application> applications){
		
		Application[] apps_array = new Application[applications.size()];
		applications.toArray(apps_array);
		
		String arrivalModelName = profile.get(QueueParams.INTER_ARRIVAL_MODEL);
		FederationQueue fq = null;
		
		try {
			
			InterArrivalModelItf arrivalModel = (InterArrivalModelItf) Class.forName(arrivalModelName).newInstance();
			Object[] ret = arrivalModel.getSchedulingTime(apps_array);
			
			fq = new FederationQueue(federation, ret);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return fq;
		
	}
	
	public static FederationQueue getFederationQueue(FederationQueueProfile profile, Federation federation, List<Application> applications){
		return createFederationQueue(profile, federation, applications);
	}
	
}
