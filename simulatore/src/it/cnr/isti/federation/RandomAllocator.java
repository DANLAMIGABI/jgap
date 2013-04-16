package it.cnr.isti.federation;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.cloudbus.cloudsim.Cloudlet;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.networking.InternetEstimator;
import it.cnr.isti.networking.NetEstimator;

public class RandomAllocator extends AbstractAllocator
{

	public RandomAllocator(MonitoringHub monitoring, InternetEstimator netEstimator)
	{
		super(monitoring, netEstimator);
	}

	@Override
	public MappingSolution findAllocation(Application application)
	{
		
		List<FederationDatacenter> dcs = getMonitoring().getView();
		MappingSolution solution = new MappingSolution();
			
		// for all the vertex of the graph
		Set<ApplicationVertex> vertexes =  application.vertexSet();
		for (ApplicationVertex vertex : vertexes)
		{
			List<Cloudlet> cloudlets = vertex.getCloudlets();
				
			for (Cloudlet c: cloudlets)
			{
				// choose a random datacenter
				Random r = new Random();
				FederationDatacenter fd = dcs.get(r.nextInt(dcs.size()));
				solution.set(c, fd);
			}
		}
		
		return solution;
	}
}
