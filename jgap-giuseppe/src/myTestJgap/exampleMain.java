package myTestJgap;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MapGene;

public class exampleMain {

	public static void main(String[] args) throws Exception {
		Configuration conf = new DefaultConfiguration();

		  
		  int targetAmount = 99;
		  FitnessFunction myFunc =
		    new exampleFit( targetAmount );

		  conf.setFitnessFunction( myFunc );

		  
		  Gene[] sampleGenes = new Gene[ 4 ];

		  sampleGenes[0] = new IntegerGene(conf, 0, 3 );  // Quarters
		  sampleGenes[1] = new IntegerGene(conf, 0, 2 );  // Dimes
		  sampleGenes[2] = new IntegerGene(conf, 0, 1 );  // Nickels
		  sampleGenes[3] = new IntegerGene(conf, 0, 4 );  // Pennies

		  Chromosome sampleChromosome = new Chromosome(conf, sampleGenes );

		  conf.setSampleChromosome( sampleChromosome );

		  
		  conf.setPopulationSize( 500 );
		  
		  Genotype population = Genotype.randomInitialGenotype( conf );
		  
		  IChromosome bestSolutionSoFar;

		  for( int i = 0; i < 1; i++ )
		  {
		      population.evolve();
		  }
		  bestSolutionSoFar = population.getFittestChromosome();
		  System.out.println( "The best solution contained the following: " );

		  System.out.println(
		      exampleFit.getNumberOfCoinsAtGene(
		          bestSolutionSoFar, 0 ) + " quarters." );

		  System.out.println(
				  exampleFit.getNumberOfCoinsAtGene(
		          bestSolutionSoFar, 1 ) + " dimes." );

		  System.out.println(
				  exampleFit.getNumberOfCoinsAtGene(
		          bestSolutionSoFar, 2 ) + " nickels." );

		  System.out.println(
				  exampleFit.getNumberOfCoinsAtGene(
		          bestSolutionSoFar, 3 ) + " pennies." );

		  System.out.println( "For a total of " +
				  exampleFit.amountOfChange(
		          bestSolutionSoFar ) + " cents in " +
		          exampleFit.getTotalNumberOfCoins(
		          bestSolutionSoFar ) + " coins." );
		
	}
	
}
