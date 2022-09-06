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
import modle.Order;
import modle.User;

/**
 * Servlet implementation class AddOrder
 */
@WebServlet("/AddOrder")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User ourUser = (User) session.getAttribute("User");
		Vector<Game> cart = (Vector<Game>) session.getAttribute("Cart");
		out.println("<script>");
		// Call the function to add the new order to the client after he submit payment
		if (Control.addOrder(cart, ourUser.getId())) {
			// If ordered added we add the new order to the client history
			Vector<Order> history = Control.createHistoryVector(ourUser.getId());
			session.removeAttribute("History");
			session.setAttribute("History", history);
			// Show the proper message and bring us to the index
			out.println("window.alert('Order has been executed');");
			out.println("window.location='http://localhost:8000/GameWarrior/IndexSignOutClient.jsp';");
			session.setAttribute("Cart", cart);
		} else {
			// If ordered did not added we keep the client history as it was
			Vector<Order> history = Control.createHistoryVector(ourUser.getId());
			session.removeAttribute("History");
			session.setAttribute("History", history);
			session.setAttribute("Cart", cart);
			// Go to game Info page while show proprer message
			out.println("window.alert('Error in executing order');");
			out.println("window.location = 'http://localhost:8000/GameWarrior/GameInfo.jsp';");
		}
		out.println("</script>");
	}

}
