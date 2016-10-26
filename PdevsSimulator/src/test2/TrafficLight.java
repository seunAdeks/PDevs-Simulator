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
	
	public TrafficLight(String name) {
		super(name);
		
		// Definition of the output port (for Y):
		// the name of the port is xxx.color if the name of the model is xxx
		// the value sent out is a color: "Green", "Yellow", "Red" or "Off"
		//String[] colors = { "Green", "Yellow", "Red", "Off"};
		//addOutputPortStructure(new DEVS_Enum(colors), this.getName()+".COLOR", "Color sent out for users");
		out = new Port("out", new String());
		addOutPort(out);
		
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
		if (stateColor==0) toSend = "Yellow";
		if (stateColor==1) toSend = "Red";
		if (stateColor==2) toSend = "Yellow";
		if (stateColor==4) toSend = "Off";
		if (stateColor==5) toSend = "Green";
		if (stateColor==6) toSend = "Green";
		setOutPortdata(toSend, out);
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
		System.out.println(received+"This");
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
		else if (stateColor == 2) return 5.0;
		else if (stateColor == 3) return Double.POSITIVE_INFINITY;
		else return 0;
	}
}