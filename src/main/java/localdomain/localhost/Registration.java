package localdomain.localhost;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
				String email = req.getParameter("email");
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
			
		case 2:
			String email = req.getParameter("email");
			String code = req.getParameter("code");
			//check code
			DBWorker worker = new DBWorker();
			if(worker.CheckTryCode(email, code)){
				HttpSession session = req.getSession();
				session.setAttribute("email", email);
				session.setAttribute("code", code);
				resp.sendRedirect("registration-step3.jsp");
				return;				
			} else {
				resp.sendError(HttpServletResponse.SC_CONFLICT, "This is wrong code!");
			}
			break;
			
		case 3:
			String email1 = (String) req.getSession().getAttribute("email");
			String login = req.getParameter("login");
			String pass = req.getParameter("pass");
			String pass2 = req.getParameter("pass2");
			if(pass == pass2){
				//Continue registration
				DBWorker dbWorker = new DBWorker();
				if(dbWorker.ExistEmail(email1)){
					resp.sendError(HttpServletResponse.SC_CONFLICT, "This email was already registrated!");
				} else {
					if(dbWorker.ExistLogin(login)){
						resp.sendError(HttpServletResponse.SC_CONFLICT, "This login was registrated!");
					} else {
						if(dbWorker.AddUser(email1, login, pass)){
							resp.sendRedirect("registration-step4.jsp");
							return;
						} else {
							resp.sendError(HttpServletResponse.SC_CONFLICT, "You can not been registrated in system!");
						}
					}
				}
			} else {
				resp.sendError(HttpServletResponse.SC_CONFLICT, "Passwords are not matches!");
			}
			break;
			
		case 4:
			//String email2 = (String) req.getSession().getAttribute("email");
			resp.sendRedirect("../welcome.jsp");
			break;

		default:
			break;
		}
	}

}
