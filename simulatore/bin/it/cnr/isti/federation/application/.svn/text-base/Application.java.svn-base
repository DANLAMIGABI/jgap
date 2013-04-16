package it.cnr.isti.federation.application;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;
import org.jgrapht.graph.Multigraph;

public class Application // extends Multigraph<ApplicationVertex, ApplicationEdge>
{
	private Multigraph<ApplicationVertex, ApplicationEdge> graph;
	private List<Cloudlet> cloudlets;
	private Hashtable<Cloudlet, ApplicationVertex> cloudletToVertex;
	private Hashtable<Vm, ApplicationVertex> vmToVertex;

	public Application() 
	{
		graph = new Multigraph<ApplicationVertex, ApplicationEdge>(ApplicationEdge.class);
		cloudlets = new ArrayList<Cloudlet>();
		cloudletToVertex = new Hashtable<Cloudlet, ApplicationVertex>();
		vmToVertex = new Hashtable<Vm, ApplicationVertex>();
	}

	
	public void addVertex(ApplicationVertex av)
	{
		cloudlets.addAll(av.getCloudlets());
		
		for (Cloudlet c: av.getCloudlets())
		{
			cloudletToVertex.put(c, av);
		}
		
		for (Vm vm: av.getVms())
		{
			vmToVertex.put(vm, av);
		}
		
			
		graph.addVertex(av);
	}
	
	public void addEdge(ApplicationEdge ed, ApplicationVertex av1, ApplicationVertex av2)
	{
		graph.addEdge(av1, av2, ed);
	}
	
	public Set<Cloudlet> allCloudletLinked(Cloudlet cloudlet)
	{
		Set<Cloudlet> set = new HashSet<Cloudlet>();
		
		// adds all the cloudlets in the same vertex
		ApplicationVertex av = this.getVertexForCloudlet(cloudlet);
		set.addAll(av.getCloudlets());
		
		// adds the cloudlets from the connected vertex
		for (ApplicationEdge ae: graph.edgesOf(av))
		{
			ApplicationVertex source = graph.getEdgeSource(ae);
			if (source.equals(av) == false)
				set.addAll(source.getCloudlets());
			
			ApplicationVertex target = graph.getEdgeTarget(ae);
			if (target.equals(av) == false)
				set.addAll(source.getCloudlets());
		}
		
		return set;
	}
	
	public Set<ApplicationEdge> edgesOf(ApplicationVertex av1)
	{
		return graph.edgesOf(av1); 
	}
	
	public ApplicationEdge edgeBetween(ApplicationVertex av1, ApplicationVertex av2)
	{
		return graph.getEdge(av1, av2);
	}
	
	public Set<ApplicationVertex> vertexSet()
	{
		return graph.vertexSet();
	}
	
	public List<Cloudlet> getAllCloudlets()
	{
		return cloudlets;
	}
	
	public ApplicationVertex getVertexForCloudlet(Cloudlet cloudlet)
	{
		return cloudletToVertex.get(cloudlet);
	}
	
	public ApplicationVertex getVertexForVm(Vm vm)
	{
		return vmToVertex.get(vm);
	}

	public List<Vm> getAllVms()
	{
		List<Vm> list = new ArrayList<Vm>();
		
		for (ApplicationVertex av: vertexSet())
		{
			list.addAll(av.getVms());
		}
		
		return list;
	}
}
