package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;

import java.util.List;


import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.TournamentSelector;
import org.jgap.impl.WeightedRouletteSelector;





public class JGAPMapping {
	
//	private static MetaschedulerConfiguration qosConf;
//	private static Configuration jgapConf;
	
//	private static List<IMSProvider> providers;
//	private static IMSApplication app;
	
//	public static void init(MetaschedulerConfiguration config){
//		qosConf = config;
//		jgapConf = config.getJGAPConfiguration();
//		providers = config.providers;
//		app = config.application;
//	}
	
	public static BestSolution startMapping(IMSApplication application, List<IMSProvider> providerList, List<MSPolicy> policy, List<String> aggregationParam){
		
//		UtilityJGAP.printProviders(providers);
//		UtilityJGAP.printICApplication(app);
		BestSolution sol = new BestSolution();

		try {
			Configuration conf = new Configuration();

			conf.addNaturalSelector(new BestChromosomesSelector(conf), false);
//			 conf.addNaturalSelector(new WeightedRouletteSelector(conf), true);
			// false);
			// conf.addNaturalSelector(new TournamentSelector(a_config,
			// a_tournament_size, a_probability), false);
			conf.setEventManager(new EventManager());
			conf.addGeneticOperator(new MyCrossoverOperator(conf,2));
			/* My mutation Operator */
			conf.addGeneticOperator(new MymutationOperator(conf,10));
			conf.setFitnessEvaluator(new DefaultFitnessEvaluator());

			// make gene
			List<MSApplicationNode> nodes = application.getNodes();
			Gene[] genes = new Gene[nodes.size()];
			for (int i = 0; i < nodes.size(); i++) {
				genes[i] = new CGene(conf);
			}
			Chromosome sampleCh = null;

			sampleCh = new Chromosome(conf, genes);

			conf.setSampleChromosome(sampleCh);
			conf.setPopulationSize(Constant.POP_SIZE);
			conf.setRandomGenerator(new NewCRandGenerator(providerList.size()));

			CObjectiveFitness fitness = new CObjectiveFitness(application,
					providerList, policy);

			conf.setFitnessFunction(fitness);

			Genotype.setStaticConfiguration(conf);

			Genotype population = Genotype.randomInitialGenotype(conf);
			System.out.println("Startin metascheduler evolution ....");
			List<String> message = population.evolve(new Monitor(5,50));
			System.out.println("Stoppin metascheduler evolution ...");
			for( String s : message){
				System.out.println(s);
			}
			IChromosome bestSolutionSoFar = population.getPopulation().determineFittestChromosome();
//			System.out.println("asfdfdfdfafdsfds " + bestSolutionSoFar.getGene(0).getAllele());
			sol.setSolution(bestSolutionSoFar,nodes);
			sol.setFit(bestSolutionSoFar.getFitnessValue());
			Configuration.reset();

		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sol;
		
	}

}
