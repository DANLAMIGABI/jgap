package temp;

import msApplication.MSApplication;
import msProvider.MSProvider;

import org.jgap.IApplicationData;


public class ChApplicationData implements IApplicationData {
	private MSApplication[] appList;
	private MSProvider[] provList;

	public ChApplicationData() {
		new ChApplicationData(null, null);
	}

	public ChApplicationData(MSApplication[] appList, MSProvider[] provList) {
		this.appList = appList;
		this.provList = provList;
	}
	
	public void setAppList(MSApplication[] applist){
		this.appList = applist;
	}
	public void setProvList(MSProvider[] provList){
		this.provList =  provList;
	}
	
	public MSApplication[] getAppList(){
		return appList;
	}
	public MSProvider[] getProvList(){
		return provList;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if( o == null)
			return 1;
//		ChApplicationData data = (ChApplicationData)o;
//		if( appList.)
		return 0;
	}
	public Object clone(){
		return new ChApplicationData(appList, provList);
	}
}
