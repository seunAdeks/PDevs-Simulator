package model;

import java.util.ArrayList;

public abstract class AtomicModel extends BasicModel{
	
	  public AtomicModel(String name) {
	    super(name);
	  }
	  
	  public abstract void deltaExt(double elapsedTime);
	  
	  public abstract void deltaInternal();
	  
	  public abstract void deltaConfluent();
	  
	  public abstract void lambda();
	  
	  public abstract double timeAdvance();
	  
	  public void setOutPortdata(Object val, Port p){
		  try {
			 p.setBag(outputPort) ;
			outputPort.getPort(p).write(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  
	  public ArrayList<Object> getAllInPortData(Port p){
		  return p.getValues();
	  }
	  public Object getInPortData(Port p){
		  return p.getValue();
	  }
	  
	  public int getValuesCount(Port p){
		  return p.getValuesCount();
	  }

}
