//Or Cohen 307852681
//Jonas Zerbib 340941301
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;

public class Database {
	private static Connection conn = null;
	private static Statement statement = null;
	private static PreparedStatement pStatement = null;
	// Connecting to our database so we could use queries
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dbUrl = "jdbc:mysql://localhost/gameWarior";
			conn = DriverManager.getConnection(dbUrl, "root", "");
			statement = (Statement) conn.createStatement();
		} catch (Exception error) {
			System.out.println("Error in our database");
			System.out.println(error.getMessage());
		}
	}

	// The function get string and check if we have user with the same username
	public static boolean isUserExist(String username) {
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("SELECT * FROM Users WHERE userName=(?);");
			pStatement.setString(1, username);
			ResultSet rs = pStatement.executeQuery();
			// Check if we got row or not if yes return true
			while (rs.next()) {
				return true;
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// The function used to add user to the database
	public static boolean addUser(String userName, String email, String password, String firstName, String lastName,
			String birthday, String phone, String gender) {

		try {
			pStatement = (PreparedStatement) conn.prepareStatement(
					"INSERT INTO Users(userName,email,password,firstName,lastName,birthday,phone,gender,type) VALUES (?,?,?,?,?,?,?,?,?)");
			pStatement.setString(1, userName);
			pStatement.setString(2, email);
			pStatement.setString(3, password);
			pStatement.setString(4, firstName);
			pStatement.setString(5, lastName);
			pStatement.setString(6, birthday);
			pStatement.setString(7, phone);
			pStatement.setString(8, gender);
			pStatement.setString(9, "Client");
			pStatement.executeUpdate();

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return false;
		}
		return true;
	}

	// Functions that update our user details
	public static boolean updateField(int id, String email, String password, String firstName, String lastName,
			String birthday, String phone, String gender) {
		try {
			pStatement = (PreparedStatement) conn
					.prepareStatement("UPDATE Users SET email=(?), password=(?), firstName=(?)"
							+ ", lastName=(?), birthday=(?), phone=(?), gender=(?) WHERE id=(?);");
			pStatement.setString(1, email);
			pStatement.setString(2, password);
			pStatement.setString(3, firstName);
			pStatement.setString(4, lastName);
			pStatement.setString(5, birthday);
			pStatement.setString(6, phone);
			pStatement.setString(7, gender);
			pStatement.setInt(8, id);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return false;
	}

	// Function that return the games our user ordered
	public static ResultSet userGames(int userId) {
		ResultSet rs = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("SELECT * FROM Orders WHERE idClient=(?);");
			pStatement.setInt(1, userId);
			rs = pStatement.executeQuery();
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return null;
		}
	}

	// Function to get game from database
	public static ResultSet getGame(int idGame) {
		ResultSet rs = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("SELECT * FROM Games WHERE id=(?);");
			pStatement.setInt(1, idGame);
			rs = pStatement.executeQuery();
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return null;
		}
	}

	// Function to get relevant games from database
	public static ResultSet getRelevantGame() {
		ResultSet rs = null;
		String getGame = "SELECT * FROM Games WHERE relevant='Yes';";
		try {
			rs = statement.executeQuery(getGame);
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return null;
		}
	}

	// Function to get a user from database
	public static ResultSet getUser(String userName) {
		ResultSet rs = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("SELECT * FROM Users WHERE userName=(?);");
			pStatement.setString(1, userName);
			rs = pStatement.executeQuery();
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return null;
		}

	}

	// Functions that update our game details
	public static boolean updateGame(int gameId, double price, String name, String summary, String developer,
			String publisher, String trailer, String picture, String releaseDate, String[] gameGenre) {

		try {
			// Get old genre of the game
			pStatement = (PreparedStatement) conn.prepareStatement("SELECT * FROM Genre WHERE gameId=(?);");
			pStatement.setInt(1, gameId);
			ResultSet rs1 = pStatement.executeQuery();
			Vector<String> gameGenre1 = new Vector<String>();
			while (rs1.next()) {
				gameGenre1.add(rs1.getString("genre"));
			}
			String[] oldGameGenre = new String[gameGenre1.size()];
			for (int i = 0; i < oldGameGenre.length; i++) {
				oldGameGenre[i] = gameGenre1.elementAt(i);

			}
			// Delete old genre
			pStatement = (PreparedStatement) conn.prepareStatement("DELETE FROM Genre WHERE gameId=(?);");
			pStatement.setInt(1, gameId);
			pStatement.executeUpdate();
			// Update genre
			pStatement = (PreparedStatement) conn.prepareStatement(
					"UPDATE Games SET price=(?), name=(?), summary=(?), developer=(?), publisher=(?), trailer=(?), picture=(?), releaseDate=(?) WHERE id=(?);");
			pStatement.setDouble(1, price);
			pStatement.setString(2, name);
			pStatement.setString(3, summary);
			pStatement.setString(4, developer);
			pStatement.setString(5, publisher);
			pStatement.setString(6, trailer);
			pStatement.setString(7, picture);
			pStatement.setString(8, releaseDate);
			pStatement.setInt(9, gameId);
			if (addGameToGenre(gameGenre, gameId)) {
				pStatement.executeUpdate();
				return true;
			} else {
				// If failed update genre return old genre and don't update
				addGameToGenre(oldGameGenre, gameId);
			}

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return false;
	}

	// Function that add new game to our database
	public static boolean addGame(double price, String name, String summary, String developer, String publisher,
			String trailer, String picture, String releaseDate, String[] gamesGenre) {
		int gameId = -1;
		ResultSet rs = null;

		try {
			pStatement = (PreparedStatement) conn.prepareStatement(
					"INSERT INTO Games (price,name,summary,developer,publisher,trailer,picture,releaseDate) VALUES (?,?,?,?,?,?,?,?);");
			pStatement.setDouble(1, price);
			pStatement.setString(2, name);
			pStatement.setString(3, summary);
			pStatement.setString(4, developer);
			pStatement.setString(5, publisher);
			pStatement.setString(6, trailer);
			pStatement.setString(7, picture);
			pStatement.setString(8, releaseDate);
			// Add our game to the the Games table
			pStatement.executeUpdate();
			// Using a Query to get the game Id so we could add his genres to the Genre
			// table
			pStatement = (PreparedStatement) conn.prepareStatement(
					"SELECT * FROM Games WHERE price=(?) AND name=(?) AND summary=(?) AND developer=(?) AND  publisher=(?) AND trailer=(?) AND picture=(?) AND releaseDate=(?);");
			pStatement.setDouble(1, price);
			pStatement.setString(2, name);
			pStatement.setString(3, summary);
			pStatement.setString(4, developer);
			pStatement.setString(5, publisher);
			pStatement.setString(6, trailer);
			pStatement.setString(7, picture);
			pStatement.setString(8, releaseDate);
			rs = pStatement.executeQuery();
			rs.next();
			gameId = rs.getInt("id");
			if (addGameToGenre(gamesGenre, gameId)) {
				return true;
			}
			pStatement = (PreparedStatement) conn.prepareStatement("DELETE FROM Games WHERE gameId=(?);");
			pStatement.setInt(1, gameId);
			pStatement.executeUpdate();
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Add the game genre to our Genre table after adding new game
	public static boolean addGameToGenre(String[] gamesGenre, int gameId) {
		int j = 0;
		PreparedStatement StatementTwo=null;
		try {
			// Trying to add the game genres
			for (int i = 0; i < gamesGenre.length; i++) {
				StatementTwo = (PreparedStatement) conn.prepareStatement("INSERT INTO Genre(genre,gameId) VALUES (?,?);");
				StatementTwo.setString(1, gamesGenre[i]);
				StatementTwo.setInt(2, gameId);
				StatementTwo.executeUpdate();
				j = i;
			}
			return true;
		} catch (SQLException sqlError) {
			// If failed display proper message and try to delete the genres entered already
			// to the table
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			try {
				// Try to delete the Genre entered already
				for (int i = 0; i < j; i++) {
					StatementTwo = (PreparedStatement) conn.prepareStatement("DELETE FROM Genre WHERE gameId=(?);");
					StatementTwo.setInt(1, gameId);
					StatementTwo.executeUpdate();
				}
			} catch (SQLException sqlSecondError) {
				System.out.println(sqlSecondError.getMessage());
				sqlSecondError.printStackTrace();
				return false;
			}
			return false;
		}
	}

	// Function that return result set of all clients
	public static ResultSet getClients() {
		ResultSet rs = null;
		String get = "SELECT * FROM Users WHERE type='Client';";
		try {
			rs = statement.executeQuery(get);
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return null;
		}

	}

	// Function that delete a user
	public static boolean deleteUser(String userName) {
		ResultSet rs = null;
		int id;

		try {
			pStatement = (PreparedStatement) conn.prepareStatement("SELECT id FROM Users WHERE userName=(?);");
			pStatement.setString(1, userName);
			// We also delete is orders and order lines that are connected to his orders
			rs = pStatement.executeQuery();
			rs.next();
			id = rs.getInt("id");
			deleteOrderByClientId(id);
			deleteRatingByClientId(id);
			pStatement = (PreparedStatement) conn.prepareStatement("DELETE FROM Users WHERE id=(?);");
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return false;

	}

	// Delete our orders of certain user id
	public static void deleteOrderByClientId(int id) throws SQLException {
		PreparedStatement statementTwo = null;
		statementTwo = (PreparedStatement) conn.prepareStatement("SELECT numOrder FROM Orders WHERE idClient=(?);");
		statementTwo.setInt(1, id);
		ResultSet rs = null;
		int numOrder = -1;
		rs = statementTwo.executeQuery();
		if (!rs.next()) {

		} else {
			do {
				numOrder = rs.getInt("numOrder");
				statementTwo = (PreparedStatement) conn.prepareStatement("DELETE FROM OrderLines WHERE numOrder=(?);");
				statementTwo.setInt(1, numOrder);
				statementTwo.executeUpdate();
				statementTwo = (PreparedStatement) conn.prepareStatement("DELETE FROM Orders WHERE numOrder=(?);");
				statementTwo.setInt(1, numOrder);
				statementTwo.executeUpdate();
			} while (rs.next());
		}
		rs.close();
	}

	// Function that unrelevant game
	public static boolean unrelevantGame(int id) {
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("UPDATE Games SET relevant='No' WHERE id=(?);");
			pStatement.setInt(1, id);
			if (deleteGameFromGenre(id)) {
				pStatement.executeUpdate();
				return true;
			}
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();

		}
		return false;

	}

	// delete the game from the genre table
	public static boolean deleteGameFromGenre(int id) throws SQLException {
		PreparedStatement statementTwo = null;
		statementTwo = (PreparedStatement) conn.prepareStatement("DELETE FROM Genre WHERE gameId=(?);");
		statementTwo.setInt(1, id);
		statementTwo.executeUpdate();
		return true;

	}

	// Return result set with all the games in specify genre
	public static ResultSet getGamesByGenre(String genre) {
		ResultSet rs = null;
		try {
			PreparedStatement statementTwo = null;
			statementTwo = (PreparedStatement) conn.prepareStatement("SELECT * FROM Genre WHERE genre=(?);");
			statementTwo.setString(1, genre);
			rs = statementTwo.executeQuery();
			return getGamesById(rs);
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return null;
	}

	// Gets a ResultSet with all the id of games and return a ResultSet with all
	// this games
	public static ResultSet getGamesById(ResultSet rs) throws SQLException {
		Statement statementTwo = (Statement) conn.createStatement();
		if (rs.next()) {
			String get = "SELECT * FROM Games WHERE id=" + rs.getInt("gameId");
			while (rs.next()) {
				get = get + " OR id=" + rs.getInt("gameId");
			}
			get = get + ";";
			return statementTwo.executeQuery(get);
		}
		return null;
	}

	// Get our client orders
	public static ResultSet getClientOrders(int id) {
		ResultSet rs = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("SELECT * FROM Orders WHERE idClient=(?);");
			pStatement.setInt(1, id);
			rs = pStatement.executeQuery();
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return null;

	}

	// Get our Order lines for a numOrder
	public static ResultSet getOrderLines(int numOrder) {
		ResultSet rs;
		try {
			PreparedStatement statementTwo = null;
			statementTwo = (PreparedStatement) conn.prepareStatement("SELECT * FROM OrderLines WHERE numOrder=(?);");
			statementTwo.setInt(1, numOrder);
			rs = statementTwo.executeQuery();
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return null;

	}

	// Function to add new order
	public static ResultSet addOrder(int idClient) {
		ResultSet rs = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("INSERT INTO Orders (idClient) VALUES (?);");
			pStatement.setInt(1, idClient);
			pStatement.executeUpdate();
			// Locking tables so we could get our orderNum
			lockTables();
			// Getting the new order num
			rs = getLastNumOrder();
			unlockTables();
			return rs;

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return null;
	}

	// Function that delete order with same id
	public static boolean deleteOrder(int numOrder) {
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("DELETE FROM Orders WHERE numOrder=(?);");
			pStatement.setInt(1, numOrder);
			pStatement.executeUpdate();
			return true;

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Function that adds the order lines for order
	public static boolean addOrderLines(int numOrder, int gameId) {
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("INSERT INTO OrderLines (numOrder,gameId) VALUES(?,?);");
			pStatement.setInt(1, numOrder);
			pStatement.setInt(2, gameId);
			pStatement.executeUpdate();
			return true;

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Function that delete the order lines for order
	public static boolean deleteOrderLines(int numOrder, int gameId) {
		try {
			pStatement = (PreparedStatement) conn.prepareStatement("DELETE FROM OrderLines WHERE numOrder=(?) AND gameId=(?);");
			pStatement.setInt(1, numOrder);
			pStatement.setInt(2, gameId);
			pStatement.executeUpdate();
			return true;

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Function that delete the order lines for number of order
	public static boolean deleteOrderLines(int numOrder) {


		try {
			pStatement = (PreparedStatement) conn.prepareStatement("DELETE FROM OrderLines WHERE numOrder=(?);");
			pStatement.setInt(1, numOrder);
			pStatement.executeUpdate();
			return true;

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Getting the result set contain numOrder of last order
	public static ResultSet getLastNumOrder() {

		ResultSet rs = null;
		String get = "SELECT * FROM Orders WHERE numOrder=LAST_INSERT_ID();";
		try {
			Statement statementTwo = (Statement) conn.createStatement();
			rs = statementTwo.executeQuery(get);
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return null;
	}

	// Lock tables
	public static void lockTables() {
		String lock = "LOCK TABLES Orders WRITE ,Games WRITE ,Users WRITE,OrderLines WRITE,Genre WRITE";
		try {
			Statement statementThree = (Statement) conn.createStatement();
			statementThree.executeQuery(lock);

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}

	}

	// unlock tables
	public static void unlockTables() {
		String unlock = "UNLOCK TABLES";
		try {
			Statement statementFourth = (Statement) conn.createStatement();
			statementFourth.executeQuery(unlock);

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
	}

	// add rating zero to all the games to new user
	public static boolean addRatingRowsForNewUser(int idClient) {
		ResultSet rs = null;
		String numRow = "SELECT COUNT(*) FROM Games;";

		try {
			rs = statement.executeQuery(numRow);
			rs.next();
			int j = rs.getInt(1);
			for (int i = 1; i <= j; i++) {
				if (!addRating(idClient, i, 0)) {
					for (int k = i - 1; k > 0; k--) {
						deleteRating(idClient, k);
					}
					return false;
				}
			}
			return true;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Add rate of a game by user to our table
	public static boolean addRating(int idClient, int gameId, double rating) {
		
		try {
			PreparedStatement statementTwo = null;
			 statementTwo = (PreparedStatement) conn.prepareStatement("INSERT Rating(idClient,gameId,rating) VALUES(?,?,?);");
			 statementTwo.setInt(1, idClient);
			 statementTwo.setInt(2, gameId);
			 statementTwo.setDouble(3, rating);
			statementTwo.executeUpdate();
			return true;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Function to get the game id
	public static int getGameId(double price, String name, String summary, String developer, String publisher,
			String trailer, String picture, String releaseDate) {

		ResultSet rs = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(
					"SELECT * FROM Games WHERE price=(?) AND name=(?) AND summary=(?) AND developer=(?) AND publisher=(?) AND trailer=(?) AND picture=(?) AND releaseDate=(?);");
			pStatement.setDouble(1, price);
			pStatement.setString(2, name);
			pStatement.setString(3, summary);
			pStatement.setString(4, developer);
			pStatement.setString(5, publisher);
			pStatement.setString(6, trailer);
			pStatement.setString(7, picture);
			pStatement.setString(8, releaseDate);
			rs = pStatement.executeQuery();
			rs.next();
			return rs.getInt("id");
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return -1;
	}

	// Function to delete a game
	public static ResultSet deleteGame(int idGame) {
		ResultSet rs = null;
		try {
			pStatement=(PreparedStatement) conn.prepareStatement("DELETE  FROM Games WHERE id=(?);");
			pStatement.setInt(1, idGame);
			rs = pStatement.executeQuery();
			return rs;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
			return null;
		}
	}

	// Delete rate of a game by user and game id to our table
	public static boolean deleteRating(int idClient, int gameId) {
		try {
			PreparedStatement statementTwo = null;
			statementTwo=(PreparedStatement) conn.prepareStatement("DELETE FROM Rating WHERE idClient=(?) AND gameId=(?);");
			statementTwo.setInt(1, idClient);
			statementTwo.setInt(2, gameId);
			statementTwo.executeUpdate();
			return true;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Delete rate of a game by user to our table by client id
	public static boolean deleteRatingByClientId(int idClient) {
		try {
			PreparedStatement statementFive =null;
			statementFive=(PreparedStatement) conn.prepareStatement("DELETE FROM Rating WHERE idClient=(?);");
			statementFive.setInt(1, idClient);
			statementFive.executeUpdate();
			return true;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Add rate of a game by user to our table
	public static boolean updateRating(int idClient, int gameId, double rating) {
		
		try {
			PreparedStatement statementThree =null;
			statementThree =(PreparedStatement) conn.prepareStatement ("UPDATE Rating SET  rating=(?) WHERE idClient=(?) AND gameId=(?);");
			statementThree.setDouble(1,rating);
			statementThree.setInt(2,idClient);
			statementThree.setInt(3,gameId);
			statementThree.executeUpdate();
			return true;
		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return false;
	}

	// Add rating info of our user
	public static ResultSet getUserRating(int idClient) {
		ResultSet rs = null;
		try {
			pStatement=(PreparedStatement) conn.prepareStatement("SELECT * FROM Rating  WHERE idClient=(?);");
			pStatement.setInt(1,idClient);
			rs = pStatement.executeQuery();
			return rs;

		} catch (SQLException sqlError) {
			System.out.println(sqlError.getMessage());
			sqlError.printStackTrace();
		}
		return null;
	}
}
