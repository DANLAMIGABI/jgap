package it.cnr.isti;

import it.cnr.isti.federation.Allocation;
import it.cnr.isti.federation.Federation;
import it.cnr.isti.federation.FederationQueue;
import it.cnr.isti.federation.FederationQueueProfile;
import it.cnr.isti.federation.FederationQueueProvider;
import it.cnr.isti.federation.application.Application;
import it.cnr.isti.federation.resources.FederationDatacenter;
import it.cnr.isti.federation.resources.VmProvider;
import it.cnr.isti.test.DataSette;
import it.cnr.isti.test.TestResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

public class TestJgapMapping {
	public static List<FederationDatacenter> dcList;
	
	private static void printVmList(List<Vm> vmList){
		for(int i=0; i<vmList.size(); i++){
			Vm vm = vmList.get(i);
			Log.printLine("  Vm id: " +vm.getId());
			Log.printLine(" Vm ram: " + vm.getRam());
			Log.printLine("  Vm size: " + vm.getSize());
		}
	}
	
	private static void printHostInfo(Host host){
		
		Log.printLine("  hostid:"+ host.getId());
		Log.printLine("  host ram: " + host.getRam());
		Log.printLine("  host store: " + host.getStorage());
		Log.printLine("  host mips: " +host.getTotalMips());
		Log.printLine("  host pelist size: " + host.getPeList().size());
		printVmList(host.getVmList());
		Log.printLine();
	}
	
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
				+ "Data center ID" + indent + "VM ID" + indent + "Time" + indent
				+ "Start Time" + indent + "Finish Time");

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
	
	private static void printFederationDataCenter(FederationDatacenter fdata){
		Log.print("id: " + fdata.getId()+ " | name: "+fdata.getName());
	}
	
	public static void main (String[] args) throws Exception{
		//Log.disable();
		
		int cloudlet = 2;
		int datacenter = 3;
		
		int num_user = 1;   // users
        Calendar calendar = Calendar.getInstance();
        boolean trace_flag = true;  // trace events
        
        int hostPerDatacenter = 1 * cloudlet;

        // Initialize the CloudSim library
        CloudSim.init(num_user, calendar, trace_flag);
        
     // create datacenters
        // dcList = DataSette.generateDatanceters(datacenter, hostPerDatacenter, hostPerDatacenter, 4, 8);
        dcList = DataSette.generateDatanceters2(datacenter, hostPerDatacenter,1,4);
        
		// create the federation
        Federation fed = new Federation("Federation");
        fed.setDatacenters(dcList);
		CloudSim.addEntity(fed);
		VmProvider.userId = fed.getId();
		
		Iterator<FederationDatacenter> fedIter = dcList.iterator();
		while(fedIter.hasNext()){
			FederationDatacenter fdata = fedIter.next();
			printFederationDataCenter(fdata);
			List<Host> hostList = fdata.getHostList();
			Iterator<Host> hostIterator = hostList.iterator();
			while (hostIterator.hasNext()){
				Host host = hostIterator.next();
				printHostInfo(host);
				
			}
		}
		
		
		
		
		
		/*
		// create the applications
		List<Application> applications = new ArrayList<Application>();
		//applications.add(new SimpleApplication());
		//applications.add(new SimpleApplication());
		//applications.add(new ThreeTierBusinessApplication(1, 1, 1));
		applications.add(DataSette.getApplication(cloudlet));
		
		// create the queue
		FederationQueueProfile queueProfile = FederationQueueProfile.getDefault();
		FederationQueue queue = FederationQueueProvider.getFederationQueue(queueProfile, fed, applications);
		CloudSim.addEntity(queue);
		
		// create the end of the simulation
		CloudSim.terminateSimulation(1000000); // in milliseconds
		
		// print all the entites
		// for (SimEntity entity: CloudSim.getEntityList())
			// System.out.println(entity.getId()+ " - "+entity.getName());
		
		
		// here we start the simulation
		CloudSim.startSimulation();
		
		// print the debts
		// dc1.printDebts();
		
		List<Cloudlet> newList = fed.getReceivedCloudlet();
		printCloudletList(newList);

		// Print the debt of each user to each datacenter
		// datacenter0.printDebts();
		
		Log.printLine("\n========== ALLOCATIONS ==========");
		for (Allocation a: fed.getAllocations())
		{
			// Log.printLine(a.getDuration());
			TestResult res = new TestResult();
			res.simtime = a.getSimDuration();
			res.realtime = a.getRealDuration();
			
			if (a.isCompleted() == false)
				System.out.println("Failed");
			else
				System.out.println(a.getRealDuration());
		}
		*/
        
	}

}
