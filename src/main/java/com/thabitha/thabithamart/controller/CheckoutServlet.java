package com.thabitha.thabithamart.controller;

import com.thabitha.thabithamart.exception.OutOfStockException;
import com.thabitha.thabithamart.model.CartItem;
import com.thabitha.thabithamart.model.Order;
import com.thabitha.thabithamart.model.ShippingInfo;
import com.thabitha.thabithamart.model.User;
import com.thabitha.thabithamart.service.OrderService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CheckoutServlet extends HttpServlet {

    private final OrderService service = new OrderService();

    private long userId(HttpServletRequest request) {
        return ((User) request.getSession().getAttribute("user")).id;
    }

    private static String clean(String s) {
        return s == null ? "" : s.trim();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ctx = request.getContextPath();
        try {
            String orderParam = request.getParameter("orderId");
            if (orderParam != null) {
                Order order = service.getOrder(Long.parseLong(orderParam), userId(request));
                if (order == null) {
                    response.sendRedirect(ctx + "/cart");
                    return;
                }
                request.setAttribute("order", order);
                request.getRequestDispatcher("/WEB-INF/views/order-success.jsp").forward(request, response);
                return;
            }
            showCheckout(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(ctx + "/cart");
        } catch (SQLException e) {
            throw new ServletException("Could not load checkout", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ShippingInfo ship = new ShippingInfo();
        ship.name = clean(request.getParameter("fullName"));
        ship.address = clean(request.getParameter("address"));
        ship.mobile = clean(request.getParameter("mobile"));
        try {
            long orderId = service.placeOrder(userId(request), request.getParameter("paymentMethod"), ship);
            response.sendRedirect(request.getContextPath() + "/checkout?orderId=" + orderId);
        } catch (OutOfStockException | IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("ship", ship);
            try {
                showCheckout(request, response);
            } catch (SQLException ex) {
                throw new ServletException("Could not load checkout", ex);
            }
        } catch (SQLException e) {
            throw new ServletException("Could not place order", e);
        }
    }

    private void showCheckout(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<CartItem> items = service.getCart(userId(request));
        if (items.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        request.setAttribute("cartItems", items);
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }
}