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
import modle.Client;
import modle.Order;
import modle.User;

@SuppressWarnings("unchecked")
/**
 * Servlet implementation class OrderInfo
 */
@WebServlet("/OrderInfo")
public class OrderInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderInfo() {
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
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Vector<Order> orders = null;
		String idString = request.getParameter("numOrder");
		User ourUser = (User) session.getAttribute("User");
		// Get the number of order we want to see her info
		if (ourUser instanceof Client) {
			orders = (Vector<Order>) session.getAttribute("History");
		} else {
			String clientString = request.getParameter("clientId");
			int clientId = Integer.parseInt(clientString);
			orders = Control.createHistoryVector(clientId);
		}

		int numOrder = Integer.parseInt(idString);
		// Create vector with all the the order lines of the order and enter it as
		// attribute to the session so we could display it
		// in the OrderInfo.jsp page
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getNumOrder() == numOrder) {
				Order showOrder = orders.get(i);
				session.setAttribute("OrderInfo", showOrder);
			}
		}
		//Redirect us to the info about the order
		out.println("<script>");
		if (ourUser instanceof Client) {
			out.println("window.location='http://localhost:8000/GameWarrior/OrderInfo.jsp';");
		} else {
			out.println("window.location='http://localhost:8000/GameWarrior/OrderInfoManager.jsp';");
		}
		out.println("</script>");
	}

}
