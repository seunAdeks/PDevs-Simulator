package test2;

import model.*;

public class TrafficLight extends AtomicModel {
	// Definition of S: a state variable is used for the current color

	int stateColor;
	// 0 for Go
	// 1 for Ready to stop
	// 2 for Stop
	// 3 for ShutDown
	// 4 for Before ShutDown
	// 5 for After ShutDown
	// 6 for Ready to go
	
	Port in;
	Port out;
	Port out2Lane;
	Port outt;
	
	public TrafficLight(String name) {
		super(name);
		
		// Definition of the output port (for Y):
		// the name of the port is xxx.color if the name of the model is xxx
		// the value sent out is a color: "Green", "Yellow", "Red" or "Off"
		//String[] colors = { "Green", "Yellow", "Red", "Off"};
		//addOutputPortStructure(new DEVS_Enum(colors), this.getName()+".COLOR", "Color sent out for users");
		out = new Port("out", new String());
		addOutPort(out);
		
		out2Lane = new Port("outing", new String());
		addOutPort(out2Lane);
		
		//Output to the other Traffic light
		outt =new Port(new String());
		addOutPort(outt);
		
		// Definition of the input port (for X):
		// the name of the port is xxx.command if the name of the model is xxx
		// the value received is a command: "Failure" or "Recovery"
		//String[] commands = { "Failure", "Recovery"};
		//addInputPortStructure(new DEVS_Enum(commands), this.getName()+".COMMAND", "Trigger received");
		in = new Port("in", new String());
		addInPort(in);
		
		// State initialization: the light starts Green
		stateColor = 0;
	}

	public void deltaInt() {
		// The behavior is the following:
		// Go -> Ready to stop -> Stop -> Ready to go -> Go -> Ready to stop -> ...
		// Special cases: Before ShutDown -> ShutDown, After ShutDown -> Green 
		if (stateColor == 0) stateColor = 1;
		else if (stateColor == 1) stateColor = 2;
		else if (stateColor == 2) stateColor = 6;
		else if (stateColor == 4) stateColor = 3;
		else if (stateColor == 5) stateColor = 0;
		else if (stateColor == 6) stateColor = 0;
	}

	public double ta() {
		// ta(Go) = 5
		// ta(Ready) = 1
		// ta(Stop) = 3
		// ta(ShutDown) = +infinity
		// ta(Before ShutDown) = ta(After ShutDown) = 0
		if (stateColor == 0) return 8.0;
		else if (stateColor == 1 || stateColor == 6) return 2.0;
		else if (stateColor == 2) return 5.0;
		else if (stateColor == 3) return Double.POSITIVE_INFINITY;
		else return 0;
	}

	public void lambda(){
		// L(Go) = Yellow
		// L(Ready) = Red
		// L(Stop) = Green
		// L(Before ShutDown) = Off
		// L(After ShutDown) = Green
		// There is no L for ShutDown, since ta(ShutDown) = +infinity
		String toSend = "Green";
		String to = "";
		if (stateColor==0){
			toSend = "Yellow";
			to = "2"; // Red outputs ready to stop
		}
		if (stateColor==1){
			toSend = "Red";
			to = "6"; // ready to go outputs green
		}
		if (stateColor==2){
			toSend = "Yellow";
			to = "0"; // Green outputs ready to stop
		}
		if (stateColor==4){
			toSend = "Off";
			to = "1"; // ready to stop outputs red
		}
		if (stateColor==5){
			toSend = "Green";
			to = "1"; // ready to stop outputs red
		}
		if (stateColor==6){
			toSend = "Green";
			to = "1"; // ready to stop outputs red
		}
		setOutPortdata(toSend, out);
		setOutPortdata(toSend, out2Lane);
		setOutPortdata(to, outt);
	}

	public void deltaExt(double e){
		// Let's get the value received
		String received = (String) getInPortData(in);
		
		// Then process it:
		// in case of failure, the system goes to ShutDown
		// in case of recovery, it goes to Green
		// these are done whatever the current state is and whatever the elapsed time is
		if (received.compareTo("Failure") == 0) stateColor = 4;
		else if (received.compareTo("Recovery") == 0) stateColor = 5;
		else {
			System.out.println("Unexpected value received in " + this.getName() + ":" + received);
			 return;
		}
	}

	@Override
	public void deltaInternal() {
		// The behavior is the following:
		// Go -> Ready to stop -> Stop -> Ready to go -> Go -> Ready to stop -> ...
		// Special cases: Before ShutDown -> ShutDown, After ShutDown -> Green 
		if (stateColor == 0) stateColor = 1;
		else if (stateColor == 1) stateColor = 2;
		else if (stateColor == 2) stateColor = 6;
		else if (stateColor == 4) stateColor = 3;
		else if (stateColor == 5) stateColor = 0;
		else if (stateColor == 6) stateColor = 0;
	}

	@Override
	public void deltaConfluent() {
		// Let's get the value received
		String received = (String) getInPortData(in);
		//System.out.println(received+"This");
		// Then process it:
		// in case of failure, the system goes to ShutDown
		// in case of recovery, it goes to Green
		// these are done whatever the current state is and whatever the elapsed time is
		if (received.compareTo("Failure") == 0) stateColor = 4;
		else if (received.compareTo("Recovery") == 0) stateColor = 5;
		else {
			System.out.println("Unexpected value received in " + this.getName() + ":" + received);
			 return;
		}
	}

	@Override
	public double timeAdvance() {
		// ta(Go) = 5
		// ta(Ready) = 1
		// ta(Stop) = 3
		// ta(ShutDown) = +infinity
		// ta(Before ShutDown) = ta(After ShutDown) = 0
		if (stateColor == 0) return 8.0;
		else if (stateColor == 1 || stateColor == 6) return 2.0;
		else if (stateColor == 2) return 8.0;
		else if (stateColor == 3) return Double.POSITIVE_INFINITY;
		else return 0;
	}
}