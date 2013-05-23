package it.cnr.isti.federation.metascheduler;

import java.util.List;

import org.jgap.Configuration;
import org.jgap.Population;
import org.jgap.audit.IEvolutionMonitor;
import org.jgap.eval.PopulationHistoryIndexed;


public class Monitor implements IEvolutionMonitor{

	@Override
	public void event(String arg0, int arg1, Object[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PopulationHistoryIndexed getPopulations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean nextCycle(Population arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		System.out.println("                                          aaaaaaaaaaaaaa"+  arg0.determineFittestChromosome().getFitnessValue());
//		System.out.println("                                          bbbbbbbbbbbbbb"+  arg0.determineFittestChromosome().getFitnessValueDirectly());
		if(arg0.determineFittestChromosome().getFitnessValue()> 11000)
			System.out.println(" beccata");
		if(arg0.determineFittestChromosome().getFitnessValue() > 11870)
			return false;
		return true;
	}

	@Override
	public void start(Configuration arg0) {
		// TODO Auto-generated method stub
		
	}

}
