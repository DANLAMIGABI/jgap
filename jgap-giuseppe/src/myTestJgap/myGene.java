package myTestJgap;

import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IGeneConstraintChecker;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

public class myGene extends BaseGene implements Gene, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private myObj provider;
	private myObj[] app;
	private myObj[] plist;
/*
	public myGene(Configuration arg0) throws InvalidConfigurationException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
*/	
	public myGene(Configuration arg0, myObj provider, myObj[] app, myObj[] p) throws InvalidConfigurationException {
		super(arg0);
		this.provider = provider;
		this.app = app;
		this.plist = p;
	}

	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
			return 1;
		if( provider == null){
			if( ((myGene)o).provider == null)
				return 0;
			return -1;
		}
		return provider.compareTo(((myGene)o).provider);
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
		System.out.println("$$ applyMutation");
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getAllele() {
		// TODO Auto-generated method stub
		return provider;
	}

	@Override
	public Object getApplicationData() {
		// TODO Auto-generated method stub
		return app;
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
			return new myGene(getConfiguration(), provider, app, plist);
		}catch (InvalidConfigurationException ex) {
	        throw new IllegalStateException(ex.getMessage());
		}
	}

	@Override
	public void setAllele(Object arg0) {
		// TODO Auto-generated method stub
		provider = (myObj)arg0;
		System.out.println("**** " + ((myObj)arg0).getID());
		
	}

	@Override
	public void setApplicationData(Object arg0) {
		// TODO Auto-generated method stub
		app = (myObj[])arg0;
		
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
		boolean exit = false;
		int i =-1;
		while(!exit){
			i = Math.abs(arg0.nextInt(plist.length));
			if(Main.test[i] == false){
				setAllele(plist[i]);
				Main.test[i] = true;
				exit = true;			
			}
		}
		for(int j=0; j<Main.test.length; j++){
			exit = exit && Main.test[j];
		}
		if(exit){
			for(int j=0; j< Main.test.length; j++){
				Main.test[j] = false;
			}
		}
		System.out.println("######### " + (i+1));
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
		return 0;
	}

	@Override
	protected Object getInternalValue() {
		// TODO Auto-generated method stub
		return provider;
	}

	@Override
	protected Gene newGeneInternal() {
		// TODO Auto-generated method stub
		System.out.println("$$ newGeneInternal");
		return null;
	}


}
