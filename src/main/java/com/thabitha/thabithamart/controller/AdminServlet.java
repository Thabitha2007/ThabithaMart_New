package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.dao.UserDao;
import com.thabitha.thabithamart.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AdminServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<User> users = userDao.listAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load users", e);
        }
    }
}
