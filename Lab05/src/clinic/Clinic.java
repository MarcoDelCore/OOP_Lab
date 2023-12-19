package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;


/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	private Map<String, Patient> patients = new HashMap<>();
	private Map<Integer, Doctor> doctors = new HashMap<>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		if (patients.containsKey(ssn)) return;
		Patient tmp = new Patient(first.trim(), last.trim(), ssn.trim());
		patients.put(ssn.trim(), tmp);
		return;
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if (!patients.containsKey(ssn)) throw new NoSuchPatient();
		return patients.get(ssn).toString();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		if(!patients.containsKey(ssn.trim())) {
			patients.put(ssn, new Patient(first.trim(), last.trim(), ssn.trim()));
		}
		if (!doctors.containsKey(docID))
			doctors.put(docID, new Doctor(first.trim(), last.trim(), ssn.trim(), docID, specialization.trim()));
		return;
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if (!doctors.containsKey(docID)) throw new NoSuchDoctor();
		return doctors.get(docID).toString();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if (!patients.containsKey(ssn)) throw new NoSuchPatient();
		if (!doctors.containsKey(docID)) throw new NoSuchDoctor();
		patients.get(ssn).assignDoctor(doctors.get(docID));
		doctors.get(docID).addPatient(patients.get(ssn));
		return;
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if (!patients.containsKey(ssn)) throw new NoSuchPatient();
		Doctor d = patients.get(ssn).getAssignedDoctor();
		if (d==null) throw new NoSuchDoctor();
		return d.getBadgeNumber();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if (!doctors.containsKey(id)) throw new NoSuchDoctor();
		return doctors.get(id).getPatients();
	}
	
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		try (BufferedReader br = new BufferedReader(reader)) {
			int n = 0;
			String row;
			while((row = br.readLine()) != null) {
				String fields[] = row.split(";");
				if (fields[0].compareTo("P") == 0) {
					if (fields.length == 4) {
						addPatient(fields[1], fields[2], fields[3]);
						n++;
					}
				} else {
					if (fields.length == 6) {
						Pattern p = Pattern.compile(".*[a-zA-Z].*");
						Matcher m = p.matcher(fields[1]);
						if (! m.matches()) {
							addDoctor(fields[2], fields[3], fields[4], Integer.parseInt(fields[1]), fields[5]);
							n++;
						}
					}
				}
			}
			return n;
		}
		catch (IOException e) {
			throw e;
		}
		
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>
	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		try (BufferedReader br = new BufferedReader(reader)) {
			int n = 0;
			String row;
			while((row = br.readLine()) != null) {
				String fields[] = row.split(";");
				if (fields[0].compareTo("P") == 0) {
					if (fields.length == 4)  {
						addPatient(fields[1], fields[2], fields[3]);
						n++;
					}
					else listener.offending(row);
				} else {
					if (fields.length == 6) {
						Pattern p = Pattern.compile(".*[a-zA-Z].*");
						Matcher m = p.matcher(fields[1]);
						if (! m.matches()) {
							addDoctor(fields[2], fields[3], fields[4], Integer.parseInt(fields[1]), fields[5]);
							n++;
						}
						else listener.offending(row);
					}
					else listener.offending(row);
				}
			}
			return n;
		}
		catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		Collection<Integer> res = doctors.values().stream().filter(x -> x.getPatients().size() == 0)
				.sorted(comparing(Doctor::getSurname).thenComparing(Doctor::getName))
				.map(x -> x.getBadgeNumber()).toList();
		return res;
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		double avg = (double) (doctors.values().stream().collect(summingInt(x -> x.getPatients().size())) / doctors.values().size());
		Collection<Integer> res = doctors.values().stream().filter(x -> x.getPatients().size() > avg)
				.sorted(comparing(Doctor::getSurname).thenComparing(Doctor::getName))
				.map(x -> x.getBadgeNumber()).toList();
		return res;
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		Collection<String> res = doctors.values().stream().sorted(comparing(Doctor::getNumberOfPatients, reverseOrder()))
				.map(x -> {
					String s = String.format("%03d : %d %s %s", x.getNumberOfPatients(), x.getBadgeNumber(), x.getSurname(), x.getName());
					return s;
				}).collect(toList());
		return res;
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		Collection<String> res = patients.values().stream().filter(p -> p.getAssignedDoctor() != null).collect(groupingBy(
				p -> p.getAssignedDoctor().getSpecialization(), counting())).entrySet().stream()
				.map(e -> String.format("%3d - %s", e.getValue(), e.getKey())).collect(toList());
		return res;
	}

}
