//Or Cohen 307852681
//Jonas Zerbib 340941301
package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Control;
import modle.User;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUser() {
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
		//// Get the user we want to delete by the request
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String[] usersToDelete = request.getParameterValues("user[]");
		ArrayList<User> clients = null;
		// Try to delete user
		out.println("<script>");
		if (!Control.deleteUser(usersToDelete)) {
			// If did not succeed we remain with the same clients in the system and
			// represent proper error message
			clients = Control.getClientUsers();
			session.removeAttribute("Clients");
			session.setAttribute("Clients", clients);
			out.println("window.alert('System error');");
			out.println("window.location='http://localhost:8000/GameWarrior/ManageUsers.jsp';");

		} else {
			// If we deleted the client we update the clients attribute in the session so
			// the new vector with out the client will appear
			clients = Control.getClientUsers();
			session.removeAttribute("Clients");
			session.setAttribute("Clients", clients);
			out.println("window.alert('Action has been done');");
			out.println("window.location='http://localhost:8000/GameWarrior/ManageUsers.jsp';");

		}
		out.println("</script>");
	}

}
