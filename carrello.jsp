<%@ page import="Model.Utente.UtenteBean" %>
<div id="content-container-carrello">
    <div id="overlay-carrello"></div>

    <button id="bottoneCarrello">
        <img src="${pageContext.request.contextPath}/img/carrello.svg" height="30px" width="30px" alt="carrello" />
    </button>

    <div id="carrello">
        <h2 id="TitoloCarrello">Carrello</h2>
        <div id="items"></div>
        <div id="totale">
            <p class="prodotti">Totale: &euro;<span id="totaleDaPagare"></span></p>

            <% if (session.getAttribute("utente") == null) { %>
            <button id="gotoLoginCarrello"
                    onclick="window.location.href='${pageContext.request.contextPath}/login.jsp'">
                Accedi
            </button>
            <% } %>
        </div>
    </div>
</div>