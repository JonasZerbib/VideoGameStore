package modle;

public class Administrator extends User {
	// Constructors
	public Administrator() {
		super();
	}

	public Administrator(int id, String userName, String email, String password, String firstName, String lastName,
			String birthday, String phone, String gender) {
		super(id, userName, email, password, firstName, lastName, birthday, phone, gender);
	}

}
