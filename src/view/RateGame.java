//Or Cohen 307852681
//Jonas Zerbib 340941301
package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Control;
import modle.Game;
import modle.User;

/**
 * Servlet implementation class RateGame
 */
@WebServlet("/RateGame")
public class RateGame extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RateGame() {
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
		// Get the rating we entered
		String ratingStr = request.getParameter("rating");
		double rating = Double.parseDouble(ratingStr);
		// Get the user and game info
		User ourUser = (User) session.getAttribute("User");
		Game game = (Game) session.getAttribute("GameInfo");
		out.println("<script>");
		// Rate the game with the new rating
		if (Control.rateGame(ourUser.getId(), game.getId(), rating)) {
			// If succeed display proper message
			out.println("window.alert('Thank you for rating');");

		} else { // If succeed display proper message
			out.println("window.alert('Problem in rating');");
		}
		session.setAttribute("ourRating", rating);
		out.println("window.location='http://localhost:8000/GameWarrior/GameInfo.jsp';");
		out.println("</script>");

	}

}
