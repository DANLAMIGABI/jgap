package contrailJgap;

import java.security.Policy;
import java.util.Vector;

import org.jgap.FitnessFunction;
import org.jgap.IApplicationData;
import org.jgap.IChromosome;

import test.Constant;

import cApplication.CApplication;
import cApplication.CApplicationNode;
import cApplicationIface.ICApplication;
import cPolicy.TestPolicy;
import cProvider.CProvider;
import cProviderIface.ICProvider;
import cPolicy.*;

public class CFitnessEasy extends FitnessFunction{
	
	static private double tollerance;
	static private int penality =100;
	
	static private ICApplication app;
	static private ICProvider[] pList;
	static private Vector<CPolicy> policy;
	
	public CFitnessEasy(double tollerance,ICApplication app, ICProvider[] provList, Vector<CPolicy> policy){
		CFitnessEasy.tollerance = tollerance;
		CFitnessEasy.app = app;
		pList = provList;
		CFitnessEasy.policy = policy;
	}
	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		
		double fitness=0;		
		CFitParameter param = getDistance(arg0);
		
		if(param.distance == 0 && param.equal <=0 && param.validity){
			fitness -= Math.abs(param.ascendent);
			//System.out.println("goodAscendent: " + fitness);
		}else{
			double ret = 1/param.distance;
			System.out.println("PENALITY " +ret );
			return ret;
		}
			
		fitness += Math.abs(param.descendent);
		System.out.println("FITNESS: " + fitness);
		return Math.abs(fitness);
	}
	
	
	private CFitParameter getDistance(IChromosome ch){
		CFitParameter param =  new CFitParameter();
		for(int i=0; i<ch.size(); i++){
			Integer index = (Integer) ch.getGene(i).getAllele();
			if(param.firstAllele == -1)
				param.firstAllele = index;
			for( int j =0; j<policy.size(); j++){
				setParameter(pList[index], policy.get(i), param);
			}
		}
		return param;
	}
	
	public void setParameter(ICProvider prov, CPolicy constrain, CFitParameter param){
		double val;
		if((val = constrain.elaluatePolicy(app, prov)) >0)
			param.distance += val;
		else{
			switch (constrain.getType()) {
			case Constant.ASCENDENT_TYPE:param.ascendent += val; break;
			case Constant.DESCENDENT_TYPE: param.descendent += val;	break;
			case Constant.EQUAL_TYPE:param.equal += val; break;
			default:
				break;
			}
		}
	}
	
	
	public void evalutateValidity(CApplicationNode node, int pID, MyObj param){
		if(param.firstAllele <0)
			param.firstAllele = pID;
		if(param.firstAllele != pID ){
			param.validity = false;			
			param.distance += 100;
		}
	}
	
	
//	private void evalutateAscendent(CApplicationNode node, ICProvider prov, MyObj param){
//		double temp;
//		if( (temp = TestPolicy.storagePolicy(node, prov)) > 0){ //violation
//			param.distance += temp * 1;
//		}else{
//			param.ascendent += temp;
//		}
//		
//	}
//	
//	private void evalutateDescendent(ICApplication app, ICProvider prov, MyObj param){
//		double temp;
//		if( (temp = TestPolicy.applicationCost(app, prov)) > 0){ // violazione constrain
//			param.distance += temp * 1; // 1 deve esser sostituito con il peso
//		}else{
//			param.descendent +=temp;
//		}
//	}
//	
//	private void evalutateEqualAttribute(ICApplication app, ICProvider prov, MyObj param){
//		double temp;
//		if( (temp = Math.abs(TestPolicy.applicationPlacePolicy(app, prov))) - tollerance >0){ //violazione constrain
//			param.distance += temp * 1;
//			param.equal = temp;
//		}
//		
//	}
//	evalutateValidity(app.getNodes()[i], pList[i].getID(), param);
//	evalutateEqualAttribute(app, pList[index], param);
//	evalutateDescendent(app, pList[index], param);
//	evalutateAscendent(app.getNodes()[i], pList[index], param);
//	System.out.println(" DistanceINFO: \n   A.ID " + app[i].getID() + " P.ID " + prov[index].getID() 
//			+ " ret: " + temp);
	
	
	
	
	
//	private double getGlobablContraint(ICApplication app, ICProvider prov){
//		double ret = 0, temp;
//		if( (temp = Policy.applicationCost(app, prov)) > 0) // violazione constrain
//			ret += temp * 1; // 1 deve esser sostituito con il peso
////		if( (temp = Math.abs(Policy.applicationPlacePolicy(app, prov))) - tollerance >0) //violazione constrain
////			ret += temp * 1;
//		return ret;
//	}
	
//	private double getLocalConstrain(ICApplication app, ICProvider prov){
//		double ret =0, temp;
//		if( (temp = Policy.storagePolicy(app, prov)) > 0) //violation
//			ret += temp * 1;
//		return ret;
//	}
	
	
	
	private double evalutatePolicy(ICApplication app, ICProvider prov){
	double good=0, bad =0, temp=0;
	/* Se una policy e` = 0 vuol dire che e` accettabile,
	 * quindi valutare se dare un peso < 0
	 */
//	if( Policy.applicationPlacePolicy(app, prov)==0){
		if( (temp=  TestPolicy.applicationCost(app, prov)) <0 )
			good += temp;
		else
			bad += temp;
//		if((temp = Policy.applicationCost(app, prov)) <0)
//			good += temp;
//		else 
//			bad += temp;
//		if((temp = Policy.computingPolicy(app, prov)) <0 )
//			good += temp;
//		bad += temp;
//		if((temp = Policy.storagePolicy(app, prov)) <0)
//			good += temp;
//		else 
//			bad += temp;
//		if((temp = Policy.networkPolicy(app, prov)) <0)
//			good +=temp;
//		bad += temp;
//	}else
//		bad += 1000;
	
	return bad >0 ? bad : good;
}
	
	
	private double evalutateCromosome(IChromosome ch){
		double ret=-1;
		ICApplication[] app = (ICApplication[])ch.getApplicationData();
		
		for(int i =0; i< ch.size(); i++){
			ICProvider[] prov = (ICProvider[])ch.getGene(i).getApplicationData();
			Integer provIndex = (Integer)ch.getGene(i).getAllele();
//			System.out.println("#### " + provIndex);
			ret = evalutatePolicy(app[i], prov[provIndex]);
			System.out.println("INFO: \n   A.ID " + app[i].getID() + " P.ID " + prov[provIndex].getID() 
					+ " ret: " + ret);
			
		}
		return ret;
	}
	
	
//	protected double evaluate(IChromosome arg0) {
//		// TODO Auto-generated method stub
//		
//		double distance;
//		distance = getDistance(arg0);
//		System.out.println("distance: " + distance);
//		double valute = evalutateCromosome(arg0);
//		if(distance > 0){
//			//penalizzazione
//			System.out.println("# PENALITY " +distance	);
//			return Math.abs(1/(distance+penality));
//		}
//		
//		double fitness = 1+ penality * distance;//Math.abs(valute) + penality * distance;
//		System.out.println("FITNESS: " + fitness);
//		return fitness;
//	}
	
	
	
	
	
	
//	private double ecalutateEqualsConstrains(ICApplication app, ICProvider prov){
//		return Math.abs(Policy.applicationPlacePolicy(app, prov)) - tollerance;
//	}
	
//	private double evalutatePolicy(ICApplication app, ICProvider prov){
//		double good=0, bad =0, temp=0;
//		/* Se una policy e` = 0 vuol dire che e` accettabile,
//		 * quindi valutare se dare un peso < 0
//		 */
//		if( Policy.applicationPlacePolicy(app, prov) && Policy.applicationCost(app, prov)<0 ){
////			if((temp = Policy.applicationCost(app, prov)) <0)
////				good += temp;
////			else 
////				bad += temp;
////			if((temp = Policy.computingPolicy(app, prov)) <0 )
////				good += temp;
////			bad += temp;
//			if((temp = Policy.storagePolicy(app, prov)) <0)
//				good += temp;
//			else 
//				bad += temp;
////			if((temp = Policy.networkPolicy(app, prov)) <0)
////				good +=temp;
////			bad += temp;
//		}else
//			bad += 1000;
//		
//		return bad >0 ? bad : good;
//	}
	
	
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
