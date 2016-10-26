package model;

public class CouplingPair {
	private Pair P1;
	private Pair P2;
	
	public CouplingPair(Pair p1, Pair p2){
		P1 = p1;
		P2 = p2;
	}
	
	public Pair getSender(){
		return P1;
	}
	
	public Pair getReceiver(){
		return P2;
	}
	
	public Port getConnection(BasicModel m){
		if(m == P1.getModel()){
			return P2.getPort();
		}
		return null;
	}
	
	public boolean isModel(BasicModel model){
		return (P1.getModel() == model || P2.getModel()== model);
	}

}
