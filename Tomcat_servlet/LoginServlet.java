package com.demo;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        description = "Login servlet",
        urlPatterns = {"/LoginServlet"},
        initParams = {
                @WebInitParam(name = "user", value = "abc"),
                @WebInitParam(name = "password", value = "abc@123A")
        }
)
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (!isValidName(user)) {
            out.println("<font color='red'>Invalid name format. Name must start with a capital letter and be at least 3 characters long.</font>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            rd.include(request, response);
            return;
        }

        if (!isValidPassword(pwd)) {
            out.println("<font color='red'>Invalid password. Must be at least 8 characters long, have 1 uppercase letter, 1 number, and exactly 1 special character.</font>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            rd.include(request, response);
            return;
        }

        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");

        if (userID.equals(user) && password.equals(pwd)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
        } else {
            out.println("<font color='red'>Either user name or password is wrong.</font>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            rd.include(request, response);
        }
    }

    private boolean isValidName(String name) {
        return name != null && name.matches("^[A-Z][a-zA-Z]{2,}$");
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8)
            return false;

        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        int specialCharCount = password.replaceAll("[a-zA-Z0-9]", "").length();

        return hasUpperCase && hasNumber && specialCharCount == 1;
    }
}
