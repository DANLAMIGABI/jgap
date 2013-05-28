package it.cnr.isti.federation;

import it.cnr.isti.federation.StorageProfile.StorageParams;

import java.lang.reflect.Constructor;

import org.cloudbus.cloudsim.Storage;

public class StorageProvider 
{
	private Storage createStorage(StorageProfile profile)
	{
		Storage storage = null;
		
		try
		{
			Class clazz = Class.forName(profile.get(StorageParams.CLASS));
			storage = (Storage)clazz.getDeclaredConstructor(Double.class).newInstance(
					initargs
					);
		}
		catch (Exception e)
		{
			// TODO: log the error
			e.printStackTrace();
		}
	}
}
