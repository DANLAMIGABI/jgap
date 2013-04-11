package test;

import java.util.HashMap;
import java.util.Iterator;

import org.jgap.Chromosome;

import cApplication.CApplicationNode;

import contrailJgap.CFitnessEasy;

public class CFitnessEasyTest {

	public static void printHashMap(HashMap< Integer, CApplicationNode> appNode){
		Iterator<Integer> keys = appNode.keySet().iterator();
		Integer index;
		while(keys.hasNext()){
			index = keys.next();
			System.out.println("provID: " + index + " appID: " + ((CApplicationNode) appNode.get(index)).getID() 
					+ " dudget: " + ((CApplicationNode) appNode.get(index)).getNodeBudget());
		}
	}
	
	
	
	public static void getApplicationMapTest(Chromosome ch, CFitnessEasy fitFunz){
		HashMap<Integer, CApplicationNode> appMap = fitFunz.getApplicationMap(ch);
		printHashMap(appMap);
		
	}

}
