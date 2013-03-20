package test;

public class Test {
	
	
	
	private static void setTest(obj o){
		o.abc = 25;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		obj o = new obj();
		
		setTest(o);
		System.out.println("abc: " + o.abc);
		
	}

}
