package view;

import controller.Control;
import modle.Game;
import modle.Genre;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddGame
 */
@WebServlet("/AddGame")
public class AddGame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddGame() {
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
		HttpSession session = request.getSession();
		// Get the info of the game we want to add from the request
		String name = request.getParameter("name");
		String priceStr = request.getParameter("price").toString();
		double price = Double.valueOf(priceStr);
		String summary = request.getParameter("summary");
		String developer = request.getParameter("developer");
		String publisher = request.getParameter("publisher");
		String trailer = request.getParameter("trailer");
		String picture = request.getParameter("picture");
		String releaseDate = request.getParameter("releaseDate");
		String[] gameGenre = request.getParameterValues("genre[]");
		out.println("<script>");
		// Try to add the game to our database
		if (Control.addGame(name, price, summary, developer, publisher, trailer, picture, releaseDate, gameGenre)) {
			// If we succeeded in adding the game send proper message

			out.println("window.alert('Game has been added');");
			out.println("window.location='http://localhost:8000/GameWarrior/IndexSignOutManager.jsp';");

		} else {
			// If we did not succeeded in adding the game send proper message and show a
			// alert message

			out.println("window.alert('System error');");
			out.println("window.location='http://localhost:8000/GameWarrior/AddGame.jsp';");

		}
		// Update our attributes of the session
		// We actually update the arrays which each array contain games by genre
		// and also the array which contain all the games we do this so we could display
		// the new game we added in
		// the store
		session.removeAttribute("Games");
		session.removeAttribute("ActionGames");
		session.removeAttribute("AdventureGames");
		session.removeAttribute("RPGGames");
		session.removeAttribute("SportGames");
		session.removeAttribute("StrategyGames");
		Vector<Game> relevantGames = Control.getRelevantGames();
		Genre actionGames = new Genre("Action", Control.createGenreGameVector("Action"));
		Genre adventureGames = new Genre("Adventure", Control.createGenreGameVector("Adventure"));
		Genre rpgGames = new Genre("RPG", Control.createGenreGameVector("RPG"));
		Genre sportGames = new Genre("Sport", Control.createGenreGameVector("Sport"));
		Genre strategyGames = new Genre("strategy", Control.createGenreGameVector("Strategy"));
		session.setAttribute("Games", relevantGames);
		session.setAttribute("ActionGames", actionGames);
		session.setAttribute("AdventureGames", adventureGames);
		session.setAttribute("RPGGames", rpgGames);
		session.setAttribute("SportGames", sportGames);
		session.setAttribute("StrategyGames", strategyGames);
		out.println("</script>");

	}

}
