<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ThabithaMart - Authentication</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 20px 0;
        }
        .container {
            background-color: #ffffff;
            padding: 28px 32px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 320px;
        }
        h2 {
            margin-top: 0;
            text-align: center;
            color: #333333;
            font-size: 22px;
        }
        .form-group {
            margin-bottom: 16px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #555555;
        }
        input[type="text"],
        input[type="password"],
        input[type="email"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .btn {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 8px;
            font-weight: bold;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .error-message {
            background-color: #ffe6e6;
            color: #d9534f;
            border: 1px solid #f5c6cb;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 16px;
            font-size: 14px;
            text-align: center;
            font-weight: bold;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 16px;
            font-size: 14px;
            text-align: center;
            font-weight: bold;
        }
        .toggle-link {
            text-align: center;
            margin-top: 16px;
            font-size: 14px;
            color: #007bff;
            cursor: pointer;
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <%-- மெசேஜ் காட்டும் பகுதி --%>
    <% 
        String error = (String) request.getAttribute("error");
        if (error != null && !error.trim().isEmpty()) { 
    %>
        <div class="error-message">
            <%= error %>
        </div>
    <% 
        } 
        String message = (String) request.getAttribute("message");
        if (message != null && !message.trim().isEmpty()) { 
    %>
        <div class="success-message">
            <%= message %>
        </div>
    <% 
        } 
    %>

    <!-- Login Form -->
    <div id="loginSection">
        <h2>ThabithaMart Login</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="action" value="login">

            <div class="form-group">
                <label for="login-username">Username</label>
                <input type="text" id="login-username" name="username" required>
            </div>

            <div class="form-group">
                <label for="login-password">Password</label>
                <input type="password" id="login-password" name="password" required>
            </div>

            <button type="submit" class="btn">Login</button>
        </form>
        <div class="toggle-link" onclick="toggleForm('register')">New user? Register here</div>
    </div>

    <!-- Register Form -->
    <div id="registerSection" style="display: none;">
        <h2>Create Account</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="action" value="register">

            <div class="form-group">
                <label for="reg-username">Username</label>
                <input type="text" id="reg-username" name="username" required>
            </div>

            <div class="form-group">
                <label for="reg-email">Email Address</label>
                <input type="email" id="reg-email" name="email" required>
            </div>

            <div class="form-group">
                <label for="reg-password">Password</label>
                <input type="password" id="reg-password" name="password" required>
            </div>

            <div class="form-group">
                <label for="reg-role">Role</label>
                <select id="reg-role" name="role" required>
                    <option value="BUYER">Buyer (Customer)</option>
                    <option value="SELLER">Seller (Merchant)</option>
                </select>
            </div>

            <button type="submit" class="btn" style="background-color: #28a745;">Register</button>
        </form>
        <div class="toggle-link" onclick="toggleForm('login')">Already have an account? Login</div>
    </div>
</div>

<script>
    function toggleForm(formType) {
        var loginSec = document.getElementById('loginSection');
        var registerSec = document.getElementById('registerSection');
        if (formType === 'register') {
            loginSec.style.display = 'none';
            registerSec.style.display = 'block';
        } else {
            loginSec.style.display = 'block';
            registerSec.style.display = 'none';
        }
    }
</script>

</body>
</html>