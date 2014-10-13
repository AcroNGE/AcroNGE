package localdomain.localhost;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
* Servlet implement new user registration process (all step)
*/

@WebServlet(description="new user registration", 
		urlPatterns = { "/registration/Registration" })


public class Registration extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int step = (int) req.getAttribute("step");
		switch (step) {
		case 1:
			if(req.getAttribute("condition") == "accept"){
				String email = (String) req.getAttribute("email");
				DBWorker dbWorker = new DBWorker();
				try {
					//check was registered
					if(dbWorker.ExistEmail(email)){
						resp.sendError(HttpServletResponse.SC_CONFLICT, "Email exist");
						return;
					}
					//check try registered
					//if()
				} catch (SQLException | NamingException e) {
					// send server error
					req.getSession().setAttribute("error", "reg-step1|| " + e.getMessage());
					resp.addCookie(new Cookie("error", "server error"));
					resp.sendRedirect("../ServerError.jsp");
				}
			}
			break;

		default:
			break;
		}
	}

}
