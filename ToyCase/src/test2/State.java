package test2;

public class State {
	private int state;
	private double sigma;
	
	public State(int at){
		state = at;
		sigma = 0;
	}
	
	public void setState(int a){
		state = a;
	}
	
	public void setSigma(double d){
		sigma = d;
	}
	
	public int getState(){
		return state;
	}
	
	 public double getSigma(){
		 return sigma;
	 }
}
