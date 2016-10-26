package test2;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import exceptions.*;
import model.*;

public class Collector extends AtomicModel {
	// Definition of S :
	// there is only one single possible state which lasts for ever until a trigger is received 
	// a state variable is used for the current repository (a file)
	PrintStream trajectory;
	Port aa;
	
	public Collector(String name) {
		super(name);
		
		// No output port: Y = {}
		
		// Definition of the input port (for X):
		// the name of the port is xxx.store if the name of the model is xxx
		// the value received is a color: "Green", "Yellow", "Red" or "Blink"
		//String[] colors = { "Green", "Yellow", "Red", "Off"};
		//addInputPortStructure(new DEVS_Enum(colors), this.getName()+".STORE", "Data to store");
		aa = new Port("b", new String());
		addInPort(aa);
		
		// State initialization: the name of the file is xxx.txt if the name of the model is xxx
		try {
			trajectory = new PrintStream(name+".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public double ta() {
		return Double.POSITIVE_INFINITY;
	}
	
	public void deltaInt() {
		// Not defined, since there is a single possible state which time advance value is +infinity
	}

	public void lambda(){
		// Not defined, for the same reasons
	}

	public void deltaExt(double e) {
		// Let's get the value received and the simulation time it has been received
		String received = (String) getInPortData(aa);
		double when = this.getSimulator().getTL();
		
		// Then store them in the following shape:
		// simulation time : data received
		trajectory.println(when + " : " + received);
	}

	@Override
	public void deltaInternal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deltaConfluent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double timeAdvance() {
		return Double.POSITIVE_INFINITY;
	}
}