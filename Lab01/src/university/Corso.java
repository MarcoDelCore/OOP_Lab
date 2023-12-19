package university;

public class Corso {
	String titoloCorso, nomeDocente;
	int codCorso;
	int n_partecipanti = 0;
	Student partecipanti[] = new Student[100];
	
	public Corso(String titolo, String nome, int id) {
		titoloCorso = titolo;
		nomeDocente = nome;
		codCorso = id;
	}
	
	public Corso() {};
	
	public int getID() {
		return codCorso;
	}
	
	public String toStr() {
		return codCorso+","+titoloCorso+","+nomeDocente;
	}
	
	public String getTitle() {
		return titoloCorso;
	}
	
	public int enrollStudent(Student s) {
		partecipanti[n_partecipanti] = s;
		return n_partecipanti++;
	}
	
	public String getEnrolled() {
		StringBuffer sb = new StringBuffer();
		for (Student studente : partecipanti) {
			if (studente != null) {
				sb.append(studente.toString()).append('\n');
			}
		}
		return sb.toString().trim();
	}
	
}
