package com.thabitha.thabithamart.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import com.thabitha.thabithamart.dao.UserDao;
import com.thabitha.thabithamart.model.User;
import com.thabitha.thabithamart.util.PasswordUtil;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {

            UserDao dao = new UserDao();

            if ("register".equals(action)) {

                String role = request.getParameter("role");

                dao.create(
                    username,
                    PasswordUtil.hash(password),
                    role
                );

                response.sendRedirect(
                    request.getContextPath() + "/login"
                );

                return;
            }

            User user = dao.find(username);

            if (user != null &&
                PasswordUtil.matches(password, user.passwordHash)) {

                HttpSession oldSession = request.getSession(false);

                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session = request.getSession(true);

                session.setMaxInactiveInterval(1800);

                session.setAttribute("user", user);

                response.sendRedirect(
                    request.getContextPath() + "/home"
                );

            } else {

                request.setAttribute(
                    "error",
                    "Invalid username or password"
                );

                request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp"
                ).forward(request, response);
            }

        } catch (Exception e) {

            throw new ServletException(e);
        }
    }
}