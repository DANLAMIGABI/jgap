package myTestJgap;

public class myAllele {

	private myObj app;
	private myObj prov;
	private int index;
	
	public myAllele(myObj app, myObj prov){
		this.app = app;
		this.prov = prov;
		this.index = -1;
	}
	
	public myObj getApp(){
		return this.app;
	}
	public myObj getProvider(){
		return this.prov;
	}
	
	public int getMatchIndex(){
		return this.index;
	}
		
}
