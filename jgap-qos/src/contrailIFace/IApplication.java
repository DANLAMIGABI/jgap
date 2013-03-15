package contrailIFace;

import org.jgap.IApplicationData;

public interface IApplication extends IApplicationData {
	
	public void setID(int id);
	
	public void setBadget(double badget);
	
// 	choose exception
	public void setPlace(String place);	
	
// 	choose exception
	public void setBandwidth(int bandwidth);
	
	public double getBadget();
	
	public String getPlace(); 
	
	public int getBandwidth();
	public int getID();


}
