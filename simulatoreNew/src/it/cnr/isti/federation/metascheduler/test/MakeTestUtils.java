package it.cnr.isti.federation.metascheduler.test;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.application.ApplicationVertex;
import it.cnr.isti.federation.metascheduler.Constant;
import it.cnr.isti.federation.metascheduler.resources.MSApplicationNode;
import it.cnr.isti.federation.metascheduler.test.FederationDatacenterProfileMeta.DatacenterParams;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.HostProfile;
import it.cnr.isti.federation.resources.HostProviderMetaScheduler;
import it.cnr.isti.federation.resources.HostProfile.HostParams;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.math3.transform.DctNormalization;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.codehaus.stax2.validation.Validatable;



public class MakeTestUtils {
	
	protected static Random rand;
	
	public static void printVmlist(Application app){
		String str = "Application To String \n";
		List<Vm> vmList = app.getAllVms();
		for( Vm a : vmList){
			
			str += "vmID:"+ a.getId() + "\t" +app.getVertexForVm(a).getPlace()+"\n";
			str += "    Size GB: " +a.getSize()/(1024*1024)+"\n";
			str += "    Ram GB: "+ a.getRam()/(1024*1024)+"\n";
			str += "    Mips: " +a.getMips()+"\n";
			str += "    Net: " + a.getBw()+"\n";
			
		
		}
		System.out.println(str);
	}
	
	
	public static String datacenterStateToString(FederationDatacenter dc ){
		String str ="";
		String separatore = "\n";
		List<HostDynamicWorkload> hostList = dc.getHostList();
		str += "dc_ID :          " + dc.getId()+ separatore;
		str += "dc_place:       " + dc.getMSCharacteristics().getPlace()+separatore;
		str += "dc_Size:         " + hostList.size() +separatore;
		str += "cost_mem:        " + dc.getMSCharacteristics().getCostPerMem()+ separatore;
		str += "cost_storage:    " + dc.getMSCharacteristics().getCostPerStorage()+ separatore;
		str += "cost_sec:        " + dc.getMSCharacteristics().getCostPerSecond()+ separatore;
		if(hostList.size()>0){
			HostDynamicWorkload host = hostList.get(0) ;
			str += "   host_id:      " + host.getId()+ separatore;
			str += "   host_ram GB:     " + ((host.getRam() - host.getUtilizationOfRam())/(1024*1024))+ separatore;
			str += "   host_store GB:   " + (host.getStorage()/(1024*1024))+ separatore;
			str += "   host_mips:    " + (host.getTotalMips() - host.getUtilizationMips())+ separatore;
			str += "   host_net:     " + host.getUtilizationOfBw()+ separatore;
			str += "   host_net_tot: " + host.getBw()+ separatore;
		}
		
		return str+"\n";
		
	}
	
	public static double getSolutionCost(HashMap<Integer, Integer> map, List<FederationDatacenter> dclist, Application app){
		List<Vm> vmlist = app.getAllVms();
		double solutionCost=0;
		for(Vm v : vmlist){
			FederationDatacenter dc = dclist.get(map.get(v.getId()));
			double providerRamPrice = dc.getMSCharacteristics().getCostPerMem();
			int vmRam =v.getRam()/(1024*1024);
			solutionCost+= providerRamPrice * vmRam;
			
//			System.out.println("vm id: "+v.getId() + " -> prov id" + dclist.get(map.get(v.getId())).getId() + 
//					"\tvmRam: " + vmRam + "\tPcost: " + providerRamPrice + "\tcost" + cost );
		}
		System.out.println("COSTO SOLUZIONE:\t" + solutionCost );
		return solutionCost;
	}
	
	
	public static Application geCustomFederationApplication(int userID, String[] places, String[] budgets,int numberOfCloudlets)
	{
		Double number = new Double(numberOfCloudlets);
		if (number < 3)
			number = 3d;
		
		int frontend = new Double(Math.ceil(number * 20 / 100)).intValue();
		int database = new Double(Math.ceil(number * 20 / 100)).intValue();
		int appserver = number.intValue() - frontend - database;
		
		return new ThreeTierBusinessApplicationMeta(userID,places, budgets,frontend, appserver, database);
	}
	
	public static Application getFederationApplication(int userID, int numberOfCloudlets, Properties app_prop)
	{
		String[] places = app_prop.getProperty(Constant.APPLICATION_PLACES).toString().split(",");
		String[] budgets =app_prop.getProperty(Constant.APPLICATION_BUDGET).toString().split(",");
		Double number = new Double(numberOfCloudlets);
		if (number < 3)
			number = 3d;
		
		int frontend = new Double(Math.ceil(number * 20 / 100)).intValue();
		int database = new Double(Math.ceil(number * 20 / 100)).intValue();
		int appserver = number.intValue() - frontend - database;
		
		return new ThreeTierBusinessApplicationMeta(userID,places, budgets,frontend, appserver, database);
	}
	
	private static PowerHostUtilizationHistory createHost(int factor,Properties prop){
		double ram = Double.parseDouble(prop.getProperty(Constant.RAM))*factor;
		long store = Long.parseLong(prop.getProperty(Constant.STORE))*factor;
		List<Pe> peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple(Double.parseDouble(prop.getProperty(Constant.MIPS)))));
		HostProfile prof = HostProfile.getDefault();
		prof.set(HostParams.STORAGE_MB, (new Long(store)*1024*1024)+"");
		prof.set(HostParams.RAM_AMOUNT_MB,  new Double(ram*1024*1024).intValue()+"")  ;  
		prof.set(HostParams.BW_AMOUNT, (new Long(prop.getProperty(Constant.BW))*1024*1024)+"");

		return  HostProviderMetaScheduler.get(prof, peList);
	}

	private static FederationDatacenter getDatacenter(int dc_id,Properties prop, String place){
		FederationDatacenterProfileMeta prof = FederationDatacenterProfileMeta.getDefault();
		prof.set(DatacenterParams.COST_PER_BW, prop.getProperty(Constant.COST_BW));
		prof.set(DatacenterParams.COST_PER_MEM, rand.nextDouble()+"");
		prof.set(DatacenterParams.COST_PER_SEC, prop.getProperty(Constant.COST_SEC));
		prof.set(DatacenterParams.COST_PER_STORAGE, prop.getProperty(Constant.COST_STORAGE));
		prof.set(DatacenterParams.PLACE, place);
		
		int hostListSize = Integer.parseInt(prop.getProperty(Constant.DATACENTER_SIZE));
		//make datacenter
		List<PowerHost> hostList = new ArrayList<>();
		int increment = new Double(Math.pow(2, (dc_id%10))).intValue();
		for(int i=0;i<hostListSize;i++){
			hostList.add(createHost(increment,prop));
		}
		List<Storage> storageList = new ArrayList<Storage>();
		return FederationDatacenterProviderMeta.get(prof, hostList, storageList);
	}
	
	public static List<FederationDatacenter> getDatacenterList(Properties prop){
		List<FederationDatacenter> list = new ArrayList<>();
		String[] dc_places = prop.get(Constant.DATACENTER_PLACES).toString().split(",");
		int size = Integer.parseInt(prop.getProperty(Constant.DATACENTER_NUMEBER));
		for(int i=0;i<size;i++){
			list.add(getDatacenter(i,prop, dc_places[i%dc_places.length]));			
		}
		return list;
	}
	
	
	public static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
				+ "Data center ID" + indent + "VM ID" + indent + "Time"
				+ indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getResourceId()
						+ indent + indent + indent + cloudlet.getVmId()
						+ indent + indent
						+ dft.format(cloudlet.getActualCPUTime()) + indent
						+ indent + dft.format(cloudlet.getExecStartTime())
						+ indent + indent
						+ dft.format(cloudlet.getFinishTime()));
			}
		}
	}
	
	
	
	public static void printDatacenter(FederationDatacenter dc){
		long ram = 0;
		long net = 0;
		long netTot = 0;
		long mips = 0;
		long storage = 0;
		List<HostDynamicWorkload> hostlist = dc.getHostList();
		for (int i = 0; i < hostlist.size(); i++) {
			ram += hostlist.get(i).getRam()- hostlist.get(i).getUtilizationOfRam();
			//net += hostlist.get(i).getBw()- hostlist.get(i).getUtilizationOfBw();
			net += hostlist.get(i).getUtilizationOfBw();
			netTot += hostlist.get(i).getBw();
			storage += hostlist.get(i).getStorage();
			mips += hostlist.get(i).getTotalMips()- hostlist.get(i).getUtilizationMips();

		}
		System.out.println("@@@@@@@@@@@@@@ Stato Datacenter: " + dc.getId() + " @@@@@@@@@@@" + hostlist.size() );
		System.out.println("RAM: " + ram);
		System.out.println("NET used: " + net);
		System.out.println("NET tot: " +netTot);
		System.out.println("STORAGE: " + storage);
		System.out.println("MIPS: " + mips);
	}
	
	public static void printHostInfo(Host host){
		Log.printLine("  hostid:           "+ host.getId());
		Log.printLine("  host ram:         " + host.getRam());
		Log.printLine("  host store:       " + host.getStorage());
		Log.printLine("  host mips:        " + host.getTotalMips());
		Log.printLine("  host net:         " + host.getBw());
		Log.printLine("  host pelist size: " + host.getPeList().size());
		Log.printLine();
	}
	public static void saveFederationToFile(String path, List<FederationDatacenter> fed ){
		
		try{
			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(fed);
			oos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static List<FederationDatacenter> readFederationFromFile(String path){
		List<FederationDatacenter> dclist = null;
		try{
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fin);
			dclist = (List<FederationDatacenter>)ois.readObject();
			ois.close();
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		return dclist;
	}
	
	
	
	

}
