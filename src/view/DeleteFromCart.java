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

import modle.Game;

/**
 * Servlet implementation class DeleteFromCart
 */
@WebServlet("/DeleteFromCart")
public class DeleteFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteFromCart() {
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
		// Get the cell number(line with the info about the game we wanted to buy) we
		// want to delete from the vector cart
		String cellStr = request.getParameter("cellNumber").toString();
		int cellNum = Integer.parseInt(cellStr);
		// Get the cart attribute from the session
		Vector<Game> cart = (Vector<Game>) session.getAttribute("Cart");
		// Remove the object we wanted
		out.println("<script>");
		try {
			// Try to remove it and update the cart attribute of the session
			// Send proper message
			cart.remove(cellNum);
			session.setAttribute("Cart", cart);
			out.println("window.alert('Game deleted from cart');");
			out.println("window.location = 'http://localhost:8000/GameWarrior/Cart.jsp';");

		} catch (Exception error) { // Send proper message if did not succeeded in removing
			out.println("window.alert('Error in deleting order line from the order');");
			out.println("window.location = 'http://localhost:8000/GameWarrior/Cart.jsp';");
		}
		out.println("</script>");
	}

}
