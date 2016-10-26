package test;
import processor.RootCoordinator;

	
public class Simulation {

	public static void main(String[] args) {
		// Creation of the study
		Cople study = new Cople("TLS");
		
		// Creation of the simulation tree
		RootCoordinator root = new RootCoordinator(study);
		
		// Experimentation:
		// initial time is 0.0
		// final time is 1000.0
		
			root.run(1000);
			
	}
}
