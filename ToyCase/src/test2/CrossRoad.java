package test2;
import model.*;
import java.util.*;

public class CrossRoad extends CoupledModel {

	public CrossRoad(String name) {
		super(name);
		
		TrafficLightSystem Sys1 = new TrafficLightSystem("Road1");
		TrafficLightSystem2 Sys2 = new TrafficLightSystem2("Road2");
		
		addSubModel(Sys1);
		addSubModel(Sys2);
		
		addIC(Sys1,null,Sys2,null);
	}
}
