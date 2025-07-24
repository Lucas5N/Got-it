<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Utente.UtenteBean" %>
<%@ page import="Model.Ordine.OrdineDAO" %>
<%@ page import="Model.Ordine.OrdineBean" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Vendere.VendereDAO" %>
<%@ page import="Model.Prodotto.ProdottoBean" %>
<%var contextPath = request.getContextPath();%>
<html lang="it">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="UTF-8">
  <title>Profilo Utente</title>
  <link rel="stylesheet" href="Css/navbar.css">
  <link rel="stylesheet" href="Css/profile.css">
  <link rel="stylesheet" href="Css/carrello.css">
  <link rel="stylesheet" href="Css/wishList.css">
  <link rel="stylesheet" href="Css/Ordini.css">
  <link rel="stylesheet" href="Css/footer.css">

  <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon/favicon.ico">
</head>
<body class="profile-page">

<%@ include file="nav-bar.jsp" %>
<%if(utente == null) response.sendRedirect(request.getContextPath() + "/login.jsp");%>

<div class="page-wrapper">
  <div class="profile-container">
    <div class="menu">
      <form action="<%= request.getContextPath() %>/logout" method="get">
        <input type="submit" value="Logout" id="logoutButton">
      </form>
    </div>

    <div class="profile-content">
      <div class="profile-column">
        <div id="infoSection">
          <div id="infoUser" class="infoUser">
            <h1>Informazioni Utente</h1>

            <div class="user-info">
              <p><strong>ID Utente:</strong> <%= utente.getID_Utente() %></p>
              <p><strong>Username:</strong> <%= utente.getUsername() %></p>
              <p><strong>Nome:</strong> <%= utente.getNome() %></p>
              <p><strong>Cognome:</strong> <%= utente.getCognome() %></p>
              <p><strong>Email:</strong> <%= utente.getEmail() %></p>
              <p><strong>Stato:</strong> <%= utente.getStato() %></p>
              <p><strong>Regione:</strong> <%= utente.getRegione() %></p>
              <p><strong>Città:</strong> <%= utente.getCitta() %></p>
              <p><strong>Via:</strong> <%= utente.getVia() %></p>
              <p><strong>Numero Civico:</strong> <%= utente.getNumero_civico() %></p>
              <p><strong>Conto Corrente:</strong>
                <%= utente.getConto_corrente_addebbitabile() %>
              </p>
            </div>

            <button id="editButton" class="edit-button"
                    onclick="document.getElementById('editEmailForm').style.display='block';
                               this.style.display='none';">
              Modifica Dati
            </button>

            <% String result = (String) session.getAttribute("result");
              if (result != null) { %>
            <p class="success-message"><%= result %></p>
            <%  session.removeAttribute("result");
            } %>

            <form id="editEmailForm"
                  action="<%= request.getContextPath() %>/editUserServlet"
                  method="post"
                  style="display:none; margin-top:1em;">
              <input type="email"   name="newEmail"          placeholder="Nuova email" class="edit-input"><br>
              <input type="text"    name="newUsername"       placeholder="Nuovo username" class="edit-input"><br>
              <input type="password"name="newPassword"       placeholder="Nuova password" class="edit-input"><br>
              <input type="text"    name="newStato"          placeholder="Nuovo stato" class="edit-input"><br>
              <input type="text"    name="newRegione"        placeholder="Nuova regione" class="edit-input"><br>
              <input type="text"    name="newCitta"          placeholder="Nuova città" class="edit-input"><br>
              <input type="text"    name="newTelefono"       placeholder="Nuovo telefono" class="edit-input"><br>
              <input type="text"    name="newVia"            placeholder="Nuova via" class="edit-input"><br>
              <input type="text"    name="newCivico"         placeholder="Nuovo civico" class="edit-input"><br>
              <input type="text"    name="newContoCorrente"  placeholder="Nuovo conto corrente" class="edit-input"><br>
              <input type="submit"  value="Salva Modifiche"  class="edit-submit">
            </form>

          </div>
        </div>
      </div>





      <%
        OrdineDAO odao = new OrdineDAO();
        List<OrdineBean> ordini = odao.doRetrieveByUtenteId(utente.getID_Utente());
      %>

      <div id="ordersSection" class="section orders-section">
        <h2>I miei ordini</h2>
        <% if (ordini != null && !ordini.isEmpty()) { %>
        <ul class="orders-list">
          <% for (OrdineBean o : ordini) { %>
          <li class="order-item">
            <span class="order-id">Ordine #<%= o.getCodice() %></span>
            <span class="order-buy">(<%= o.getData_acquisto() %>)</span>
            <span class="order-date">(<%= o.getData_consegna() %>)</span>
            <span class="order-state">(<%= o.getStato_consegna() %>)</span>
          </li>
          <% } %>
        </ul>
        <% } else { %>
        <p class="empty-message">Non hai ancora effettuato ordini.</p>
        <% } %>
      </div>

      <%
        VendereDAO vdao = new VendereDAO();
        List<ProdottoBean> prodotti = vdao.doRetrieveByUtenteId(utente.getID_Utente());
      %>

      <div id="productsSection" class="section products-section">
        <h2>I miei prodotti in vendita</h2>
        <% if (prodotti != null && !prodotti.isEmpty()) { %>
        <ul class="products-list">
          <% for (ProdottoBean p : prodotti) {
            if(!p.getDeleted()){
              double prezzoScontato = p.getPrezzoScontato();

          %>

          <li class="product-item">
            <img src="<%=contextPath%>/images/<%=p.getImg_path()%>"  alt="<%= p.getNome() %>" class="product-thumb">
            <div class="product-info">
              <span class="product-name"><%= p.getNome() %></span>
              <% if (p.getDiscounted() == 1) { %>
              <span class="original-price" style="text-decoration: line-through; color:#999;">&euro;<%= String.format("%.2f", p.getPrezzo()) %></span>
              <span class="discounted-price" id="price-<%= p.getCodice() %>">&euro;<%= String.format("%.2f", prezzoScontato) %></span>
              <% } else { %>
              <span class="product-price" id="price-<%= p.getCodice() %>">&euro;<%= String.format("%.2f", p.getPrezzo()) %></span>
              <% } %>
              <span class="product-conditions"><%= p.getCondizioni() %></span>
            </div>
            <button class="edit-price-btn">Modifica Prezzo</button>
            <div class="edit-price-form" style="display:none; margin-top:8px;">
              <input type="hidden" name="id" value="<%= p.getCodice() %>">
              <input type="hidden" name="originale" value="<%= p.getPrezzo() %>">
              <input type="number" step="0.01" min="0" name = "prezzo_scontato" class="new-price-input" value="<%= p.getPrezzo() %>">
              <button class="save-price-btn">Applica</button>
            </div>
            <!-- Bottone di rimozione prodotto -->
            <button class="remove-product-btn" data-codice="<%= p.getCodice() %>">Rimuovi Prodotto</button>
          </li>
          <%} else { %>
          <p class="empty-message"></p>
          <% }
          } %>
        </ul>
        <%
        }
        else { %>
        <p class="empty-message"></p>
        <% } %>
      </div>
    </div>
  </div>
  <%@include file="footer.jsp"%>
</div>




<script src="<%= request.getContextPath() %>/Js/profile.js"></script>
<script src="<%= request.getContextPath() %>/Js/sconto.js"></script>
<script src="${pageContext.request.contextPath}/Js/navbar.js" defer></script>
<script src="${pageContext.request.contextPath}/Js/carrello.js"></script>
<script src="${pageContext.request.contextPath}/Js/wishlist.js"></script>
<script src="${pageContext.request.contextPath}/Js/Ordini.js"></script>


</body>
</html>