
<%@ page import="Model.Utente.UtenteBean" %>

<script>
    var contextPath = '${pageContext.request.contextPath}';
    var loggato = <%= (session.getAttribute("utente") != null) ? "true" : "false" %>;
    var imgPath = contextPath + '/img/caricamento.svg'; // Per lo spinner
    var servletOrdiniUrl = contextPath + '/ServletOrdini';
    var reviewPageUrl = contextPath + '/PaginaRecensione.jsp';
</script>


<%%>
<div id="content-container-ordini">
    <div id="overlay-ordini"></div>
    <button id="bottoneOrdini">
        <img src="${pageContext.request.contextPath}/img/ordini.svg"
             height="30" width="30" alt="Ordini">
    </button>

    <div id="Ordini" class="sidebar">
        <h2 id="TitoloOrdini">I tuoi Ordini</h2>
        <div id="Ordini-items"></div>

        <% if (session.getAttribute("utente") == null) { %>
        <button id="gotoLoginOrdini"
                onclick="window.location.href='${pageContext.request.contextPath}/login.jsp'">
            Accedi
        </button>
        <% } %>
    </div>
</div>