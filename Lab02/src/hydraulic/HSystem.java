package hydraulic;

import java.util.Arrays;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem implements SimulationObserver{
	
	final static protected String SIMPLE_CONNECTORS = "->";
	final static protected String SPLIT_CONNECTORS = "+->";
	
	
	Element elementi[] = new Element[100];
	private int prossimo_elemento = 0;
	int n_elementi = 0;
// R1
	/**
	 * Adds a new element to the system
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		if (prossimo_elemento == elementi.length) {
			elementi = Arrays.copyOf(elementi, prossimo_elemento);
		}
		elementi[prossimo_elemento++] = elem;
		n_elementi++;
	} 
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		Element e[] = Arrays.copyOf(elementi, prossimo_elemento);
		return e;
	}

// R4
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		//TODO: to be implemented
		for (Element e : elementi) {
			if (e instanceof Source) {
				e.simulate(observer, e.flow);
			}
		}
	}

	
	public String getClass(Element e) {
		if (e instanceof Source) {
			return "Source";
		} else if (e instanceof Sink) {
			return "Sink";
		} else if (e instanceof Tap) {
			return "Tap";
		} else if (e instanceof Split) {
			return "Split";
		} else {
			return "Multisplit";
		}
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		StringBuilder s = new StringBuilder("");
		for (Element e : elementi) {
			if (e instanceof Source) {
				e.layout(s, 0);
			}
		}
		return s.toString();
	}

// R7
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public boolean deleteElement(String name) {
		Element tmp = getElemento(name);
		boolean r = false;
		if (tmp instanceof Split) {
			if (tmp.checkMultipleout()) return false;
		}
		for (Element e : elementi) {
			if (e != null && e.isConnected(tmp));
		}
		for (int i=0; i<prossimo_elemento; i++) {
			if (elementi[i] == tmp) {
				elementi[i] = null;
				return true;
			}
		}
		return false;
	}
	
	public Element getElemento(String nome) {
		for (Element e : elementi) {
			if (e.nome == nome) return e;
		}
		return null;
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		//TODO: to be implemented
		for (Element e : elementi) {
			if (e instanceof Source) {
				e.simulateFlow(observer, e.flow);
			}
		}
	}

@Override
	public void notify(Level level, String type, String name, double inFlow, double... flows) {
		// TODO Auto-generated method stub
		
	}
}
