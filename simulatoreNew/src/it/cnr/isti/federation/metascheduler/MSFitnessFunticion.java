package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
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
		association = null;
	}
	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		double fitness = 0;
		CFitParameter param = checkCompability(arg0);
		double count;
		if(param.violation == 0){
			if((count = checkValidity())==0){
				// Respect Equal
				fitness += param.equal;
				// Maximize ascendent
				fitness += param.ascendent;
//				System.out.println("######################### "+param.ascendent);

				// Minimize descendent
				fitness += param.descendent;
				return Math.abs(fitness);
			}
			return 1/count;
		}
		return 1 / (Math.log(param.violation)+1);
	}
	
	private double checkValidity(){
		Set<Integer> keyset = association.keySet();
		double count =0;
		for(Integer providerId: keyset ){
			Integer vmNumbers = (Integer) providerList.get(providerId).getCharacteristic().get(Constant.VM_INSTANCES);
			double validity = association.get(providerId) - vmNumbers;
			if(validity >0){
				count = validity+1;
			}
		}
		return count;
	}
	
	private CFitParameter checkCompability(IChromosome ch){
		CFitParameter param = new CFitParameter();
		association = new HashMap<>();
		List<MSApplicationNode> nodes = application.getNodes();
		
		Gene[] genes = ch.getGenes();
		for(int i=0; i<genes.length; i++){
			Integer providerID = (Integer) genes[i].getAllele();
			double val;			
			for( int j =0; j<policy.size(); j++){
				if ((val = policy.get(j).evaluateLocalPolicy(nodes.get(i),providerList.get(providerID))) > 0) { // violazione
					
					param.violation += val;
				}else {
					updateParameter(policy.get(j).getType(), val, param);
					Integer count = association.get(providerID);
					
					if( count != null)
						association.put(providerID, count+1 );
					else 
						association.put(providerID, 1);
					System.out.println("$$ checkCompability: count: " + association.get(providerID));
				}
			}
		}
		return param;
	}
	
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
