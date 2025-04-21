package com.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CopyOnWriteArrayList;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();

    static class User {
        String firstName, lastName, email, password;

        User(String fn, String ln, String em, String pw) {
            this.firstName = fn;
            this.lastName = ln;
            this.email = em;
            this.password = pw;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String email     = request.getParameter("email");
        String password  = request.getParameter("password");

        User newUser = new User(firstName, lastName, email, password);
        users.add(newUser);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("User registered successfully!");
        out.println("Total users registered: " + users.size());
    }
}
