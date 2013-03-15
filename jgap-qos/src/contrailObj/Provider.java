package contrailObj;

import contrailIFace.IProvider;

public class Provider implements IProvider {
	
	
	private static String place;
	private static int ID;
	private static int bandwidth;
	private static double ratePrice;
	/* not used at this time */
	private static char BandwidthUnit;
	

	public Provider(){
		bandwidth =-1;
		ratePrice =-1;
		place = null;
	}
	public Provider(String place, double ratePrice, int bandwidth ){
		if(place == null || ratePrice <= 0 || bandwidth <=0)
			return;
		Provider.place = place;
		Provider.bandwidth = bandwidth;
		Provider.ratePrice = ratePrice;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
			return 1;
		Provider prov = (Provider)o;
		if(place.compareTo(prov.getPlace()) == 0 &&
				bandwidth == prov.getBandwidth() &&
				ratePrice == prov.getRatePrice())
			return 1;
		return ID -  prov.getID();
	}
	
	@Override
	public Object clone(){
		return null;
	}

	@Override
	public int getBandwidth() {
		// TODO Auto-generated method stub
		return bandwidth;
	}

	@Override
	public void setRatePrice(double rate) {
		// TODO Auto-generated method stub
		if(rate > 0)
			Provider.ratePrice = rate;
		return;
		
	}

	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() != 0)
			Provider.place = place;
		return;
		
	}

	@Override
	public void setBandwidth(int bandwidth) {
		// TODO Auto-generated method stub
		if( bandwidth >0)
			Provider.bandwidth = bandwidth;
		
	}

	@Override
	public double getRatePrice() {
		// TODO Auto-generated method stub
		return ratePrice;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
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
