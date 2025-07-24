<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pid = request.getParameter("productId");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Lascia una recensione</title>
    <link rel="stylesheet" href="Css/PaginaRecensione.css">
</head>
<body>

<div class="recensione-form-wrapper">
    <div class="rec-product-info" id="product-info"></div>

    <form class="recensione-form" id="formRecensione">
        <input type="hidden" id="productId" value="<%= pid %>">

        <label for="descrizione">La tua recensione</label>
        <textarea name="descrizione" id="descrizione"
                  maxlength="500"
                  placeholder="Scrivi qui la tua recensione..."
                  required></textarea>

        <div class="voto-wrapper">
            <label for="voto">Valutazione:</label>
            <div class="star-rating" id="star-rating">
                <span class="star" data-value="1">&#9733;</span>
                <span class="star" data-value="2">&#9733;</span>
                <span class="star" data-value="3">&#9733;</span>
                <span class="star" data-value="4">&#9733;</span>
                <span class="star" data-value="5">&#9733;</span>
            </div>
            <input type="hidden" name="voto" id="voto" value="0">
        </div>

        <div class="form-buttons">
            <button type="submit" id="invio">Invia recensione</button>
            <button type="button" id="annulla" onclick="window.history.back();">Annulla</button>
        </div>
        <div class="info-msg">Il tuo feedback aiuterà altri utenti!</div>
    </form>
</div>
<script>var contextPath = "${pageContext.request.contextPath}";</script>
<script src="Js/PaginaRecensione.js"></script>
</body>
</html>
