package processor;


import java.util.ArrayList;

import model.*;
import exceptions.*;
import messages.*;

public class Simulator extends AbstractSimulator {
	
		protected AtomicModel model_;   //model to be processed
		
		

		/**
		 * Constructor : sets the atomic model.
		 * 
		 * @param m
		 *            atomic model.
		 */
		public Simulator(AtomicModel m) {
			model_ = m;
		}

	@Override
	public BasicModel getModel() {
		return model_;
	}

	@Override
	public void init(double t) throws Exception {
		TL = t;
		TN = t + model_.timeAdvance();
	}

	@Override
	public void handleMessage(Message msg)
			throws SynchronizationErrorException, Exception {
		double t = msg.getTime();

		if (msg instanceof I_Message) {
			init(t);
			parent_.handleMessage(new doneMessage(null, TN, msg));
		}else if (msg instanceof collectMessage){
			if(TN == t){
				model_.lambda();
				ArrayList<Port> tem = model_.outputPort.getPortWithValue();
				for(int i =0; i<tem.size(); i++){
					parent_.handleMessage(new Y_Message(tem.get(i), t, model_, model_));
				}
				parent_.handleMessage(new doneMessage(null, t, msg));
			}
		}else if (msg instanceof Y_Message){
			throw new Exception("Simulators dont get Y_messages");
		}else if (msg instanceof XMessage){
			XMessage ms = (XMessage) msg;
			if(ms.getOwner() != null){
				Tint = ms.getTime();
				getParent().handleMessage(new doneMessage(null, Tint, ms));
			}
		}else if (msg instanceof starMessage){
			if(TL <= t && t < TN && model_.inputPort.hasPortWithValue()){
				double e = t - TL;
				TL = t;
				model_.deltaExt(e);
				model_.inputPort.removePorts();
				TN = TL + model_.timeAdvance();
			}else if(TN == t && !model_.inputPort.hasPortWithValue()){
				TL = t;
				model_.deltaInternal();
				TN = TL + model_.timeAdvance();
			}else if(TN == t && model_.inputPort.hasPortWithValue()){
				TL = t;
				model_.deltaConfluent();
				model_.inputPort.removePorts();
				TN = TL + model_.timeAdvance();
			}else
				throw new SynchronizationErrorException("Unaccepted time of event");
			getParent().handleMessage(new doneMessage(null, TN, msg));
		}else if (msg instanceof doneMessage){
			throw new Exception("Simulators dont get d_messages");
		}
	}

	@Override
	public void initialize() {
		
	}

}
