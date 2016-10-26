package test5;
import processor.RootCoordinator;

	
public class Simulation {

	public static void main(String[] args) {
		// Creation of the study
		TrafficLightSystem study = new TrafficLightSystem("TLS");
		
		// Creation of the simulation tree
		RootCoordinator root = new RootCoordinator(study);
		
		// Experimentation:
		// initial time is 0.0
		// final time is 1000.0
		long start=System.currentTimeMillis();
		root.run(1000.0);
		long end=System.currentTimeMillis();
		System.out.println(end-start);
	}
}
