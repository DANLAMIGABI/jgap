package myTestJgap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IApplicationData;
import org.jgap.IChromosome;
import org.jgap.ICloneHandler;
import org.jgap.IInitializer;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultCloneHandler;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MapGene;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;

public class Main {
	
	static boolean[] test = new boolean[3];

	public static void PrintChromosome(Chromosome chrom, myObj[] application){
		for(int i=0; i< chrom.size(); i++){
			myObj[] prov = (myObj[])chrom.getApplicationData();
			Integer index = (Integer)chrom.getGene(i).getAllele();
			System.out.println("app:  " + application[i].getID() + " -> prov: " + prov[index].getID() );
		}
	}
	
	
	public static void printPop(Genotype p){
		System.out.println("Population: ");
		for(int i=0; i< p.getPopulation().size(); i++){
			System.out.println("chromosoma " + i);
			myObj[] app = (myObj[])p.getPopulation().getChromosome(i).getApplicationData();
			PrintChromosome((Chromosome)p.getPopulation().getChromosome(i), app);
		}
		
	}
	
	public static void printPopulation(Genotype p){
		for(int i=0; i<p.getPopulation().size(); i++){
			System.out.println("Popolation " + i);	
			myObj[] prov = (myObj[])p.getPopulation().getChromosome(i).getApplicationData();
			for(int j=0; j<p.getPopulation().getChromosomes().size(); j++){
				System.out.println("  ch: " + j);
				for(int k=0; k<p.getPopulation().getChromosomes().get(j).size(); k++){
					Integer allele = (Integer) p.getPopulation().getChromosome(j).getGene(k).getAllele();
					System.out.println("    id: " + prov[allele].getID());
				}
			}
		}
	}
	
	
	public static void Test1( Configuration conf, int target, myObj[] application, myObj provider[] )
	throws InvalidConfigurationException{
		FitnessFunction myfunz = new myFit(target);
		
		conf.setFitnessFunction(myfunz);

		conf.setFitnessEvaluator(new DefaultFitnessEvaluator());

		conf.addGeneticOperator(new CrossoverOperator(conf));
		BestChromosomesSelector bestChromsSelector = new
        BestChromosomesSelector(conf, 1.0d);
		bestChromsSelector.setDoubletteChromosomesAllowed(false);
		conf.addNaturalSelector(bestChromsSelector, true);
		conf.setRandomGenerator(new StockRandomGenerator());
		conf.setEventManager(new EventManager());
		conf.addGeneticOperator(new MutationOperator(conf));

		
/*		BestChromosomesSelector bestCh = new BestChromosomesSelector(conf);
		conf.addNaturalSelector(bestCh, true);
		conf.setRandomGenerator(new StockRandomGenerator());
*/		
		
		Gene[] gene = new Gene[3]; 
		for(int i=0; i<3; i++){
			gene[i] = new myGene(conf, provider[i], application,provider);
		}
		
		Chromosome ch = new Chromosome(conf, gene);
		conf.setSampleChromosome(ch);
		conf.setPopulationSize(2);
		conf.setPreservFittestIndividual(true);
		
		Genotype population = Genotype.randomInitialGenotype(conf);
		
		for(int i=0; i< 5; i++){
			population.evolve();
			IChromosome bestSolutionSoFar = population.getFittestChromosome();
			PrintChromosome((Chromosome)bestSolutionSoFar, application);
			//printPopulation(population);
		}
		
//		population.evolve(10);
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		
//		
		for(int i=0; i< bestSolutionSoFar.size(); i++){
			myObj prov = (myObj)bestSolutionSoFar.getGene(i).getAllele();
			System.out.println("index " + (i+1) + " -> " + prov.getID() );
		}
		
		
		System.out.println("ok");
		/*MapGene[] gene = new MapGene[3];
		for(int i=0; i<gene.)
		
		gene[0] = new MapGene(conf);
		gene[0].addAllele(provider[0]);
		gene[0].setApplicationData(application);
		
		gene.addAllele(provider[0]);
		*/
		
	}
	
	public static Genotype initGenotype(Configuration conf) throws InvalidConfigurationException{
		
		int popSize = conf.getPopulationSize();
		Population pop = new Population(conf, popSize);
		Genotype ret = new Genotype(conf, pop);
		//test
			IChromosome sampleChrom = ret.getConfiguration().getSampleChromosome();
			if(ret.getConfiguration().getSampleChromosome().getApplicationData() != null){
				System.out.println("initGenotype: ApplicationData ok");
			}
			Class sampleClass = sampleChrom.getClass();
			IInitializer chromIniter = ret.getConfiguration().getJGAPFactory().
					getInitializerFor(sampleChrom, sampleClass);
		    if (chromIniter == null) {
		      throw new InvalidConfigurationException("No initializer found for class "
		          + sampleClass);
		    }
		    try {
		      for (int i = 0; i < popSize; i++) {
//		    	  IChromosome test = ret.getConfiguration().getSampleChromosome();
//		    	  ret.getPopulation().addChromosome((IChromosome)((MyChromosome)test).perform(null, null, null));
//		    	  System.out.println("caio");
		    
		        ret.getPopulation().addChromosome( (IChromosome) chromIniter.perform(sampleChrom, sampleClass, sampleChrom.getApplicationData()));
		        //ret.getPopulation().getChromosome(i).setApplicationData(ret.getConfiguration().getSampleChromosome().getApplicationData());
		      }
		      if(ret.getPopulation().getChromosome(0).getApplicationData() != null){
		    	  System.out.println("initGenotype ApplicationData ok");
		      }else
		    	  System.out.println("initGenotype applicationData null");
		    } catch (Exception ex) {
		      // Try to propagate exception, see "bug" 1661635.
		      // ----------------------------------------------
		      if (ex.getCause() != null) {
		        throw new IllegalStateException(ex.getCause().toString());
		      }
		      else {
		        throw new IllegalStateException(ex.getMessage());
		      }
		    }
		//endTest
		return ret;
		
		
	}
	
	
	public static void Test2( Configuration conf, int target, myObj[] application, myObj provider[] )
			throws InvalidConfigurationException, CloneNotSupportedException{
		
		Gene[] gene = new Gene[3];
		for(int i=0; i<gene.length; i++){
			gene[i] = new MyIntegerGene(conf);
			gene[i].setApplicationData(provider);
		}
		
		
		Chromosome ch = new Chromosome(conf,gene);
		ch.setApplicationData(application);

		conf.setSampleChromosome(ch);
		conf.setPopulationSize(4);
//		if(conf.getSampleChromosome().getApplicationData() != null){
//			System.out.println("Sample chromosome appdata ok");
//		}
		
		Genotype population = Genotype.randomInitialGenotype(conf);
		// add applicationData to chromosomes 
		for(int i =0; i<population.getPopulation().size(); i++){
			population.getPopulation().getChromosome(i).setApplicationData(application);
		}
		System.out.println("Initial population");
		printPop(population);
		
		for(int i=0; i< 5; i++){
			population.evolve();
			System.out.println("###### best evolve");
			IChromosome bestSolutionSoFar = population.getFittestChromosome();
			PrintChromosome((Chromosome)bestSolutionSoFar, application);
			System.out.println("####### end best");
		}
		
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		
		for(int i=0; i< bestSolutionSoFar.size(); i++){
			myObj[] prov = (myObj[])bestSolutionSoFar.getApplicationData();
			Integer index = (Integer)bestSolutionSoFar.getGene(i).getAllele();
			System.out.println("app:  " + application[i].getID() + " -> prov: "  + prov[index].getID() );
		}
		
	}
	
	
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int target = 20;
		
		myObj[] provider =  new myObj[3];
		provider[0] = new myObj(50, 1, -1);
		provider[1] = new myObj(85, 2, -1);
		provider[2] = new myObj(10, 3, -1);
		
		myObj[] application =  new myObj[3];
		application[0] = new myObj(70, 1, 29.19);
		application[1] = new myObj(80, 2, 33.36);
		application[2] = new myObj(90, 3, 37.53);
				
		Configuration conf = new DefaultConfiguration(); 
		FitnessFunction fit = new myFit_2(target);
		MyGenerator myrand = new MyGenerator(3);
		
		conf.setFitnessFunction(fit);
		conf.setRandomGenerator(myrand);
		
		Test2(conf, target, application, provider);
		
		
//		List<Integer> ilist = new ArrayList<Integer>();
//		for(int i=0;i<5; i++){
//			ilist.add(i);
//		}
//		System.out.println(ilist);
//		Random rand = new Random(System.currentTimeMillis());
//		for(int i=0;i<5; i++){
//			Collections.shuffle(ilist, rand);
//			System.out.println(ilist);
//		}
//		System.out.println("shuffle ");
//		System.out.println(ilist);
		
	}
	
}
