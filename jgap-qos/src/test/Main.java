package test;

import static java.lang.System.out;

import java.lang.reflect.Method;
import java.util.Vector;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.WeightedRouletteSelector;

import contrailJgap.CFitnessEasy;
import contrailJgap.CGene;
import contrailJgap.CRandGenerator;
import contrailJgap.NewCRandGenerator;

import cApplication.CApplication;
import cApplication.CApplicationComputing;
import cApplication.CApplicationNetwork;
import cApplication.CApplicationNode;
import cApplication.CApplicationStorage;
import cApplicationIface.ICApplication;
import cPolicy.CPolicy;
import cPolicy.TestPolicy;
import cProvider.CProvider;
import cProvider.CProviderComputing;
import cProvider.CProviderNetwork;
import cProvider.CProviderStorage;
import cProviderIface.ICProvider;

public class Main {
	
	static String[] provPlaces = new String[]{
		"Italia","Svezia","Italia","Italia","Svezia","Italia"
	};
	static Integer[] costs = new Integer[]{
		85,65,150,80,120,75
		
	};
	static Integer[] ram = new Integer[]{
		1000,5000,6000,2000,1000,3000
	};
	static Integer[] bandwidth = new Integer[]{
		1000,1000,100000,2000,10000,1000
	};
	static Integer[] storeSize = new Integer[]{
		20,30,100,50,80,35
	};
 	
	static String[] appPlaces = new String[]{
		"Italia","Italia","Svezia"
	};
	
	static Integer[] budgets = new Integer[]{
		100,70,85
	};
	
		
	public static CApplicationNode[] makeNodes(int size){
		
		CApplicationComputing comp = new CApplicationComputing(null, 0, -1, 1000);
		CApplicationNetwork net = new CApplicationNetwork(1000, 0, null, -1);
		CApplicationStorage store =  new  CApplicationStorage(0, 20, 0, null);
		
		CApplicationNode[] nodes = new CApplicationNode[size];
		
		for(int i=0; i< nodes.length; i++){
			nodes[i] = new CApplicationNode(i, comp, store, net);
		}
		return nodes;
	}
	
	
	public static CProvider[] makeProviderList(int size){
		CProvider[] provlist = new CProvider[size];
		CProviderComputing comp; 
		CProviderNetwork net; 
		CProviderStorage store; 
		for(int i=0; i<provlist.length; i++){
			comp = new CProviderComputing();
			net = new CProviderNetwork();
			store =  new CProviderStorage();
			comp.setRam(ram[i%ram.length]);
			net.setBandwidth(bandwidth[i%bandwidth.length]);
			store.setAmount(storeSize[i%storeSize.length]);
			provlist[i] = new CProvider(provPlaces[i%provPlaces.length], costs[i%costs.length], 101+i, comp, store, net);
			
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
	
	public static void PrintChromosome(Chromosome chrom, ICApplication app, ICProvider[] provList){
		CApplicationNode[] nodes = app.getNodes();
		for(int i=0; i< chrom.size(); i++){
			Integer index = (Integer)chrom.getGene(i).getAllele();
				out.println(String.format("%7s%d.%d -> %3s%d %3s %3.2f %3s %3.2f", 
						"A.", app.getID(), nodes[i].getID(), "P.", provList[index].getID(), 
						"B:", app.getBudget(), "C:", provList[index].getCost() ));
				
//			System.out.println(String.format("%7s%d.%d -> %3s%d %3s %3.2f %3s %3.2f", 
//					"A.", app.getID(),  "P.", provList[index].getID(), 
//					"B:", app.getBudget(), "C:", provList[index].getCost() ));
			}
	}
	
	public static void printPop(Genotype p, ICApplication app, ICProvider[] provList){
		CApplicationNode[] nodes = app.getNodes();
		System.out.println("Population: ");
		for(int i=0; i< p.getPopulation().size(); i++){
			System.out.println(String.format("%3s%d %-5f", "ch", i, p.getPopulation().getChromosome(i).getFitnessValue() ));
//			System.out.println("\tchromosoma " + i);
			PrintChromosome((Chromosome)p.getPopulation().getChromosome(i), app, provList );
		}
		
	}
	
	public static void printApp(ICApplication app){
		CApplicationNode[] nodes = app.getNodes();
		for(int i=0; i< nodes.length; i++){
			System.out.println(String.format("App.%d.%-7d %-10s %-10.2f %-10d %-10d",
					app.getID(), nodes[i].getID(), app.getPlace(), app.getBudget(), 
					nodes[i].getStorage().getAmount(), nodes[i].getComputing().getRam()));
//			System.out.println("App." + app[i].getID()  + 
//					"\t\t" + app[i].getPlace() + 
//					"\t" + app[i].getBudget());
		}
		System.out.println();
	}
	
	public static void printProviderList(ICProvider[] prov){
		System.out.println(String.format("%-12s%-10s%-10s%-10s%-10s%-10s", "ID", "Place","Cost", "Net","Store","Comp"));
		for(int i=0; i<prov.length; i++){
			System.out.println(String.format("P.%-10d%-10s%-10.2f%-10d%-10d%-10d", prov[i].getID(), prov[i].getPlace(), prov[i].getCost(),
					prov[i].getNetwork().getBandwidth(),prov[i].getStorage().getAmount(),prov[i].getComputing().getRam()));
//			System.out.println("P."+prov[i].getID() + 
//					"\t\t" + prov[i].getPlace() +
//					"\t\t" + prov[i].getCost());
		}
		System.out.println();
	}
	
	
	public static Configuration getDefaultConf(){
		return new DefaultConfiguration();
	}
	public static Configuration getConfiguration() throws InvalidConfigurationException{
		Configuration conf = new Configuration();
		//conf.addNaturalSelector(new WeightedRouletteSelector(conf),true);
		conf.addNaturalSelector(new BestChromosomesSelector(conf),false);
		conf.setEventManager(new EventManager());
		conf.addGeneticOperator(new CrossoverOperator(conf));
		conf.addGeneticOperator(new MutationOperator(conf));
		conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
		return conf;
	}
	
	
	
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		ICProvider[] provList = (ICProvider[])makeProviderList(Constant.PROV_SIZE);
		/* only one application */
		CApplicationNode[] nodes = makeNodes(Constant.APP_SIZE);
		ICApplication app = new CApplication(555, "Italia", 100, nodes );
		
		Vector<CPolicy> policy = new  Vector<CPolicy>();
		policy.add(MakePolicy.makeCostPolicy());
		//policy.add(MakePolicy.makePlacePolicy());
		
		
		printProviderList(provList);
		printApp(app);
		
		//Configuration conf = getDefaultConf();
		
		Configuration conf = getConfiguration();
		
		NewCRandGenerator rand = new NewCRandGenerator(provPlaces.length);
		
		conf.setRandomGenerator(rand);
		
		// gene configuration
		Gene[] gene = new Gene[nodes.length];
		for(int i=0; i<gene.length; i++){
			gene[i] = new CGene(conf);
		}
		// chromosome configuration
		Chromosome sampleCh = new Chromosome(conf, gene);
		
		conf.setSampleChromosome(sampleCh);
		conf.setPopulationSize(Constant.POP_SIZE);
		CFitnessEasy fitFunz = new CFitnessEasy(0.9, app, provList, policy);
		conf.setFitnessFunction(fitFunz);
		
		Genotype.setStaticConfiguration(conf);
		Genotype population = Genotype.randomInitialGenotype(conf);
		// aggiunta application data, controllare xke l'initial conf non la mantiene
//		for(int i=0; i<population.getPopulation().size(); i++){
//			population.getPopulation().getChromosome(i).setApplicationData(app);
//		}
		
		printPop(population, app, provList);
		
		//evlolution
		for(int i=0; i<Constant.EVOLUTION_SIZE; i++){
			population.evolve();
			//printPop(population, app, provList);
			System.out.println("###### best evolve");
			IChromosome bestSolutionSoFar = population.getFittestChromosome();
			PrintChromosome((Chromosome)bestSolutionSoFar, app, provList);
			System.out.println("\tfit: " + bestSolutionSoFar.getFitnessValue());
			System.out.println("####### end best");
		}
		
		
		
		
		
		
	}

}
