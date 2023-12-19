package clinic;

public class Patient {
	private String name;
	private String surname;
	private String codiceFiscale;
	private Doctor assignedDoctor;
	
	public Patient(String n, String s, String c) {
		name = n;
		surname = s;
		codiceFiscale = c;
		assignedDoctor = null;
	}
	
	public  String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public  String getSurname() {
		return surname;
	}
	public  void setSurname(String surname) {
		this.surname = surname;
	}
	public  String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String toString() {
		return (surname + " " + name + " (" + codiceFiscale + ")");
	}
	
	public void assignDoctor(Doctor d) {
		assignedDoctor = d;
		return;
	}
	
	public Doctor getAssignedDoctor() {
		return assignedDoctor;
	}
}
