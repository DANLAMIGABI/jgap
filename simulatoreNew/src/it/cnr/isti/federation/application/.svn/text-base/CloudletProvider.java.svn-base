package it.cnr.isti.federation.application;

import it.cnr.isti.federation.application.CloudletProfile.CloudletParams;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;

public class CloudletProvider 
{	
	private static int ID_COUNTER = 0;
	
	private static Cloudlet createCloudlet(CloudletProfile profile)
	{
		// Instantiate utilization models
		UtilizationModel uCPU = null;
		UtilizationModel uRAM = null; 
		UtilizationModel uBW = null;
		
		try
		{
			uCPU = (UtilizationModel)Class.forName(profile.get(CloudletParams.CPU_MODEL)).newInstance();
			uRAM = (UtilizationModel)Class.forName(profile.get(CloudletParams.RAM_MODEL)).newInstance();
			uBW = (UtilizationModel)Class.forName(profile.get(CloudletParams.BW_MODEL)).newInstance();
		}
		catch (Exception e)
		{
			// TODO: log the error
			e.printStackTrace();
		}
		
		
		Cloudlet c = new Cloudlet(ID_COUNTER++, 
				Integer.parseInt(profile.get(CloudletParams.LENGTH)), 
				Integer.parseInt(profile.get(CloudletParams.PES_NUM)), 
				Integer.parseInt(profile.get(CloudletParams.FILE_SIZE)),
				Integer.parseInt(profile.get(CloudletParams.OUTPUT_SIZE)),
				uCPU, uRAM, uBW);
		
		return c;
	}

	public static Cloudlet getDefault()
	{
		return createCloudlet(CloudletProfile.getDefault());
	}
	
	public static Cloudlet get(CloudletProfile profile)
	{
		return createCloudlet(profile);
	}
}
