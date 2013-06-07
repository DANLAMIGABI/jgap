package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import resources.TestObjA;

public class Test {
	
	public static void main(String args[]) throws IOException{
		
		Properties prop = new Properties();
		prop.load(new FileInputStream("testProperties"));
		
		String[] test = prop.getProperty("places").toString().split(",");
		
		System.out.println(test);
		for (String s: test){
			System.out.println(s);
		}
		
		List<String> strlist = new ArrayList<>();
		strlist.add("hello");
		
		System.out.println(strlist);
		
		
		
	}

}
