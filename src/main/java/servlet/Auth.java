package servlet;

import DAO.ConnectionDB;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "Auth", urlPatterns = "/")
public class Auth implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String login = req.getParameter("name");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        Cookie[] cookies = req.getCookies();
        String message = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("message")) {
                    message = c.getValue();
                }
            }
        }

        if ((session) != null && (session.getAttribute("user")) != null) {
            req.setAttribute("hasCookie", !message.equals(""));
            req.setAttribute("message", message);
            req.getRequestDispatcher("user.jsp").forward(req, resp);
        } else if (login==null) {
            req.getSession().setAttribute("message", message);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }else {
            String validate = null;
            if (ConnectionDB.validate(login, password)) {
                validate = "true";
            } else {
                validate = "false";
            }
            if ("true".equals(validate)) {
                req.setAttribute("hasCookie", !message.equals(""));
                req.setAttribute("message", message);
                req.getSession().setAttribute("user","name");
                req.getRequestDispatcher("user.jsp").forward(req, resp);
            } else {
                req.getSession().setAttribute("message", message);
                req.getRequestDispatcher("errorPage.jsp").forward(req, resp);
            }
        }
    }
}
