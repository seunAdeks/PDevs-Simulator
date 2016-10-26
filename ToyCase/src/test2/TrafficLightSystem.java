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
		Lane Lan = new Lane("LANE");
		CarGenerator Gen = new CarGenerator("GIVER");
		
		// Definition of D
		addSubModel(Mgen);
		addSubModel(Mlight);
		addSubModel(Mcoll);
		addSubModel(Gen);
		addSubModel(Lan);
		
		// There is no EIC, since the global model is input-free

		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addIC(Mgen,Mgen.a, Mlight,Mlight.in) ;
		addIC(Mlight,Mlight.out, Mcoll,Mcoll.aa) ;
		
		addIC(Gen, Gen.gen, Lan, Lan.fromCarGen);
		addIC(Lan, Lan.toColl, Mcoll, Mcoll.bb);
		
		addIC(Mlight, Mlight.out2Lane, Lan, Lan.fromTraffic);

		// There is no EOC, since the global model is output-free
		addEOC(Mlight,Mlight.outt);
	}
	
	
}
