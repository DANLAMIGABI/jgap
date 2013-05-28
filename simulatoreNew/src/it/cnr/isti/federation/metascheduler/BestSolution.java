package it.cnr.isti.federation.metascheduler;

import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.jgap.IChromosome;


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
	
	public HashMap<Integer, Integer> getMapping(IChromosome chromosome, List<MSApplicationNode>nodes){
		HashMap<Integer, Integer> mapping = new HashMap<Integer, Integer>();
		for(int i=0; i<chromosome.size(); i++){
			mapping.put(nodes.get(i).getID(), (Integer)chromosome.getGene(i).getAllele());
//			System.out.println("################################ " +  );
		}
		return mapping;
	}
	
	public void setSolution(IChromosome chromosome, List<MSApplicationNode> nodes){
		this.associationMap = getMapping(chromosome,nodes);
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
