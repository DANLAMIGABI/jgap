package contrailJgap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jgap.RandomGenerator;

public class NewCRandGenerator implements RandomGenerator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int n_range;

	private static Random random;
	
	private static List<Integer> indexList;
	
	public NewCRandGenerator(int size){
		n_range = size;
		random = new Random(System.currentTimeMillis());
	}

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
		int ret = Math.abs(random.nextInt()%n_range);
		//System.out.println(String.format("%100d", ret));
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
