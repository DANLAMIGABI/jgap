package it.cnr.isti.federation.mapping;

import java.util.ArrayList;
import java.util.List;

import metaschedulerJgap.MSPolicy;
import msApplicationIface.*;
import msProviderIface.*;

public class ConfigurationFitness {
	
	String name;
	String id;
	
	private List<IMSProvider> providers;
	private IMSApplication application;
	private List<MSPolicy> constrains;
	private List<String> aggregationParams;
	
	public ConfigurationFitness(){
		this("","");
	}
	
	public ConfigurationFitness(String name, String id){
		this.name = name;
		this.id = id;
		providers = new ArrayList<>();
		constrains = new ArrayList<MSPolicy>();
		aggregationParams = new ArrayList<>();
		application = null;
	}
	
	public void setApplication(IMSApplication application){
		if(application != null)
			this.application = application;
	}
	/*
	public boolean addProvider(IMSProvider provider){
		if(provider != null)
			return false;
		return providers.add(provider);
	}
	*/
	public boolean addConstrain(MSPolicy constrain){
		if(constrain != null){
			System.out.println("cafkajfjdsf;fdasj");
			return false;
			
		}
		
		return this.constrains.add(constrain);
	}
	
	public IMSApplication getApplication(){
		return application;
	}
	public List<IMSProvider> getProviders(){
		return providers;
	}
	
	public List<MSPolicy> getConstrains(){
		return constrains;
	}
	
	public void addAggregationParams(String param){
		aggregationParams.add(param);
	}
	public void setProviders(List<IMSProvider> providerList){
		this.providers = providerList;
	}
	public List<String> getAggregationParams(){
		return aggregationParams;
	}
	
	public void setConstrains(List<MSPolicy> constrains){
		this.constrains = constrains;
	}
	
}
