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
                String email = request.getParameter("email");
                String role = request.getParameter("role");

                // ரோல் எதுவும் வராவிட்டால் இயல்பாக BUYER என அமைத்தல்
                if (role == null || role.trim().isEmpty()) {
                    role = "BUYER";
                }

                // பயனரை உருவாக்குதல்
                dao.create(
                    username,
                    PasswordUtil.hash(password),
                    role
                );

                // பதிவு முடிந்ததும் லாகின் பக்கத்திற்கு வெற்றிச் செய்தியுடன் அனுப்புதல்
                request.setAttribute("message", "Registration successful! Please login.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }

            // Login Action
            User user = dao.find(username);

            if (user != null &&
                PasswordUtil.matches(password, user.passwordHash)) {

                HttpSession oldSession = request.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(1800);

                // லாகின் செய்த முழு User ஆப்ஜெக்ட் மற்றும் ரோலை session-ல் வைத்தல்
                session.setAttribute("user", user);
                session.setAttribute("username", user.username);
                session.setAttribute("role", user.role);

                // புது வரி: இந்த login நேரத்தை DB-ல சேமிக்கிறோம்
                dao.updateLastLogin(user.id);

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