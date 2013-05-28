package it.cnr.isti.federation.metascheduler.test;

import java.util.ArrayList;

import org.cloudbus.cloudsim.Cloudlet;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.application.CloudletProfile;
import it.cnr.isti.federation.application.CloudletProvider;
import it.cnr.isti.federation.resources.VmProvider.VmType;
import it.cnr.isti.networking.SecuritySupport;

public class BusinessApplication extends Application{
	
	public BusinessApplication(int userID, int vertexNumber){
		
		ApplicationVertex precVertex = null;
		
		VmType[] type = { VmType.SMALL, VmType.MEDIUM, VmType.LARGE};
		
		for(int i=0; i<vertexNumber; i++){
			CloudletProfile profile = CloudletProfile.getDefault();
			
			ArrayList<Cloudlet> vertexList = new ArrayList<Cloudlet>();

			Cloudlet c = CloudletProvider.get(profile);
			c.setUserId(userID);
			vertexList.add(c);
			ApplicationVertex vertex = new ApplicationVertex(vertexList, type[i%type.length]);
			this.addVertex(vertex);
			if(precVertex != null){
				ApplicationEdge network = new ApplicationEdge(1024, SecuritySupport.BASE, 1000);
				this.addEdge(network, precVertex, vertex);
			}
			precVertex = vertex;
		}
	}

}
