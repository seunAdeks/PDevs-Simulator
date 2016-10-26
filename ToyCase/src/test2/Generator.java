package test2;
import model.*;
import exceptions.*;
import java.util.*;

public class Generator extends AtomicModel {
	// Definition of S : no state variable since there is only one single possible state
	
	// Additional attribute necessary for random number generation
	Random rd;
	int nb;
	Port a;
	
	public Generator(String name) {
		super(name);
		
		// Definition of the output port (for Y):
		// the name of the port is xxx.out if the name of the model is xxx
		// the value sent out is a command: "Failure" or "Recovery"
		//String[] commands = {"Failure", "Recovery"};
		//addOutputPortStructure(new DEVS_Enum(commands), this.getName()+".OUT", "My single output port");
		a = new Port(new String());
		addOutPort(a);
		
		// No input port : X = {}
		
		// No state initialization: the state will always be the same implicit one
		
		// Initialization of the additional attribute
		rd = new Random();
		nb = rd.nextInt(2);
	}

	public void deltaInt() {
		// Nothing to say: we always return to the same implicit state
	}

	public double ta() {
		// The real value returned is in [4, 20]
		return (rd.nextDouble()*16 + 4.0);
	}

	public void lambda() {
		// The value sent out is randomly "Failure" or "Recovery"
		String toSend = (nb == 0)?"Failure":"Recovery";
		nb = (nb+1)%2;
		setOutPortdata(toSend, a);
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
		return (rd.nextDouble()*16 + 4.0);
	}
}