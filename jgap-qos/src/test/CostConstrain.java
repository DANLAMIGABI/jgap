package test;
//
//import cApplicationIface.ICApplication;
//import cPolicy.ICConstrain;
//import cProviderIface.ICProvider;
//
//public class CostConstrain implements ICConstrain {
//	
//	private double weight;
//	private char type;
//	
//	public CostConstrain(double weight){
//		this.weight = weight;
//	}
//
//	@Override
//	public double evaluateConstrain(ICApplication app, ICProvider prov) {
//		// TODO Auto-generated method stub
//		return (prov.getCost() - app.getBudget()) * weight;
//	
//	}
//
//	@Override
//	public char getType() {
//		// TODO Auto-generated method stub
//		return type;
//	}
//
//	@Override
//	public void setType(char type) {
//		// TODO Auto-generated method stub
//		this.type = type;
//	}
//
//	@Override
//	public double getWeight() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setWeight(double weight) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//}
