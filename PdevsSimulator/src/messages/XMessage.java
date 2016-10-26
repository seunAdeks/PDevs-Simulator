package messages;

import java.util.ArrayList;

import model.BasicModel;
import model.Port;

public class XMessage extends Message {
	
	BasicModel owner;
	BasicModel sender;
	ArrayList<Object> msg;
	ArrayList<Port> toPorts;
		/**
		 * Constructor : calls the constructor of the super class.
		 * 
		 * @param t
		 *            timestamp of the message.
		 * @param p
		 *            port of the message.
		 */
		public XMessage(Port p, double t) {
			super(p, t);
		}
		public XMessage(Port p, double t, BasicModel o, BasicModel s) {
			super(p, t);
			owner = o;
			sender = s;
		}
		
		public void setMessage(ArrayList a){
			msg = a;
		}
		
		public void setToPorts(ArrayList<Port> p){
			toPorts = p;
		}
		
		public ArrayList<Object> getMesaage(){
			return msg;
		}
		
		public BasicModel getOwner(){
			return owner;
		}
		
		public BasicModel getSender(){
			return sender;
		}

}
