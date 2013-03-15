package myTestJgap;

public class testMain {
	public static void main(String[] args) throws Exception {
		
		myObj[] provider =  new myObj[2];
		provider[0] = new myObj(10, 1, -1);
		provider[1] = new myObj(30, 2, -1);
		
		myObj[] application =  new myObj[2];
		application[0] = new myObj(10, 1, -1);
		application[1] = new myObj(30, 2, -1);
		
		System.out.println("compare: " + provider.equals(application));
		
	}

}
