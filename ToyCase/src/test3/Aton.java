package test3;
import model.*;
import exceptions.*;

import java.util.*;

public class Aton extends AtomicModel {
	// Definition of S : no state variable since there is only one single possible state
	
	// Additional attribute necessary for random number generation
	Random rd;
	int nb;
	Port as;
	//Transport forMe;
	
	public Aton(String name) {
		super(name);
		
		//forMe = new Transport();
		
		// Definition of the output port (for Y):
		// the name of the port is xxx.out if the name of the model is xxx
		// the value sent out is a command: "Failure" or "Recovery"
		//Integer[] commands = {0, 1};
		as = new Port("a", new Integer(0));
		addOutPort(as);
		
		// No input port : X = {}
		
		// No state initialization: the state will always be the same implicit one
		
		// Initialization of the additional attribute
		rd = new Random();
		
	}

	public void deltaInt() {
		// Nothing to say: we always return to the same implicit state
	}

	public double ta() {
		// The real value returned is in [4, 20]
		return (rd.nextDouble()*4 + 2.0);
	}

	public void lambda() {
		// The value sent out is randomly "Failure" or "Recovery"
		nb = rd.nextInt(2);
		//String toSend = (nb == 0)?"Failure":"Recovery";
		//nb = (nb+1)%2;
		setOutPortdata(nb,as);
		//forMe.setValue(nb);
	}

	public void deltaExt(double e){
		// Not defined, since there is no input
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
		// The real value returned is in [4, 20]
		//return (rd.nextDouble()*4 + 2.0);
		return 2.0;
	}
}