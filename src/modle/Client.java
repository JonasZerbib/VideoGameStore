package modle;

import java.util.*;

public class Client extends User {

	public Vector<Game> gamesPurchased;

	// Constructors
	public Client() {
		super();
	}

	public Client(User newUser, Vector<Game> gamesPurchased) {
		super(newUser);
		if (this.gamesPurchased == null) {
			gamesPurchased = new Vector<Game>();
		} else {
			this.gamesPurchased.clear();
		}

		this.gamesPurchased.setSize(gamesPurchased.size());
		Collections.copy(this.gamesPurchased, gamesPurchased);
	}

	public Client(int id, String userName, String email, String password, String firstName, String lastName,
			String birthday, String phone, String gender, Vector<Game> gamePurchased) {
		super(id, userName, email, password, firstName, lastName, birthday, phone, gender);
		if (this.gamesPurchased == null) {
			gamesPurchased = new Vector<Game>();
		} else {
			this.gamesPurchased.clear();
		}

		this.gamesPurchased.setSize(gamesPurchased.size());
		Collections.copy(this.gamesPurchased, gamesPurchased);

	}

	public Client(String userName, String email, String password, String firstName, String lastName, String birthday,
			String phone, String gender, Vector<Game> gamePurchased) {
		super(userName, email, password, firstName, lastName, birthday, phone, gender);
		if (this.gamesPurchased == null) {
			gamesPurchased = new Vector<Game>();
		} else {
			this.gamesPurchased.clear();
		}

		this.gamesPurchased.setSize(gamesPurchased.size());
		Collections.copy(this.gamesPurchased, gamesPurchased);

	}

	public Client(Client newClient) {
		super(newClient.userName, newClient.email, newClient.password, newClient.firstName, newClient.lastName,
				newClient.birthday, newClient.phone, newClient.gender);
		if (this.gamesPurchased == null) {
			gamesPurchased = new Vector<Game>();
		} else {
			this.gamesPurchased.clear();
		}

		this.gamesPurchased.setSize(newClient.gamesPurchased.size());
		Collections.copy(this.gamesPurchased, newClient.gamesPurchased);

	}

	// Get and Set functions
	public Vector<Game> getGamesPurchased() {
		return gamesPurchased;
	}

	public void setGamesPurchased(Vector<Game> gamesPurchased) {

		this.gamesPurchased.clear();
		this.gamesPurchased.setSize(gamesPurchased.size());
		Collections.copy(this.gamesPurchased, gamesPurchased);
	}
}
