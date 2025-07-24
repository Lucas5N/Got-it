<%@ page import="java.util.concurrent.ThreadLocalRandom" %>
<%@ page import="Model.Prodotto.ProdottoDAO" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="Model.Vendere.VendereDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Prodotto.ProdottoBean" %>
<%@ page import="Model.Utente.UtenteBean" %>
<%@ page import="Model.CCP.CCPDAO" %>
<%@ page import="Model.CWP.CWPDAO" %>
<%@ page import="Model.Immagine.ImmagineDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    ProdottoBean product = (ProdottoBean) session.getAttribute("product");
    UtenteBean utente1 = (UtenteBean) session.getAttribute("utente");

    boolean isVendor = false;
    boolean isInCart = false;
    boolean isInWishlist = false;

    if (utente1 != null) {
        VendereDAO vendereDAO = new VendereDAO();
        int venditoreId = vendereDAO.doRetrieveByProdottoCodice(product.getCodice());
        if (venditoreId == utente1.getID_Utente()) {
            isVendor = true;
        }
        CCPDAO carrelloDAO = new CCPDAO();
        CWPDAO wishlistDAO = new CWPDAO();
        isInCart = carrelloDAO.isProductInCart(utente1.getID_Utente(), product.getCodice());
        isInWishlist = wishlistDAO.isProductInWishlist(utente1.getID_Utente(), product.getCodice());
    }

    if (!isInCart) {
        List<ProdottoBean> sessionCart = (List<ProdottoBean>) session.getAttribute("carrello");
        if (sessionCart != null) {
            for (ProdottoBean p : sessionCart) {
                if (p.getCodice() == product.getCodice()) {
                    isInCart = true;
                    break;
                }
            }
        }
    }

    if (!isInWishlist) {
        List<ProdottoBean> sessionWishlist = (List<ProdottoBean>) session.getAttribute("wishlist");
        if (sessionWishlist != null) {
            for (ProdottoBean p : sessionWishlist) {
                if (p.getCodice() == product.getCodice()) {
                    isInWishlist = true;
                    break;
                }
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title><%= product.getNome() %></title>
    <link rel="stylesheet" href="Css/product.css">
    <link rel="stylesheet" href="Css/navbar.css">
    <link rel="stylesheet" href="Css/carrello.css">
    <link rel="stylesheet" href="Css/wishList.css">
    <link rel="stylesheet" href="Css/Ordini.css">
    <link rel="stylesheet" href="Css/footer.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon/favicon.ico">
</head>
<body>
<%@ include file="nav-bar.jsp" %>

<div class="wrapper">
    <div class="product-container">

        <div class="image-viewer">
            <%
                ImmagineDAO idao = new ImmagineDAO();
                List<String> immagini = idao.doRetrieveImageByProduct(product.getCodice());
                if (immagini != null && !immagini.isEmpty()) {
                    for (int i = 0; i < immagini.size(); i++) {
            %>
            <img class="viewer-img<%= (i == 0) ? " active" : "" %>"
                 src="<%= request.getContextPath() + "/images/" + immagini.get(i)%>"
                 alt="Foto prodotto <%= product.getNome() %>">
            <%  }
            } else { %>
            <span>Nessuna immagine disponibile</span>
            <% } %>
            <button class="img-nav prev" aria-label="Immagine precedente">&lt;</button>
            <button class="img-nav next" aria-label="Immagine successiva">&gt;</button>
        </div>

        <div class="product-details">
            <div id="h1Container">
                <h1><%= product.getNome() %></h1>
            </div>

            <div class="price-section">
                <% if (product.getDiscounted() == 1) { %>
                <span class="prezzo">€<%= String.format("%.2f", product.getPrezzoScontato()) %></span>
                <span class="prezzo-originale">€<%= String.format("%.2f", product.getPrezzo()) %></span>
                <% } else { %>
                <span class="prezzo">€<%= String.format("%.2f", product.getPrezzo()) %></span>
                <% } %>
            </div>

            <ul class="attributes">
                <li>Categoria: <%= product.getCategoria() %></li>
                <li>Taglia: <%= product.getPeso() %></li>
                <li>Colore: <%= product.getColore() %></li>
            </ul>

            <div class="buttons">
                <form action="${pageContext.request.contextPath}/ServletCarrello" method="post">
                    <input type="hidden" name="codice" value="<%= product.getCodice() %>">
                    <input type="hidden" name="nome" value="<%= product.getNome() %>">
                    <input type="hidden" name="prezzo" value="<%= product.getPrezzo() %>">
                    <input type="hidden" name="prezzo_scontato" value="<%= product.getPrezzoScontato() %>">
                    <input type="hidden" name="descrizione" value="<%= product.getDescrizione() %>">
                    <% if (!isVendor) { %>
                    <% if (isInCart) { %>
                    <button type="button" class="btn cart" disabled>Aggiunto al carrello</button>
                    <% } else { %>
                    <button type="submit" class="btn cart">Aggiungi al carrello</button>
                    <% } %>
                    <% } %>
                </form>

                <form action="${pageContext.request.contextPath}/ServletWishlist" method="post">
                    <input type="hidden" name="codice" value="<%= product.getCodice() %>">
                    <input type="hidden" name="nome" value="<%= product.getNome() %>">
                    <input type="hidden" name="prezzo" value="<%= product.getPrezzo() %>">
                    <input type="hidden" name="prezzo_scontato" value="<%= product.getPrezzoScontato() %>">
                    <input type="hidden" name="descrizione" value="<%= product.getDescrizione() %>">
                    <% if (!isVendor) { %>
                    <% if (isInWishlist) { %>
                    <button type="button" class="btn wishlist" disabled>Aggiunto alla wishlist</button>
                    <% } else { %>
                    <button type="submit" class="btn wishlist">Aggiungi alla wishlist</button>
                    <% } %>
                    <% } %>
                </form>
            </div>
        </div>
    </div>

    <div id = "interests">Ti potrebbero anche interessare...</div>
    <div class="recommended-products">
            <%

                ProdottoDAO dao = new ProdottoDAO();
                List<ProdottoBean> listaCat = dao.doRetrieveByCategoria(product.getCategoria());
                if(listaCat != null && !listaCat.isEmpty()){
                    for(ProdottoBean prod : listaCat) {
            %>

            <div class="rec-product" onclick="window.location.href='${pageContext.request.contextPath}/ProductServlet?id=<%=prod.getCodice()%>'">
                <img src="<%=request.getContextPath()%>/images/<%=prod.getImg_path()  %>" alt="<%= prod.getNome() %>">
                <p class="rec-name"><%= prod.getNome() %></p>
                <p class="rec-price">€ <%= prod.getPrezzo() %></p>
                <p class="rec-condition"> <%= prod.getCondizioni()%> </p>
            </div>

            <%}
            }else {
            %>
            <span>Nessun prodotto consigliato disponibile</span>
            <%}%>
    </div>

    <div class="recensioni-grid-wrapper">
        <div class="right-col-recensioni" id="recensioni">
            <h2 id="header-rece">Recensioni degli utenti</h2>
            <div id="recensioni-items"></div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</div>

<script>
    var contextPath   = '<%= request.getContextPath() %>';
    var productId     = '<%= product.getCodice() %>';
    var loggato       = <%= session.getAttribute("utente") != null %>;
    var imgPath       = contextPath + '/img/caricamento.svg';
    var servletOrdiniUrl = contextPath + '/ServletOrdini';
    var reviewPageUrl    = contextPath + '/PaginaRecensione.jsp';
</script>
<script src="Js/product.js"></script>
<script src="Js/carrello.js"></script>
<script src="Js/wishlist.js"></script>
<script src="Js/Ordini.js"></script>
<script src="Js/homePage.js"></script>
<script src="Js/navbar.js"></script>
</body>
</html>
