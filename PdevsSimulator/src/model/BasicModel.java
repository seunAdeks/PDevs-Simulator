package model;

import java.util.Enumeration;
import java.util.Iterator;

import processor.AbstractSimulator;

public class BasicModel {
	
	String name;     //name of model
	public Bag inputPort = new Bag();  //Input Bag containing list of input ports
	public Bag outputPort = new Bag();  //Output Bag containing list of output ports
	BasicModel parent;  // Parent component of the model 
	
	/**
	 * Simulator of model
	 */
	protected AbstractSimulator sim;

	
	public BasicModel(){}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of the model
	 */
	public BasicModel(String name){
		this.name = name;
	}
	
	/**
	 * Adds a simulator to the coupled model
	 * 
	 * @param m
	 *            the simulator to add.
	 */
	public void addSimulator(AbstractSimulator s){
		sim = s;
	}
	
	public AbstractSimulator getSimulator(){
		return sim;
	}
	
	public void setParent(BasicModel par){
		parent = par;
	}
	
	public BasicModel getParent(){
		return parent;
	}
	
	public boolean hasPort(Port port){
	    return (inputPort.hasPort(port)) || (outputPort.hasPort(port));
	  }

	 public void addPort(Bag portSet, Port port){
	    if ((!inputPort.hasPort(port)) && (!outputPort.hasPort(port))) {
	      portSet.addPort(port);
	    }
	  }
	 
	 public void addInPort(Port port)
	  {
	    addPort(inputPort, port);
	  }
	 
	 public void addOutPort(Port port)
	  {
	    addPort(outputPort, port);
	  }
	 
	 public void clearInPorts()
	  {
		 inputPort.clearPorts();
	  }
	  
	  public void clearOutPorts()
	  {
		  outputPort.clearPorts();
	  }
	  
	  public String getName(){
			return name;
	  } 
	  
	  public final boolean hasExternalInput()
	  {
	    Enumeration<Port> en = inputPort.portBag.keys();
	    while (en.hasMoreElements())
	    {
	      Port port = (Port)en.nextElement();
	      if (port.hasValue()) {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  public Object readPortValue(Port port)
	  {
	    if (inputPort.hasPort(port.getName())) {
	      return inputPort.getPort(port.getName()).getValue();
	    }
	    return outputPort.getPort(port.getName()).getValue();
	  }
	  
	  public Object readPortValues(Port port)
	  {
	    if (inputPort.hasPort(port.getName())) {
	      return inputPort.getPort(port.getName()).getValues();
	    }
	    return outputPort.getPort(port.getName()).getValues();
	  }
	  
	  public void writePort(Port port, Object o) throws Exception
	  {
	    if (inputPort.hasPort(port.getName())) {
	      inputPort.getPort(port.getName()).write(o);
	    }
	    outputPort.getPort(port.getName()).write(o);
	  }
}
