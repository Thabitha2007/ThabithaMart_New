<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.text.NumberFormat, com.thabitha.thabithamart.model.Order, com.thabitha.thabithamart.model.OrderItem" %>
<%!
    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    Order order = (Order) request.getAttribute("order");
    NumberFormat nf = NumberFormat.getInstance(new Locale("en", "IN"));
    nf.setMinimumFractionDigits(2);
    nf.setMaximumFractionDigits(2);
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Order placed - ThabithaMart</title>
<style>
* { box-sizing: border-box; margin: 0; padding: 0; }
body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f6fa; color: #222; }
.navbar { background: #1a1a2e; padding: 16px 32px; }
.navbar a { color: #fff; font-size: 22px; font-weight: 700; text-decoration: none; }
.wrap { max-width: 700px; margin: 0 auto; padding: 32px 16px 60px; text-align: center; }
.tick { font-size: 56px; color: #28a745; }
h1 { font-size: 24px; color: #1a1a2e; margin: 8px 0; }
.card { background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,.08); padding: 18px; margin: 20px 0; text-align: left; }
.row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #eee; font-size: 14px; }
.row:last-child { border-bottom: none; }
.grand { font-size: 18px; font-weight: 700; }
.btn { background: #1a1a2e; color: #fff; padding: 11px 22px; border-radius: 6px; text-decoration: none; font-size: 14px; display: inline-block; }
</style>
</head>
<body>
<div class="navbar"><a href="<%= ctx %>/home">ThabithaMart</a></div>
<div class="wrap">
    <div class="tick">&#10004;</div>
    <h1>Your order has been placed successfully!</h1>
    <p>Payment confirmed (demo). Order #<%= order.id %> &middot; Status: <strong><%= esc(order.status) %></strong></p>

    <div class="card">
        <% for (OrderItem it : order.items) { %>
        <div class="row">
            <span><%= esc(it.productName) %> (<%= esc(it.size) %>) &times; <%= it.quantity %></span>
            <span>&#8377;<%= nf.format(it.subtotal()) %></span>
        </div>
        <% } %>
        <div class="row grand"><span>Total</span><span>&#8377;<%= nf.format(order.totalAmount) %></span></div>
    </div>

    <a class="btn" href="<%= ctx %>/products">Continue shopping</a>
</div>
</body>
</html>