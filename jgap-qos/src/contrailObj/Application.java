package contrailObj;

import contrailIFace.IApplication;

public class Application implements IApplication {
	
	private static String place;
	private static int ID;
	private static int bandwidth;
	
	private static double badget;
	
	public Application(){
		place = null;
		bandwidth = -1;
		badget = -1;
	}
	public Application(String place,int bandwidth, double badget){
		if(place == null || place.length() == 0 || bandwidth <= 0 || badget <=0){
			//choose exception
			return;
		}
		Application.place = place;
		Application.bandwidth = bandwidth;
		Application.badget = badget;
		
		
	}
	
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o == null)
			return 1;
		Application app = (Application)o;
		//uguaglianza solo su ID ?? plausibile?
		if( place.compareTo(app.getPlace()) == 0 &&
				bandwidth == app.getBandwidth() &&
				badget == app.getBadget() &&
				ID == app.getID())
			return 0;
		return ID - app.getID();
	}

	@Override
	public Object clone(){
		return new Application(place,bandwidth,badget);
	}

	@Override
	public void setBadget(double badget) {
		// TODO Auto-generated method stub
		if(badget >0)
			Application.badget = badget;
		return;
	}

	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() != 0)
			Application.place = place;
		// choose exception
		return;
	}

	@Override
	public void setBandwidth(int bandwidth) {
		// TODO Auto-generated method stub
		if( bandwidth >0)
			Application.bandwidth = bandwidth;
		return;
	}

	@Override
	public double getBadget() {
		// TODO Auto-generated method stub
		return badget;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}

	@Override
	public int getBandwidth() {
		// TODO Auto-generated method stub
		return bandwidth;
	}
	
	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		ID = id;
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	

}
