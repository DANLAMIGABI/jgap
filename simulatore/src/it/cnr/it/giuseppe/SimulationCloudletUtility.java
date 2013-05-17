package it.cnr.it.giuseppe;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

public class SimulationCloudletUtility {
	
	public static Cloudlet getDefautlCloudlet(int brokerId, int pesNumber, int vmid){
		// Cloudlet properties
        int id = 0;
        long length = 4000;
        long fileSize = 300;
        long outputSize = 300;
        UtilizationModel utilizationModel = new UtilizationModelFull();

        Cloudlet cloudlet = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        cloudlet.setUserId(brokerId);
        cloudlet.setVmId(vmid);
        return cloudlet;
        		
		
	}

}
