package it.cnr.isti.federation.application_inter_arrival;

import it.cnr.isti.federation.application.Application;

public interface InterArrivalModelItf {

	public Object[] getSchedulingTime(Application[] applications, String ...strings);
}
