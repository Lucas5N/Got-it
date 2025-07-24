<%@ page import="Model.Utente.UtenteBean" %>
<%
    UtenteBean utente = (UtenteBean) session.getAttribute("utente");
%>
<nav>
    <form action="${pageContext.request.contextPath}/InitServlet" method="get">
        <button type="submit" style="border:none; background:none; padding:0;">
            <img src="${pageContext.request.contextPath}/img/logo.png"
                 alt="Logo"
                 id="logo"
                 style="width:50px; height:45px;">
        </button>
    </form>

    <div id="desktop-menu-items">
        <div id="menu">
            <div id="Categoria">Categoria</div>
            <div id="list">
                <div id="elettronica" class="categoria">Elettronica</div>
                <div id="elettrodomestici" class="categoria">Elettrodomestici</div>
                <div id="intrattenimento" class="categoria">Intrattenimento</div>
                <div id="bellezza" class="categoria">Bellezza</div>
            </div>
        </div>

        <div id="barra">
            <img src="${pageContext.request.contextPath}/img/lente.png"
                 alt="Cerca"
                 id="lente"
                 width="30">
            <form action="${pageContext.request.contextPath}/searchServlet" method="get">
                <input type="text"
                       name="search"
                       placeholder="Search"
                       id="search">
            </form>
        </div>
    </div>

    <div id="right-nav-items">
        <div id="nav-button-group">
            <%@ include file="/carrello.jsp" %>
            <%@ include file="/wishList.jsp" %>
            <%@ include file="/Ordini.jsp" %>
        </div>

        <div id="username">
            <% if (utente == null) { %>
            <div id="Accedi">
                <a href="${pageContext.request.contextPath}/login.jsp">Accedi</a>
            </div>
            <% } else { %>
            <a href="${pageContext.request.contextPath}/profile.jsp">
                <img src="${pageContext.request.contextPath}/img/profile.svg"
                     width="50"
                     height="50"
                     id="img-user"
                     alt="Immagine profilo">
            </a>
            <span class="user-info-nav" id = "usernameInfo"><%= utente.getUsername() %></span>
            <% if (utente.getAmministratore()) { %>
            <span class="user-info-nav" id="usernameInfoAdmin">(Admin)</span>
            <% } %>
            <% } %>
        </div>
    </div>

    <button id="hamburger-btn"><img src="${pageContext.request.contextPath}/img/hamburger.svg" width="30" height="30" alt="Menu"></button>

    <div id="mobile-nav">
        <a href="#" class="mobile-link" onclick="document.getElementById('catFormElettronica').submit();">Elettronica</a>
        <a href="#" class="mobile-link" onclick="document.getElementById('catFormElettrodomestici').submit();">Elettrodomestici</a>
        <a href="#" class="mobile-link" onclick="document.getElementById('catFormIntrattenimento').submit();">Intrattenimento</a>
        <a href="#" class="mobile-link" onclick="document.getElementById('catFormBellezza').submit();">Bellezza</a>
        <div class="mobile-search">
            <form action="${pageContext.request.contextPath}/searchServlet" method="get">
                <input type="text" name="search" placeholder="Cerca nel sito...">
                <button type="submit">Cerca</button>
            </form>
        </div>
    </div>

    <form id="catFormElettronica" action="${pageContext.request.contextPath}/CategorieServlet" method="get" style="display:none;"><input type="hidden" name="category" value="elettronica"></form>
    <form id="catFormElettrodomestici" action="${pageContext.request.contextPath}/CategorieServlet" method="get" style="display:none;"><input type="hidden" name="category" value="elettrodomestici"></form>
    <form id="catFormIntrattenimento" action="${pageContext.request.contextPath}/CategorieServlet" method="get" style="display:none;"><input type="hidden" name="category" value="intrattenimento"></form>
    <form id="catFormBellezza" action="${pageContext.request.contextPath}/CategorieServlet" method="get" style="display:none;"><input type="hidden" name="category" value="bellezza"></form>

</nav>