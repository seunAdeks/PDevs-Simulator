package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import exceptions.InvalidCouplingException;

import processor.AbstractSimulator;

abstract public class CoupledModel extends BasicModel {
	/**
	 * list of the sub-models.
	 */
	protected ArrayList<BasicModel> subModels_ = new ArrayList<BasicModel>();
	
	/**
	 * List of influencee for each sub-model
	 */
	protected HashMap<BasicModel , ArrayList<BasicModel>> influencee = new HashMap<BasicModel, ArrayList<BasicModel>>();

	/**
	 * List of External Input Coupling.
	 */
	protected ArrayList<CouplingPair> EIC_ = new ArrayList<CouplingPair>();

	/**
	 * List of External Output Coupling.
	 */
	protected ArrayList<CouplingPair> EOC_ = new ArrayList<CouplingPair>();

	/**
	 * List of Internal Coupling.
	 */
	protected ArrayList<CouplingPair> IC_ = new ArrayList<CouplingPair>();
	
	public CoupledModel(String name){
		super(name);
	}
	
	/**
	 * Adds a submodel to the coupled model
	 * 
	 * @param m
	 *            the submodel to add.
	 */
	public void addSubModel(BasicModel m) {
		m.setParent(this);
		subModels_.add(m);
	}
	
	
	public void addEIC(BasicModel aChildModel, Port atomicChildPort){
			setInfluencee(this, aChildModel);
			addCoupling(this, null, aChildModel, atomicChildPort);
		}
	
	
	public void addEOC(BasicModel aChildModel, Port atomicChildPort){
		setInfluencee(aChildModel, this);
		addCoupling(aChildModel, atomicChildPort, this, null);
	}
	
	
	public void addIC(BasicModel aChildModel1, Port port1,  BasicModel aChildModel2, Port port2){
		setInfluencee(aChildModel1, aChildModel2);
		addCoupling(aChildModel1, port1, aChildModel2, port2);
	}
	
	public void setInfluencee(BasicModel model1, BasicModel model2){
		if(!influencee.keySet().contains(model1)){
			ArrayList tem = new ArrayList();
			tem.add(model2);
			influencee.put(model1, tem);
		}else{
			influencee.get(model1).add(model2);
		}
	}

	/**
	 * Adds an External Input Coupling. 
	 * If any of the models is a coupled model add a null port.
	 * 
	 * @param port1
	 *            port of the coupled model
	 * @param port2
	 *            port of one of the submodel
	 */
	private void addCoupling(BasicModel model1, Port port1, BasicModel model2, Port port2) {
		Pair p1 = new Pair(model1, port1);
		Pair p2 = new Pair(model2, port2);
		 if (model1 == this){
			 addCoupling(EIC_, p1, p2);
		    }
		    else
		    {
		      if (model2 == this) {
		    	  addCoupling(EOC_, p1, p2);
		      } else {
		    	  addCoupling(IC_, p1, p2);
		      }
		    }
		
	}
	
	
	public ArrayList<BasicModel> getInfluencee(BasicModel model){
		return influencee.get(model);
	}
	
	private void addCoupling(ArrayList couplings, Pair a, Pair b) {
		BasicModel model1 = a.getModel();
		Port port1 = a.getPort();
		
		BasicModel model2 = b.getModel();
		Port port2 = b.getPort();
		
		if (((!this.subModels_.contains(model1)) && (this != model1)) || ((!this.subModels_.contains(model2)) && (this != model2)) )
	    {
	      String notExist = "";
	      if ((!this.subModels_.contains(model1)) && (this != model1)) {
	        notExist = " model " + model1.getName();
	      }
	      if ((!this.subModels_.contains(model2)) && (this != model2)) {
	        notExist = notExist + " model " + model2.getName();
	      }
	    }
	    if (model1 == model2) {
	      throw new InvalidCouplingException("A model cannot be connected to itself!\n(" + model1.getName() + ":" + port1.getName() + " - " + model2.getName() + ":" + port2.getName() + ")");
	    }
	    couplings.add(new CouplingPair(a, b));
	}
	
	public boolean checkForModel(ArrayList<CouplingPair> couplings, BasicModel model){
	    Iterator<CouplingPair> itc = couplings.iterator();
	    while (itc.hasNext()){
	      Object c = itc.next();
	      if (((c instanceof CouplingPair)) && (
	        (((CouplingPair)c).isModel(model)))) {
	        return true;
	      }
	    }
	    return false;
	  }


	

	/**
	 * Gets the linked outputs
	 * 
	 * @param port
	 *            port of the coupled model
	 * @return the list of outputs
	 * @throws ConceptionErrorException
	 */
	public ArrayList<Port> getLinked(ArrayList<CouplingPair> coupling, BasicModel model, Port port){
		ArrayList<Port> linkedPorts = new ArrayList<Port>();

		for (Object o : coupling) {
			CouplingPair pair = (CouplingPair) o;

			if (pair.getSender().getModel() == model) {
				if(pair.getReceiver().getPort() != null && pair.getSender().getPort() == port)
					linkedPorts.add(pair.getReceiver().getPort());
			} 
		}

		return linkedPorts;
	}
	
	public ArrayList<BasicModel> getLinkedInternal(){
		ArrayList<BasicModel> linked = new ArrayList<BasicModel>();
		for (Object o : EIC_) {
			CouplingPair pair = (CouplingPair) o;
			linked.add(pair.getReceiver().getModel());
		}
		return linked;
	}

	

	/**
	 * Gets all the linked ports for a given port
	 * 
	 * @param port
	 *            linked port
	 * @return the list of linked ports
	 * @throws ConceptionErrorException
	 */
	public ArrayList<Port> getLinkedPortEOC(BasicModel model, Port port){
		ArrayList<Port> linkedPorts = new ArrayList<Port>();

		linkedPorts.addAll(getLinked(EOC_, model, port));
		return linkedPorts;
	}
	
	public ArrayList<Port> getLinkedPortEIC(BasicModel model, Port port){
		ArrayList<Port> linkedPorts = new ArrayList<Port>();
		
		linkedPorts.addAll(getLinked(EIC_, model, port));
		return linkedPorts;
	}
	
	public ArrayList<Port> getLinkedPortIC(BasicModel model, Port port){
		ArrayList<Port> linkedPorts = new ArrayList<Port>();

		linkedPorts.addAll(getLinked(IC_, model, port));
		return linkedPorts;
	}
	
	public ArrayList<BasicModel> getChildren(){
		return subModels_;
	}

}
