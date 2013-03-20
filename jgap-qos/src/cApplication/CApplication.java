package cApplication;

import cApplicationIface.ICApplication;

public class CApplication implements ICApplication {
	
	private int ID;
	private String place;
	private double budget;
	
	private CApplicationNode[] nodes;
	
	public CApplication(){
		new CApplication(-1, null, 0, null);
	}
	
	public CApplication(int ID, String place, double badget, CApplicationNode[] nodes){
		this.ID = ID;
		this.place = place;
		this.budget = badget;
		this.nodes = nodes;
		
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		/*
		 * RIVEDERE!!!!!!
		 */
		if( o == null)
			return 1;
		CApplication app = (CApplication)o;
		if (ID == app.ID && place.equalsIgnoreCase(app.place)
				&& budget == app.budget)
			return 0;
		return ID = app.ID;
	}
	
	public Object clone(){
		return new CApplication(ID, place, budget, nodes);
	}
	
	@Override
	public void setBudget(double badget) {
		// TODO Auto-generated method stub
		if(badget >0 )
			this.budget = badget;
		
	}
	@Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if(place != null && place.length() >0)
			this.place = place;
		
	}
	
	@Override
	public double getBudget() {
		// TODO Auto-generated method stub
		return budget;
	}
	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setNodes(CApplicationNode[] nodes) {
		// TODO Auto-generated method stub
		this.nodes = nodes;
		
	}

	@Override
	public CApplicationNode[] getNodes() {
		// TODO Auto-generated method stub
		return nodes ;
	}
	
	
	

}
