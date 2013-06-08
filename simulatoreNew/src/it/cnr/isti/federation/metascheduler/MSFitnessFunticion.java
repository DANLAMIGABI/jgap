package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.resources.MSProvider;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSApplication;
import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class MSFitnessFunticion extends FitnessFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static private IMSApplication application;
	static private List<IMSProvider> providerList;
	static private List<MSPolicy> policy;

	private HashMap<Integer, Integer> association;
	
	public MSFitnessFunticion(IMSApplication app, List<IMSProvider> plist, List<MSPolicy> policyList){
		application = app;
		providerList = plist;
		policy = policyList;
//		association = null;
	}
	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		double fitness = 0;
		
		CFitParameter param = checkCompability(arg0);
		double count;
		if(param.violation == 0){
			if((count = checkValidity(association))==0){
				// Respect Equal
				fitness += param.equal;
				// Maximize ascendent
				fitness += param.ascendent;
//				System.out.println("######################### "+param.ascendent);

				// Minimize descendent
				fitness += param.descendent;
				return Math.abs(fitness);
			}
//			System.out.println("********************************************* evaluate " + count);
			return 1/count;
		}
		//System.out.println("fitness:             " + (Math.log(param.violation)+1) + " ######################################## " + param.violation);
		//return 1 / (Math.log(param.violation)+1);
		return 1/(param.violation+1);
	}
	
	private double checkValidity(HashMap<Integer, Integer> association){
		Set<Integer> keyset = association.keySet();
		double count =0;
//		System.out.println("############### checkValidity ###########");
		for(Integer providerId: keyset ){
			Integer vmNumbers = (Integer) providerList.get(providerId).getCharacteristic().get(Constant.VM_INSTANCES);
//			System.out.println("prov: " + providerId  + " -- ass: " +association.get(providerId) + " --  vmNumbers: " + vmNumbers);
			double validity = association.get(providerId) - vmNumbers;
			if(validity >0){
				count += validity+1;
			}
		}
//		System.out.println("############### END ###########");
		return count;
	}
	
	private CFitParameter checkCompability(IChromosome ch){
		CFitParameter param = new CFitParameter();
		association = new HashMap<>();
		List<MSApplicationNode> nodes = application.getNodes();
		
		Gene[] genes = ch.getGenes();
//		System.out.println("_________________ CROMOSOMA");
		for (int i = 0; i < genes.length; i++) {
			Integer providerID = (Integer) genes[i].getAllele();
//			 System.out.println(" " + i + " -- " + providerID);
			if( checkConstraints(nodes.get(i), providerList.get(providerID), param)){
				Integer count = association.get(providerID);
				if( count != null){
					count +=1;
					association.put(providerID, count );
//					System.out.println("checkCompability:  provID: " + providerID + " --- count " + count);
				}
				else {
					association.put(providerID, new Integer(1));
//					System.out.println("checkCompability: ELSE  provID" + providerID + " -- count 1");
				}
//				System.out.println("$$ checkCompability: count: " + association.get(providerID));
			}
				
		}
//		System.out.println("_________________END CROMOSOMA");
		return param;
	}
	private boolean checkConstraints(MSApplicationNode node, IMSProvider provider, CFitParameter param){
		boolean status =  true;
		double val;
		for (int j = 0; j < policy.size(); j++) {
			if ((val = policy.get(j).evaluateLocalPolicy(node,provider)) > 0) { // violazione
				param.violation += val;
				status = false;
				continue;
			}
			updateParameter(policy.get(j).getType(), val, param);
		}
		return status;
	}
		
	

		
	
//	private boolean checkConstraints()
	
	private void updateParameter(char constrainType, double val, CFitParameter param){
		switch (constrainType) {
		case Constant.ASCENDENT_TYPE: param.ascendent += val; break;
		case Constant.DESCENDENT_TYPE: param.descendent += val; break;
		case Constant.EQUAL_TYPE: param.equal += val; break;
		default:
			break;
		}
	}
	

	
	
	
}
