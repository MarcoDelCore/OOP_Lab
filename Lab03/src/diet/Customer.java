package diet;


public class Customer{
	String firstName;
	String lastName;
	String email;
	String phoneNumber;
	
	public Customer(String fn, String ln, String e, String p) {
		firstName = fn;
		lastName = ln;
		email = e;
		phoneNumber = p;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phoneNumber;
	}
	
	public void SetEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phoneNumber = phone;
	}

	public String toStr() {
		return firstName + " " + lastName;
	}
	
	public String toString() {
		return firstName + " " + lastName;
	}
	
}
