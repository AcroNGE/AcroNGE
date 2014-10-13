package localdomain.localhost;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		int step = Integer.parseInt(req.getParameter("step"));
		switch (step) {
		case 1:
			if(req.getParameter("condition") == "accept"){
				String email = (String) req.getParameter("email");
				DBWorker dbWorker = new DBWorker();
				//check was registered
				if(dbWorker.ExistEmail(email)){
					resp.sendError(HttpServletResponse.SC_CONFLICT, "Email exist");
					return;
				}
				//check try registered
				if(dbWorker.ExistTryEmail(email)){
					resp.sendError(HttpServletResponse.SC_CONFLICT, "Email was tried to reg");
					return;
				}
				if(dbWorker.AddTryEmail(email)){
					resp.sendRedirect("registration-step2.jsp?email=" + email);
					return;
				} else {
					resp.sendError(HttpServletResponse.SC_CONFLICT, "Error in email sending");
				}
			}
			break;

		default:
			break;
		}
	}

}
