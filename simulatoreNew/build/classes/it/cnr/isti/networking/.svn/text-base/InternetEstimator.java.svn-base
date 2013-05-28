package it.cnr.isti.networking;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.resources.FederationDatacenter;

import java.util.Arrays;
import java.util.HashMap;


public class InternetEstimator
{
	private InternetLink[][] links = null;
	private HashMap<Long, InternetLink[][]> sessions = null;
	private HashMap<Integer, Integer> datacenterIdTranslation = null;
	
	private long sessionId = 0;
	private int datacenterTraslationId = -1;
	
	
	public InternetEstimator(int datacenters)
	{
		links = new InternetLink[datacenters][datacenters];
		sessions = new HashMap<Long, InternetLink[][]>();
		datacenterIdTranslation = new HashMap<Integer, Integer>();
		
	}
	
	public void defineDirectLink(FederationDatacenter source, FederationDatacenter dest, 
			long bandwidth, int latency, SecuritySupport security)
	{
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		links[sourceDc][destDc] = new InternetLink(bandwidth, latency, security);
	}
	
	public void defineUndirectLink(FederationDatacenter source, FederationDatacenter dest, 
			long bandwidth, int latency, SecuritySupport security)
	{
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		links[sourceDc][destDc] = new InternetLink(bandwidth, latency, security);
		links[destDc][sourceDc] = new InternetLink(bandwidth, latency, security);
	}
	
	public long createSession()
	{	
		sessionId++;
		
		InternetLink[][] session = this.cloneLinks();		
		sessions.put(sessionId, session);
		
		return sessionId;
	}
	
	public void disposeAllocationSession(long id) throws NullPointerException 
	{
		Object[] retValue = sessions.remove(id);
		if(retValue == null) 
			throw new NullPointerException("No Such session with given ID");
	}

	
	public boolean allocateLink (long sessionId, FederationDatacenter source, FederationDatacenter dest, 
			ApplicationEdge edge, Application application)
	{	
		InternetLink[][] session =  sessions.get(sessionId);
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		return session[sourceDc][destDc].mapEdge(application, edge);
	}
	
	public boolean allocateLink (FederationDatacenter source, FederationDatacenter dest, ApplicationEdge edge,
			Application application)
	{
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		return links[sourceDc][destDc].mapEdge(application, edge);
		
	}

	public void deallocateLink (long sessionId, FederationDatacenter source, FederationDatacenter dest, 
			ApplicationEdge edge, Application application)
	{
	
		InternetLink[][] session =  sessions.get(sessionId);
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		session[sourceDc][destDc].unmapEdge(application, edge);
	}
	
	public void deallocateLink (FederationDatacenter source, FederationDatacenter dest, ApplicationEdge edge,
			Application application)
	{
		
		int sourceDc = translate(source);
		int destDc = translate(dest);
		
		links[sourceDc][destDc].unmapEdge(application, edge);	
	}
	
	/** Current Assumption: the original bandwidth matrix does not changes during the allocation procedure */
	public void consolidateAllocationSession(long id)
	{
		InternetLink[][] session =  sessions.get(sessionId);
		links = session;
	}
	
	private InternetLink[][] cloneLinks()
	{
		InternetLink[][] clone = new InternetLink[links.length][];
			
		for (int i=0; i<links.length; i++)
		{
			clone[i] = Arrays.copyOf(links[i], links[i].length);
		}
		
		return clone;
	}
	
	private int translate(FederationDatacenter source)
	{	
		if(datacenterIdTranslation.containsKey(source.getId())) 
			return datacenterIdTranslation.get(source.getId());
		else{
			datacenterTraslationId++;
			datacenterIdTranslation.put(source.getId(), datacenterTraslationId);
			return datacenterTraslationId;
		}
	}

}
