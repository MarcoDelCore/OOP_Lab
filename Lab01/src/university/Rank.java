package university;

public class Rank {
	class Elemento {
		Student student;
		double points;
		Elemento next;
	
		public Elemento(Student s, double p, Elemento r) {
			student = s;
			points = p;
			next = r;
		}
		
		Elemento(Student s, double v) {
			points = v;
			next = testa; 
		}
	}
	
	protected Elemento testa = null;
	
	public void aggiungi(Student s, double p) {
		
		if(testa==null || testa.points < p) {
			Elemento nuovo = new Elemento(s, p, testa);
			testa = nuovo;
			return;
		}
		
		Elemento current = testa;
		
		while (current != null) {
			if (current.next == null || current.next.points < p) {
				Elemento nuovo = new Elemento(s, p, current.next);
				current.next = nuovo;
				break;
			}
			current = current.next;
		}
	}
	
	public String topThree() {
		String r = "";
		Elemento current = testa;
		int n = 1;
		while (current != null && n <= 3) {
			r += (current.student.name + " " + current.student.surname + " : " + current.points + "\n");
			n++;
			current = current.next;
		}
		return r.trim();
	}
}
