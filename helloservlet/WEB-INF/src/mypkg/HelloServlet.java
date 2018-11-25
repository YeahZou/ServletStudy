// <CATALINA_HOME>\webapps\helloservlet\WEB-INF\src\mypkg\HelloServlet.java
package mypkg;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// set response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // write response message into network socket
        PrintWriter out = response.getWriter();
        try {
           out.println("<!DOCTYPE html>");
           out.println("<html><head>");
           out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
           out.println("<title>Hello, World</title></head>");
           out.println("<body>");
           out.println("<h1>Hello, world!</h1>");  // says Hello
           // Echo client's request information
           out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
           out.println("<p>Protocol: " + request.getProtocol() + "</p>");
           out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
           out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
           // Generate a random number upon each request
           out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
           out.println("</body>");
           out.println("</html>");
        } catch (Exception e) {
            
        } finally {
            out.close();
        }
    }
}