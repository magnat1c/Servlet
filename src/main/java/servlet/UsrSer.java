package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class UsrSer extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response){
        try{
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Welcome to servlets</h2>");
            Cookie[] c=request.getCookies();
            out.println("Name: "+c[1].getValue());
            out.println("<a href=\"/logout\">Back</a>");
            out.println("<a href=\"/image\">Image Click here</a>");

        }catch(Exception exp){
            System.out.println(exp);
        }
    }
}