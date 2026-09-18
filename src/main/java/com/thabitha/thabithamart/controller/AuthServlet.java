package com.thabitha.thabithamart.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import com.thabitha.thabithamart.dao.UserDao;
import com.thabitha.thabithamart.model.User;
import com.thabitha.thabithamart.util.PasswordUtil;

public class AuthServlet extends HttpServlet {

    protected void doGet(HttpServletRequest r, HttpServletResponse s)
            throws ServletException, IOException {

        r.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(r, s);
    }

    protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws ServletException, IOException {

        String action = r.getParameter("action");
        String u = r.getParameter("username");
        String p = r.getParameter("password");

        try {

            UserDao d = new UserDao();

            if ("register".equals(action)) {

                String role = r.getParameter("role");

                d.create(u, PasswordUtil.hash(p), role);

                s.sendRedirect("login");

                return;
            }

            User x = d.find(u);

            if (x != null && PasswordUtil.matches(p, x.passwordHash)) {

                HttpSession old = r.getSession(false);

                if (old != null) {
                    old.invalidate();
                }

                HttpSession n = r.getSession(true);

                n.setMaxInactiveInterval(1800);

                n.setAttribute("user", x);

                s.sendRedirect("home");

            } else {

                s.sendError(401, "Invalid credentials");

            }

        } catch (Exception e) {

            throw new ServletException(e);

        }

    }

}