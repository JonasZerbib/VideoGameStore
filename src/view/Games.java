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
 * Servlet implementation class Games
 */
@WebServlet("/Games")
public class Games extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Games() {
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
		// Update our games in genre and also relevant games
		Vector<Game> relevantGames = Control.getRelevantGames();
		Genre actionGames = new Genre("Action", Control.createGenreGameVector("Action"));
		Genre adventureGames = new Genre("Adventure", Control.createGenreGameVector("Adventure"));
		Genre rpgGames = new Genre("RPG", Control.createGenreGameVector("RPG"));
		Genre sportGames = new Genre("Sport", Control.createGenreGameVector("Sport"));
		Genre strategyGames = new Genre("strategy", Control.createGenreGameVector("Strategy"));
		// Update game categories
		session.setAttribute("Games", relevantGames);
		session.setAttribute("ActionGames", actionGames);
		session.setAttribute("AdventureGames", adventureGames);
		session.setAttribute("RPGGames", rpgGames);
		session.setAttribute("SportGames", sportGames);
		session.setAttribute("StrategyGames", strategyGames);
		// Direct to games page
		out.println("<script>");
		out.println("window.location='http://localhost:8000/GameWarrior/Games.jsp';");
		out.println("</script>");
	}

}
