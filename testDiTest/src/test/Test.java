package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import resources.TestObjA;

public class Test {
	
	public static void main(String args[]) throws IOException{
		String address = "testSerializzazione";
		TestObjA aobj = new TestObjA();
		
		FileOutputStream fout = new FileOutputStream(address);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(aobj);
		oos.close();
	}

}
