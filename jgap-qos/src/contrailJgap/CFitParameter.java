package contrailJgap;

public class CFitParameter {

	public  double violation;
	public  double ascendent;
	public  double descendent;
	public  double equal;
	public  boolean validity;
	public  int firstAllele;
	public  int tmpCounter;
	
	public CFitParameter(){
		// TODO Auto-generated constructor stub
		descendent =0;
		ascendent = 0;
		violation = 0;
		equal =0;
		validity = true;
		firstAllele = -1;
		tmpCounter =0;
	}
	
}
