package processor;
	

	import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import exceptions.SynchronizationErrorException;

import messages.*;
import model.*;

	
	public abstract class AbstractSimulator implements Comparable<AbstractSimulator> {
		/**
		 * Timestamp of the next event.
		 */
		protected Double TN = new Double(Double.POSITIVE_INFINITY);

		/**
		 * Timestamp of the last event
		 */
		protected Double TL = new Double(Double.NEGATIVE_INFINITY);
		
		

		/**
		 * Elapsed time since the last event.
		 */
		protected Double Tint = 0.0D;

		/**
		 * Parent simulator.
		 */
		protected AbstractSimulator parent_ = null;
		
		/**
		 * void constructor
		 * 
		 */
		public AbstractSimulator() {
		}

		/**
		 * Constructor II : set the parent.
		 * 
		 * @param s
		 *            new parent
		 */
		public AbstractSimulator(AbstractSimulator s) {
			parent_ = s;
		}

		/**
		 * Returns the simulated model.
		 * 
		 * @return the simulated model
		 */
		abstract public BasicModel getModel();

		/**
		 * Gets the parent of the simulator
		 * 
		 * @return parent_
		 */
		public AbstractSimulator getParent() {
			return parent_;
		}
		
		/**
		 * Sets the parent of the simulator
		 * 
		 * @param s
		 *            new parent
		 */
		public void setParent(AbstractSimulator s) {
			parent_ = s;
		}

		/**
		 * Gets the time of the last event
		 * 
		 * @return TL
		 */
		public double getTL() {
			return TL;
		}

		/**
		 * Gets the time of the next event.
		 * 
		 * @return TN
		 */
		public double getTN() {
			return TN;
		}

		/**
		 * Initialisation of the model
		 * 
		 * @param t
		 *            time
		 * @throws Exception
		 */
		abstract public void init(double t) throws Exception;
		
		

		/**
		 * Handles the incoming message.
		 * 
		 * @param msg
		 *            message that as to be treated.
		 * @throws Exception
		 */
		public abstract void handleMessage(Message msg) throws SynchronizationErrorException, Exception;
		

		public Hashtable getSendValues() {
			    BasicModel model = (BasicModel)getModel();
			    
			    Hashtable<String, List> result = new Hashtable<String, List>();
			    
			    Enumeration<Port> itp = model.outputPort.portBag.keys();
			    while (itp.hasMoreElements())
			    {
			      Port p = (Port)itp.nextElement();
			      if (p.hasValue())
			      {
			        List<Object> list = p.getAllValues();
			        p.clear();
			        



			        result.put(p.getName(), list);
			      }
			    }
			    return result;
		}
		
		public int compareTo(AbstractSimulator s){
			if(s.getTN() < getTN()){
				return -1;
			}else if(s.getTN() > getTN()){
				return 1;
			}else
				return 1;
		}

		public abstract void initialize() ;



}
