<%@ page import="Model.Utente.UtenteBean" %>
<%@ page import="Model.Prodotto.ProdottoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Immagine.ImmagineDAO" %>


<%
    String contextPath = request.getContextPath();
    List<ProdottoBean> productList = (List<ProdottoBean>) request.getAttribute("productList");
    if (productList == null) {
        productList = new java.util.ArrayList<>();
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Got-it!</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon/favicon.ico">


    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/homePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/categoria.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/carrello.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/wishList.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Ordini.css">

</head>
<body>
<%if("true".equals(request.getAttribute("successo"))){%>
<div id="confermaPagamento"></div>
<%}%>

<div id="overlay"></div>
<%@ include file="/nav-bar.jsp"%>
<div class="main-content">
    <section>

        <div id = "Add-Container">
            <div id="add-product">
                <p id="p-product">Inizia a vendere!</p>
            </div>
        </div>

        <%

            int visibleCount = 0;
            for(ProdottoBean t : productList){ if(!t.getDeleted()) visibleCount++; }

            final int BASE = visibleCount / 2;
            final int CARDS = BASE - (BASE % 3);

            int promoIndex = Math.min(CARDS, visibleCount);

            String role = (String) request.getSession().getAttribute("role");
            if(role != null) {
                if(role.equals("admin")) {

        %>
        <div class="button-admin">
            <button type="button" class="btn-logout" onclick="window.location.href = '/admin/RimuoviProdotto.jsp'">Area Amministratore</button>
        </div>

        <%}
        }%>

        <div id="consigliati">

            <% if (request.getAttribute("errore") != null) { %>
            <p class="error-message">
                <%= request.getAttribute("errore") %>
            </p>
            <% } %>

            <button class="carousel-btn prev" aria-label="Indietro">&lt;</button>


            <div class="carousel-window">
                <div class="carousel-track">

                    <%
                        if(productList != null && !productList.isEmpty()) {
                            for(ProdottoBean prod : productList) {
                                if(!prod.getDeleted()) {

                    %>

                    <div class="product-card" onclick="window.location.href='<%=contextPath%>/ProductServlet?id=<%=prod.getCodice()%>'">
                        <img src="<%=contextPath%>/images/<%=prod.getImg_path()%>" alt="" class="product-image">
                        <div class="product-info">
                            <h3 class="product-name"><%=prod.getNome()%></h3>
                            <p class="product-price">
                                <%
                                    if(prod.getDiscounted() == 1) {
                                %>
                                <span class="old-price">&euro;<%= prod.getPrezzo() %></span>
                                <span class="new-price">&euro;<%= prod.getPrezzoScontato()%> </span>
                                <%
                                } else {
                                %>
                                <span class="price">&euro;<%= prod.getPrezzo() %></span>
                                <%
                                    }
                                %>
                            </p>
                            <p class="product-description"><%=prod.getDescrizione()%></p>
                            <p class="product-color"><%=prod.getColore()%></p>
                            <p class="product-rating"><%=(prod.getMedia_recensioni() == null) ? 0 : prod.getMedia_recensioni()%></p>
                        </div>
                    </div>

                    <%}
                    }

                    } else {
                    %>
                    <p>Nessun prodotto consigliato disponibile</p>
                    <%
                        }
                    %>

                </div>
            </div>
            <button class="carousel-btn next" aria-label="Avanti">&gt;</button>
        </div>
    </section>

    <div class="products-grid">

        <%
            int i = 0;
            for(ProdottoBean p : productList) {
                if(!p.getDeleted()) {

        %>

        <div class="rec-product" onclick="window.location.href='${pageContext.request.contextPath}/ProductServlet?id=<%=p.getCodice()%>'">
            <img src="<%=contextPath%>/images/<%=p.getImg_path()%>" alt="" class="product-image">
            <p class="rec-name"><%= p.getNome() %></p>
            <%
                if(p.getDiscounted() == 1) {
            %>
            <strong><%= String.format("%.2f", p.getPrezzoScontato()) %></strong>
            <span class="prezzo-originale">&euro;<%= String.format("%.2f", p.getPrezzo()) %></span>
            <style>.prezzo-originale { text-decoration: line-through; color: #888; margin-left: 0.5em;} </style>
            <%} else {%>
            <span class="prezzo">&euro;<%= String.format("%.2f", p.getPrezzo()) %></span>
            <style>.prezzo {font-weight: bold;}</style>
            <%}%>
            <p class="rec-condition"> <%= p.getCondizioni()%> </p>

        </div>

        <%

            i++;

        } else {
        %>
        <p>Nessun prodotto consigliato disponibile</p>
        <%
            }
        %>

        <%

            if(i == promoIndex){ %>
        <div class="promo-banner">
            <img src="img/Banner.png" alt="Banner Vendi ora">
            <div class="promo-content">
                <h2>Hai oggetti inutilizzati a casa?</h2>
                <button class="promo-btn">Vendi ora</button>
            </div>
        </div>
        <% }
        %>

        <%
            } %>
    </div>
</div>
<%@ include file="/footer.jsp"%>


<script>
    var contextPath = '${pageContext.request.contextPath}';
    var loggato = <%= (session.getAttribute("utente") != null) ? "true" : "false" %>;
    var imgPath = contextPath + '/img/caricamento.svg'; // Per lo spinner
    var servletOrdiniUrl = contextPath + '/ServletOrdini';
    var reviewPageUrl = contextPath + '/PaginaRecensione.jsp';
</script>

<script src="${pageContext.request.contextPath}/Js/navbar.js" defer></script>
<script src="${pageContext.request.contextPath}/Js/homePage.js"></script>
<script src="${pageContext.request.contextPath}/Js/sconto.js"></script>
<script src="${pageContext.request.contextPath}/Js/carrello.js"></script>
<script src="${pageContext.request.contextPath}/Js/wishlist.js"></script>
<script src="${pageContext.request.contextPath}/Js/Ordini.js"></script>

</body>
</html>