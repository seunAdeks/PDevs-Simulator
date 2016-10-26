package test2;
import processor.RootCoordinator;

	
public class Simulation {

	public static void main(String[] args) {
		// Creation of the study
		CrossRoad study = new CrossRoad("TLS");
		
		// Creation of the simulation tree
		RootCoordinator root = new RootCoordinator(study);
		
		// Experimentation:
		// initial time is 0.0
		// final time is 1000.0
		try {
			root.run(300.0);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
