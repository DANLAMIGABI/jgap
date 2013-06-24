package it.cnr.isti.federation.metascheduler.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.cloudbus.cloudsim.Cloudlet;
import org.ow2.contrail.common.ParserManager;
import org.ow2.contrail.common.exceptions.MalformedOVFException;
import org.ow2.contrail.common.implementation.application.ApplianceDescriptor;
import org.ow2.contrail.common.implementation.application.ApplicationDescriptor;
import org.ow2.contrail.federation.federationcore.aem.mapping.ApplicationGraph;
import org.ow2.contrail.federation.federationcore.aem.mapping.ContrailEdge;
import org.ow2.contrail.federation.federationcore.utils.GraphUtils;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.application.CloudletProfile;
import it.cnr.isti.federation.application.CloudletProvider;
import it.cnr.isti.federation.resources.VmProvider.VmType;
import it.cnr.isti.networking.SecuritySupport;

public class ApplicationFromOVF {
	static ApplicationVertex createSimulationVertex(int cloudletNumber, VmType vmtype){
		ArrayList<Cloudlet> frontendList = new ArrayList<Cloudlet>();
		CloudletProfile profile = CloudletProfile.getDefault();
		
		for (int i=0; i < cloudletNumber; i++)
		{
			Cloudlet c = CloudletProvider.get(profile);
			frontendList.add(c);
		}
		ApplicationVertex vertex = new ApplicationVertex(frontendList, vmtype);
		return vertex;
	}
	
	static ApplicationEdge createAppEdgeFromContrailEdge(ContrailEdge e){
		System.out.println(e.getEdgeName() + " " + e.getWeight());
		return new ApplicationEdge(1024, SecuritySupport.BASE, 1000);
	}
	

	public static Application getApplicationFromOVF(String ovfFile){
		ParserManager pm = null;
		 final String ovf = "ovf-MultipleVM-cnr-ubntServer.ovf";
		//final String ovf = "contrail_petstore.xml";
		try {
			pm = new ParserManager(ovf);
		} catch (NumberFormatException | XPathExpressionException
				| DOMException | IOException | ParserConfigurationException
				| SAXException | URISyntaxException | MalformedOVFException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		ApplicationDescriptor ad = pm.getApplication();
		ApplicationGraph<String, ContrailEdge> appGraphDesc = GraphUtils.MakeGraph(-1, ad);
		
		Application simApp = new Application();
		
		System.out.println(appGraphDesc.name);
		Collection<ApplianceDescriptor> appl =  appGraphDesc.getAppliances();
		
		for (ApplianceDescriptor a : appl){
			System.out.println("                                     required memory: " + a.getRequiredMemory().getAllocationUnits());
			ApplicationVertex av = null;
			long ram = a.getRequiredMemory().getVirtualQuantity()/1024;
			if((ram) <= 1740){
				 av = createSimulationVertex(3, VmType.SMALL);
			}else if( ram <= 3840){
				 av = createSimulationVertex(3, VmType.MEDIUM);
			}else if (ram <= 7680) {
				 av = createSimulationVertex(3, VmType.LARGE);
			}else{
				 av = createSimulationVertex(3, VmType.XLARGE);
			}
			av = createSimulationVertex(3, VmType.XLARGE);
			av.setName(a.getID());
			simApp.addVertex(av);
		}
		
		Set<ApplicationVertex> avSet = simApp.vertexSet();
		ApplicationVertex[] av1 = new ApplicationVertex[avSet.size()];
		
		int i=0;
		for (ApplicationVertex a : avSet){
			av1[i] = a;
			i++;
		}
		// ApplicationVertex[] av2 = a1; 
		
		ApplianceDescriptor[] a1 = new ApplianceDescriptor[appl.size()];
		i=0;
		for (ApplianceDescriptor a :appl){
			a1[i] = a;
			i++;
		}
		
		for (i=0; i< a1.length-1; i++){
			for (int j=i+1; j< a1.length; j++){
				ContrailEdge e = appGraphDesc.getEdge(a1[i].getID(), a1[j].getID());
				if (e != null){
					ApplicationEdge ae = createAppEdgeFromContrailEdge(e);
					simApp.addEdge(ae, av1[i], av1[j]); // devo avere un mapping tra i vertici nelle due rappresentazioni
				}
			}
			
		}
		
		String res = new String();
		
////		System.out.println(appGraphDesc);
////		System.out.println(simApp);
//		System.out.println(simApp.getAllVms().get(0).getSize());
//		System.out.println(simApp.getAllVms().get(1).getSize());
		return simApp;
	}
	
}
