package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.dao.ProductDAO;
import com.thabitha.thabithamart.model.Product;
import com.thabitha.thabithamart.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ProductListServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        try {
            List<Product> products = productDAO.listBySeller(user.id);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/views/seller-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load products", e);
        }
    }
}
