package test2;
import model.*;
import java.util.*;

public class TrafficLightSystem2 extends CoupledModel {

	public TrafficLightSystem2(String name) {
		super(name);

		// Definition of {Md}
		Generator2 Mgen = new Generator2("SOURCE2");
		TrafficLight2 Mlight = new TrafficLight2("TARGET2");
		Collector2 Mcoll = new Collector2("SINK2");
		Lane Lan = new Lane("LANEE");
		CarGenerator Gen = new CarGenerator("GIVERE");
		
		// Definition of D
		addSubModel(Mgen);
		addSubModel(Mlight);
		addSubModel(Mcoll);
		addSubModel(Gen);
		addSubModel(Lan);
		
		// There is no EIC, since the global model is input-free
		addEIC(Mlight,Mlight.in3);

		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addIC(Mgen,Mgen.a, Mlight,Mlight.in2) ;
		addIC(Mlight,Mlight.out2, Mcoll,Mcoll.aa) ;
		
		addIC(Gen, Gen.gen, Lan, Lan.fromCarGen);
		addIC(Lan, Lan.toColl, Mcoll, Mcoll.bb);
		
		addIC(Mlight, Mlight.out2Lane2, Lan, Lan.fromTraffic);

		// There is no EOC, since the global model is output-free
	}
	
	
}
