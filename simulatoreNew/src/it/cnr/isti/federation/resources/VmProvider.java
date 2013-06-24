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
		Vm vm = new Vm(VM_COUNTER++, 
				userId, 
				6502.18, 
				1, 
				new Double(4 * 1024 *1024 ).intValue(), // RAM: 4 GB
				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
				new Long(128 * 1024*1024), // DISK: 128 GB
				"Xen", 
				new CloudletSchedulerTimeShared());
		
		return vm;
	}
	
	private static Vm createMedium()
	{		
		Vm vm = new Vm(VM_COUNTER++, 
				userId, 
				6502.18, // data not available, i assume as small instances
				1, 
				new Double(8 * 1024*1024).intValue(), // 8 GB
				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
				new Long(256 * 1024*1024), // 256 GB
				"Xen", 
				new CloudletSchedulerTimeShared());
		
		return vm;
	}

	private static Vm createLarge()
	{		
		Vm vm = new Vm(VM_COUNTER++, 
				userId, 
				8022, 
				2, 
				new Double(16 * 1024*1024).intValue(), // 16 GB
				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
				new Long(512 * 1024*1024), // 500GB
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
				new Double(32 * 1024 * 1024).intValue(), // 32 GB
				new Long(1 * 1024 * 1024), // i assume at least 1MB p/s  
				new Long(1000 * 1024 * 1024), // 1TB
				"Xen", 
				new CloudletSchedulerTimeShared());
		
		return vm;
	}

}
