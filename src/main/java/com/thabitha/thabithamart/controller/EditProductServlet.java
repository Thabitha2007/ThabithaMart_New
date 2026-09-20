package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.dao.ProductDAO;
import com.thabitha.thabithamart.model.Product;
import com.thabitha.thabithamart.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class EditProductServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        long productId = Long.parseLong(request.getParameter("id"));

        try {
            Product product = productDAO.getByIdAndSeller(productId, user.id);

            if (product == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "இந்த product உங்களுடையது இல்ல.");
                return;
            }

            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/views/edit-product.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Could not load product", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        try {
            Product p = new Product();
            p.id = Long.parseLong(request.getParameter("id"));
            p.sellerId = user.id;
            p.name = request.getParameter("name");
            p.description = request.getParameter("description");
            p.price = new BigDecimal(request.getParameter("price"));
            p.stock = Integer.parseInt(request.getParameter("stock"));
            p.category = request.getParameter("category");
            p.imageUrl = request.getParameter("imageUrl");

            boolean updated = productDAO.update(p);

            if (!updated) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "இந்த product உங்களுடையது இல்ல.");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/seller/products");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Price மற்றும் Stock சரியான எண்ணா இருக்கணும்.");
            request.getRequestDispatcher("/WEB-INF/views/edit-product.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not update product", e);
        }
    }
}