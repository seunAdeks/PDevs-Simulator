package messages;

import java.util.ArrayList;

import model.*;

public class Y_Message extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4114517719552325031L;
	BasicModel owner;
	BasicModel sender;
	ArrayList<Object> msg;
	
		/**
		 * Constructor : calls the constructor of the super class.
		 * 
		 * @param t
		 *            timestamp of the message.
		 * @param p
		 *            port of the message.
		 */
		public Y_Message(Port p, double t) {
			super(p, t);
		}
		public Y_Message(Port p, double t, BasicModel o, BasicModel s) {
			super(p, t);
			owner = o;
			sender = s;
		}
		
		public void setMessage(ArrayList a){
			msg = a;
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
