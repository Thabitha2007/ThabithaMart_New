<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html><html><head><title>ThabithaMart Login</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"></head>
<body><main class="card"><h1>ThabithaMart</h1><p>Fashion for every story.</p>
<form method="post" action="${pageContext.request.contextPath}/login"><input type="hidden" name="action" value="login">
<input name="username" placeholder="Username" required><input type="password" name="password" placeholder="Password" required><button>Login</button></form>
<hr><h2>New user registration</h2><form method="post" action="${pageContext.request.contextPath}/login"><input type="hidden" name="action" value="register">
<input name="username" placeholder="Username" required><input type="password" name="password" placeholder="Password" required>
<select name="role"><option>BUYER</option><option>SELLER</option></select><button>Register</button></form></main></body></html>