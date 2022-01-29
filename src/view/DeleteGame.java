//Or Cohen 307852681
//Jonas Zerbib 340941301
package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Control;
import modle.Game;
import modle.Genre;

/**
 * Servlet implementation class DeleteGame
 */
@WebServlet("/DeleteGame")
public class DeleteGame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteGame() {
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
		// Get the game we wanted to delete
		String[] gameId = request.getParameterValues("game[]");
		out.println("<script>");
		// If deleted(unrelevant) send proper message else send a error
		if (Control.unrelevantGame(gameId)) {

			out.println("window.alert('Action has been done');");
			out.println("window.location='http://localhost:8000/GameWarrior/DeleteGame.jsp';");

		} else {

			out.println("window.alert('System error');");
			out.println("window.location='http://localhost:8000/GameWarrior/DeleteGame.jsp';");

		}
		// After finishing we update our session attributes so the game will not appear
		// in the store
		// update each array contains the game by certain genre and also the array that
		// contain all the games
		// if we did not succeed in urelevant him the store will stay the same as it was
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
