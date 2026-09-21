<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.thabitha.thabithamart.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - ThabithaMart</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f5f6fa; color: #222; }
        .navbar { background: #1a1a2e; padding: 16px 32px; display: flex; justify-content: space-between; align-items: center; }
        .navbar .brand { color: #fff; font-size: 20px; font-weight: 700; }
        .navbar a { color: #ffcb45; text-decoration: none; font-size: 14px; }
        .container { max-width: 1100px; margin: 0 auto; padding: 24px 20px; }
        h1 { font-size: 22px; color: #1a1a2e; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 8px; overflow: hidden;
                box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        th, td { padding: 12px 14px; text-align: left; font-size: 14px; border-bottom: 1px solid #eee; }
        th { background: #f0f0f5; color: #444; font-size: 13px; text-transform: uppercase; }
        .role-badge { padding: 3px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; color: #fff; }
        .role-ADMIN { background: #6f42c1; }
        .role-SELLER { background: #28a745; }
        .role-BUYER { background: #007bff; }
        .never { color: #999; font-style: italic; }
    </style>
</head>
<body>

<div class="navbar">
    <div class="brand">ThabithaMart Admin Panel</div>
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</div>

<div class="container">
    <h1>All Users &amp; Sellers</h1>

    <%
        List<User> users = (List<User>) request.getAttribute("users");
    %>

    <table>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
            <th>Last Access Time</th>
        </tr>
        <% for (User u : users) { %>
            <tr>
                <td><%= u.id %></td>
                <td><%= u.username %></td>
                <td><%= u.email != null ? u.email : "-" %></td>
                <td><span class="role-badge role-<%= u.role %>"><%= u.role %></span></td>
                <td>
                    <% if (u.lastLogin != null) { %>
                        <%= u.lastLogin %>
                    <% } else { %>
                        <span class="never">Never logged in</span>
                    <% } %>
                </td>
            </tr>
        <% } %>
    </table>
</div>

</body>
</html>