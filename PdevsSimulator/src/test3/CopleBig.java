package test3;
import model.*;
import java.util.*;


public class CopleBig extends CoupledModel {

	public CopleBig(String name) {
		super(name);

		// Definition of {Md}
		Cople Mgen = new Cople("mySOURCE");
		coll Mcoll = new coll("Collector");
		
		
		// Definition of D
		addSubModel(Mgen);
		addSubModel(Mcoll);
		
		//addOutputPortStructure(new DEVS_Integer(),this.getName()+".COL","Output port of coupled model");
		// There is no EIC, since the global model is input-free
		

		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addIC(Mgen,null, Mcoll,Mcoll.aa) ;
		
		// There is no EOC, since the global model is output-free
		
	}
	
	
}
