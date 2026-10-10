package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.dao.CartDao;
import com.thabitha.thabithamart.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Set;

public class CartServlet extends HttpServlet {

    private final CartDao cartDao = new CartDao();
    private static final Set<String> SIZES = Set.of("S", "M", "L", "XL");

    private long userId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.id;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("cartItems", cartDao.findByUser(userId(request)));
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Could not load cart", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            long uid = userId(request);
            if ("add".equals(action)) {
                long productId = Long.parseLong(request.getParameter("productId"));
                String size = request.getParameter("size");
                if (size != null && SIZES.contains(size)) {
                    cartDao.add(uid, productId, size);
                }
            } else if ("update".equals(action)) {
                long id = Long.parseLong(request.getParameter("itemId"));
                int qty = Integer.parseInt(request.getParameter("quantity"));
                if (qty < 1) {
                    cartDao.remove(uid, id);
                } else {
                    cartDao.updateQuantity(uid, id, qty);
                }
            } else if ("remove".equals(action)) {
                cartDao.remove(uid, Long.parseLong(request.getParameter("itemId")));
            }
        } catch (NumberFormatException e) {
            // bad input: ignore and just show the cart
        } catch (Exception e) {
            throw new ServletException("Cart action failed", e);
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}