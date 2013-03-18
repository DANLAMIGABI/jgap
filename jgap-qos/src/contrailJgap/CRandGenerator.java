package contrailJgap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jgap.RandomGenerator;

public class CRandGenerator implements RandomGenerator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int set_size_required;
	private static int n_range;
	
	
	private static int listSize;
	private static Random random;
	private static Set<Integer> set; 
	
	private static List<Integer> indexList;
	
	public CRandGenerator(int size){
		listSize = size;
		random = new Random(System.currentTimeMillis());
		indexList =  new ArrayList<Integer>(listSize);
		resetList();
	}
	
	private void resetList(){
		for(int i=0; i<listSize; i++){
			indexList.add(i);
		}
		
		for( int i=0; i< listSize; i++)
			Collections.shuffle(indexList, random );
		
//		for(int j =0; j<5; j++)
//			System.out.println("cazzo: " + Math.abs(random.nextInt())%listSize);
//		System.out.println(indexList);
	}
	
//	private void randomInitMyGenerator(){
//		random = new Random(System.currentTimeMillis());
//		set = new HashSet<Integer>(set_size_required);
//		while(set.size() < set_size_required ){
//			while(set.add(random.nextInt(n_range)%set_size_required) != true);
//		}
//		System.out.println("SET: "+ set);
//	}

	@Override
	public boolean nextBoolean() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double nextDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float nextFloat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nextInt() {
		// TODO Auto-generated method stub
		if(indexList.isEmpty()){
			resetList();
//			System.out.println("resetList");
		}
		int i = Math.abs(random.nextInt())%indexList.size();
	//	System.out.println("ad;ksfjkafafjafjas   " +Math.abs(random.nextInt()));
		int ret = indexList.get(i);
		indexList.remove(i);
		return ret;
	}

	@Override
	public int nextInt(int arg0) {
		// TODO Auto-generated method stub
		return nextInt();
	}

	@Override
	public long nextLong() {
		// TODO Auto-generated method stub
		return 0;
	}

}
