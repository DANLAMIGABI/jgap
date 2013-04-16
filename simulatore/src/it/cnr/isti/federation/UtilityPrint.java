package it.cnr.isti.federation;

import org.cloudbus.cloudsim.Cloudlet;

public class UtilityPrint 
{
	public static String toString(Cloudlet c)
	{		
		return "Cloudlet["+c.getCloudletId()+"]";
		// return "Cloudlet [ID="+c.getCloudletId()+",PES="+c.getNumberOfPes()+"]";
	}

}
