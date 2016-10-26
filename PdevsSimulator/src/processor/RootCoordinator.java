package processor;

import model.*;
import exceptions.*;
import messages.*;

public class RootCoordinator extends AbstractSimulator {
	
	AbstractSimulator sim;  // Topmost Coordinator
	boolean done1 = false;  // Synchronization parameter
	boolean done2 = true;   // Synchronization parameter

	/**
	 * Constructor
	 * 
	 * @param s
	 *            son simulator
	 */
	public RootCoordinator(CoupledModel cm) {
		sim = new Coordinator(cm);
		sim.setParent(this);
		cm.addSimulator(sim);
	}

	public BasicModel getModel() {
		return null;
	}

	@Override
	public void init(double t) throws Exception {
		sim.handleMessage(new I_Message(t));
	}

	@Override
	public void handleMessage(Message msg)
			throws SynchronizationErrorException, Exception {
		if (msg instanceof I_Message) {
			throw new SynchronizationErrorException("Root cannot be initialized");
		}else if (msg instanceof collectMessage){
			throw new SynchronizationErrorException("collect message not applicable to the root");
		}else if (msg instanceof Y_Message){
			throw new SynchronizationErrorException("Y message not applicable to the root");
		}else if (msg instanceof XMessage){
			throw new SynchronizationErrorException("X message not applicable to the root");
		}else if (msg instanceof starMessage){
			throw new SynchronizationErrorException("star message not applicable to the root");
		}else if (msg instanceof doneMessage){
			doneMessage ms = (doneMessage) msg;
			if(ms.message instanceof starMessage){
				TN = ms.getTime();
				done1=false;
				done2=true;
			}else if(ms.message instanceof collectMessage){
				TL = ms.getTime();
				done1=true;
				done2=false;
			}else if(ms.message instanceof I_Message){
				if(TN == 0 || TN > ms.getTime())TN = ms.getTime();
			}else{
			}
		}
	}

	@Override
	public void initialize() {
		sim.initialize();
	}
	
	/** 
	 * Launches the simulation, with a boolean 
	 * 
	 * @param limit
	 *            duration of the simulation
	 */
	public void run(Boolean param){
		TN=0.0D;
		TL=0.0D;
		initialize();
		try{
			init(getTN());
			while (param) {
				if(done2)sim.handleMessage(new collectMessage(getTN()));
				if(done1){
					sim.handleMessage(new starMessage(getTN()));
				}
			}
		}catch (SynchronizationErrorException e) {
			e.getMessage();
			e.printStackTrace();
		}catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	/** 
	 * Launches the simulation, with a limit of time
	 * 
	 * @param limit
	 *            duration of the simulation
	 */
	public void run(double limit){
		TN=0.0D;
		TL=0.0D;
		initialize();
		try{
			init(getTN());
			while (TN <= limit) {
				if(done2)sim.handleMessage(new collectMessage(getTN()));
				if(done1){
					sim.handleMessage(new starMessage(getTN()));
				}
			}
		}catch (SynchronizationErrorException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


}
