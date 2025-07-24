
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Prodotto.ProdottoBean" %>

<%
    String categoria = (String) request.getAttribute("categoria");
    List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodottiC");
%>
<!DOCTYPE html>
<html lang="it">





<head>
    <meta charset="UTF-8">
    <title>Risultati per “<%= categoria %>”</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/homePage.css">
    <link rel="stylesheet" href="Css/navbar.css">
    <link rel="stylesheet" href="Css/carrello.css">
    <link rel="stylesheet" href="Css/wishList.css">
    <link rel="stylesheet" href="Css/Ordini.css">
    <link rel="stylesheet" href="Css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/categoria.css">

</head>
<body>
<%@ include file="nav-bar.jsp" %>

<div class="wrapper">

<h1>Risultati per “<%= categoria %>”</h1>

<%
    if (prodotti == null || prodotti.isEmpty()) {
%>
<p>Nessun prodotto trovato.</p>

<%
} else { %>

<div class="products-grid">
<%
    for (ProdottoBean p : prodotti) {
%>
<div class="rec-product" onclick="window.location.href='${pageContext.request.contextPath}/ProductServlet?id=<%=p.getCodice()%>'">
    <img src="<%=request.getContextPath()%>/images/<%=p.getImg_path()%>" alt="<%= p.getNome() %>">
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
        }
    %>

</div>
<%
    }
%>
    <div id = "backHome"><p><a href="${pageContext.request.contextPath}/InitServlet">Torna alla homepage</a></p></div>

    <%@ include file="footer.jsp"%>
</div>
<script src ="Js/carrello.js"></script>
<script src ="Js/wishlist.js"></script>
<script src ="Js/Ordini.js"></script>
<script src ="Js/homePage.js"></script>
<script src ="Js/navbar.js"></script>

</body>
</html>
