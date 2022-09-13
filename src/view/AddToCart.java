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

@SuppressWarnings("unchecked")

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToCart() {
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
		// Get our cart attribute from the session
		HttpSession session = request.getSession();
		String idStr = request.getParameter("gameId").toString();
		int id = Integer.parseInt(idStr);
		// Add our game to the cart and update the session
		Game game = Control.getGame(id);
		Vector<Game> cart = (Vector<Game>) session.getAttribute("Cart");
		out.println("<script>");
		if (cart.add(game)) {
			session.setAttribute("Cart", cart);
			// After adding it to the cart send proper message
			out.println("window.alert('Game added to cart');");
			out.println("window.location = 'http://localhost:8000/GameWarrior/GameInfo.jsp';");
		} else {
			// If did not succeeded in adding game to the cart send proper message
			out.println("window.alert('Error in adding game to cart');");
			out.println("window.location = 'http://localhost:8000/GameWarrior/GameInfo.jsp';");
		}
		out.println("</script>");
	}

}
