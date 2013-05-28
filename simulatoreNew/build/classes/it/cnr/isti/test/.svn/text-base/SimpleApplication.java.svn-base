package it.cnr.isti.test;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.application.CloudletProvider;
import it.cnr.isti.federation.resources.VmProvider.VmType;

public class SimpleApplication extends Application
{
	public SimpleApplication()
	{
		List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
		cloudletList.add(CloudletProvider.getDefault());
		
		this.addVertex(new ApplicationVertex(cloudletList, VmType.SMALL));
	}
	
}
