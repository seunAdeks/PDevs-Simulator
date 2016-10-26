package model;

import java.util.ArrayList;
import java.util.List;


public class Port {
	ArrayList values;
	int count;
	String name;
	public Class<?> forValue;
	protected Bag myBag;
	
	public Port(String name, Object v){
		this.name=name;
		this.values = new ArrayList();
		this.forValue = v.getClass();
		this.count = 0;
	}
	
	public String getName(){
		return name;
	}
	
	protected Bag getBag() {
		return myBag;
	}
	
	public void setBag(Bag b) {
		myBag = b;
	}
	
	public void clear()
	  {
	    if (count == 0) {
	      return;
	    }
	    this.values = new ArrayList();
	    this.count = 0;
	  }
	
	  public int getValuesCount()
	  {
	    return this.count;
	  }

	  public boolean hasValue()
	  {
	    return this.count > 0;
	  }
	  
	  public Object getValue()
	  {
	    if (!hasValue()) {
	      return null;
	    }
	    Object temp = values.get(0);
	    values.remove(0);
	    count--;
	    getBag().portBag.put(this,false);
	    return temp;
	  }
	  
	  public ArrayList<Object> getValues()
	  {
	    if (!hasValue()) {
	      return null;
	    }
	    ArrayList<Object> temp = new ArrayList<Object>();
	    temp = values;
	    count -= values.size();
	    getBag().portBag.put(this,false);
	    return temp;
	  }
	  
	  public  void write(Object o) throws Exception
	  {
	    if (o.getClass() != forValue) {
	      throw new Exception("Writing on port " + name + " with a wrong type " + o.getClass().getName() + " instead of " + forValue.getName());
	    }
	    this.values.add(o);
	    count += 1;
	    getBag().portBag.put(this,true);
	  }
	  
	  public  void writeAll(List<Object> o) throws Exception
	  {
		  if (o.get(0).getClass() != forValue) {
		      throw new Exception("Writing on port " + name + " with a wrong type " + o.getClass().getName() + " instead of " + forValue.getName());
		    }
	    this.values.addAll(o);
	   count += o.size();
	   getBag().portBag.put(this,true);
	  }
	  
	  public Object getValueIndex(int index)
	  {
	    return this.values.get(index);
	  }
	  
	  public final ArrayList<Object> getAllValues()
	  {
	    return this.values;
	  }
	  
	  
}
