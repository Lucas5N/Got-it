<%@ page import="Model.Utente.UtenteBean" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/checkout.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon/favicon.ico">

</head>
<body>

<div id="checkoutDiv">
    <%
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <form id="checkoutForm" action="${pageContext.request.contextPath}/Checkout" method="post">

        <% if (request.getAttribute("errore") != null) { %>
        <p style="color:red; text-align:center;">
            <%= request.getAttribute("errore") %>
        </p>
        <% } %>

        <div id="checkoutFormContent">

            <div id="items"></div>
            <p>Totale: &euro;<span id="totaleDaPagare"></span></p>

            <p>
                Indirizzo: <%= utente.getVia() %> <%= utente.getNumero_civico() %>,
                <%= utente.getCitta() %> (<%= utente.getRegione() %>)
            </p>

            <div id="cards">
                <p>Carte accettate:</p>
                <img src="img/mastercard.svg" alt="Mastercard">
                <img src="img/visa.svg"       alt="Visa">
                <img src="img/amex.svg"       alt="American Express">
            </div>

            <div class="form-group">
                <label for="numeroCarta">Numero carta</label>
                <input type="text" id="numeroCarta" name="numeroCarta" class="input" placeholder="1234567890123456" required pattern="[0-9]{16}">
            </div>

            <div class="row">
                <div class="form-group half">
                    <label for="dataDiScadenza">Scadenza</label>
                    <input type="text" id="dataDiScadenza" name="dataDiScadenza" class="input"
                           placeholder="MM/AA"
                           required pattern="(0[1-9]|1[0-2])\/([0-9]{2})">
                </div>
                <div class="form-group half">
                    <label for="cvv">CVV</label>
                    <input type="text" id="cvv" name="cvv" class="input"
                           placeholder="123" required pattern="[0-9]{3}">
                </div>
            </div>

            <div class="form-group">
                <label for="nameOnCard">Nome e cognome (sulla carta)</label>
                <input type="text" id="nameOnCard" name="NomeCognome" class="input" required>
            </div>

            <div id="buttons">
                <input type="submit" id="submit" value="Paga ora">
                <button type="submit" id="chiudi" formnovalidate name="action" value="chiudi">
                    Chiudi
                </button>
            </div>
        </div>
    </form>
</div>
<script>const contextPath = "${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/Js/checkout.js" charset="UTF-8"></script>
</body>
</html>
