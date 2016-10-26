package test3;
import model.*;
import java.util.*;


public class Coplez extends CoupledModel {

	public Coplez(String name) {
		super(name);

		// Definition of {Md}
		coll Mcoll = new coll("Collector");
		
		
		// Definition of D
		addSubModel(Mcoll);
		
		// There is no EIC, since the global model is input-free
		

		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addEIC(Mcoll,Mcoll.aa) ;
		
		// There is no EOC, since the global model is output-free
		
	}
	
	
}
