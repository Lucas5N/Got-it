<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Css/login.css">
    <link rel="icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/img/favicon/favicon.ico">
</head>
<body>
<div id="scia" class="forward"></div>

<div id="totale">
    <% String redirect = request.getParameter("redirect"); %>
    <form action="<%= request.getContextPath() %>/login" method="post">
        <div id="modulo">
            <h1>ACCEDI</h1>

            <% if (request.getAttribute("errore") != null) { %>
            <p class="error-message">
                <%= request.getAttribute("errore") %>
            </p>
            <% } %>

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" class="form" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="password" id="password" class="form" required>
                <div class="forgot-link">
                    <a href="#">Password dimenticata?</a>
                </div>
            </div>

            <% if (redirect != null && !redirect.isEmpty()) { %>
            <input type="hidden" name="redirect" value="<%= redirect %>">
            <% } %>

            <div class="form-group">
                <input type="submit" value="Accedi" class="button-style" id="bt-accedi">
            </div>

            <div id="registration">
                <button id="registrationButton" class="button-style" type="button"
                        onclick="window.location.href='registration.jsp'">
                    Registrati
                </button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
