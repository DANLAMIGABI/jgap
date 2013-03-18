package test;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;

import contrailJgap.CFitnessEasy;
import contrailJgap.CGene;
import contrailJgap.CRandGenerator;

import cApplication.CApplication;
import cApplicationIface.ICApplication;
import cProvider.CProvider;
import cProviderIface.ICProvider;

public class Main {
	
	static String[] provPlaces = new String[]{
		"Italia","Francia","USA","Germania","Svezia","Russia"
	};
	
	static String[] appPlaces = new String[]{
		"Germania","Italia","Svezia"
	};
	
	static Integer[] costs = new Integer[]{
		90,65,150,75,120,80
	};
	
	static Integer[] budgets = new Integer[]{
		100,70,85
	};
	
	
	public static CApplication[] makeApp(int size){
		CApplication[] appList = new CApplication[size];
		for(int i=0; i< appList.length; i++){
			appList[i] = new CApplication(i+1, appPlaces[i%appPlaces.length] , 
					budgets[i%budgets.length], null, null, null);
		}
		return appList;
	}
	
	
	public static CProvider[] makeProviderList(int size){
		CProvider[] provlist = new CProvider[size];
		
		for(int i=0; i<provlist.length; i++){
			provlist[i] = new CProvider(provPlaces[i%provPlaces.length], costs[i%costs.length], 101+i, null, null, null);
		}
		return provlist;	
	}
	
//	public static Gene[] makeGeneList(int size,Configuration conf) throws InvalidConfigurationException{
//		Gene[] gene = new Gene[size];
//		for(int i=0; i<gene.length; i++){
//			gene[i] = new CGene(conf);
//			gene[i].setApplicationData(arg0)
//		}
//	}
	
	public static void PrintChromosome(Chromosome chrom, ICApplication[] app){
		for(int i=0; i< chrom.size(); i++){
			ICProvider[] prov = (ICProvider[])chrom.getGene(i).getApplicationData();
			Integer index = (Integer)chrom.getGene(i).getAllele();
			System.out.println("\t\tA." + app[i].getID()  
					+"\t\tP." + prov[index].getID() 
					+ "\n\t\tB: "+ app[i].getBudget() 
					+ "\tC: " + prov[index].getCost());
		}
	}
	
	public static void printPop(Genotype p){
		System.out.println("Population: ");
		for(int i=0; i< p.getPopulation().size(); i++){
			System.out.println("\tchromosoma " + i);
			ICApplication[] app = (ICApplication[])p.getPopulation().getChromosome(i).getApplicationData();
			PrintChromosome((Chromosome)p.getPopulation().getChromosome(i), app);
		}
		
	}
	
	public static void printApp(ICApplication[] app){
		for(int i=0; i< app.length; i++){
			System.out.println("App." + app[i].getID()  + 
					"\t\t" + app[i].getPlace() + 
					"\t" + app[i].getBudget());
		}
		System.out.println();
	}
	
	public static void printProviderList(ICProvider[] prov){
		for(int i=0; i<prov.length; i++){
			System.out.println("P."+prov[i].getID() + 
					"\t\t" + prov[i].getPlace() +
					"\t\t" + prov[i].getCost());
		}
		System.out.println();
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		
		
		
		ICProvider[] provList = (ICProvider[])makeProviderList(Constant.PROV_SIZE);
		/* only one application */
		ICApplication[] app = (ICApplication[]) makeApp(Constant.APP_SIZE);
		
		printProviderList(provList);
		printApp(app);
		
		Configuration conf = new DefaultConfiguration();
		CFitnessEasy fitFunz = new CFitnessEasy(Constant.TARGET);
		CRandGenerator rand = new CRandGenerator(provPlaces.length);
		
		conf.setFitnessFunction(fitFunz);
		conf.setRandomGenerator(rand);
		
		// gene configuration
		Gene[] gene = new Gene[app.length];
		for(int i=0; i<gene.length; i++){
			gene[i] = new CGene(conf);
			gene[i].setApplicationData(provList);
		}
		// chromosome configuration
		Chromosome sampleCh = new Chromosome(conf, gene);
		sampleCh.setApplicationData(appPlaces);
		
		conf.setSampleChromosome(sampleCh);
		conf.setPopulationSize(Constant.POP_SIZE);
		
		Genotype population = Genotype.randomInitialGenotype(conf);
		// aggiunta application data, controllare xke l'initial conf non la mantiene
		for(int i=0; i<population.getPopulation().size(); i++){
			population.getPopulation().getChromosome(i).setApplicationData(app);
		}
		
		printPop(population);
		
		//evlolution
		for(int i=0; i<Constant.EVOLUTION_SIZE; i++){
			population.evolve();
			System.out.println("###### best evolve");
			IChromosome bestSolutionSoFar = population.getFittestChromosome();
			PrintChromosome((Chromosome)bestSolutionSoFar, app);
			System.out.println("\tfit: " + bestSolutionSoFar.getFitnessValue());
			System.out.println("####### end best");
		}
		
		
		
		
		
		
	}

}
