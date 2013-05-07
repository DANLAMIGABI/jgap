package it.cnr.isti.federation.mapping;

import java.util.ArrayList;
import java.util.List;

import org.jgap.FitnessFunction;

import sun.net.InetAddressCachePolicy;

import cApplicationIface.ICApplication;
import cPolicy.CPolicy;
import cProviderIface.ICProvider;

public class ConfigurationFitness {
	
	String name;
	String id;
	
	private List<ICProvider> providers;
	private ICApplication application;
	private List<CPolicy> constrains;
	private List<String> aggregationParams;
	
	public ConfigurationFitness(){
		this("","");
	}
	
	public ConfigurationFitness(String name, String id){
		this.name = name;
		this.id = id;
		providers = new ArrayList<>();
		constrains = new ArrayList<CPolicy>();
		aggregationParams = new ArrayList<>();
		application = null;
	}
	
	public void setApplication(ICApplication application){
		if(application != null)
			this.application = application;
	}
	/*
	public boolean addProvider(ICProvider provider){
		if(provider != null)
			return false;
		return providers.add(provider);
	}
	*/
	public boolean addConstrain(CPolicy constrain){
		if(constrain != null){
			System.out.println("cafkajfjdsf;fdasj");
			return false;
			
		}
		
		return this.constrains.add(constrain);
	}
	
	public ICApplication getApplication(){
		return application;
	}
	public List<ICProvider> getProviders(){
		return providers;
	}
	
	public List<CPolicy> getConstrains(){
		return constrains;
	}
	
	public void addAggregationParams(String param){
		aggregationParams.add(param);
	}
	public void setProviders(List<ICProvider> providerList){
		this.providers = providerList;
	}
	public List<String> getAggregationParams(){
		return aggregationParams;
	}
	
	public void setConstrains(List<CPolicy> constrains){
		this.constrains = constrains;
	}
	
}
