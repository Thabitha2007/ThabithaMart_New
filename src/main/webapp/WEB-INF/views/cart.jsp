<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.math.BigDecimal, java.text.NumberFormat, com.thabitha.thabithamart.model.CartItem" %>
<%!
    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
    NumberFormat nf = NumberFormat.getInstance(new Locale("en", "IN"));
    nf.setMinimumFractionDigits(2);
    nf.setMaximumFractionDigits(2);
    BigDecimal total = BigDecimal.ZERO;
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Your Cart - ThabithaMart</title>
<style>
* { box-sizing: border-box; margin: 0; padding: 0; }
body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f6fa; color: #222; }
.navbar { background: #1a1a2e; padding: 16px 32px; display: flex; justify-content: space-between; align-items: center; }
.navbar .brand { color: #fff; font-size: 22px; font-weight: 700; text-decoration: none; }
.navbar a.link { color: #ffcb45; text-decoration: none; font-size: 14px; margin-left: 16px; }
.wrap { max-width: 900px; margin: 0 auto; padding: 24px 16px 60px; }
h1 { font-size: 24px; color: #1a1a2e; margin-bottom: 16px; }
.item { background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,.08); display: flex; gap: 16px; padding: 14px; margin-bottom: 14px; }
.item img { width: 110px; height: 110px; object-fit: cover; border-radius: 8px; }
.details { flex: 1; }
.details h3 { font-size: 17px; color: #1a1a2e; }
.meta { font-size: 13px; color: #777; margin: 4px 0; }
.price { font-weight: 700; color: #d6336c; }
.qty { display: flex; align-items: center; gap: 8px; margin-top: 8px; }
.qty form { display: inline; }
.qty button { width: 30px; height: 30px; border: 1px solid #ccc; background: #fff; border-radius: 6px; font-size: 16px; cursor: pointer; }
.qty button:disabled { opacity: .4; cursor: default; }
.side { text-align: right; display: flex; flex-direction: column; justify-content: space-between; }
.remove { background: none; border: none; color: #c0392b; cursor: pointer; font-size: 13px; }
.summary { background: #fff; border-radius: 10px; padding: 18px; box-shadow: 0 2px 8px rgba(0,0,0,.08); display: flex; justify-content: space-between; align-items: center; }
.summary .grand { font-size: 22px; font-weight: 700; }
.btn { background: #1a1a2e; color: #fff; border: none; padding: 10px 20px; border-radius: 6px; font-size: 14px; cursor: pointer; text-decoration: none; }
.btn.disabled { opacity: .5; cursor: not-allowed; }
.empty { text-align: center; padding: 60px 20px; color: #999; }
@media (max-width: 600px) { .item { flex-direction: column; } .item img { width: 100%; height: 200px; } .side { flex-direction: row; align-items: center; } }
</style>
</head>
<body>
<div class="navbar">
    <a href="<%= ctx %>/home" class="brand">ThabithaMart</a>
    <div>
        <a class="link" href="<%= ctx %>/products">Continue shopping</a>
        <a class="link" href="<%= ctx %>/logout">Logout</a>
    </div>
</div>

<div class="wrap">
<h1>Your Cart</h1>

<% if (items == null || items.isEmpty()) { %>
    <div class="empty">
        <p>Your cart is empty.</p><br>
        <a class="btn" href="<%= ctx %>/products">Browse products</a>
    </div>
<% } else { %>
    <% for (CartItem it : items) {
           total = total.add(it.subtotal()); %>
    <div class="item">
        <img src="<%= esc(it.product.imageUrl) %>" alt="<%= esc(it.product.name) %>"
             onerror="this.onerror=null;this.src='https://placehold.co/600x400?text=No+Image';">
        <div class="details">
            <h3><%= esc(it.product.name) %></h3>
            <div class="meta">Size: <strong><%= esc(it.size) %></strong>
                | <%= esc(it.product.material) %> | <%= esc(it.product.color) %></div>
            <div class="price">&#8377;<%= nf.format(it.product.price) %></div>
            <div class="qty">
                <form method="post" action="<%= ctx %>/cart">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="itemId" value="<%= it.id %>">
                    <input type="hidden" name="quantity" value="<%= it.quantity - 1 %>">
                    <button type="submit" <%= it.quantity <= 1 ? "disabled" : "" %>>&minus;</button>
                </form>
                <strong><%= it.quantity %></strong>
                <form method="post" action="<%= ctx %>/cart">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="itemId" value="<%= it.id %>">
                    <input type="hidden" name="quantity" value="<%= it.quantity + 1 %>">
                    <button type="submit" <%= it.quantity >= it.product.stock ? "disabled" : "" %>>+</button>
                </form>
                <span class="meta">(<%= it.product.stock %> in stock)</span>
            </div>
        </div>
        <div class="side">
            <div class="price">&#8377;<%= nf.format(it.subtotal()) %></div>
            <form method="post" action="<%= ctx %>/cart">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="itemId" value="<%= it.id %>">
                <button type="submit" class="remove">Remove</button>
            </form>
        </div>
    </div>
    <% } %>

    <div class="summary">
        <div>Grand Total<div class="grand">&#8377;<%= nf.format(total) %></div></div>
        <a class="btn" href="<%= ctx %>/checkout">Proceed to checkout</a>
    </div>
<% } %>
</div>
</body>
</html>