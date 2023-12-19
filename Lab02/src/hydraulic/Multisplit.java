package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	Element outputs[];
	double flows[];
	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name);
		outputs = new Element[numOutput];
	}
	
	
	
	@Override
	public void connect(Element elem, int index) {
		// TODO Auto-generated method stub
		outputs[index] = elem;
	}

	

	@Override
	public Element[] getOutputs() {
		Element e[] = new Element[outputs.length];
		int i=0;
		for (Element el : outputs) {
			if (e != null) e[i++] = el;
		}
		return e;
	}

	@Override
	public void simulate(SimulationObserver observer, double f) {
		double out[] = new double[outputs.length];
		for (int i=0; i<outputs.length; ++i) {
			out[i] = f*flows[i];
		}
		observer.notifyFlow("Multisplit", nome, f, out);
		for (int i=0; i<outputs.length; ++i) {
			outputs[i].simulate(observer, out[i]);
		}
	}

	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		flows = new double[outputs.length];
		for (int i=0; i<outputs.length; ++i) {
			flows[i] = proportions[i];
		}
	}
	
	public void layout(StringBuilder s, int depth) {
		int actual;
		s.append(this.layoutString() + " ");
		actual = depth+this.layoutString().length()+1;
//		for (Element e : outputs) {
//			s.append(" ").append(HSystem.SPLIT_CONNECTORS).append(" ");
//			if ( e == null ) {
//				s.append(" * ");
//			}
//			else {
//				e.layout(s,  actual);
//				for (int i = 0; i < actual; i++) s.append(" ");
//				s.append(" |\n");
//				for (int i = 0; i < actual; i++) s.append(" ");
//			}
//		}
		for (int i=0; i < outputs.length; i++) {
			if (outputs[i] != null) {
				if ( i > 0 ) {
					s.append("\n");
					s.append(" ".repeat(actual));
					s.append("|\n");
					s.append(" ".repeat(actual));
				}
				s.append("+-> ");
				outputs[i].layout(s, actual+4);
			}
		}
	}
	
	public String layoutString() {
		return "["+nome+"]Multisplit";
	}



	@Override
	public void simulateFlow(SimulationObserver observer, double f) {
		double out[] = new double[outputs.length];
		if (f > max) observer.notifyFlowError("Multisplit", nome, f, max);
		for (int i=0; i<outputs.length; ++i) {
			out[i] = f*flows[i];
		}
		observer.notifyFlow("Multisplit", nome, f, out);
		for (int i=0; i<outputs.length; ++i) {
			outputs[i].simulateFlow(observer, out[i]);
		}
	}
	
	
}
