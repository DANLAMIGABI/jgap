package it.cnr.isti.federation.metascheduler;

import java.util.List;

import org.jgap.Configuration;
import org.jgap.Population;
import org.jgap.audit.IEvolutionMonitor;
import org.jgap.eval.PopulationHistoryIndexed;


public class Monitor implements IEvolutionMonitor{
	
	private int maxIteration=0;
	private int iterationCount=0;
	private int notModifiedCount =0;
	private double maxFit=0;
	
	private long startMillisec;
	private long lastCheckMillisec;
	
	private int checkIntervalSeconds;
	private int firstCheckIntervalSeconds;
	
	public Monitor(int maxIteration, int checkIntervalSeconds){
		this.maxIteration = maxIteration;
		this.checkIntervalSeconds = checkIntervalSeconds;
		
	}

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
		double fit = arg0.determineFittestChromosome().getFitnessValue();
		if(System.currentTimeMillis() - startMillisec > checkIntervalSeconds*1000){
			arg1.add("MaxTimeExecution reached " + fit );
			arg1.add("Max Fitness Evaluated:" + maxFit);
			arg1.add("ExecutionTime: " + (System.currentTimeMillis()-startMillisec)/1000);
			return false;
		}
//		if(notModifiedCount > maxIteration){
//			arg1.add("MaxIteration reached. fit: " + fit);
//			arg1.add("Max Fitness Evaluated:" + maxFit);
//			arg1.add("ExecutionTime: " + (System.currentTimeMillis()-startMillisec)/1000);
//			return false;
//		}
		if( fit > maxFit){
			maxFit = fit;
			notModifiedCount=0;
			lastCheckMillisec = System.currentTimeMillis();
		}else if( fit == maxFit)
			notModifiedCount++;
		System.out.println("         fit monitor: " + fit);
		return true;
	}

	@Override
	public void start(Configuration arg0) {
		lastCheckMillisec = System.currentTimeMillis();
		startMillisec = System.currentTimeMillis();
	}

}
