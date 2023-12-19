package clinic;

import java.util.Collection;

public class Example {

	public static void main(String[] args) throws NoSuchPatient, NoSuchDoctor {
		Clinic clinic = new Clinic();

		clinic.addPatient("Alice", "Green", "ALCGRN");
		clinic.addPatient("Robert", "Smith", "RBTSMT");
		clinic.addPatient("Steve", "Moore", "STVMRE");
		clinic.addPatient("Susan", "Madison", "SSNMDS");
		
		clinic.addDoctor("George", "Sun","SNUGRG", 14,"Physician");
		clinic.addDoctor("Kate", "Love", "LVOKTA",86,"Dentist");
		clinic.addDoctor("Adamo", "Curie", "MRICRU",88,"Chemist");
		clinic.addDoctor("Marie", "Burie", "MRICRU",69,"Chemist");
		
		clinic.assignPatientToDoctor("SSNMDS", 86);
		clinic.assignPatientToDoctor("ALCGRN", 14);
		clinic.assignPatientToDoctor("RBTSMT", 14);
		clinic.assignPatientToDoctor("STVMRE", 14);
		
		Collection<Integer> r = clinic.idleDoctors();
		System.out.println(r);
		
		System.out.println(clinic.countPatientsPerSpecialization());

	}

}
