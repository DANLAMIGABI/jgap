package it.cnr.isti.federation.application;

import it.cnr.isti.federation.UtilityPrint;
import it.cnr.isti.federation.resources.VmProvider;
import it.cnr.isti.federation.resources.VmProvider.VmType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;


public class ApplicationVertex
{
	private List<Cloudlet> cloudlets;
	private List<Vm> vms;
	private Map<Cloudlet, Vm> cloudletMap;
	private Map<Vm, Cloudlet> vmMap;
	private String name = null;
	
	public ApplicationVertex(List<Cloudlet> cloudlets, VmType vmtype)
	{
		this.cloudlets = cloudlets;
		this.cloudletMap = new HashMap<Cloudlet, Vm>();
		this.vmMap = new HashMap<Vm, Cloudlet>();
		this.vms = new ArrayList<Vm>();
		
		for (Cloudlet c : cloudlets)
		{
			Vm cloned = VmProvider.getVm(vmtype);
			this.vms.add(cloned);
			this.cloudletMap.put(c, cloned);
			this.vmMap.put(cloned, c);
		}
	}
	
	public void setName(String n)
	{
		this.name = n;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public List<Cloudlet> getCloudlets()
	{
		return this.cloudlets;
	}
	
	public List<Vm> getVms()
	{
		return this.vms;
	}
	
	public Vm getAssociatedVm(Cloudlet cloudlet)
	{
		return cloudletMap.get(cloudlet);
	}
	
	public Cloudlet getAssociatedcloudlet(Vm vm)
	{
		return vmMap.get(vm);
	}

	@Override
	public String toString()
	{
		return this.name;
	}
	
	public String toCompleteString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("ApplicationVertex [size=").append(cloudlets.size()).append(",");
		for (Cloudlet c: cloudlets)
		{
			sb.append(UtilityPrint.toString(c));
		}
		sb.append("]");
		
		return sb.toString();
	}
}
