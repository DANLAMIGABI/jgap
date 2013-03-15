package contrailIFace;

import org.jgap.IApplicationData;

public interface IProvider extends IApplicationData{
	
	public void setID(int id);
	
	public void setRatePrice(double rate);
	
// 	choose exception
	public void setPlace(String place);	
	
	public int getID();
	
// 	choose exception
	public void setBandwidth(int bandwidth);
	
	public double getRatePrice();
	
	public String getPlace(); 
	
	public int getBandwidth();
}
