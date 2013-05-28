package it.cnr.isti.federation;

import it.cnr.isti.federation.resources.FederationDatacenter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.cloudbus.cloudsim.Cloudlet;

public class MappingSolution 
{
	private HashMap<Cloudlet, FederationDatacenter> mapping;
	private boolean valid = true;
	
	public MappingSolution()
	{
		mapping = new HashMap<Cloudlet, FederationDatacenter>();
	}

	public HashMap<Cloudlet, FederationDatacenter> getMapping() 
	{
		return mapping;
	}
	
	public void set(Cloudlet cloudlet, FederationDatacenter dc)
	{
		mapping.put(cloudlet, dc);
	}
	
	public FederationDatacenter uset(Cloudlet cloudlet)
	{
		return mapping.remove(cloudlet);
	}
	
	public boolean isValid()
	{
		return valid;
	}
	
	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "MappingSolution ["
				+ (mapping != null ? toString(mapping.entrySet(), maxLen)
						: null) + "]";
	}
	
	/**
	 * toString helper
	 */
	private String toString(Collection<Entry<Cloudlet, FederationDatacenter>> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			Entry<Cloudlet, FederationDatacenter> n = (Entry<Cloudlet, FederationDatacenter>) iterator.next();
			builder.append(UtilityPrint.toString(n.getKey())+"->"+n.getValue());
		}
		builder.append("]");
		return builder.toString();
	}
}
