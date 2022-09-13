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
import modle.User;
import modle.Client;

/**
 * Servlet implementation class UpdateFields
 */
@WebServlet("/UpdateFields")
public class UpdateFields extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateFields() {
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
		// Get the user info he entered
		int id = ourUser.getId();
		String username = ourUser.getUserName();
		String email = request.getParameter("email").toString();
		String password = request.getParameter("password").toString();
		String verifyPassword = request.getParameter("verifyPassword").toString();
		String firstName = request.getParameter("firstName").toString();
		String lastName = request.getParameter("lastName").toString();
		String birthday = request.getParameter("birthday").toString();
		String phone = request.getParameter("phone").toString();
		String gender = request.getParameter("gender").toString();
		// Use function to update the fields in the database
		int updateUser = Control.updateField(id, email, password, verifyPassword, firstName, lastName, birthday, phone,
				gender);
		// Checks if update succeed
		// If the password did not match in both fields we get value 2 and display
		// proper message and return
		// page we were
		out.println("<script>");
		if (updateUser == 2) {

			out.println("window.alert('The password fields do not match');");
			if (ourUser instanceof Client) {
				out.println("window.location='http://localhost:8000/GameWarrior/UpdateFieldsClient.jsp';");
			} else {
				out.println("window.location='http://localhost:8000/GameWarrior/UpdateFieldsManager.jsp';");
			}

		} // If we got other error we get value 1 and we display proper message and return
			// to the page we were
		else if (updateUser == 1) {
			out.println("window.alert('System Error');");
			if (ourUser instanceof Client) {
				out.println("window.location='http://localhost:8000/GameWarrior/UpdateFieldsClient.jsp';");
			} else {
				out.println("window.location='http://localhost:8000/GameWarrior/UpdateFieldsManager.jsp';");
			}

		} else {// If update succeed wee remove the user session attribute and update it with
				// new user object that contain the
				// new info and show proper message
			ourUser = Control.getUser(username);
			session.removeAttribute("User");
			session.setAttribute("User", ourUser);
			out.println("window.alert('Account info has been updated');");
			if (ourUser instanceof Client) {
				out.println("window.location='http://localhost:8000/GameWarrior/IndexSignOutClient.jsp';");
			} else {
				out.println("window.location='http://localhost:8000/GameWarrior/IndexSignOutManager.jsp';");
			}

		}
		out.println("</script>");
	}

}
