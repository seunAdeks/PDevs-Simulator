package test2;
import model.AtomicModel;
import model.Port;


import java.util.*;

public class CarGenerator extends AtomicModel {
	// Definition of S : no state variable since there is only one single possible state
	
	// Additional attribute necessary for random number generation
	Random rd;
	Integer nb;
	Port gen;
	
	public CarGenerator(String name) {
		/*
		 * This Car generator generates a car in 4 to 6 time units
		 */
		super(name);
		
		// Definition of the output port (for Y):
		// the name of the port is xxx.out if the name of the model is xxx
		gen = new Port(new String());
		addOutPort(gen);
		// No input port : X = {}
		
		// No state initialization: the state will always be the same implicit one
		
		// Initialization of the additional attribute
		rd = new Random();
		
	}

	public void deltaInternal() {
		// Nothing to say: we always return to the same implicit state
	}

	public double timeAdvance() {
		// The real value returned is in [4, 6]
		return (rd.nextDouble()*4 + 2.0);
	}

	public void lambda(){
		// The value sent out is randomly 0 or 1
		nb = rd.nextInt(2);
		setOutPortdata(nb.toString(), gen);
	}

	public void deltaExt(double e){
		// Not defined, since there is no input
	}
	
	public void deltaConfluent() {
		// Not defined, since there is no input
	}
}