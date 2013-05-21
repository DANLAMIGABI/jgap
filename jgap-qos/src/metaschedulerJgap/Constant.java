package metaschedulerJgap;

public class Constant {
	public static final String BW = "BW_AMOUNT";
	public static final String STORE = "STORAGE_MB";
	public static final String RAM = "RAM_AMOUNT_MB";
	public static final String MIPS = "MIPS";
	
	public static final String BUDGET = "BUDGET";
	
	public static final String COST_SEC = "COST_PER_SEC";
	public static final String COST_MEM = "COST_PER_MEM";
	public static final String COST_STORAGE = "COST_PER_STORAGE";
	public static final String COST_BW = "COST_PER_BW";

	public static final String PLACE = "PLACE";
	public static final String ID = "ID";
	
	public static final String[] aggregationParam = {"RAM_AMOUNT_MB", "BW_AMOUNT", "STORAGE_MB"};
	
	
	//MAPPING JGAP
	public static final int POP_SIZE = 5;
//	public static final int APP_SIZE = 2;
	//public static final int PROV_SIZE = 6;
	//public static final int TARGET = 5;
	public static final int EVOLUTION_SIZE=1;
	
	public static final char ASCENDENT_TYPE = 'A';
	public static final char DESCENDENT_TYPE = 'D';
	public static final char EQUAL_TYPE = 'E';
	
	public static final char GLOBAL_CONSTRAIN ='G';
	public static final char LOCAL_CONSTRAIN = 'L';
	
}
