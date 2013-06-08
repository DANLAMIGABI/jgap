package it.cnr.isti.federation.metascheduler.test;

import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.metascheduler.Constant;
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

import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.math3.transform.DctNormalization;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;



public class MakeTestUtils {
	public static String datacenterStateToString(FederationDatacenter dc ){
		String str ="";
		List<HostDynamicWorkload> hostList = dc.getHostList();
		HostDynamicWorkload host = hostList.get(0) ;

		
		str += "dc_ID :          " + dc.getId()+ "\n";
		str += "dc_Size:         " + hostList.size() +"\n";
		str += "cost_mem:        " + dc.getMSCharacteristics().getCostPerMem()+ "\n";
		str += "cost_storage:    " + dc.getMSCharacteristics().getCostPerStorage()+ "\n";
		str += "cost_sec:        " + dc.getMSCharacteristics().getCostPerSecond()+ "\n";
		str += "   host_id:      " + host.getId()+ "\n";
		str += "   host_ram:     " + (host.getRam() - host.getUtilizationOfRam())+ "\n";
		str += "   host_store:   " + (host.getStorage())+ "\n";
		str += "   host_mips:    " + (host.getTotalMips() - host.getUtilizationMips())+ "\n";
		str += "   host_net:     " + host.getUtilizationOfBw()+ "\n";
		str += "   host_net_tot: " + host.getBw()+ "\n";
		
		return str;
		
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
		String[] budgets = app_prop.getProperty(Constant.APPLICATION_BUDGET).toString().split(",");
		Double number = new Double(numberOfCloudlets);
		if (number < 3)
			number = 3d;
		
		int frontend = new Double(Math.ceil(number * 20 / 100)).intValue();
		int database = new Double(Math.ceil(number * 20 / 100)).intValue();
		int appserver = number.intValue() - frontend - database;
		
		return new ThreeTierBusinessApplicationMeta(userID,places, budgets,frontend, appserver, database);
	}
	
	private static PowerHostUtilizationHistory createHost(Properties prop,  int index){
		String[] ram_increments = prop.getProperty("ram_increments").toString().split(",");
		Double ram_inc =  new Double(ram_increments[index]);
		System.out.println("ram_increments " + ram_inc + " -- " +	(new Double(prop.getProperty(Constant.RAM))+ram_inc));
		
		List<Pe> peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple(Double.parseDouble(prop.getProperty(Constant.MIPS)))));
		HostProfile prof = HostProfile.getDefault();
		prof.set(HostParams.STORAGE_MB, (new Long(prop.getProperty(Constant.STORE))*1024)+"");
		prof.set(HostParams.RAM_AMOUNT_MB,  (new Double((new Double(prop.getProperty(Constant.RAM))+ram_inc)*1024)).intValue()+"")  ;   //(new Double(prop.getProperty(Constant.RAM)+ram_inc)*1024).intValue()+"");
		prof.set(HostParams.BW_AMOUNT, (new Long(prop.getProperty(Constant.BW))*1024*1024)+"");

		return  HostProviderMetaScheduler.get(prof, peList);
	}

	public static FederationDatacenter getDatacenter(Properties prop, int size, String place, int index){
		FederationDatacenterProfileMeta prof = FederationDatacenterProfileMeta.getDefault();
		prof.set(DatacenterParams.COST_PER_BW, prop.getProperty(Constant.COST_BW));
		prof.set(DatacenterParams.COST_PER_MEM, prop.getProperty(Constant.COST_MEM));
		prof.set(DatacenterParams.COST_PER_SEC, prop.getProperty(Constant.COST_SEC));
		prof.set(DatacenterParams.COST_PER_STORAGE, prop.getProperty(Constant.COST_STORAGE));
		prof.set(DatacenterParams.PLACE, place);
		
		
		//make datacenter
		List<PowerHost> hostList = new ArrayList<>();
		for(int i=0;i<size;i++)
			hostList.add(createHost(prop, index));
		List<Storage> storageList = new ArrayList<Storage>();
		return FederationDatacenterProviderMeta.get(prof, hostList, storageList);
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
