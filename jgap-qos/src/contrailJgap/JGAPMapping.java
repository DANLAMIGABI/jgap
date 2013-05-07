package contrailJgap;

import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

import cApplicationIface.ICApplication;
import cProviderIface.ICProvider;

import test.UtilityJGAP;




public class JGAPMapping {
	
	private static ConfigurationJGAPQos qosConf;
	private static Configuration jgapConf;
	
	private static List<ICProvider> providers;
	private static ICApplication app;
	
	public static void init(ConfigurationJGAPQos config){
		qosConf = config;
		jgapConf = config.getJGAPConfiguration();
		providers = config.providers;
		app = config.application;
	}
	
	public static BestSolution startMapping(){
		
		UtilityJGAP.printProviders(providers);
		UtilityJGAP.printICApplication(app);
		
		Genotype.setStaticConfiguration(jgapConf);
		BestSolution sol = new BestSolution();
		try {
			Genotype population = Genotype.randomInitialGenotype(jgapConf);
			//TEST
			//System.out.println(UtilityJGAP.printChromosome(population.getPopulation().getChromosome(0),providers,app));
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		try {
			Genotype population = Genotype.randomInitialGenotype(jgapConf);
			for(int i=0; i<qosConf.getEvolutionSize(); i++){
				population.evolve();
				//printPop(population, app, provList);
//				System.out.println("###### best evolve");
//				IChromosome bestSolutionSoFar = population.getFittestChromosome();
				//PrintChromosome((Chromosome)bestSolutionSoFar, qosConf.get, provList);
//				System.out.println("\tfit: " + bestSolutionSoFar.getFitnessValue());
//				System.out.println("####### end best");
				//printPop(population, app, provList);
			}
			IChromosome bestSolutionSoFar = population.getFittestChromosome();
			System.out.println("###### BEST SOLO JGAP.MAPPING #####");
			System.out.println("FIT: " + bestSolutionSoFar.getFitnessValue());
			System.out.println(UtilityJGAP.printChromosome(bestSolutionSoFar,providers,app));
			System.out.println("###################################");
			sol.setSolution(bestSolutionSoFar);
			
			
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sol;
		
	//	return null;
	}

}
