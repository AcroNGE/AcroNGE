package localdomain.localhost;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
*Servlet implement Test Servlet working 
*/

@WebServlet(description = "Test Servlet", urlPatterns = { "/ServletTest1" })

public class ServletTest1 extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        writer.println("<html>");
        writer.println("<head><title>Servlet test</title></head>");
        writer.println("<body><h1>Servlet test</h1>");

        writer.println("<h2>it's work</h2>");
        DBWorker dbw = new DBWorker();
        writer.println(dbw.DBcreate("Users"));
        writer.println(dbw.DBcreate("Logon"));
        writer.println("</body></html>");

    }
}
