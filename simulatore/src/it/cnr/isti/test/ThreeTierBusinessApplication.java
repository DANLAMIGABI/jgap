package it.cnr.isti.test;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.application.CloudletProfile;
import it.cnr.isti.federation.application.CloudletProvider;
import it.cnr.isti.federation.resources.VmProvider.VmType;
import it.cnr.isti.networking.SecuritySupport;

import java.util.ArrayList;

import org.cloudbus.cloudsim.Cloudlet;

/**
 * This class represents the definition of a simple three tier business application:
 * web-fronted, web-backend and database.
 * It is possible to define the cardinality of each tier at construction time.
 * @author carlini
 *
 */
public class ThreeTierBusinessApplication extends Application
{
	public ThreeTierBusinessApplication(int frontendNumber, int backendNumber, int databaseNumber)
	{
		
		// cloudlet profiles definition
		CloudletProfile profileDatabase = CloudletProfile.getDefault();
		CloudletProfile profileFronted = CloudletProfile.getDefault();
		CloudletProfile profileBackend = CloudletProfile.getDefault();

		
		// Web-fronted tier
		ArrayList<Cloudlet> frontendList = new ArrayList<Cloudlet>();
		for (int i=0; i<frontendNumber; i++)
		{
			Cloudlet c = CloudletProvider.get(profileFronted);
			frontendList.add(c);
		}
		ApplicationVertex vertexFrontend = new ApplicationVertex(frontendList, VmType.SMALL);
		
		
		// Backend tier
		ArrayList<Cloudlet> backendList = new ArrayList<Cloudlet>();
		for (int i=0; i<backendNumber; i++)
		{
			Cloudlet c = CloudletProvider.get(profileBackend);
			backendList.add(c);
		}	
		ApplicationVertex vertexBackend = new ApplicationVertex(backendList, VmType.MEDIUM);
		
			
		// Database tier
		ArrayList<Cloudlet> databaseList = new ArrayList<Cloudlet>();
		for (int i=0; i<databaseNumber; i++)
		{
			Cloudlet c = CloudletProvider.get(profileDatabase);
			databaseList.add(c);
		}	
		ApplicationVertex vertexDatabase = new ApplicationVertex(databaseList, VmType.LARGE);
		
		// Add the vertexes to the graph
		this.addVertex(vertexFrontend);
		this.addVertex(vertexBackend);
		this.addVertex(vertexDatabase);
	
		// Network
		ApplicationEdge frontToBack = new ApplicationEdge(1024, SecuritySupport.BASE, 1000);
		ApplicationEdge backToDB = new ApplicationEdge(512, SecuritySupport.BASE, 1000);
		
		this.addEdge(frontToBack, vertexFrontend, vertexBackend);
		this.addEdge(backToDB, vertexBackend, vertexDatabase);
	
	}
	
}
