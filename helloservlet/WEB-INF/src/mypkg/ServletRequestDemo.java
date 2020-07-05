// <CATALINA_HOME>\webapps\helloservlet\WEB-INF\src\mypkg\ServletRequest.java
// http://localhost:8088/helloservlet/requestdemo?reqArgs=hahahha
package mypkg;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Arrays;

public class ServletRequestDemo extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// set response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // write response message into network socket
        PrintWriter out = response.getWriter();
		
		// set attribute
		request.setAttribute("name", Arrays.asList("Yeah", "zz"));
		
        try {
           out.println("<!DOCTYPE html>");
           out.println("<html><head>");
           out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
           out.println("<title>Hello, World</title></head>");
           out.println("<body>");
		   
		   // Echo ServletRequest interface method information
           out.println("<h1>Some important methods of ServletRequest interface: </h1>");
           out.println("<p>getAttribute(String name): " + request.getAttribute("name") + "</p>");
           out.println("<p>getAttributeName(): " + request.getAttributeNames() + "</p>");
           out.println("<p>getContentLength(): " + request.getContentLength() + "</p>");
           out.println("<p>getContentType(): " + request.getContentType() + "</p>");
		   out.println("<p>getInputStream(): " + request.getInputStream() + "</p>");
           out.println("<p>getParameter(String name): " + request.getParameter("reqArgs") + "</p>");
           out.println("<p>getLocalAddr(): " + request.getLocalAddr() + "</p>");
           out.println("<p>getParameterNames(): " + request.getParameterNames() + "</p>");
		   
		   out.println("<p>getParameterValues(String name): " + request.getParameterValues("reqArgs") + "</p>");
           out.println("<p>getServletContext(): " + request.getServletContext() + "</p>");
           out.println("<p>getServerName(): " + request.getServerName() + "</p>");
		   out.println("<p>getServerPort(): " + request.getServerPort() + "</p>");
           out.println("<p>isSecure(): " + request.isSecure() + "</p>");
           out.println("<p>removeAttribute(String name)</p>");
		   
		   // Echo HttpServletRequest interface method information
		   out.println("<h1>Some important methods of HttpServletRequest interface: </h1>");
           out.println("<p>getContextPath(): " + request.getContextPath() + "</p>");
           out.println("<p>getCookies(): " + request.getCookies().toString() + "</p>");
		   out.println("<p>getQueryString(): " + request.getQueryString() + "</p>");
           out.println("<p>getSession(): " + request.getSession() + "</p>");
		   out.println("<p>getMethod(): " + request.getMethod() + "</p>");
           out.println("<p>getPart(String name): " + request.getPart("file") + "</p>");
		   out.println("<p>getPathInfo(): " + request.getPathInfo() + "</p>");
		   out.println("<p>getServletPath(): " + request.getServletPath() + "</p>");
           out.println("</body>");
           out.println("</html>");
        } catch (Exception e) {
            
        } finally {
            out.close();
        }
    }
}