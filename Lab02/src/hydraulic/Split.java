package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {
	Element outputs[] = new Element[2];
	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		nome = name;
	}
	
	@Override
	public void connect(Element elem, int index){
		outputs[index] = elem;
	}
	
	@Override
	public Element[] getOutputs(){
		return outputs;
	}
	
	@Override
	public void simulate(SimulationObserver observer, double f) {
		observer.notifyFlow("Split", nome, f, f/2, f/2);
		this.outputs[0].simulate(observer, f/2);
		this.outputs[1].simulate(observer, f/2);
	}
	
	@Override
	public void layout(StringBuilder s, int depth) {
		int actual;
		s.append(this.layoutString() + " ");
		actual = depth + this.layoutString().length()+1;
//		
//		for (Element e : outputs) {
//			s.append(" ").append(HSystem.SPLIT_CONNECTORS).append(" ");
//			if (e == null) {
//				s.append(" * ");
//			}
//			else {
//				e.layout(s,  depth + this.layoutString().length() + 5);
//				//s.append("\n");
//				for (int i = 0; i < actual; i++) s.append(" ");
//				s.append(" |\n");
//				for (int i = 0; i < actual; i++) s.append(" ");
//				//s.append(" ").append(HSystem.SPLIT_CONNECTORS).append(" ");
//				if (e.output != null)
//					e.output.layout(s, depth + this.toString().length() + 5);
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
		
		return;
	}
	
	public String layoutString() {
		return "["+this.nome+"]Split";
	}
	

	@Override
	public void simulateFlow(SimulationObserver observer, double f) {
		if (f > max) observer.notifyFlowError("Split", nome, f, max);
		observer.notifyFlow("Split", nome, f, f/2, f/2);
		this.outputs[0].simulateFlow(observer, f/2);
		this.outputs[1].simulateFlow(observer, f/2);
	}

	@Override
	public boolean checkMultipleout() {
		if (outputs.length > 1) return true;
		return false;
	}

	@Override
	public boolean isConnected(Element e) {
		for (Element el : outputs) {
			if (el == e) {
				outputs[0] = e.output;
				e.output = null;
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
