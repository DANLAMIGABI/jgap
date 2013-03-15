package myTestJgap;

import java.security.Provider;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class myFit extends FitnessFunction{
	private int max_target;
	
	
	public myFit(int target){
		//check error
		max_target = target;
	}

	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		
		double match, match_index;
		match = check(arg0);
		System.out.println("---- " + match);
		
		if(match <0 ){
			//penalizzazione
			System.out.println("PENALIZZAZIONE");
			return match;
		}
		double difference = Math.abs(max_target - match);
		double fitness = 100 - difference;
		System.out.println("fitness: " + fitness);
		return fitness;
	}
	
	

	public static double check(IChromosome p_solution){
		double good =0;
		double bad =0;
		double tmp;
		myObj allele;
		for(int i=0; i<p_solution.size();i++){
			//System.out.println( ((myObj)p_solution.getGene(i).getAllele()).getID() );
			myObj[] app = (myObj[]) p_solution.getGene(i).getApplicationData();
			//System.out.println("check: appID->" + app[i].getID());
			allele= (myObj)p_solution.getGene(i).getAllele();
			if((tmp = app[i].getCosto() - allele.getCosto() ) >= 0){
				good += tmp + Math.abs(allele.getGuadagno());
			}else{
				bad += tmp; 
			}
		}
		if(bad >0)
			return bad;
		return good;
	}

	
	/*
	public static int check(IChromosome p_solution){
		int good =0;
		int bad = 0;
		int tmp =0;
		myAllele allele;
		for(int i =0; i<3; i++){
			allele = (myAllele) p_solution.getGene(i).getAllele();
			if((tmp = allele.getProvider().getCosto() - allele.getApp().getCosto()) > 0)
				bad += tmp;
			else
				good +=tmp;
		}
		if(bad > 0)
			return bad;
		return good;
	}
	
	public static int getMatch(IChromosome p_solution){
		myAllele allele;
		int ret =0;
		for(int i=0; i<3; i++){
			allele = (myAllele)p_solution.getGene(i).getAllele();
			ret += (allele.getProvider().getCosto() - allele.getApp().getCosto());
		}
		return ret;
	}
	*/

}
