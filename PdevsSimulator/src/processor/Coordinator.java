package processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import exceptions.SynchronizationErrorException;
import model.*;
import messages.*;

public class Coordinator extends AbstractSimulator {
	/**
	 * driven coupled model
	 */
	protected CoupledModel model_;
	
	/**
	 * keep tabs on the number of done
	 */
	int done = 0;
	int done2 = 0;

	/**
	 * List of the sons (simulators)
	 */
	protected TreeMap<AbstractSimulator, BasicModel> subjects_ = new TreeMap<AbstractSimulator, BasicModel>();
	
	TreeSet<Double> timeKeeper = new TreeSet<Double>(); 

	/**
	 * Constructor : builds the coordinator for a given model.
	 * 
	 * @param cm
	 */
	public Coordinator(CoupledModel cm) {
		model_ = cm;
	}

	/**
	 * Returns the model of the coordinator
	 */
	public BasicModel getModel() {
		return model_;
	}

	/**
	 * Initialization of the sub models
	 * 
	 */
	public void initialize(){
		Iterator it = (Iterator) model_.getChildren().iterator();
		while(it.hasNext()){
			BasicModel m = (BasicModel) it.next();
			AbstractSimulator sim = null;
			if ((m instanceof CoupledModel)) {
		        sim = new Coordinator((CoupledModel)m);
		      } else {
		        sim = new Simulator((AtomicModel)m);
		      }
		      sim.initialize();
		      sim.setParent(this);
		      m.setParent(model_);
		      m.addSimulator(sim);
		      this.subjects_.put(sim, m);
		}
	}

	/**
	 * Initialisation of the coordinator. Sends init messages to all the sons
				-- int to double --
	 */
	public void init(double t) throws Exception {
		for (Object o : subjects_.keySet()) {
			if (o instanceof AbstractSimulator) {
				AbstractSimulator sim = (AbstractSimulator) o;

				I_Message msg = new I_Message(t);

				sim.handleMessage((Message) msg);
			} else
				throw new Exception();
		}
	}

	/**
	 * Find components with equal time of next event
	 * 
	 * @param AbstractSimulator
	 * 
	 * @return a list of components with the same TN
	 */
	public ArrayList findEqual(AbstractSimulator s) {
		ArrayList te = new ArrayList();
		for (Object o : subjects_.keySet()){
			AbstractSimulator sim = (AbstractSimulator) o;
			double b = s.TN - sim.Tint;
			if(s.TN == sim.TN || b == 0){
				te.add(sim);
			}
		}
		return te;
	}
	
	/**
	 * Get the component with the lowest time of next Event
	 * 
	 * @param  T (Map)
	 * @return AbstractSimulator 
	 */
	public AbstractSimulator getLowest(TreeMap<AbstractSimulator, BasicModel> T){
		AbstractSimulator marp = T.firstKey();
		for (Entry<AbstractSimulator, BasicModel> en : T.entrySet()){
			if (en.getKey().TN < marp.TN)
				marp = en.getKey();
		}
		return marp;
	}
	
	/**
	 * Check for components with time t
	 * 
	 * @param t
	 * @return boolean 
	 */
	public boolean check(double t){
		for (Entry<AbstractSimulator, BasicModel> en : subjects_.entrySet()){
			if (en.getKey().TN == t)return true;
		}
		return false;
	}

	@Override
	public void handleMessage(Message msg)
			throws SynchronizationErrorException, Exception {
		double t = msg.getTime();

		if (msg instanceof I_Message) {
			init(t);
		}else if (msg instanceof collectMessage){
			TN = t;
				ArrayList tem = findEqual(getLowest(subjects_));
				for (Object o : tem){
					AbstractSimulator sim = (AbstractSimulator) o;
					sim.handleMessage(new collectMessage(t));
					done++;
				}
				if(done == 0)parent_.handleMessage(new doneMessage(null, t, msg));
				else throw new SynchronizationErrorException("Incomplete done acknowledgement");
		}else if (msg instanceof Y_Message){
			Y_Message ms = (Y_Message) msg;
			if(ms.getOwner() != null){
				ArrayList inf = model_.getInfluencee(ms.getSender());
				for(int i=0; i<inf.size(); i++){
					if(inf.get(i)==model_){
						parent_.handleMessage(new Y_Message(ms.getMessagePort(), t, ms.getOwner(), model_));
					}else{
						XMessage mess = new XMessage(ms.getMessagePort(), t, ms.getOwner(), model_);
						if(inf.get(i) instanceof AtomicModel){
							ArrayList<Port> p = null;
							if(ms.getOwner() == ms.getSender()){
								p = model_.getLinkedPortIC(ms.getOwner(), ms.getMessagePort());
							}else 
								p = model_.getLinkedPortIC(ms.getSender(), null);
							for(Port pe : p){
								ArrayList<Object> s = ms.getMessagePort().getValues();
								pe.writeAll(s);
								s.clear();
								pe.setBag(((BasicModel)inf.get(i)).inputPort);
							}
							mess.setToPorts(p);
						}
							((BasicModel)inf.get(i)).getSimulator().handleMessage(mess);
					}
				}
			}else throw new Exception("Cannot determine owner of message");
		}else if (msg instanceof XMessage){
			XMessage ms = (XMessage) msg;
			if(ms.getOwner() != null){
				ArrayList inf = model_.getInfluencee(ms.getSender());
				for(int i=0; i<inf.size(); i++){
					if(inf.get(i) instanceof AtomicModel)
						((BasicModel)inf.get(i)).getSimulator().handleMessage(new XMessage(ms.getMessagePort(), t, ms.getOwner(), model_));	
					else{
						ArrayList infe = model_.getLinkedInternal();
						for(int j = 0; j< infe.size(); j++){
							((BasicModel)infe.get(j)).getSimulator().handleMessage(new XMessage(ms.getMessagePort(), t, ms.getOwner(), model_));
						}
					}
				}
			}else throw new Exception("Not allowed(Coupled models dont receive XMessages");
		}else if(msg instanceof starMessage){
			if(TN == t){
				ArrayList tem = findEqual(getLowest(subjects_));
				for (Object o : tem){
					AbstractSimulator sim = (AbstractSimulator) o;
					sim.handleMessage(new starMessage(t));
					done2++;
					
				}
				if(done2 == 0){
					double tempp = timeKeeper.first();
					while(!check(tempp)){
						timeKeeper.remove(tempp);
						tempp = timeKeeper.first();
					}
					TN = tempp;
					timeKeeper.remove(tempp);
					parent_.handleMessage(new doneMessage(null, tempp, msg));
				}
				else throw new SynchronizationErrorException("Incomplete done acknowledgement");
			}
		}else if(msg instanceof doneMessage){
			doneMessage d = (doneMessage) msg;
			if(d.message instanceof  collectMessage){
				done--;
			}else if(d.message instanceof starMessage){
				done2--;
				timeKeeper.add(d.getTime());
			}else if(d.message instanceof I_Message){
				if(TN == 0) TN = t;
				else if (TN != t){
					if(TN > t){
						timeKeeper.add(TN);
						TN = t;
					}else if(TN < t)timeKeeper.add(t);
				}
				getParent().handleMessage(d);
			}else if(d.message instanceof XMessage){
				if (TN != t && TN > t){
					timeKeeper.add(TN);
					TN = t;
				}else if(TN < t)throw new SynchronizationErrorException("Impossible time of next event");
			}
		}
		
	}
}
