package contrailJgap;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import cApplication.CApplication;
import cApplicationIface.ICApplication;
import cPolicy.Policy;
import cProvider.CProvider;
import cProviderIface.ICProvider;
import cPolicy.*;

public class CopyOfCFitnessEasy extends FitnessFunction{
	
	static private int target;
	static private double tollerance;
	
	public CopyOfCFitnessEasy(int target, double tollerance){
		CopyOfCFitnessEasy.target = target;
		CopyOfCFitnessEasy.tollerance = tollerance;
	}
	
	public void printInfo(double fit, IChromosome ch){
		ICApplication[] app = (ICApplication[]) ch.getApplicationData();
		
	}

	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		
		double chWeight;
		chWeight = evalutateCromosome(arg0);
		
		if(chWeight > 0){
			//penalizzazione
			return Math.abs(1/chWeight);
		}
		chWeight = Math.abs(chWeight);
//		double goodness = Math.abs(target - chWeight);
//		double fitness = Math.abs(100-goodness);
		double fitness = chWeight;
		System.out.println("FITNESS: " + fitness);
		return fitness;
	}
	
	
	private double evalutateCromosome(IChromosome ch){
		double ret=-1;
		ICApplication[] app = (ICApplication[])ch.getApplicationData();
		
		for(int i =0; i< ch.size(); i++){
			ICProvider[] prov = (ICProvider[])ch.getGene(i).getApplicationData();
			Integer provIndex = (Integer)ch.getGene(i).getAllele();
			System.out.println("#### " + provIndex);
			ret = evalutatePolicy(app[i], prov[provIndex]);
			System.out.println("## info: \n#   A.ID " + app[i].getID() + " P.ID " + prov[provIndex].getID() 
					+ " ret: " + ret);
			
		}
		return ret;
	}
	
//	private double ecalutateEqualsConstrains(ICApplication app, ICProvider prov){
//		return Math.abs(Policy.applicationPlacePolicy(app, prov)) - tollerance;
//	}
	
	private double evalutatePolicy(ICApplication app, ICProvider prov){
		double good=0, bad =0, temp=0;
		/* Se una policy e` = 0 vuol dire che e` accettabile,
		 * quindi valutare se dare un peso < 0
		 */
		if( Policy.applicationPlacePolicy(app, prov) && Policy.applicationCost(app, prov)<0 ){
//			if((temp = Policy.applicationCost(app, prov)) <0)
//				good += temp;
//			else 
//				bad += temp;
//			if((temp = Policy.computingPolicy(app, prov)) <0 )
//				good += temp;
//			bad += temp;
			if((temp = Policy.storagePolicy(app, prov)) <0)
				good += temp;
			else 
				bad += temp;
//			if((temp = Policy.networkPolicy(app, prov)) <0)
//				good +=temp;
//			bad += temp;
		}else
			bad += 1000;
		
		return bad >0 ? bad : good;
	}
	
	
//	private double getWeight(CApplication app, CProvider prov){
//		double good=0, bad=0, temp;
//		if((temp =  prov.getCost() - app.getBudget()) < 0)
//			good += temp;
//		else
//			bad += temp;
//		if(good <0 )
//			return good;
//		return bad;
//		
//	}
	

}
