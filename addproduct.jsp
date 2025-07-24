<%@ page import="Model.Utente.UtenteBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vendi un Articolo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/add-product.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon/favicon.ico">
</head>
<body>
<%
    UtenteBean user = (UtenteBean) session.getAttribute("utente");

%>

<div class="form-wrapper">
    <h1>Vendi un Articolo</h1>
    <form action="add-product" method="post" enctype="multipart/form-data" id="form" class="product-form">

        <div class="image-upload-column">
            <div id="main-image-container">
                <img src="${pageContext.request.contextPath}/img/add-icon.svg" alt="Aggiungi immagine principale" id="main-image-preview">
                <p>Aggiungi foto</p>
            </div>
            <input type="file" id="imageInput" name="immagine" accept="image/*" style="display: none;" multiple>
            <div id="thumbnail-container">
            </div>
        </div>

        <div class="details-column">
            <div class="form-group">
                <label for="name">Nome Prodotto</label>
                <input type="text" name="name-product" id="name" required placeholder="Es. T-shirt in cotone">
            </div>

            <div class="form-group">
                <label for="description">Descrizione</label>
                <textarea name="descrizione" id="description" required placeholder="Descrivi il tuo articolo in almeno 50 caratteri..."></textarea>
                <p id="charCount" class="char-counter">0 caratteri</p>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="category">Categoria</label>
                    <select name="category" id="category">
                        <option value="Bellezza">Bellezza</option>
                        <option value="Elettrodomestici">Elettrodomestici</option>
                        <option value="Elettronica">Elettronica</option>
                        <option value="Intrattenimento">Intrattenimento</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="condizione">Condizione</label>
                    <select name="condizione" id="condizione">
                        <option value="Nuovo">Nuovo</option>
                        <option value="Come nuovo">Come nuovo</option>
                        <option value="In ottime condizioni">In ottime condizioni</option>
                        <option value="In buone condizioni">In buone condizioni</option>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="price">Prezzo (€)</label>
                    <input type="number" name="prezzo" id="price" required min="0" step="0.01" placeholder="25.00">
                </div>
                <div class="form-group">
                    <label for="peso">Peso (kg)</label>
                    <input type="number" name="peso" id="peso" required min="0" step="0.1" placeholder="1.5">
                </div>
                <div class="form-group">
                    <label for="color">Colore</label>
                    <input type="text" name="colore" id="color" required placeholder="Blu">
                </div>
            </div>

            <input type="hidden" name="user_id" value="<%= user.getID_Utente() %>">

            <button type="submit" class="btn-submit">Metti in vendita</button>
        </div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/Js/add-product.js"></script>

</body>
</html>