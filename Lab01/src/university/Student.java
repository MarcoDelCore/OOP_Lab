package university;

public class Student {
	String name, surname;
	int id;
	int n_corsi=0, n_esami=0;
	Corso corsi[] = new Corso[25];
	esami esamiSostenuti[] = new esami[50];
	
	public Student() {};
	
	public Student(String name, String surname, int id) {
		super();
		this.name = name;
		this.surname = surname;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return id + " " + name + " " + surname;
	}
	
	public int addCourse(Corso c) {
		corsi[n_corsi] = c;
		return n_corsi++;
	}
	
	public String getStudyPlan() {
		StringBuffer sb = new StringBuffer();
		for (Corso course : corsi) {
			if (course != null) {
				sb.append(course.toStr()).append('\n');
			}
		}
		return sb.toString().trim();
	}
	
	public int addExam(esami e) {
		esamiSostenuti[n_esami] = e;
		return n_esami++;
	}
	
	public double getAverage() {
		double media = 0.0;
		if (n_esami != 0) { //fixed
			for (esami e : esamiSostenuti) {
				if (e != null) {
					media += e.voto;
				}
			}
			return media/n_esami;
		}
		return media;
	}
	
}
