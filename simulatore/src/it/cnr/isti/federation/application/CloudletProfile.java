package it.cnr.isti.federation.application;

import java.util.HashMap;
import java.util.Map;

public class CloudletProfile 
{
	public enum CloudletParams
	{
		LENGTH("4000"),
		PES_NUM("1"),
		FILE_SIZE("300"),
		OUTPUT_SIZE("300"),
		CPU_MODEL("org.cloudbus.cloudsim.UtilizationModelFull"),
		RAM_MODEL("org.cloudbus.cloudsim.UtilizationModelFull"),
		BW_MODEL("org.cloudbus.cloudsim.UtilizationModelFull");
		
		private String def;
		
		private CloudletParams(String def)
		{
             this.def = def;
		}
	}
	
	private  Map<CloudletParams, String> data;
	
	private CloudletProfile()
	{
		data = new HashMap<CloudletParams, String>();
		
		for (CloudletParams p : CloudletParams.values())
		{
			data.put(p, p.def);
		}
	}

	public static CloudletProfile getDefault()
	{
		return new CloudletProfile();
	}
	
	public String get(CloudletParams par)
	{
		return data.get(par);
	}
	
	public void set(CloudletParams par, String value)
	{
		data.put(par, value);
	}
		
}
