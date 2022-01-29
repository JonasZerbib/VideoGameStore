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
import modle.Game;
import modle.User;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		String searchString=request.getParameter("search").toString();
		//Get all the games that contain such string
		Vector<Game> gamesFound=Control.search(searchString);
		User ourUser=(User) session.getAttribute("User");
		out.println("<script>");
		// Enter to the session all the names we found compilable
		session.setAttribute("GamesFound", gamesFound);
		if(ourUser instanceof Client)
		{
			out.println("window.location='http://localhost:8000/GameWarrior/SearchClient.jsp';");
		}
		else
		{
			out.println("window.location='http://localhost:8000/GameWarrior/SearchManager.jsp';");
		}
		out.println("</script>");
		
	}

}
