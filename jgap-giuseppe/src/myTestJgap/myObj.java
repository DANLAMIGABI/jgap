package myTestJgap;

import org.jgap.IApplicationData;
import org.jgap.ICloneHandler;

public class myObj implements IApplicationData{
	
	private double costo;
	private int id;
	private double guadagno;
	
	public myObj(){
		this.costo =-1;
		this.guadagno = -1;
		this.id = -1;
	}
	
	public myObj(int costo, int id, double guadagno){
		this.costo = costo;
		this.id = id;
		this.guadagno = guadagno;
	}
	
	public double getCosto(){
		return costo;
	}
	public int getID(){
		return this.id;
	}
	public double getGuadagno(){
		return this.guadagno;
	}
	@Override
	public int compareTo(Object o){
		myObj obj = (myObj)o;
		if(obj == null)
			return 1;
		return Double.compare(costo, obj.costo);
	}
	@Override
	public Object clone() throws CloneNotSupportedException{
		//return super.clone();
		myObj o = new myObj();
		o.guadagno = this.guadagno;
		o.costo = this.costo;
		o.id = this.id;
		return (Object)o;
	}

	
	
	

}
