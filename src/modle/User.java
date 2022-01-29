//Or Cohen 307852681
//Jonas Zerbib 340941301
package modle;

public class User {
	// User Class fields
	protected int id;
	protected String userName;
	protected String email;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String birthday;
	protected String phone;
	protected String gender;

	// Constructors
	public User() {
	}

	public User(String userName, String email, String password, String firstName, String lastName,
			String birthday, String phone, String gender) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.phone = phone;
		this.gender = gender;
	}

	public User(int id,String userName, String email, String password, String firstName, String lastName,
			String birthday, String phone, String gender) {
		super();
		this.id=id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.phone = phone;
		this.gender = gender;
	}

	public User(User newUser) {
		super();
		this.userName = newUser.userName;
		this.email = newUser.email;
		this.password = newUser.password;
		this.firstName = newUser.firstName;
		this.lastName = newUser.lastName;
		this.birthday = newUser.birthday;
		this.phone = newUser.phone;
		this.gender = newUser.gender;
	}

	// Get and Set functions
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
