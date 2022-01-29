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
import modle.Client;
import modle.Game;
import modle.User;

/**
 * Servlet implementation class GameInfo
 */
@WebServlet("/GameInfo")
public class GameInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameInfo() {
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
		// Get the game id of the game we clicked from the request also gets the user
		// rating on the game
		String getId = request.getParameter("gameId");
		int id = Integer.parseInt(getId);
		User ourUser = (User) session.getAttribute("User");
		Game gameToShow = Control.getGame(id);
		double rating = 0;
		if (ourUser instanceof Client) {
			rating = ((Control.getRatingTable()).get(ourUser.getId())).get(id);
		}
		// Show the game info if succeed else show proper message
		out.println("<script>");
		if (gameToShow == null) {

			out.println("window.alert('System error');");
			out.println("window.location.href = window.location.href;");

		} else {
			// Add to the session the gameInfo and rating attribute that we could show it to
			// the user no rating if it the manager
			session.setAttribute("GameInfo", gameToShow);
			if (ourUser instanceof Client) {
				session.setAttribute("ourRating", rating);
				out.println("window.location = 'http://localhost:8000/GameWarrior/GameInfo.jsp';");
			} else {
				out.println("window.location = 'http://localhost:8000/GameWarrior/GameInfoManager.jsp';");
			}
		}
		out.println("</script>");
	}

}
