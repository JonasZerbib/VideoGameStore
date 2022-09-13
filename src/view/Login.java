package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import controller.Control;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modle.*;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		User ourUser = null;
		HttpSession session = request.getSession();
		// Get the username and password the user entered
		String username = request.getParameter("username").toString();
		String password = request.getParameter("password").toString();
		// Get all the games relevant and also the games by genre
		Vector<Game> relevantGames = Control.getRelevantGames();
		Genre actionGames = new Genre("Action", Control.createGenreGameVector("Action"));
		Genre adventureGames = new Genre("Adventure", Control.createGenreGameVector("Adventure"));
		Genre rpgGames = new Genre("RPG", Control.createGenreGameVector("RPG"));
		Genre sportGames = new Genre("Sport", Control.createGenreGameVector("Sport"));
		Genre strategyGames = new Genre("strategy", Control.createGenreGameVector("Strategy"));
		// Check if the username is exist in the database
		out.println("<script>");
		boolean exist = Control.isExist(username);
		if (exist) {
			// Check if the password match
			if (Control.passwordMatch(username, password)) {
				// Get the user object
				ourUser = Control.getUser(username);
				// If failed show error

				if (ourUser == null) {
					out.println("window.alert('System error');");
					out.println("window.location='http://localhost:8000/GameWarrior/Login.jsp';");
				}
				session.invalidate();
				session = request.getSession(true);
				// Enter the user info to the session
				session.setAttribute("User", ourUser);
				// Enter the games by genre and all the games arrays in the session so we could
				// display to the user the games in the store
				session.setAttribute("Games", relevantGames);
				session.setAttribute("ActionGames", actionGames);
				session.setAttribute("AdventureGames", adventureGames);
				session.setAttribute("RPGGames", rpgGames);
				session.setAttribute("SportGames", sportGames);
				session.setAttribute("StrategyGames", strategyGames);

				// If the user is client get is order history and create him vector of games
				// that contain the games he wish to buy(cart)
				if (ourUser instanceof Client) {
					int id = ourUser.getId();
					Vector<Order> history = Control.createHistoryVector(id);
					Vector<Game> cart = new Vector<Game>();
					session.setAttribute("Cart", cart);
					session.setAttribute("History", history);
					out.println("window.location='http://localhost:8000/GameWarrior/IndexSignOutClient.jsp';");
				} else {
					// If the user is admin enter to the session vector with info on all the clients
					ArrayList<User> clients = Control.getClientUsers();
					session.setAttribute("Clients", clients);
					out.println("window.location='http://localhost:8000/GameWarrior/IndexSignOutManager.jsp';");
				}

			} else {
				// If password does not match the username password display error
				out.println("window.alert('Invaild password');");
				out.println("window.location='http://localhost:8000/GameWarrior/Login.jsp';");

			}

		} else {
			// If we don't have user with this username display error
			out.println("window.alert('Invaild username');");
			out.println("window.location='http://localhost:8000/GameWarrior/Login.jsp';");

		}
		out.println("</script>");
	}

}
