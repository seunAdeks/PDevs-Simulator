package test4;
import model.*;
import java.util.*;


public class Cople1 extends CoupledModel {

	public Cople1(String name) {
		super(name);

		// Definition of {Md}
		Cople Mgen = new Cople("mySOURCE");
		
		
		
		// Definition of D
		addSubModel(Mgen);
		
		
		//addOutputPortStructure(new DEVS_Integer(),this.getName()+".COL","Output port of coupled model");
		// There is no EIC, since the global model is input-free
		

		// Definition of IC:
		// SOURCE(OUT)-->(COMMAND)TARGET(COLOR)-->(STORE)SINK
		addEOC(Mgen,null) ;
		
		// There is no EOC, since the global model is output-free
		
	}
	
	
}
