package model;

public class Pair {
	  private BasicModel model;
	  private String name;
	  private Port port;
	  
	  public Pair(BasicModel model1, Port port){
	    setModel(model1);
	    this.port = port;
	  }
	  
	  public boolean compareTo(Pair o){
	    boolean i = getModel().name.equalsIgnoreCase(o.getModel().name);   
	    boolean k = getPort().name.equalsIgnoreCase(o.getPort().name);
	    if (i && k) {
	      return i;
	    }
	    return false;
	  }
	  
	  public BasicModel getModel(){
	    return this.model;
	  }
	  
	  public String getName()
	  {
	    return this.name;
	  }
	  
	  public Port getPort(){
	    return this.port;
	  }
	  
	  public final void setName(String name)
	  {
	    this.name = name;
	  }
	  
	  public final void setModel(BasicModel model)
	  {
	    this.model = model;
	  }
	
}
