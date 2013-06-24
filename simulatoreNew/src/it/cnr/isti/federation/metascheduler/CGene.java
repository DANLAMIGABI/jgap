package it.cnr.isti.federation.metascheduler;



import it.cnr.isti.federation.metascheduler.resources.iface.IMSProvider;

import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IGeneConstraintChecker;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.impl.IntegerGene;



public class CGene extends BaseGene implements Gene, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//int upperBound;
	Integer allele;
	private IMSProvider[] provider;
/*
	public myGene(Configuration arg0) throws InvalidConfigurationException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
*/	
	public CGene(Configuration arg0) throws InvalidConfigurationException {
		super(arg0);
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
//		System.out.println("COMPARE TO CGENE " + getAllele());
		if(o == null)
			return 1;
		if(allele == null){
			if(((CGene)o).allele == null ){
				return 0;
			}
			return -1;
		}
//		System.out.println("dffffdasfa "+ (Integer)((CGene)o).getAllele());
		return allele.compareTo((Integer)((CGene)o).allele);
//		return Integer.compare(allele, (Integer)((MyIntegerGene)o).getAllele());
	}

	@Override
	public String getUniqueID() {
		// TODO Auto-generated method stub
		System.out.println("$$ getUniqueID");
		return null;
	}

	@Override
	public String getUniqueIDTemplate(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUniqueIDTemplate(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyMutation(int arg0, double arg1) {
		// TODO Auto-generated method stub
//		System.out.println("                                 Mutation odl " 
//		+ getAllele() + " new: " + arg0);
//		System.out.println("                                  mutato");
		setAllele(arg0);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getAllele() {
		// TODO Auto-generated method stub
		return allele;
	}

	@Override
	public Object getApplicationData() {
		// TODO Auto-generated method stub
		return provider;
	}

	@Override
	public String getPersistentRepresentation()
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCompareApplicationData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Gene newGene() {
		// TODO Auto-generated method stub
		try{
			CGene ret = new CGene(getConfiguration());
			ret.setApplicationData(this.provider);
			return ret;
		}catch (InvalidConfigurationException ex) {
	        throw new IllegalStateException(ex.getMessage());
		}
	}

	@Override
	public void setAllele(Object arg0) {
		// TODO Auto-generated method stub
		allele = (Integer) arg0; 
//		System.out.println(arg0.getClass().getName());
		
	}

	@Override
	public void setApplicationData(Object arg0) {
		// TODO Auto-generated method stub
		provider = (IMSProvider[])arg0;
		
	}
/*
	@Override
	public void setCompareApplicationData(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
*/


	@Override
	public void setToRandomValue(RandomGenerator arg0) {
		// TODO Auto-generated method stub
		this.allele = arg0.nextInt();
//		System.out.println("~~~~ allele set "+ this.allele);
	}

	@Override
	public void setValueFromPersistentRepresentation(String arg0)
			throws UnsupportedOperationException,
			UnsupportedRepresentationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	protected Object getInternalValue() {
		// TODO Auto-generated method stub
		return allele;
	}

	@Override
	protected Gene newGeneInternal() {
		// TODO Auto-generated method stub
		System.out.println("$$ newGeneInternal");
		return null;
	}


}
