package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.dao.ProductDAO;
import com.thabitha.thabithamart.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        long productId = Long.parseLong(request.getParameter("id"));

        try {
            productDAO.delete(productId, user.id);
            response.sendRedirect(request.getContextPath() + "/seller/products");
        } catch (Exception e) {
            throw new ServletException("Could not delete product", e);
        }
    }
}
