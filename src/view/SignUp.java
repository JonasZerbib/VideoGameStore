//Or Cohen 307852681
//Jonas Zerbib 340941301
package view;

//Jonas Zerbib 340941301

import java.io.IOException;
import java.io.PrintWriter;
import controller.Control;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUp() {
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
		// Gets the values of field our user entered
		String username = request.getParameter("username").toString();
		String email = request.getParameter("email").toString();
		String password = request.getParameter("password").toString();
		String verifyPassword = request.getParameter("verifyPassword").toString();
		String firstName = request.getParameter("firstName").toString();
		String lastName = request.getParameter("lastName").toString();
		String birthday = request.getParameter("birthday").toString();
		String phone = request.getParameter("phone").toString();
		String gender = request.getParameter("gender").toString();
		// Call function to add user
		int didAdd = Control.addUser(username, email, password, verifyPassword, firstName, lastName, birthday, phone,
				gender);
		// If we got 0 we succeed in sign up and we display proper message
		out.println("<script>");
		if (didAdd == 0) {

			out.println(" window.alert('Success in signing up');");
			out.println("window.location='http://localhost:8000/GameWarrior/Login.jsp';");

		}
		// If we failed because username exist we send proper message
		else if (didAdd == 1) {

			out.println("window.alert('User Exist');");
			out.println("window.location='http://localhost:8000/GameWarrior/Signup.jsp';");

		} // If both password fields those not match send proper message
		else if (didAdd == 2) {

			out.println("window.alert('Verify password field does not match to password field');");
			out.println("window.location='http://localhost:8000/GameWarrior/Signup.jsp';");

		} else {// If we had other problem send proper message

			out.println("window.alert('System error please try again');");
			out.println("window.location='http://localhost:8000/GameWarrior/Signup.jsp';");

		}
		out.println("</script>");
	}

}
