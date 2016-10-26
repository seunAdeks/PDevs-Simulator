package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Bag {
	public Hashtable<Port, Boolean> portBag = new Hashtable<Port, Boolean>(1);
	
	public Bag(){};
	
	
	public void addPort(Port port)
	  {
		try{
			  portBag.remove(port.getName());
			  portBag.put(port, false);
		  }catch (Exception e){
			  portBag.put(port, false);
		  }
		  port.setBag(this);
	  }
	  
	  public void clearPorts()
	  {
	    while (portBag.keys().nextElement() != null) {
	      ((Port)portBag.keys().nextElement()).clear();
	    }
	  }
	  
	  public Port getPort(String name)
	  {
	    Enumeration<Port> en = portBag.keys();
	    while(en.hasMoreElements()){
	    	Port p = en.nextElement();
	    	if(p.getName().equals(name)) return p;
	    }
	    return null;
	  }
	  
	  public Port getPort(Port port)
	  {
	    Enumeration<Port> en = portBag.keys();
	    while(en.hasMoreElements()){
	    	Port p = en.nextElement();
	    	if(p == port) return p;
	    }
	    return null;
	  }
	  
	  public ArrayList<Port> getPortWithValue()
	  {
		  ArrayList<Port> pe = new ArrayList<Port>();
	    Enumeration<Port> en = portBag.keys();
	    while(en.hasMoreElements()){
	    	Port p = en.nextElement();
	    	if(p.hasValue()){
	    		pe.add(p);
	    	}
	    }
	    return pe;
	  }
	  
	  public boolean hasPortWithValue(){
		  {
			  ArrayList<Port> pe = new ArrayList<Port>();
		    Enumeration<Port> en = portBag.keys();
		    while(en.hasMoreElements()){
		    	Port p = en.nextElement();
		    	if(p.hasValue()){
		    		return true;
		    	}
		    }
		    return false;
		  }
	  }
	  
	  public boolean hasPort(String name)
	  {
	    return portBag.containsKey(name);
	  }
	  
	  public boolean hasPort(Port port)
	  {
	    return portBag.containsValue(port);
	  }
	  
	  public void removePort(Port port){
		  try{
			  portBag.remove(port.getName());
		  }catch (Exception e){
			  e.printStackTrace();
		  }
	  }
	  public void removePorts(){
		  portBag = new Hashtable<Port, Boolean>();
	  }
}
