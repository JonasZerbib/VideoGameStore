//Or Cohen 307852681
//Jonas Zerbib 340941301
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
import modle.Client;
import modle.Order;
import modle.User;

/**
 * Servlet implementation class Orders
 */
@WebServlet("/Orders")
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Orders() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		PrintWriter out=response.getWriter();
		User ourUser=(User)session.getAttribute("User");
		out.println("<script>");
		if(ourUser instanceof Client)
		{
			int id=ourUser.getId();
			//Get all the history of orders that our client done and enter it to the session
			Vector<Order> history=Control.createHistoryVector(id);
			session.setAttribute("History",history);
			out.println("window.location='http://localhost:8000/GameWarrior/History.jsp';");
		}else {
		//Create vector with all the orders of all the clients and enter it to the session
		HashMap<Integer,Vector<Order>> orders=Control.createOrders();
		session.setAttribute("Orders", orders);
		out.println("window.location='http://localhost:8000/GameWarrior/HistoryManager.jsp';");
		
		}
		out.println("</script>");
	}

}
