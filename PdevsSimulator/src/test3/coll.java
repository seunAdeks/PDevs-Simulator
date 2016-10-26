package test3;

import exceptions.*;
import model.*;


import java.io.*;

public class coll extends AtomicModel {
	// Definition of S :
	// there is only one single possible state which lasts for ever until a trigger is received 
	// a state variable is used for the current repository (a file)
	PrintStream trajectory;
	Port aa;
	
	public coll(String name) {
		super(name);
		
		// No output port: Y = {}
		
		// Definition of the input port (for X):
		// the name of the port is xxx.store if the name of the model is xxx
		// the value received is a color: "Green", "Yellow", "Red" or "Blink"
		//String[] colors = { "Green", "Yellow", "Red", "Off"};
		//addInputPortStructure(new DEVS_String(), this.getName()+".STORE", "Data to store");
		//addInputPortStructure(new DEVS_Integer(),"SINKx.STORE", "Data to store");
		aa = new Port("b", new Integer(0));
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


	public void deltaExt(double e){
		// Let's get the value received and the simulation time it has been received
		//Port received = inputPort.getPort("a");
		int a = (Integer) getInPortData(aa);
		double when = this.getSimulator().getTL();
		//System.out.println(when+ " : " +getInputPortData("SINKx.STORE").toString());
		// Then store them in the following shape:
		// simulation time : data received
		trajectory.println(when + " : " + a);
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
	public void lambda() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double timeAdvance() {
		return Double.POSITIVE_INFINITY;
	}
}