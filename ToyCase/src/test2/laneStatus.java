package test2;

public class laneStatus {
	// Representation of states
	int state;
	
	// Time advance of the state
	private double sigma;
	
	// Lane array Status
	int[] len; 
	
	//Traffic Status (Go or Stop)
	int traffic;
	
	// Count of cars passing the lane
	int go;
	
	public laneStatus(int at){
		state = at;
		sigma = 3.0;
		traffic=1;
		len = new int[5];
		go=0;
	}
	
	
	public void setSigma(double d){
		sigma = d;
	}
	
	public void putValueinArray(int d){
		if(len[0] != 1)len[0] = d;
	}
	
	public int[] getLaneArray(int val){
		if(state!=5){
			if(state==3 || state==6){
				len[len.length-1]=0;
				go++;
			}
			
			evolveLane();
			putValueinArray(val);
		}
		return len;
	}
	
	public int getState(){
		if(traffic==0 && len[len.length-1]==0)state=2;
		else if(traffic==1 && len[len.length-1]==0)state=1;
		else if(traffic==0 && checkBlocked())state=5;
		else if(traffic==1 && checkBlocked())state=6;
		else if(traffic==0 && len[len.length-1]==1)state=4;
		else if(traffic==1 && len[len.length-1]==1)state=3;
		return state;
	}
	
	 public double getSigma(){
		 return sigma;
	 }
	 
	 public void evolveLane(){
		 for(int i=len.length-1;i>0;i--){
				if(len[i]==0){
					len[i]=len[i-1];
					len[i-1]=0;
				}
			}
	 }
	 
	 public boolean checkBlocked(){
		 for(int i=0;i<len.length;i++){
			 if(len[i]==0)return false;
		 }
		 return true;
	 }
}

