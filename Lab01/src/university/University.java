package university;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	String name, rector;
	int ID = 10000;
	int cod_corso = 10;
	int n_students = 0;
	int n_corsi=0;
	int n_esami=0;
	Student students[] = new Student[100];
	Corso corsi[] = new Corso[50];
	esami exams[] = new esami[50000];
	Rank ranking = new Rank();
	
	private final int FIRST_COURSE = 10;
	private final int FIRST_STUDENT = 10000;
	
// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		// Example of logging
		// logger.info("Creating extended university object");
		//TODO: to be implemented
		this.name = name;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		//TODO: to be implemented
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		//TODO: to be implemented
		rector = first + " " + last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		//TODO: to be implemented
		return rector;
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		//TODO: to be implemented
		students[n_students] = new Student(first, last, ID);
		int cod = students[n_students].getId();
		ID++;
		n_students++;
		logger.info("New student enrolled: " + cod + ", " + first + " " + last);
		return cod;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		//TODO: to be implemented
		for (Student student:this.students) {
			if (student.id == id) {
				return student.toString();
			}
		}
		return null;
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		//TODO: to be implemented
		corsi[n_corsi] = new Corso(title, teacher, cod_corso);
		int cod = corsi[n_corsi].getID();
		cod_corso++;
		n_corsi++;
		logger.info("New course activated: " + cod + ", " + title + " " + teacher);
		return cod;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		//TODO: to be implemented
		for (Corso corso : corsi) {
			if (code == corso.codCorso) {
				return corso.toStr();
			}
		}
		return null;
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		//TODO: to be implemented
		for (Student studente : students) {
			if (studente.id == studentID) {
				studente.corsi[studente.n_corsi] = new Corso();
				for (Corso corso : corsi) {
					if (corso.codCorso == courseCode) {
						corso.partecipanti[corso.n_partecipanti] = new Student();
						corso.enrollStudent(studente);
						studente.addCourse(corso);
						logger.info("Student " + studentID + " signed up for course " + courseCode);
						break;
					}
				}
				break;
			}
		}
		
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		//TODO: to be implemented
		for (Corso corso : corsi) {
			if (corso.codCorso == courseCode) {
				return corso.getEnrolled();
			}
		}
		return null;
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		//TODO: to be implemented
		for (Student studente : students) {
			if (studente.id == studentID) {
				return studente.getStudyPlan();
			}
		}
		return null;
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		Student s = getStudent(studentId);
		Corso c = getCourse(courseID);
		exams[n_esami] = new esami(s, c, grade);
		s.addExam(exams[n_esami]);
		n_esami++;
		logger.info("Student " + studentId + " took an exam in course " + courseID + " with grade " + grade);
		return;
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		Student s = getStudent(studentId);
		double media = s.getAverage();
		System.out.println(media);
		if (media != 0.0) {
			return ("Student " + studentId + " : " + media);
		}
		return ("Student " + studentId + " hasn't taken any exams");
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		double sum = 0.0;
		int n = 0;
		String name = getCourseName(courseId);
		for (esami e : exams) {
			if (e != null && e.corso.codCorso == courseId) {
				sum += e.voto;
				n++;
			}
		}
		if (n != 0) {
			double media = sum/n;
			return ("The average for the course " + name + " is: " + media);
		}
		return ("No student has taken the exam in " + name);
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		for (Student s : students) {
			if (s != null && s.n_esami > 0) {
				double p = s.getAverage();
				p += (s.n_esami/s.n_corsi)*10;
				ranking.aggiungi(s, p);
			}
		}
		return ranking.topThree();
	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    private final static Logger logger = Logger.getLogger("University");
    
    
    
    private String getCourseStr(int code) {
    	Corso c = corsi[code-FIRST_COURSE];
    	return c.toStr();
    }
    
    private String getCourseName(int code) {
    	Corso c = corsi[code-FIRST_COURSE];
    	return c.getTitle();
    }
    
    private Corso getCourse(int code) {
    	return corsi[code-FIRST_COURSE];
    }

    private Student getStudent(int code) {
    	return students[code-FIRST_STUDENT];
    }
}
