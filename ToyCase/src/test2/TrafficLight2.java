package test2;

import java.util.ArrayList;

import model.*;

public class TrafficLight2 extends AtomicModel {
	// Definition of S: a state variable is used for the current color

	int stateColor;
	// 0 for Go
	// 1 for Ready to stop
	// 2 for Stop
	// 3 for ShutDown
	// 4 for Before ShutDown
	// 5 for After ShutDown
	// 6 for Ready to go
	// 7 for hold state
	
	Port in2;
	Port in3;
	Port out2;
	Port out2Lane2;
	
	public TrafficLight2(String name) {
		super(name);
		
		// Definition of the output port (for Y):
		// the name of the port is xxx.color if the name of the model is xxx
		// the value sent out is a color: "Green", "Yellow", "Red" or "Off"
		//String[] colors = { "Green", "Yellow", "Red", "Off"};
		//addOutputPortStructure(new DEVS_Enum(colors), this.getName()+".COLOR", "Color sent out for users");
		out2 = new Port(new String());
		addOutPort(out2);
		
		out2Lane2 = new Port(new String());
		addOutPort(out2Lane2);
		
		// Definition of the input port (for X):
		// the name of the port is xxx.command if the name of the model is xxx
		// the value received is a command: "Failure" or "Recovery"
		//String[] commands = { "Failure", "Recovery"};
		//addInputPortStructure(new DEVS_Enum(commands), this.getName()+".COMMAND", "Trigger received");
		in2 = new Port(new String());
		addInPort(in2);
		
		//Definition of input port from the other Traffic Light
		in3 = new Port(new String());
		addInPort(in3);
		
		// State initialization: the light starts Green
		stateColor = 7;
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
		setOutPortdata(toSend, out2);
		setOutPortdata(toSend, out2Lane2);
	}

	public void deltaExt(double e){
		
		//System.out.println("call External trans at time "+ getSimulator().getTL());
		
		ArrayList<Port> col = inputPort.getPortWithValue();
		
		// Always get values from port with value
		String received = (String) getInPortData(in2);
		String received2 = (String) getInPortData(in3);
		
		if(col.contains(in2)){		
			// Then process it:
			// in case of failure, the system goes to ShutDown
			// in case of recovery, it goes to Green
			// these are done whatever the current state is and whatever the elapsed time is
			if (received.compareTo("Failure") == 0) stateColor = 4;
			else if (received.compareTo("Recovery") == 0) stateColor = 7;
			else {
				System.out.println("Unexpected value received in " + this.getName() + ":" + received);
				 return;
			}
		}
		if (col.contains(in3) && stateColor != 3){
			
			// Then process it:
			// in case of failure, the system goes to ShutDown
			// in case of recovery, it goes to Green
			// these are done whatever the current state is and whatever the elapsed time is
			if (received2.compareTo("") != 0) stateColor = Integer.parseInt(received2);
			else if (received2.compareTo("") == 0){
				System.out.println("Empty other traffic value received in " + this.getName() + ":" + received2);
				 return;
			}
			else {
				System.out.println("Unexpected value received in " + this.getName() + ":" + received2);
				 return;
			}
		}
	}

	@Override
	public void deltaInternal() {
		// The behavior is the following:
		// Go -> Ready to stop -> Stop -> Ready to go -> Go -> Ready to stop -> ...
		// Special cases: Before ShutDown -> ShutDown, After ShutDown -> Green 
		if (stateColor == 0) stateColor = 7;
		else if (stateColor == 1) stateColor = 7;
		else if (stateColor == 2) stateColor = 7;
		else if (stateColor == 4) stateColor = 3;
		else if (stateColor == 5) stateColor = 7;
		else if (stateColor == 6) stateColor = 7;
	}

	@Override
	public void deltaConfluent() {
		
		System.out.println("call confluent at time "+ getSimulator().getTL());
		
		ArrayList<Port> col = inputPort.getPortWithValue();
		
		if(col.contains(in2) && col.contains(in3)){		
				// Let's get the value received
				String received = (String) getInPortData(in2);
				String received2 = (String) getInPortData(in3);
				//System.out.println(received+"This");
				// Then process it:
				// in case of failure, the system goes to ShutDown
				// in case of recovery, it goes to Green
				// these are done whatever the current state is and whatever the elapsed time is
				if (received.compareTo("Failure") == 0){
					stateColor = 4;
				}else if (received.compareTo("Recovery") == 0 && received2.compareTo("") != 0){
					stateColor = Integer.parseInt(received2);
				}
				else if (received.compareTo("Recovery") == 0) stateColor = 7;
				else {
					System.out.println("Unexpected value received in " + this.getName() + ":" + received);
					 return;
				}
		} else if(col.contains(in2)){
			// Let's get the value received
			String received = (String) getInPortData(in2);
			
			// Then process it:
			// in case of failure, the system goes to ShutDown
			// in case of recovery, it goes to Green
			// these are done whatever the current state is and whatever the elapsed time is
			if (received.compareTo("Failure") == 0) stateColor = 4;
			else if (received.compareTo("Recovery") == 0) stateColor = 7;
			else {
				System.out.println("Unexpected value received in " + this.getName() + ":" + received);
				 return;
			}
		}
		else if (col.contains(in3)){
			// Let's get the value received
			String received = (String) getInPortData(in3);
			
			// Then process it:
			// in case of failure, the system goes to ShutDown
			// in case of recovery, it goes to Green
			// these are done whatever the current state is and whatever the elapsed time is
			if (received.compareTo("") != 0) 
					stateColor = Integer.parseInt(received);
			else if (received.compareTo("") == 0){
				System.out.println("Empty other traffic value received in " + this.getName() + ":" + received);
				 return;
			}
			else {
				System.out.println("Unexpected value received in " + this.getName() + ":" + received);
				 return;
			}
		}
	}

	@Override
	public double timeAdvance() {
		// ta(Go) = 5
		// ta(Ready) = 1
		// ta(Stop) = 3
		// ta(ShutDown) = +infinity
		// ta(Before ShutDown) = ta(After ShutDown) = 0
		/*if (stateColor == 0) return 8.0;
		else if (stateColor == 1 || stateColor == 6) return 2.0;
		else if (stateColor == 2) return 8.0;
		else*/ if (stateColor == 3 || stateColor == 7) return Double.POSITIVE_INFINITY;
		else return 0;
	}
}