package test2;
import model.*;
import java.util.*;

public class TrafficLightSystem extends CoupledModel {

	public TrafficLightSystem(String name) {
		super(name);

		// Definition of {Md}
		Generator Mgen = new Generator("SOURCE");
		TrafficLight Mlight = new TrafficLight("TARGET");
		Collector Mcoll = new Collector("SINK");
		
		// Definition of D
		addSubModel(Mgen);
		addSubModel(Mlight);
		addSubModel(Mcoll);
		
		// There is no EIC, since the global model is input-free

		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addIC(Mgen,Mgen.a, Mlight,Mlight.in) ;
		addIC(Mlight,Mlight.out, Mcoll,Mcoll.aa) ;

		// There is no EOC, since the global model is output-free
	}
	
	
}
