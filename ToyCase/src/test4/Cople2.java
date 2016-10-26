package test4;
import model.*;
import java.util.*;


public class Cople2 extends CoupledModel {

	public Cople2(String name) {
		super(name);

		// Definition of {Md}
		Coplez Mcoll = new Coplez("Collector");
		
		
		// Definition of D
		addSubModel(Mcoll);
		
		//addOutputPortStructure(new DEVS_Integer(),this.getName()+".COL","Output port of coupled model");
		// There is no EIC, since the global model is input-free
		

		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addEIC(Mcoll,null) ;
		
		// There is no EOC, since the global model is output-free
		
	}
	
	
}
