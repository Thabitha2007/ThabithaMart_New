<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.math.BigDecimal, java.text.NumberFormat, com.thabitha.thabithamart.model.CartItem,com.thabitha.thabithamart.model.ShippingInfo" %>
<%!
    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
    String error = (String) request.getAttribute("error");
    ShippingInfo ship = (ShippingInfo) request.getAttribute("ship");
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
<title>Checkout - ThabithaMart</title>
<style>
* { box-sizing: border-box; margin: 0; padding: 0; }
body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f6fa; color: #222; }
.navbar { background: #1a1a2e; padding: 16px 32px; display: flex; justify-content: space-between; align-items: center; }
.navbar .brand { color: #fff; font-size: 22px; font-weight: 700; text-decoration: none; }
.navbar a.link { color: #ffcb45; text-decoration: none; font-size: 14px; margin-left: 16px; }
.wrap { max-width: 700px; margin: 0 auto; padding: 24px 16px 60px; }
h1 { font-size: 24px; color: #1a1a2e; margin-bottom: 16px; }
.card { background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,.08); padding: 18px; margin-bottom: 16px; }
.row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #eee; font-size: 14px; }
.row:last-child { border-bottom: none; }
.grand { font-size: 20px; font-weight: 700; }
.pay label { display: block; padding: 10px; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 8px; cursor: pointer; }
.error { background: #fdecea; color: #b71c1c; padding: 12px; border-radius: 8px; margin-bottom: 16px; }
.note { font-size: 12px; color: #888; margin-top: 8px; }
.field { margin-bottom: 12px; }
.field label { display: block; font-size: 13px; margin-bottom: 4px; color: #555; }
.field input, .field textarea { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; font-family: inherit; }
.btn { background: #1a1a2e; color: #fff; border: none; padding: 12px 22px; border-radius: 6px; font-size: 15px; cursor: pointer; width: 100%; }
</style>
</head>
<body>
<div class="navbar">
    <a href="<%= ctx %>/home" class="brand">ThabithaMart</a>
    <a class="link" href="<%= ctx %>/cart">Back to cart</a>
</div>
<div class="wrap">
<h1>Checkout</h1>

<% if (error != null) { %><div class="error"><%= esc(error) %></div><% } %>

<div class="card">
    <% for (CartItem it : items) { total = total.add(it.subtotal()); %>
    <div class="row">
        <span><%= esc(it.product.name) %> (<%= esc(it.size) %>) &times; <%= it.quantity %></span>
        <span>&#8377;<%= nf.format(it.subtotal()) %></span>
    </div>
    <% } %>
    <div class="row grand"><span>Total</span><span>&#8377;<%= nf.format(total) %></span></div>
</div>

<form method="post" action="<%= ctx %>/checkout">
    <div class="card">
        <strong>Delivery details</strong><br><br>
        <div class="field">
            <label for="fullName">Full name</label>
            <input type="text" id="fullName" name="fullName" required minlength="2" maxlength="100"
                   value="<%= ship != null ? esc(ship.name) : "" %>">
        </div>
        <div class="field">
            <label for="mobile">Mobile number</label>
            <input type="tel" id="mobile" name="mobile" required pattern="[6-9][0-9]{9}" maxlength="10"
                   title="10-digit mobile number" value="<%= ship != null ? esc(ship.mobile) : "" %>">
        </div>
        <div class="field">
            <label for="address">Delivery address</label>
            <textarea id="address" name="address" rows="3" required minlength="10" maxlength="300"><%= ship != null ? esc(ship.address) : "" %></textarea>
        </div>
    </div>
    <div class="card pay">
        <strong>Payment method</strong><br><br>
        <label><input type="radio" name="paymentMethod" value="CARD" checked> Credit / Debit card</label>
        <label><input type="radio" name="paymentMethod" value="UPI"> UPI</label>
        <label><input type="radio" name="paymentMethod" value="COD"> Cash on delivery</label>
        <div class="note">This is a demo. No real payment is taken.</div>
    </div>
    <button type="submit" class="btn">Pay &#8377;<%= nf.format(total) %> and place order</button>
</form>
</div>
</body>
</html>