package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestRandom {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Random rand = null;
		for(int i=0; i<2; i++){
			rand =  new Random(1);
			for(int j=0; j<1; j++){
				double num = rand.nextDouble();
				float f = rand.nextFloat();
				System.out.println(num +"\t" + f + "\t" + rand.nextLong());
			}
			System.out.println();
		}
		
		
//		List<Integer> list = new ArrayList<>();
//		for(int i =0; i<10; i++)
//			list.add(i);
//
//		Collections.shuffle(list, new Random(12));
//		for( Integer j : list)
//			System.out.print(j+ " : ") ;
	}

}
