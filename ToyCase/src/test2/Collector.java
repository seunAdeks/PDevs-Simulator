package test2;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import exceptions.*;
import model.*;

public class Collector extends AtomicModel {
	// Definition of S :
	// there is only one single possible state which lasts for ever until a trigger is received 
	// a state variable is used for the current repository (a file)
	PrintStream trajectory;
	Port aa;
	Port bb;
	
	public Collector(String name) {
		super(name);
		
		// No output port: Y = {}
		
		// Definition of the input port (for X):
		// the name of the port is xxx.store if the name of the model is xxx
		// the value received is a color: "Green", "Yellow", "Red" or "Blink"
		//String[] colors = { "Green", "Yellow", "Red", "Off"};
		//addInputPortStructure(new DEVS_Enum(colors), this.getName()+".STORE", "Data to store");
		aa = new Port(new String());
		addInPort(aa);
		
		bb = new Port(new String());
		addInPort(bb);
		
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
		ArrayList<Port> col = inputPort.getPortWithValue();
		
		// Let's get the value received and the simulation time it has been received
		//System.out.println(this.getName()+" "+aa.getValuesCount()+ " and "+bb.getValuesCount() + this.inputPort.hasPortWithValue());
		String received = (String) getInPortData(aa);
		String received2 = (String) getInPortData(bb);
		//if(inputPort.hasPortWithValue())
		//System.out.println(this.getName()+" "+aa.getValuesCount()+ " and "+bb.getValuesCount()+" "+inputPort.portBag.size() +" "+ this.inputPort.hasPortWithValue()+" "+received+aa.getValue());
		double when = this.getSimulator().getTL();
		
		// Then store them in the following shape:
		// simulation time : data received
		if(col.contains(aa)){
			trajectory.println(when + " : LIGHT : " + received);
		}
		
		if(col.contains(bb)){
			trajectory.println(when + " : " + received2);
		}
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