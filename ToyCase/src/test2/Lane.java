package test2;
import java.util.ArrayList;
import java.util.Arrays;


import model.*;
;


public class Lane extends AtomicModel{
	// Definition of S: a state variable is used for the current color
	
	
	// 1 for active lane, move traffic
	// 2 for active lane, stop traffic
	// 3 for car at end of lane, move traffic
	// 4 for car at end of lane, stop traffic
	// 5 for full lane, stop traffic
	// 6 for full lane, move traffic
	laneStatus ln;
	int state;
	
	// 1 for move{green,yellow,off}, 0 for stop{red}
	int val;
	
	// car leaving the lane
	double sigma;
	
	Port fromCarGen,toColl,fromTraffic;
		
	
	public Lane(String name) {
		super(name);
		
		// Definition of the output port (for Y):
		// the name of the port is xxx.status if the name of the model is xxx
		// the value sent out is a string that reflects state of a lane
		fromCarGen = new Port(new String());
		addInPort(fromCarGen);		// number of cars output

		
		// Definition of the input port (for X):
		// the name of the port is xxx.command if the name of the model is xxx
		// the value received is a command: 0-No car or 1-new car
		fromTraffic = new Port(new String());
		addInPort(fromTraffic);		
		// Definition of the input port (for X):
		// the name of the port is xxx.command if the name of the model is xxx
		// the value received is a command from traffic light: 
		//	0-stop(Cars cannot move out of the lane) or 
		//	1-new car(Cars can move out of the lane)
		toColl = new Port(new String());
		addOutPort(toColl);		
		
		//Initialization of state: starts at move state
		ln = new laneStatus(0);
		
		//Initialization of car ready to appear in the lane
		val = 0;
		
	}

	public void deltaExt(double e){
		
		ArrayList<Port> col = inputPort.getPortWithValue();
		
		// Let's get the value received
		String receive = (String) getInPortData(fromCarGen);
		String rec = (String) getInPortData(fromTraffic);
		
		if(col.contains(fromCarGen)){			
			int	received = Integer.parseInt(receive);
			
			
			// in case of 0, the lane should add a space at the start of lane at the end of Ta
			// in case of 1, the lane should add a new car at the start of lane at the end of Ta
			// these are done whatever the current state is and whatever the elapsed time is
			// and provided there is no car at the spot
			double when = this.getSimulator().getTL();
			
			if (received == 0){
				val = 0;
			}
			else if(received == 1){
				val = 1;
			}
		
		}
		
		if(col.contains(fromTraffic)){
			// Then process it:
			// Green, Yellow and Off implies traffic can exit the lane
			// Red implies vice-versa
			if(rec.contains("Green")){
				ln.traffic=1;
				state=ln.getState();
			}
			else if(rec.contains("Red") || rec.contains("Yellow") || rec.contains("Off")){
				ln.traffic=0;
				state=ln.getState();
			}
		}
		
		
	
		ln.setSigma(ln.getSigma()-e);
	}

	@Override
	public void deltaInternal() {
		// Determine new state based on the current array of the lane and traffic value
		state = ln.getState();
		ln.setSigma(3.0); 	
	}

	@Override
	public void lambda() {
		// Send the lane array as a String of Road state to the main collector		
		setOutPortdata(this.getName() + " : " +Arrays.toString(ln.getLaneArray(val))+ " : "+ln.go, toColl);
		val=0;
	}

	@Override
	public double timeAdvance() {
		return ln.getSigma();
	}

	@Override
	public void deltaConfluent() {
		deltaExt(0);
		deltaInternal();
	}
}
