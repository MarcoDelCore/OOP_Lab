package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		nome = name;
	}

	@Override
	public void connect(Element elem) {
		return;
	}

	@Override
	public void simulate(SimulationObserver observer, double f) {
		observer.notifyFlow("Sinnk", nome, f, NO_FLOW);
	}
	
	@Override
	public void layout(StringBuilder s, int depth) {
		s.append(this.layoutString());
	}
	
	public String layoutString() {
		return "["+this.nome+"]Sink";
	}

	@Override
	public void simulateFlow(SimulationObserver observer, double f) {
		if (f > max) observer.notifyFlowError("Sink", nome, f, max);
		observer.notifyFlow("Sinnk", nome, f, NO_FLOW);
	}
	
	
	
}
