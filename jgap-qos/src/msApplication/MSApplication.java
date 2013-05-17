package msApplication;

import java.util.HashMap;
import java.util.List;

import msApplicationIface.IMSApplication;


public class MSApplication implements IMSApplication {

	private int ID;
	private String place;
	private double budget;

	private List<MSApplicationNode> nodes;

	// testing
	private HashMap<String, Object> characteristic;

	public MSApplication() {
		new MSApplication(-1, null, 0, null);
	}

	public MSApplication(int ID, String place, double badget,
			List<MSApplicationNode> nodes) {
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
		if (o == null)
			return 1;
		MSApplication app = (MSApplication) o;
		if (ID == app.ID && place.equalsIgnoreCase(app.place))
			return 0;
		return ID = app.ID;
	}

	public Object clone() {
		return new MSApplication(ID, place, budget, nodes);
	}

	
	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setNodes(List<MSApplicationNode> nodes) {
		// TODO Auto-generated method stub
		this.nodes = nodes;

	}

	@Override
	public List<MSApplicationNode> getNodes() {
		// TODO Auto-generated method stub
		return nodes;
	}

	@Override
	public void setCharacteristic(HashMap<String, Object> characteristic) {
		// TODO Auto-generated method stub
		this.characteristic = characteristic;
	}

	@Override
	public HashMap<String, Object> getCharacteristic() {
		// TODO Auto-generated method stub
		return characteristic;
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.ID = id;
	}
	
	/*disabled
	 @Override
	public void setPlace(String place) {
		// TODO Auto-generated method stub
		if (place != null && place.length() > 0)
			this.place = place;

	}

	
	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return place;
	}
	 */

}
