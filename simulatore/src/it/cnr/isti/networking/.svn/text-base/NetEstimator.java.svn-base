package it.cnr.isti.networking;

import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.resources.FederationDatacenter;

import java.util.HashMap;


public class NetEstimator {

	private double[][] link_bandwidth = null;
	private long[][] link_latency = null;
	private SecuritySupport[][] link_security = null;
	
	private HashMap<Long, Object[]> sessions = null;
	private HashMap<Integer, Integer> datacenterIdTranslation = null;
	
	private long sessionId = 0;
	private int datacenterTraslationId = -1;
	
	
	public NetEstimator(int datacenters){
		
		link_bandwidth = new double[datacenters][datacenters];
		link_latency = new long[datacenters][datacenters];
		link_security = new SecuritySupport[datacenters][datacenters];
		
		sessions = new HashMap<Long, Object[]>();
		datacenterIdTranslation = new HashMap<Integer, Integer>();
		
	}
	
	public void defineLinkProperties (FederationDatacenter source, FederationDatacenter dest, double bandwidth, long latency, SecuritySupport security){
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		link_bandwidth[sourceDc][destDc] = bandwidth;
		link_latency[sourceDc][destDc] = latency;
		link_security[sourceDc][destDc] = security;
	}
	
	
	public long createAllocationSession(){
		
		sessionId++;
		
		double[][] tmp_link_bandwidth = link_bandwidth.clone();
		long[][] tmp_link_latency = link_latency.clone();
		SecuritySupport[][] tmp_link_security = link_security.clone();
		
		Object[] session = new Object[3];
		session[0] = tmp_link_bandwidth;
		session[1] = tmp_link_latency;
		session[2] = tmp_link_security;
		
		sessions.put(sessionId, session);
		
		return sessionId;
	}
	
	public void disposeAllocationSession(long id) throws NullPointerException {
		
		Object[] retValue = sessions.remove(id);
	
		if(retValue == null) 
			throw new NullPointerException("No Such session with given ID");
		
	}

	
	public boolean allocateLink (long sessionId, FederationDatacenter source, FederationDatacenter dest, ApplicationEdge edge){
		
		Object[] session =  sessions.get(sessionId);
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		// Admission control
		if((edge.getBandwidth() <= ( (double[][]) session[0] )[sourceDc][destDc]) && 
			  (edge.getLatency() >= ( (long[][]) session[1] )[sourceDc][destDc])  &&
			  (edge.getSecurity() == ( (SecuritySupport[][]) session[2] )[sourceDc][destDc]) )
		{
			
			( (double[][]) session[0] )[sourceDc][destDc] = ( (double[][]) session[0] )[sourceDc][destDc] - edge.getBandwidth();
			
			return true;
		}
		
		return false;
	}
	
	public boolean allocateLink (FederationDatacenter source, FederationDatacenter dest, ApplicationEdge edge){
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		// Admission control
		if((edge.getBandwidth() <= link_bandwidth[sourceDc][destDc]) && 
			  (edge.getLatency() >= link_latency[sourceDc][destDc])  &&
			  (edge.getSecurity() == link_security[sourceDc][destDc]) )
		{
			
			link_bandwidth[sourceDc][destDc] = link_bandwidth[sourceDc][destDc] - edge.getBandwidth();
			return true;
			
		}
		
		return false;
		
	}

	public void deallocateLink (long sessionId, FederationDatacenter source, FederationDatacenter dest, ApplicationEdge edge){
	
		Object[] session =  sessions.get(sessionId);
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		( (double[][]) session[0] )[sourceDc][destDc] = ( (double[][]) session[0] )[sourceDc][destDc] + edge.getBandwidth();
	
	}
	
	public void deallocateLink (FederationDatacenter source, FederationDatacenter dest, ApplicationEdge edge){
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		link_bandwidth[sourceDc][destDc] = link_bandwidth[sourceDc][destDc] + edge.getBandwidth();
	
	}

	public Object[] queryLink (long sessionId, FederationDatacenter source, FederationDatacenter dest){
		
		Object[] session =  sessions.get(sessionId);
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		double bandwidth = ( (double[][]) session[0] )[sourceDc][destDc];
		long latency = ( (long[][]) session[1] )[sourceDc][destDc];
		SecuritySupport security = ( (SecuritySupport[][]) session[2] )[sourceDc][destDc];
		
		Object[] result = new Object[3];
		result[0] = bandwidth;
		result[1] = latency;
		result[2] = security;
		
		return result;
	}
	
	
	/** Current Assumption: the original bandwidth matrix does not changes during the allocation procedure */
	public void consolidateAllocationSession(long id){
		
		Object[] session =  sessions.get(sessionId);
		link_bandwidth = (double[][]) session[0];
		
	}
	
	
	private int translate(FederationDatacenter source) {
		
		if(datacenterIdTranslation.containsKey(source.getId())) 
			return datacenterIdTranslation.get(source.getId());
		else{
			datacenterTraslationId++;
			datacenterIdTranslation.put(source.getId(), datacenterTraslationId);
			return datacenterTraslationId;
		}
	}
}
