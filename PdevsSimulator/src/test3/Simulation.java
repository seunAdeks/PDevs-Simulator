package test3;
import processor.RootCoordinator;

	
public class Simulation {

	public static void main(String[] args) {
		// Creation of the study
		CopleBig study = new CopleBig("TLS");
		
		// Creation of the simulation tree
		RootCoordinator root = new RootCoordinator(study);
		
		// Experimentation:
		// initial time is 0.0
		// final time is 1000.0
		
			root.run(1000);
			
	}
}
