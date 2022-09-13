package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Control;
import modle.Order;

/**
 * Servlet implementation class CancelOrder
 */
@WebServlet("/CancelOrder")
public class CancelOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CancelOrder() {
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
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		// Get the number of order we want to delete
		String idString = request.getParameter("numOrder");
		int numOrder = Integer.parseInt(idString);
		out.println("<script>");
		// We try to delete our order
		if (Control.deleteOrder(numOrder)) {
			// Create vector with all the orders of all the clients
			HashMap<Integer, Vector<Order>> orders = Control.createOrders();
			session.setAttribute("Orders", orders);
			out.println("window.alert('Order Canceled');");
			out.println("window.location='http://localhost:8000/GameWarrior/HistoryManager.jsp';");

		} else {
			out.println("window.alert('System error');");
			out.println("window.location='http://localhost:8000/GameWarrior/HistoryManager.jsp';");
		}
		out.println("</script>");
	}

}
