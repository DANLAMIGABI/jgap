package it.cnr.isti.federation.resources;

import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Vm;

/*
 * The VMs returned reflect the first generation machines at Amazon EC2
 * http://aws.amazon.com/ec2/instance-types/
 * 
 * CPU mips are taken from here:
 * http://www.cloudiquity.com/2009/01/amazon-ec2-instances-and-cpuinfo/
 * 
 * bandwidth is quite obscure yet.
 */
public class VmProvider 
{
	private static int VM_COUNTER = 0;
	public static int userId; // TO BE SET UP BY THE MAIN
	
	public enum VmType
	{
		SMALL,
		MEDIUM,
		LARGE,
		XLARGE
	}
	
	public static Vm getVm(VmType type)
	{
		switch (type)
		{
			case SMALL:
			{
				return createSmall();
			}
			case MEDIUM:
			{
				return createMedium();
			}
			case LARGE:
			{
				return createLarge();
			}
			case XLARGE:
			{
				return createXLarge();
			}
			default:
			{
				return createSmall();
			}
		}
	}
	

	private static Vm createSmall()
	{		
//		Vm vm = new Vm(VM_COUNTER++, 
//				userId, 
//				1000, 
//				1, 
//				new Double(1.7 * 1024 ).intValue(), // RAM: 1.7 GB
//				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
//				new Long(160 * 1024), // DISK: 160 GB
//				"Xen", 
//				new CloudletSchedulerTimeShared());
		Vm vm = new Vm(VM_COUNTER++, 
				userId, 
				1000, 
				1, 
				new Double(2 ).intValue(), // RAM: 1.7 GB
				new Long(2048), // i assume at least 1MB p/s  
				new Long(100), // DISK: 160 GB
				"Xen", 
				new CloudletSchedulerTimeShared());
		//Vm vm = new Vm(VM_COUNTER++, userId, 1000, 1, 2048, 1000, 70000, "Xen", new CloudletSchedulerTimeShared());
		
		return vm;
	}
	
	private static Vm createMedium()
	{		
//		Vm vm = new Vm(VM_COUNTER++, 
//				userId, 
//				1000, // data not available, i assume as small instances
//				1, 
//				new Double(3.75 * 1024).intValue(), // 3.75 GB
//				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
//				new Long(410 * 1024), // 410 GB
//				"Xen", 
//				new CloudletSchedulerTimeShared());
		Vm vm = new Vm(VM_COUNTER++, 
				userId, 
				1000, 
				1, 
				new Double(4 ).intValue(), // RAM: 1.7 GB
				new Long(2048), // i assume at least 1MB p/s  
				new Long(200), // DISK: 160 GB
				"Xen", 
				new CloudletSchedulerTimeShared());
		
		return vm;
	}

	private static Vm createLarge()
	{		
//		Vm vm = new Vm(VM_COUNTER++, 
//				userId, 
//				1000, 
//				1, 
//				new Double(7.5 * 1024).intValue(), // 7.5 GB
//				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
//				new Long(850 * 1024), // 850 GB
//				"Xen", 
//				new CloudletSchedulerTimeShared());
		Vm vm = new Vm(VM_COUNTER++, 
				userId, 
				1000, 
				1, 
				new Double(8 ).intValue(), // RAM: 1.7 GB
				new Long(2048), // i assume at least 1MB p/s  
				new Long(500), // DISK: 160 GB
				"Xen", 
				new CloudletSchedulerTimeShared());
		
		return vm;
	}
	
	private static Vm createXLarge()
	{		
		Vm vm = new Vm(VM_COUNTER++, 
				userId, 
				5202.15 * 4, 
				4, 
				new Double(15 * 1024 * 1024 * 1024).intValue(), // 15 GB
				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
				new Long(1690 * 1024 * 1024 * 10249), // 1690 GB
				"Xen", 
				new CloudletSchedulerTimeShared());
		
		return vm;
	}

}
