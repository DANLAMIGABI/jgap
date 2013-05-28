package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;

import java.util.ArrayList;
import java.util.List;



import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.WeightedRouletteSelector;


public class MetaschedulerConfiguration {
	
	private String ID;
	private String name;
	
	private int populationSize;
	private int evolutionSize;
	private FitnessFunction objectiveFunction;
	
	private Configuration jgapConfiguration;
	
	//TEMPORANEO
	public List<IMSProvider> providers;
	public IMSApplication application;
	
	
	
	public MetaschedulerConfiguration(){
		this("", "");
	}
	
	public MetaschedulerConfiguration(String id, String name){
		ID = id;
		this. name = name;
		evolutionSize =0;
		populationSize=0;
		jgapConfiguration = null;
	}
	
	public void setFitnessFunction(FitnessFunction objectiveFunciton){
		if(objectiveFunciton != null)
			this.objectiveFunction = objectiveFunciton;
	}
	public void setPopulationSize(int popSize){
		if(popSize >0)
			populationSize = popSize;
	}
	public void setEvolutionSize(int size){
		evolutionSize = size;
	}
	
	public int getEvolutionSize(){
		return evolutionSize;
	}
	public int getPopulationSize(){
		return populationSize;
	}
	
	public Configuration getJGAPConfiguration(){
		return jgapConfiguration;
	}
	
	public void setConfiguration(IMSApplication application, Integer randomLimit) throws InvalidConfigurationException {
		jgapConfiguration = setJGAPConfiguration();
		Gene[] genes = getGenes(application);
		Chromosome sampleCh = new Chromosome(jgapConfiguration, genes);
//		System.out.println("LOG: setConfiguration test pringChromosome \n   " + UtilityJGAP.printChromosome(sampleCh,providers,application));
		
		//jgapConfiguration.addNaturalSelector(new WeightedRouletteSelector(), true);
		jgapConfiguration.setSampleChromosome(sampleCh);
		jgapConfiguration.setPopulationSize(populationSize);
		jgapConfiguration.setFitnessFunction(objectiveFunction);
		jgapConfiguration.setRandomGenerator(new NewCRandGenerator(randomLimit));
	}
	
	private Gene[] getGenes(IMSApplication application){
		List<MSApplicationNode> nodes = application.getNodes();
		Gene[] genes = new Gene[nodes.size()];
		for(int i =0; i<nodes.size(); i++){
			try {
				genes[i] = new CGene(jgapConfiguration);
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return genes;
		
	}
	
	private Configuration setJGAPConfiguration() throws InvalidConfigurationException{
		org.jgap.Configuration conf = new org.jgap.Configuration();
		conf.addNaturalSelector(new BestChromosomesSelector(conf),false);
		conf.setEventManager(new EventManager());
		conf.addGeneticOperator(new CrossoverOperator(conf));
		/* My mutation Operator */
		conf.addGeneticOperator(new MymutationOperator(conf));
		conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
		return conf;
	}
		

}
