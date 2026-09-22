package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.dao.ProductDAO;
import com.thabitha.thabithamart.model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");
        String keyword = request.getParameter("keyword");

        try {
            List<Product> products = productDAO.search(category, keyword);

            request.setAttribute("categoryName", (category == null || category.trim().isEmpty()) ? "All" : category);
            request.setAttribute("keyword", keyword);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Could not load products", e);
        }
    }
}