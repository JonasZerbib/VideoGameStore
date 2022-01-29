//Or Cohen 307852681
//Jonas Zerbib 340941301
package controller;

import modle.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import database.*;

public class Control {

	public Control() {
	};

	// Add our new user checks if the username exist already or password in the 2
	// fields are not the same
	public static int addUser(String username, String email, String password, String verifyPassword, String firstName,
			String lastName, String birthday, String phone, String gender) {
		// Checks if both fields of password are equal
		if (password.equals(verifyPassword) != true) {
			return 2;
		} // Checks if username exist already
		else if (Database.isUserExist(username)) {
			return 1;
		}
		// Calls function that add user to database and checks if it had happened
		if (Database.addUser(username, email, password, firstName, lastName, birthday, phone, gender)) {
			User ourUser;
			if ((ourUser = getUser(username)) == null) {
				return 3;
			}
			// Calls a function that add him to rating table with zeros on the rate of all
			// games
			if (!Database.addRatingRowsForNewUser(ourUser.getId())) {
				Database.deleteUser(username);
				return 3;
			}
			return 0;
		} else {
			return 3;
		}

	}

	// Function to add a game
	public static boolean addGame(String name, double price, String summary, String developer, String publisher,
			String trailer, String picture, String releaseDate, String[] gameGenre) {
		// Call the function in database and checks
		if (Database.addGame(price, name, summary, developer, publisher, trailer, picture, releaseDate, gameGenre)) {
			// Get all of our clients
			ArrayList<User> client = new ArrayList<User>();
			client = getClientUsers();
			if (client == null) {
				return false;
			}
			// Get the game we added
			int gameId = Database.getGameId(price, name, summary, developer, publisher, trailer, picture, releaseDate);
			// Add the rating of zero to game tos all the users
			for (int i = 0; i < client.size(); i++) {
				Database.addRating(client.get(i).getId(), gameId, 0);

			}
			return true;
		}
		return false;

	}

	// Function to update game fields
	public static boolean updateGame(int gameId, String name, double price, String summary, String developer,
			String publisher, String trailer, String picture, String releaseDate, String[] gameGenre) {

		return Database.updateGame(gameId, price, name, summary, developer, publisher, trailer, picture, releaseDate,
				gameGenre);
	}

	// Check if the password string match to the userName password
	public static boolean passwordMatch(String userName, String password) {
		ResultSet rs = Database.getUser(userName);
		try {
			// Get our fields
			rs.next();
			String pass = rs.getString("password");
			// Checks if the password entered is right one to the user password
			if (pass.equals(password) == true) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return false;

		}
	}

	// Function for login action
	public static User getUser(String username) {
		User ourUser = null;
		// get user name
		ResultSet rs = Database.getUser(username);
		try {
			if (!rs.next()) {
				return null;
			}
			// Get our fields
			int id = rs.getInt("id");
			String user = rs.getString("userName");
			String email = rs.getString("email");
			String pass = rs.getString("password");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String birthday = rs.getString("birthday");
			String phone = rs.getString("phone");
			String gender = rs.getString("gender");
			String clientOrAdmin = rs.getString("type");
			// Build a Client or Admin object
			if (clientOrAdmin.equals("Client")) {
				Vector<Game> userGames = userGames(id);
				ourUser = new Client(id, user, email, pass, firstName, lastName, birthday, phone, gender, userGames);
				return ourUser;
			} else {
				ourUser = new Administrator(id, user, email, pass, firstName, lastName, birthday, phone, gender);
				return ourUser;
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return null;

	}

	// Checks if our user is Administrator
	public static boolean isAdmin(String username) {
		// Get our user
		ResultSet rs = Database.getUser(username);
		try {
			if (!rs.next()) {
				return false;
			}
			// Check for the type
			String clientOrAdmin = rs.getString("type");
			if (clientOrAdmin.equals("Client")) {
				return false;// If client return false
			}
			return true;// If the client is admin return true
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return false;
		}
	}

	// Checks if our user exist in the database
	public static boolean isExist(String username) {
		if (Database.isUserExist(username)) {
			return true;

		}
		return false;
	}

	// Function that create Vector container with all the games our user bought
	public static Vector<Game> userGames(int idClient) {
		Vector<Order> history = createHistoryVector(idClient);
		Vector<Game> userGames = new Vector<Game>();
		// Checks all the orders we made and add the games we ordered
		for (int i = 0; i < history.size(); i++) {
			Vector<Game> orderGames = history.get(i).getOrderList();
			for (int j = 0; j < orderGames.size(); j++) {
				userGames.add(orderGames.get(j));
			}
		}
		return userGames;

	}

	// Function that update user field
	public static int updateField(int id, String email, String password, String verifyPassword, String firstName,
			String lastName, String birthday, String phone, String gender) {
		// Check if our password fields match
		if (password.equals(verifyPassword) != true) {
			return 2;
		} // Update our user fields and return zero if action succeed
		if (Database.updateField(id, email, password, firstName, lastName, birthday, phone, gender)) {
			return 0;
		}
		return 1;

	}

	// Create array list of our clients
	public static ArrayList<User> getClientUsers() {
		ResultSet rs;
		// Function that return result set of all our clients
		rs = Database.getClients();
		ArrayList<User> clients = null;
		User ourUser = null;
		if (rs == null) {
			return null;
		}
		try {
			// Move on all the result set and create a array of clients
			if (rs.next()) {
				clients = new ArrayList<User>();
				do {
					// Create new user object and enter it to our array
					int id = rs.getInt("id");
					String user = rs.getString("userName");
					String email = rs.getString("email");
					String pass = rs.getString("password");
					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					String birthday = rs.getString("birthday");
					String phone = rs.getString("phone");
					String gender = rs.getString("gender");
					ourUser = new User(id, user, email, pass, firstName, lastName, birthday, phone, gender);
					clients.add(ourUser);
				} while (rs.next());
				return clients;
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return null;
	}

	// Function to delete users
	public static boolean deleteUser(String[] userName) {

		for (int i = 0; i < userName.length; i++) {
			if (!Database.deleteUser(userName[i])) {
				return false;
			}
		}
		return true;
	}

	// Function that gets result set and return Game object
	public static Game fromRsToGame(ResultSet rs) throws SQLException {
		// Get our fields
		int id = rs.getInt("id");
		double price = rs.getDouble("price");
		String name = rs.getString("name");
		String summary = rs.getString("summary");
		String developer = rs.getString("developer");
		String publisher = rs.getString("publisher");
		String trailer = rs.getString("trailer");
		String picture = rs.getString("picture");
		String releaseDate = rs.getString("releaseDate");
		Game newGame = new Game(id, price, name, summary, developer, publisher, trailer, picture, releaseDate);
		return newGame;
	}

	// Function that creates vector of games with given result set
	public static Vector<Game> createVectorGames(ResultSet rs) {
		if (rs == null) {
			return null;
		}
		try {
			if (!rs.next()) {
				return null;
			} else {
				// Create the Vector container for the games
				Vector<Game> vector = new Vector<Game>();
				do {
					// Call a fromRsToGame that returns Game object and add it to our vector only if
					// game relevant
					if (rs.getString("relevant").equals("Yes")) {
						vector.add(fromRsToGame(rs));
					}
				} while (rs.next());
				return vector;
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return null;
		}
	}

	// Function that return vector of relevant games
	public static Vector<Game> getRelevantGames() {
		ResultSet rs = Database.getRelevantGame();
		return createVectorGames(rs);
	}

	// Function that urelevant games the we don't delete games because we want to
	// use them in the
	// algorithm and show our orders history instead we make them unrelevant so they
	// won't appear in the store anymore
	public static boolean unrelevantGame(String[] gameId) {
		for (int i = 0; i < gameId.length; i++) {
			int id = Integer.parseInt(gameId[i]);
			// Call the database function to make the game with this id unrelevant
			if (!Database.unrelevantGame(id)) {
				return false;
			}
		}
		return true;
	}

	// Function that create Vector of games of a given genre we want
	public static Vector<Game> createGenreGameVector(String genre) {
		ResultSet rs = Database.getGamesByGenre(genre);
		return createVectorGames(rs);
	}

	// Function that get us the game with this id
	public static Game getGame(int id) {
		ResultSet rs = Database.getGame(id);
		try {
			if (rs.next()) {
				// Call a function that make from our result set a game object
				return fromRsToGame(rs);
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return null;
	}

	// Function that create Vector of Orders of a given client id we want
	public static Vector<Order> createHistoryVector(int id) {
		// Get result set of our client orders
		ResultSet rs = Database.getClientOrders(id);
		int numOrder;
		Vector<Game> orderList;
		Vector<Order> history = null;
		Order newOrder = null;
		if (rs == null) {
			return null;
		} else {
			try {
				// Create a Vector<Order> containing all the orders our client dod
				history = new Vector<Order>();
				while (rs.next()) {
					// Get each order info and add it to client orders history
					numOrder = rs.getInt("numOrder");
					newOrder = new Order(numOrder, id, null);
					history.add(newOrder);
				}
				rs.close();
				for (int i = 0; i < history.size(); i++) {
					// Create order list that contains all the lines of each order
					orderList = createOrderList(history.get(i).getNumOrder());
					history.get(i).setOrderList(orderList);
				}

				return history;
			} catch (SQLException sqlError) {
				System.out.println(sqlError.getMessage());
				sqlError.printStackTrace();

			}
			return null;

		}

	}

	// Create orderList
	public static Vector<Game> createOrderList(int numOrder) {
		ResultSet rs = Database.getOrderLines(numOrder);
		Vector<Game> orderList;
		int id;
		if (rs == null) {
			return null;
		} else {
			try {
				// Create the order lines each line contains one game
				orderList = new Vector<Game>();
				while (rs.next()) {
					id = rs.getInt("gameId");
					orderList.add(getGame(id));

				}
				return orderList;
			} catch (SQLException sqlError) {
				System.out.println(sqlError.getMessage());
				sqlError.printStackTrace();

			}
			return null;

		}
	}

	// Function that add new order
	public static boolean addOrder(Vector<Game> orderGames, int id) {
		// Create new order in database
		ResultSet rs = Database.addOrder(id);
		int numOrder;
		try {
			if (rs != null) {
				rs.next();
				// Get the new order number
				numOrder = rs.getInt("numOrder");
				rs.close();
				// Call addOrderLines if failed adding them we delete the new order
				if (!addOrderLines(orderGames, numOrder)) {
					Database.deleteOrder(numOrder);
					return false;
				}
				return true;
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return false;

	}

	// Function that add out order lines of a order
	public static boolean addOrderLines(Vector<Game> orderGames, int numOrder) {
		int i = orderGames.size();
		boolean flag = true;
		while (i >= 0 && flag) {
			i--;
			// Add order line for each game in our cart
			if (i >= 0) {
				flag = Database.addOrderLines(numOrder, orderGames.get(i).getId());
			}

		}
		// If we did not succeed in adding all games return false and delete all that
		// entered
		if (flag == false) {
			for (int j = orderGames.size() - 1; j > i; j--) {
				Database.deleteOrderLines(numOrder, orderGames.get(j).getId());
			}
			return false;
		}
		orderGames.removeAllElements();
		return true;
	}

	// Function that update rating to a game
	public static boolean rateGame(int clientId, int gameId, double rating) {
		return Database.updateRating(clientId, gameId, rating);

	}

	// Function that delete order
	public static boolean deleteOrder(int numOrder) {

		if (Database.deleteOrder(numOrder) && Database.deleteOrderLines(numOrder)) {
			return true;
		}
		return false;
	}

	// Function that return games which contain the string in their name
	public static Vector<Game> search(String searchString) {
		Vector<Game> games = Control.getRelevantGames();
		Vector<Game> gameFound = new Vector<Game>();
		if(games==null || games.size()==0)
		{
			return gameFound;
		}
		for (int i = 0; i < games.size(); i++) { // If the name of the game contains our searchString
			String string = games.get(i).getName().toLowerCase();
			searchString = searchString.toLowerCase();
			if (string.contains(searchString)) {
				gameFound.add(games.get(i));
			}
		}
		return gameFound;
	}

	// Function that returns all orders in system
	public static HashMap<Integer, Vector<Order>> createOrders() {
		HashMap<Integer, Vector<Order>> orders = new HashMap<Integer, Vector<Order>>();
		ArrayList<User> clients = getClientUsers();
		if(clients==null)
		{
			return orders;
		}
		for (int i = 0; i < clients.size(); i++) { // Enter each client id and is orders vector
			Vector<Order> history = createHistoryVector(clients.get(i).getId());
			orders.put(clients.get(i).getId(), history);
		}
		return orders;
	}

	// Part2-Function that make a rating table of all users
	public static HashMap<Integer, HashMap<Integer, Double>> getRatingTable() {
		ResultSet rs = null;
		// Get all our clients
		ArrayList<User> client = getClientUsers();
		HashMap<Integer, Double> userRating;
		// Hash map that the key will be our client id and will contain the rating for
		// each game by the client
		HashMap<Integer, HashMap<Integer, Double>> ratingTable = new HashMap<Integer, HashMap<Integer, Double>>();
		if (client == null) {
			return null;
		}
		for (int i = 0; i < client.size(); i++) {
			// Get each client rating result set
			rs = Database.getUserRating(client.get(i).getId());
			if (rs == null) {
				return null;
			}
			try {
				userRating = new HashMap<Integer, Double>();
				while (rs.next()) {
					// Add the game and the matched client rating for the game in hash map
					int gameId = rs.getInt("gameId");
					double rating = rs.getDouble("rating");
					userRating.put(gameId, rating);
				}
				// Put the client and is rating table for the games in the hash map
				ratingTable.put(client.get(i).getId(), userRating);
			}

			catch (SQLException sqlError) {
				System.out.println(sqlError.getMessage());
				sqlError.printStackTrace();
				return null;

			}
		}
		return ratingTable;
	}

	// Part 3-Function that creates hash map with all the clients and average rating
	// for
	// each user
	public static HashMap<Integer, Double> averageRating() {
		HashMap<Integer, HashMap<Integer, Double>> ratingTable = getRatingTable();
		HashMap<Integer, Double> averageRateUsers = new HashMap<Integer, Double>();
		HashMap<Integer, Double> Rating = null;
		double count = 0, averageRate = 0;
		if (ratingTable == null) {
			return null;
		}
		// Find the average rating for each user
		for (Integer key : ratingTable.keySet()) {
			Rating = ratingTable.get(key);
			count = 0;
			// Checks our client rating game if 0 that means client didn't rate game
			for (Integer key2 : Rating.keySet()) {
				// Count only games the client rated
				if (Rating.get(key2) != 0) {
					count++;
				}
				averageRate = averageRate + Rating.get(key2);

			}
			// Find our client averageRate and add it to our
			if (count != 0) {
				averageRate = (averageRate / count);
			}
			averageRateUsers.put(key, averageRate);
			averageRate = 0;

		}
		return averageRateUsers;
	}

	// Part 4-Function that finds each cosine correlation to our user
	public static HashMap<Integer, Double> cosineCorrelation(HashMap<Integer, Double> ourUserRate, int clientId) {
		double x = 0, y = 0, sumX = 0, sumY = 0, sumXY = 0, cosine = 0;
		HashMap<Integer, HashMap<Integer, Double>> ratingTable = getRatingTable();
		HashMap<Integer, Double> cosineCorrelation = new HashMap<Integer, Double>();
		HashMap<Integer, Double> UserRate;
		// Find the cosine correlation rate using the mathematical foundations
		for (Integer key : ratingTable.keySet()) {
			if (clientId != key) {
				cosine = 0;
				x = 0;
				y = 0;
				sumX = 0;
				sumY = 0;
				sumXY = 0;
				// Get the other client rating table
				UserRate = ratingTable.get(key);
				for (Integer key2 : UserRate.keySet()) {
					y = UserRate.get(key2);
					x = ourUserRate.get(key2);
					if ((x != 0) && (y != 0)) {
						sumXY = sumXY + ((y) * (x));
					}
					if (x != 0) {
						sumX = sumX + Math.pow((x), 2);
					}
					if (y != 0) {
						sumY = sumY + Math.pow((y), 2);
					}

				}
				sumY = Math.sqrt(sumY);
				sumX = Math.sqrt(sumX);
				cosine = (sumXY) / (sumX * sumY);
				// If we find NaN as the cosine value we know that their is no similarity
				// because we divided in zero meaning no item both client rated
				if (Double.isNaN(cosine)) {
					cosine = 0;
				}
				// Add each similarity value of each client to our User to the hash map
				cosineCorrelation.put(key, cosine);

			}
		}
		return cosineCorrelation;
	}

	// Part 5- Function that find our most similar clients to our client by checking
	// the
	// cosine similarity value
	public static Vector<Integer> fiveNeighbors(HashMap<Integer, Double> cosineCorrelation) {
		Vector<Integer> similar = new Vector<Integer>();
		int counter = 0;
		boolean flag = false;
		;
		// Find the 5 most similar clients
		for (Integer key : cosineCorrelation.keySet()) {
			if (cosineCorrelation.get(key) != 0) {
				// Add the first five clients in case we have less then 5 clients in all of our
				// database
				if (counter <= 5) {
					counter++;
					similar.add(key);

				} else {
					// If we have more then 5 clients check and put only the 5 with the highest
					// similarity value
					flag = false;
					int smallest = 0;
					// If the vector is full and we found client with higher similarity looking for
					// the client with the lowest
					// similarity in the vector remove it and add our new client
					for (int i = 0; i < similar.size(); i++) {
						if (cosineCorrelation.get(similar.get(i)) < cosineCorrelation.get(key)) {
							flag = true;
							smallest = i;

						}
						if (cosineCorrelation.get(similar.get(i)) < cosineCorrelation.get(similar.get(smallest))) {
							smallest = i;
						}
					}
					if (flag == true) {

						similar.remove(smallest);
						similar.add(key);
					}
				}
			}
		}
		return similar;
	}

	// Part 6- Function that with the five nearest neighbors finds the expected rate
	// for each game of the client we want to recommend games
	public static HashMap<Integer, Double> expectedRating(Vector<Integer> similar,
			HashMap<Integer, Double> cosineCorrelation, int clientId, HashMap<Integer, Double> averageRateUsers) {
		HashMap<Integer, Double> expectedRating = new HashMap<Integer, Double>();
		HashMap<Integer, HashMap<Integer, Double>> ratingTable = getRatingTable();
		Vector<Game> relevantGames = getRelevantGames();
		double totalCosine = 0, expectGameRate = 0;
		int gameId;
		boolean someoneRated = false;
		// Finding the expected rate of item i for our client
		// We check the expected rate only for relevant games
		for (int i = 0; i < relevantGames.size(); i++) {
			totalCosine = 0;
			expectGameRate = 0;
			someoneRated = false;
			gameId = relevantGames.get(i).getId();
			// Using mathematical foundations to find the expected client rate for each game
			for (int j = 0; j < similar.size(); j++) {
				totalCosine = Math.abs(cosineCorrelation.get(similar.get(j))) + totalCosine;
				if (ratingTable.get(similar.get(j)).get(gameId) != 0) {
					someoneRated = true;
					expectGameRate = expectGameRate + (cosineCorrelation.get(similar.get(j))
							* (((ratingTable.get(similar.get(j))).get(gameId)) - averageRateUsers.get(similar.get(j))));
				}

			}

			if (someoneRated) {
				expectGameRate = expectGameRate / (totalCosine);
				expectGameRate = expectGameRate + averageRateUsers.get(clientId);
			} else {
				expectGameRate = 0;
			}
			if (Double.isNaN(expectGameRate)) {
				expectGameRate = 0;
			}
			// Putting each game id and is expected rate
			expectedRating.put(gameId, expectGameRate);
		}
		return expectedRating;
	}

	// Part 7- Checks each game expected rate and choose only the three with highest
	// score
	public static Vector<Integer> mostRecomeddedGames(HashMap<Integer, Double> expectedRating,
			HashMap<Integer, Double> ourUserRate, int clientId) {
		int counter = 1, smallestRate = 0;
		Vector<Integer> recomenddedGames = new Vector<Integer>();
		Vector<Game> userGames = userGames(clientId);
		boolean flag = false, flag2 = false;
		// Find the three top games we should recommend that our user did not bought
		for (int key : expectedRating.keySet()) {
			if (ourUserRate.get(key) == 0) {
				flag = false;
				for (int j = 0; j < userGames.size(); j++) {
					if (userGames.get(j).getId() == key) {
						flag = true;
					}
				}
				if (flag == false) {
					if (counter <= 3) {
						recomenddedGames.add(key);
						counter++;
					} else {
						flag2 = false;
						smallestRate = 0;
						for (int i = 0; i < recomenddedGames.size(); i++) {
							// Check if we found a game with higher expected rate if so looking for the game
							// with lowest expected rate in the recommendedGames vector and remove it and
							// add our game
							if ((expectedRating.get(recomenddedGames.get(i)) < expectedRating.get(key))
									&& flag2 == false) {
								flag2 = true;
								smallestRate = i;

							}
							if (expectedRating.get(recomenddedGames.get(i)) < expectedRating
									.get(recomenddedGames.get(smallestRate))) {
								smallestRate = i;
							}

						}
						if (flag2 == true) {

							recomenddedGames.remove(smallestRate);
							recomenddedGames.add(key);
						}

					}
				}
			}
		}
		return recomenddedGames;
	}

	/*
	 * Part1-Main Function Our recommendation algorithms is 5 nearest neighbors
	 * algorithm. The neighborhood-based algorithm calculates the similarity between
	 * two clients and produces a prediction for the client by taking the weighted
	 * average of all the ratings. we do similarity computation between the clients
	 * by using cosine based similarity in here we to identify the 5 most similar
	 * client to an active client After the 5 most similar clients are found use the
	 * rating for the games to find our expected game rate we have divided the
	 * algorithm function to small functions this
	 * 
	 */
	public static Vector<Integer> recommendedAlgo(int clientId) {
		HashMap<Integer, Double> cosineCorrelation = new HashMap<Integer, Double>();
		HashMap<Integer, Double> averageRateUsers = null;
		HashMap<Integer, Double> expectedRating = new HashMap<Integer, Double>();
		HashMap<Integer, HashMap<Integer, Double>> ratingTable = getRatingTable();
		HashMap<Integer, Double> ourUserRate = null;
		Vector<Integer> recomenddedGames = new Vector<Integer>();
		int[] gameArr = new int[3];
		int maxGame = 0;
		Vector<Integer> similar = null;
		Vector<Game> relevantGames = getRelevantGames();
		// If their is no games exit
		if (relevantGames == null || relevantGames.size() == 0) {
			return new Vector<Integer>();
		}
		try {
			averageRateUsers = averageRating();
			if (averageRateUsers == null) {
				return null;
			}
			ourUserRate = ratingTable.get(clientId);
			cosineCorrelation = cosineCorrelation(ourUserRate, clientId);
			similar = fiveNeighbors(cosineCorrelation);
			expectedRating = expectedRating(similar, cosineCorrelation, clientId, averageRateUsers);
			recomenddedGames = mostRecomeddedGames(expectedRating, ourUserRate, clientId);
			// Order our games from highest expected rate to lowest
			for (int i = 0; i < gameArr.length; i++) {
				// Each time find the game with highest rate and add it to array in the cell
				// with the same number as iteration number
				for (int key = 0; key < recomenddedGames.size(); key++) {
					if (key == 0) {
						maxGame = 0;
					}
					if (expectedRating.get(recomenddedGames.get(maxGame)) < expectedRating
							.get(recomenddedGames.get(key))) {
						maxGame = key;
					}

				}
				gameArr[i] = recomenddedGames.get(maxGame);
				recomenddedGames.remove(maxGame);
			}
			// Adding the games by the order to the vector
			recomenddedGames.add(gameArr[0]);
			recomenddedGames.add(gameArr[1]);
			recomenddedGames.add(gameArr[2]);
			return recomenddedGames;

		} catch (Exception error) {
			error.printStackTrace();
			return null;
		}
	}
}
