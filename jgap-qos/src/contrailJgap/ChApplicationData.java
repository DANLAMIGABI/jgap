package contrailJgap;

import org.jgap.IApplicationData;

import cApplication.CApplication;
import cProvider.CProvider;

public class ChApplicationData implements IApplicationData {
	private CApplication[] appList;
	private CProvider[] provList;

	public ChApplicationData() {
		new ChApplicationData(null, null);
	}

	public ChApplicationData(CApplication[] appList, CProvider[] provList) {
		this.appList = appList;
		this.provList = provList;
	}
	
	public void setAppList(CApplication[] applist){
		this.appList = applist;
	}
	public void setProvList(CProvider[] provList){
		this.provList =  provList;
	}
	
	public CApplication[] getAppList(){
		return appList;
	}
	public CProvider[] getProvList(){
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
