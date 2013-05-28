package it.cnr.isti.federation;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationEdge;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.networking.InternetEstimator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

public class GreedyRollbackAllocatorAppliance extends AbstractAllocator
{
	public GreedyRollbackAllocatorAppliance(MonitoringHub monitoring, InternetEstimator netEstimator)
	{
		super(monitoring, netEstimator);
	}

	@Override
	public MappingSolution findAllocation(Application application)
	{
		System.out.println("\nThere are "+application.getAllCloudlets().size()+" Cloudlets to allocate.");
		MappingSolution toRet = new MappingSolution();
		
		// keep trace of the status of the assignment
		List<ApplicationVertex> assigned = new ArrayList<ApplicationVertex>();
		Hashtable<ApplicationVertex, FederationDatacenter> solution = new Hashtable<ApplicationVertex, FederationDatacenter>();
		
		// Initialize the netestimator session
		Long netSession = netEstimator.createSession();
		
		// reset the visited providers for the vertex
		List<FederationDatacenter> visitedDatacenter = new ArrayList< FederationDatacenter>(monitoring.getView().size()); 
		
		// main loop: until all the AV  are assigned
		ApplicationVertex av = null;
		while ((av = takeNonAssigned(assigned, application.vertexSet())) != null)
		{
			// internal loop: until there are unvistited providers or the AV is assigned
			boolean is_assigned = false;
			
			while (visitedDatacenter.size() < monitoring.getView().size() && (is_assigned == false))
			{
				System.out.println("Dc visited: "+visitedDatacenter.size());
				
				// pick a datacenter 
				FederationDatacenter fd = pickDatacenter(monitoring.getView(), visitedDatacenter);
				
				boolean ok_dc = true;
				for (Cloudlet c: av.getCloudlets())
				{
					Vm vm = av.getAssociatedVm(c);
					if (fd.getVmAllocationPolicy().allocateHostForVm(vm) == false)
						ok_dc = false;
				}
							
				// check the datacenter and the connection using the netestimator	
				boolean ok_net = this.checkNetwork(av, fd, solution, application, netSession);
			
				System.out.println("Checking net "+av+" to "+fd+" : "+ok_dc);
			
			
				if (ok_net) // host found
				{
					solution.put(av, fd);
					assigned.add(av);
					is_assigned = true;
					System.out.println(av+" assigned to "+fd);
					
					//reset the visited datacenters
					visitedDatacenter.clear();
				}
				else // host not found
				{
					visitedDatacenter.add(fd);
				}
			}
			
			if (is_assigned == false) // the av cannot be received by any provider
			{
				if (assigned.size() > 0)
				{
					// remove the cloudlet from the set of the assigned
					ApplicationVertex removed = assigned.remove(assigned.size() - 1);
					FederationDatacenter fdRemoved = solution.get(removed);
					
					// deallocate network
					for (ApplicationVertex other: application.vertexSet())
					{
						if ((application.edgeBetween(removed, other) != null)
								&& solution.contains(other))
						{
							netEstimator.deallocateLink(fdRemoved, solution.get(other), 
									application.edgeBetween(removed, other), application);
						}
					}
					
					for (Cloudlet c: removed.getCloudlets())
					{
						fdRemoved.getVmAllocationPolicy().deallocateHostForVm(removed.getAssociatedVm(c));
					}

					// remove the assignemnt in the solution
					solution.remove(removed);
					
					// clean up the visited datacenters
					visitedDatacenter.clear();
					visitedDatacenter.add(fdRemoved);
										
					System.out.println("Rollback. "+removed+" removed from "+fdRemoved);
				}
				else // failing
				{
					System.out.println("Mapping failed.");
					toRet.setValid(false);
					return toRet;
				}
			}
			
		}
	
		netEstimator.consolidateAllocationSession(netSession);
		
		
		for (ApplicationVertex vertex: solution.keySet())
		{
			for (Cloudlet c: vertex.getCloudlets())
			{
				toRet.set(c, solution.get(vertex));
			}
		}
		
		toRet.setValid(true);
		return toRet;
	}
	
	

	/**
	 * returns null if all application vertex are assigned
	 */
	private ApplicationVertex takeNonAssigned(List<ApplicationVertex> assigned, Set<ApplicationVertex> all)
	{
		for (ApplicationVertex a: all)
		{
			if (assigned.contains(a) == false)
				return a;
		}
		
		return null;
	}
	
	private FederationDatacenter pickDatacenter(List<FederationDatacenter> dcs, List<FederationDatacenter> visitedDatacenter)
	{
		for (FederationDatacenter fd: dcs)
		{
			if (visitedDatacenter.contains(fd) == false)
				return fd;
		}
		
		return null; // should never get here.
	}

	/**
	 * Check the network
	 * @return
	 */
	private boolean checkNetwork(ApplicationVertex sourceVertex, FederationDatacenter sourceFd, Hashtable<ApplicationVertex, FederationDatacenter> solution, Application application, long netSession)
	{
		// two cases:
		// -- the vertexes are in the same datacenter
		// -- the vertexes are in different datacenter
			
		for (ApplicationVertex av : solution.keySet())
		{
			if ((av.equals(sourceVertex) == false) && (application.edgeBetween(sourceVertex, av) != null))
			{

				if (sourceFd.equals(solution.get(av))) // same dc
				{
					// then OK, do nothing
				}
				else
				{
					ApplicationEdge edge = application.edgeBetween(sourceVertex, av);
					boolean result = netEstimator.allocateLink(netSession, sourceFd, solution.get(av), edge, application);
					if (result == false)
						return result;
				}
			}
		}
			
		return true;
	}
}
