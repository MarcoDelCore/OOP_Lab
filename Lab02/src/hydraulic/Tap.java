package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {
	boolean open = false;
	//Element input;
	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		nome = name;
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		this.open = open;
	}

	@Override
	public boolean checkOpen() {
		return open;
	}
	
	@Override
	public void simulate(SimulationObserver observer, double f) {
		double out=0.0;
		if (checkOpen()) out = f;
		observer.notifyFlow("Tap", nome, f, out);
		this.output.simulate(observer, out);
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
		return "["+this.nome+"]Tap";
	}
	

	@Override
	public void simulateFlow(SimulationObserver observer, double f) {
		double out=0.0;
		if (f > max) observer.notifyFlowError("Tap", nome, f, max);
		if (checkOpen()) out = f;
		observer.notifyFlow("Sinnk", nome, f, out);
		this.output.simulateFlow(observer, out);
	}
	
	
	
}
