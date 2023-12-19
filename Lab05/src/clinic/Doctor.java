package clinic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Doctor {
	private String name;
	private String surname;
	private String ssn;
	private int badgeNumber;
	private String specialization;
	private List<Patient> patients = new ArrayList<>();
	
	public Doctor(String n, String s, String ssn, int bn, String sp) {
		name = n;
		surname = s;
		this.ssn = ssn;
		badgeNumber = bn;
		specialization = sp;
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

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int getBadgeNumber() {
		return badgeNumber;
	}

	public void setBadgeNumber(int badgeNumber) {
		this.badgeNumber = badgeNumber;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String toString() {
		return (surname + " " + name + " (" + ssn + ") [" + badgeNumber + "]: " + specialization);
	}
	
	public void addPatient(Patient p) {
		patients.add(p);
	}
	
	public Collection<String> getPatients() {
		List<String> p = new ArrayList<>();
		for (Patient tmp : patients) {
			p.add(tmp.getCodiceFiscale());
		}
		return p;
	}
	
	public int getNumberOfPatients() {
		return patients.size();
	}
}
