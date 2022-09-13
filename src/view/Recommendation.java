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
import modle.User;

/**
 * Servlet implementation class Recommendation
 */
@WebServlet("/Recommendation")
public class Recommendation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Recommendation() {
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
		User ourUser = (User) session.getAttribute("User");
		int clientId = ourUser.getId();
		// We get our games id that recommended to the client using our algorithm
		Vector<Integer> idRecommended = Control.recommendedAlgo(clientId);
		Vector<Game> recommendedGames = new Vector<Game>();
		// If we had problem in the algorithm send error message
		out.println("<script>");
		if (idRecommended == null) {
			out.println("window.alert('System Error');");
			out.println("window.location='http://localhost:8000/GameWarrior/IndexSignOutClient.jsp';");
		} else { // If we got the id of the games that recommended get the games object
					// and add them to the session will display them into the recommended page
			for (int i = 0; i < idRecommended.size(); i++) {
				recommendedGames.add(Control.getGame(idRecommended.get(i)));
			}
			session.setAttribute("RecommendedGames", recommendedGames);
			out.println("window.location='http://localhost:8000/GameWarrior/Recommendation.jsp';");
		}
		out.println("</script>");
	}

}
