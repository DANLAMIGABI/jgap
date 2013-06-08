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
	List<String> messages;
	private double fit;
	
	public BestSolution(){
		associationMap = new HashMap<Integer, Integer>();
	}

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
	public void setMessages(List<String> messages){
		this.messages = messages;
	}
	public List<String> getMessages(){
		return this.messages;
	}
	
	

}
