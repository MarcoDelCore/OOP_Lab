package hydraulic;

import hydraulic.SimulationObserver.Level;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element implements SimulationObserver{
	String nome;
	Element output;
	double flow;
	double max;
	
	public Element() {
	}
	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return nome;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		output = elem;
	}
	
	public void stampaAlbero() {
		Element current = this;
		while (current != null) {
			System.out.println(current.getName());
			current = current.output;
		}
		System.out.println("------");
	}
	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
		// does nothing by default
	}
	
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		return output;
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return null;
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		// does nothing by default
		max = maxFlow;
	}
	
	public boolean checkOpen() {
		return true;
	}
	
	public void simulate(SimulationObserver observer, double f) {
		//does nothing by default
		return;
	}
	
	public void layout(StringBuilder s, int depth) {
		//does nothing by default
		return;
	}
	
	public void simulateFlow(SimulationObserver observer, double f) {
		//does nothing by default
		return;
	}
	
	public boolean isConnected(Element e) {
		if (output == e) {
			output = e.output;
			e.output = null;
			return true;
		}
		return false;
	}
	
	@Override
	public void notify(Level level, String type, String name, double inFlow, double... flows) {
		// TODO Auto-generated method stub
		
	}
	public boolean checkMultipleout() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
