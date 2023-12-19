package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {
	/**
	 * constructor
	 * @param name name of the source element
	 */
	public Source(String name) {
		nome = name;
	}

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		this.flow = flow;
	}
	
	@Override
	public void simulate(SimulationObserver observer, double f) {
		observer.notifyFlow("Source", nome, NO_FLOW, f);
		this.output.simulate(observer, f);
	}

	@Override
	public void layout(StringBuilder s, int depth) {
		s.append(this.layoutString());
		if (this.output != null) {
			s.append(" ").append(HSystem.SIMPLE_CONNECTORS).append(" ");
			this.output.layout(s, depth + this.layoutString().length() + 4);
		}
		else {
			s.append(" *");
		}
		return;
	}
	
	public String layoutString() {
		return "["+this.nome+"]Source";
	}
	
	


	@Override
	public void simulateFlow(SimulationObserver observer, double f) {
		observer.notifyFlow("Source", nome, NO_FLOW, f);
		this.output.simulateFlow(observer, f);
	}
	

}
