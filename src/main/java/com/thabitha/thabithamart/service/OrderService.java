package com.thabitha.thabithamart.service;

import com.thabitha.thabithamart.dao.CartDao;
import com.thabitha.thabithamart.dao.OrderDao;
import com.thabitha.thabithamart.exception.OutOfStockException;
import com.thabitha.thabithamart.model.CartItem;
import com.thabitha.thabithamart.model.Order;
import com.thabitha.thabithamart.model.ShippingInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class OrderService {

    private static final Set<String> PAYMENT_METHODS = Set.of("CARD", "UPI", "COD");

    private final CartDao cartDao = new CartDao();
    private final OrderDao orderDao = new OrderDao();

    public List<CartItem> getCart(long userId) throws SQLException {
        return cartDao.findByUser(userId);
    }

    public Order getOrder(long orderId, long userId) throws SQLException {
        return orderDao.findById(orderId, userId);
    }

    /** Checks the delivery details. Throws IllegalArgumentException with a user-friendly message. */
    public static void validateShipping(ShippingInfo s) {
        if (s == null || s.name == null || s.name.length() < 2 || s.name.length() > 100) {
            throw new IllegalArgumentException("Please enter your full name (2 to 100 characters).");
        }
        if (s.address == null || s.address.length() < 10 || s.address.length() > 300) {
            throw new IllegalArgumentException("Please enter your full delivery address (10 to 300 characters).");
        }
        if (s.mobile == null || !s.mobile.matches("[6-9][0-9]{9}")) {
            throw new IllegalArgumentException("Please enter a valid 10-digit mobile number.");
        }
    }

    /** Mock payment: any valid method is "confirmed". No real gateway. */
    public long placeOrder(long userId, String paymentMethod, ShippingInfo ship)
            throws SQLException, OutOfStockException {
        validateShipping(ship);
        if (paymentMethod == null || !PAYMENT_METHODS.contains(paymentMethod)) {
            throw new IllegalArgumentException("Please choose a payment method.");
        }
        List<CartItem> items = cartDao.findByUser(userId);
        if (items.isEmpty()) {
            throw new IllegalStateException("Your cart is empty.");
        }
        return orderDao.placeOrder(userId, items, ship);
    }
}