package test4;
import model.*;
import java.util.*;


public class Cople extends CoupledModel {

	public Cople(String name) {
		super(name);
		
		//addOutputPortStructure(new Transport(), "Xavier", "");

		// Definition of {Md}
		Aton Mgen = new Aton("SOURCE");
		//coll Mcoll = new coll("Collector");
		
		// Definition of D
		addSubModel(Mgen);
		//addSubModel(Mcoll);
		
		// There is no EIC, since the global model is input-free
		//addEIC(Mgen.getOutputPortStructure("SOURCE.OUT"), getOutputPortStructure(this.getName()+".COL"));
		
		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addEOC(Mgen,Mgen.as);
		
		// There is no EOC, since the global model is output-free
		//addEOC(Mgen.getOutputPortStructure("SOURCE.OUT"), this.getOutputPortStructure(this.getName()+".COL"));
	}
	
	
}
