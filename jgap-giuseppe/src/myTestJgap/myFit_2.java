package myTestJgap;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class myFit_2 extends FitnessFunction {
private int max_target;
	
	
	public myFit_2(int target){
		//check error
		max_target = target;
	}

	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		
		double match, match_index;
		match = check(arg0);
		System.out.println("myFit evalutate match " + match);
		
		if(match <0 ){
			//penalizzazione
			System.out.println("PENALIZZAZIONE");
			return Math.abs(1/match);
		}
		double difference = Math.abs(max_target - match);
		double fitness = 100 - difference;
//		double fitness = Math.abs(100 - match);
		System.out.println("fitness: " + fitness);
		return fitness;
	}
	
	

	public static double check(IChromosome p_solution){
		double good =0;
		double bad =0;
		double tmp;
		myObj[] prov;
		myObj[] app = (myObj[])p_solution.getApplicationData();
				
		
		for(int i=0; i<p_solution.size();i++){
			prov = (myObj[])p_solution.getGene(i).getApplicationData();
			Integer index = (Integer) p_solution.getGene(i).getAllele();
			if((tmp = app[i].getCosto() - prov[index].getCosto() ) >= 0){
				good += Math.abs(tmp - app[i].getGuadagno());
			}else{
				bad += tmp; 
			}
		}
		if(bad <0)
			return bad;
		return good;
	}
	

}
