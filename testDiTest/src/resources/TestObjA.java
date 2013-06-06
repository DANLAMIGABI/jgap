package resources;

import java.io.Serializable;

public class TestObjA implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TestObjB bobj ;
	
	public TestObjA(){
		bobj = new TestObjB();
	}


}
