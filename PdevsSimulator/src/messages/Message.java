package messages;

import java.io.Serializable;

import model.*;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156566792641284195L;

	/**
	 * time of the message.
				
	 */
	protected double time;

	/**
	 * Port of the message.
	 */
	protected Port port;

	/**
	 * Constructor
	 * 
	 */
	public Message() {

	}

	/**
	 * Constructor II
	 * 
	 * @param p
	 *            port.
	 * @param t
	 *            time of the message.
						
	 */
	public Message(Port p, double t) {
		time = t;
		port = p;
	}
	
	/**
	 * Constructor III
	 * 
	 * @param p
	 *            port.					
	 */
	public Message(Port p) {
		port = p;
	}

	/**
	 * Gets the time of the message.
	 * 
	 * @return the date t.
					-- int to double --
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Sets the time of the message.
	 * 
	 * @param t
	 *            the date t.
					-- int to double --
	 */
	public void setTime(double t) {
		time = t;
	}
	

	/**
	 * Gets the port of the message.
	 * 
	 * @return the port port.
	 */
	public Port getMessagePort() {
		return port;
	}
}
