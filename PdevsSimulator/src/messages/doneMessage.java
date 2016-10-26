package messages;

import model.Port;

public class doneMessage extends Message {
	public Message message;
	
	public doneMessage(Port port, Double TN, Message m){
	    super(port,TN);
	    message = m;
	  }
	  

}
