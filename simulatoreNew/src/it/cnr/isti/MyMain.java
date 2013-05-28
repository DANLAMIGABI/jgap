package it.cnr.isti;

import java.io.FileWriter;

import org.apache.commons.math3.stat.descriptive.moment.Mean;

import it.cnr.isti.test.TestResult;

public class MyMain {
	static final int REP = 100;  
	public static void main (String[] args) throws Exception {
		int[] cloudlet = {3,3,3};
		int[] datacenter = {1};
			
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<cloudlet.length; i++)
		{
			sb.append(cloudlet[i]).append("\t");
			for (int k=0; k<datacenter.length; k++)
			{
				double[] aa = new double[REP];
				double[] bb = new double[REP];
				Mean m = new Mean();
				double my = 0.0;
				for (int n=0; n < REP; n++)
				{
					// System.out.print(cloudlet[i] + " "+ datacenter[k]+": ");
					TestResult t = MainExample.start(cloudlet[i], datacenter[k]);
					aa[n] = t.simtime;
					bb[n] = t.realtime;
					my+=bb[n];
					System.out.format("%.5f%n", bb[i]);
					
				}
				double d = m.evaluate(bb, 0, REP);
				double mavg = m.evaluate(bb);
				double myavg = my /REP;
				
				System.out.print("Media: ");
				System.out.format("%.5f%n", d);
				System.out.print("My Media: " + myavg);
				System.out.print("Camp Media: " + mavg);
				sb.append(d).append("\t");
			}
			
			sb.append("\n");
		}
		
		FileWriter fw = new FileWriter("mydata.dat");
		fw.write(sb.toString());
		fw.close();
		
	}
}
