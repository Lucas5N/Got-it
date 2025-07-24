<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Registrati</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Css/registration.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon/favicon.ico">
</head>
<body>
<form action="<%= request.getContextPath() %>/registration" method="post">
    <div id="register">
        <h1>REGISTRATI</h1>
        <div>
            <% if (request.getAttribute("errore") != null) { %>
            <p style="color: red; text-align: center;">
                <%= request.getAttribute("errore") %>
            </p>
            <% } %>
        </div>
        <div class="form-grid">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" required pattern="^[A-Za-z0-9_]{3,20}$"
                       placeholder="Es: Mario_Rossi12">
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required
                       pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                       placeholder="Es: nome.cognome@example.com">
            </div>
            <div class="form-group">
                <label>Nome</label>
                <input type="text" name="nome" required pattern="^[A-Za-zÀ-ÿ ']{2,30}$"
                       placeholder="Es: Mario">
            </div>
            <div class="form-group">
                <label>Cognome</label>
                <input type="text" name="cognome" required pattern="^[A-Za-zÀ-ÿ ']{2,30}$"
                       placeholder="Es: Rossi">
            </div>
            <div class="form-group">
                <label>Stato</label>
                <input type="text" name="stato" required pattern="^[A-Za-zÀ-ÿ ']{2,40}$"
                       placeholder="Es: Italia">
            </div>
            <div class="form-group">
                <label>Regione</label>
                <input type="text" name="regione" required pattern="^[A-Za-zÀ-ÿ ']{2,40}$"
                       placeholder="Es: Lazio">
            </div>
            <div class="form-group">
                <label>Città</label>
                <input type="text" name="citta" required pattern="^[A-Za-zÀ-ÿ ']{2,40}$"
                       placeholder="Es: Roma">
            </div>
            <div class="form-group">
                <label>Via</label>
                <input type="text" name="via" required pattern="^[A-Za-zÀ-ÿ0-9 ']{2,50}$"
                       placeholder="Es: Via Roma 25">
            </div>
            <div class="form-group">
                <label>Civico</label>
                <input type="text" name="n_civico" required pattern="^\d{1,4}[A-Za-z]?$"
                       placeholder="Es: 25">
            </div>
            <div class="form-group">
                <label>Telefono</label>
                <input type="text" name="telefono" required pattern="^\d{8,13}$"
                       placeholder="Es: 3471234567">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*_=+?-]).{8,}$"
                       placeholder="Min 8 caratteri (es: Passw0rd_)">
            </div>
            <div class="form-group">
                <label>Conferma Password</label>
                <input type="password" name="conferma_Password" required
                       placeholder="Reinserisci la stessa password">
            </div>
            <div class="form-group full-width">
                <label>Conto Corrente</label>
                <input type="text" name="conto_corrente" required
                       pattern="^IT\d{2}[A-Z]\d{10,30}$"
                       placeholder="">
            </div>
            <input type="submit" value="Invia" id="RegistrationButton">

            <button id="LoginButton" type="button" onclick="window.location.href='login.jsp'">
                Hai gia' un account? Accedi!
            </button>
        </div>
    </div>
</form>
</body>
</html>
