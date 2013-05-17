package contrailJgap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgap.IChromosome;

import cApplication.CApplicationNode;
import cApplicationIface.ICApplication;
import cProviderIface.ICProvider;

public class BestSolution {
	
//	private ICApplication application;
//	private List<ICProvider> providers;
	private HashMap<Integer, Integer> associationMap;
	private double fit;
	
	public BestSolution(){
		associationMap = new HashMap<Integer, Integer>();
	}
	
//	public BestSolution(HashMap<Integer, Integer> associationMap){
////		this.application = application;
////		this.providers = providers;
//		this.associationMap = associationMap;
//	}
	
	public HashMap<Integer, Integer> getMapping(IChromosome chromosome){
		HashMap<Integer, Integer> mapping = new HashMap<Integer, Integer>();
		for(int i=0; i<chromosome.size(); i++){
			mapping.put(i, (Integer)chromosome.getGene(i).getAllele());
//			System.out.println("################################ " +  );
		}
		return mapping;
	}
	
	public void setSolution(IChromosome chromosome){
		this.associationMap = getMapping(chromosome);
	}
	public HashMap<Integer, Integer> getBest(){
		return associationMap;
	}
	public void setFit(double fit){
		this.fit = fit;
	}
	public double getFit(){
		return this.fit;
	}
	
	

}
