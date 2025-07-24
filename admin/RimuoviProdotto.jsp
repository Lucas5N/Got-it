
<%@ page import="Model.Utente.UtenteBean" %>
<%@ page import="Model.Prodotto.ProdottoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Prodotto.ProdottoDAO" %>
<%
    String contextPath = request.getContextPath();
    ProdottoDAO dao = new ProdottoDAO();
    List<ProdottoBean> prodotti = dao.doRetrieveAll();
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Admin Gestione Prodotti</title>
    <link rel="stylesheet" href="<%=contextPath%>/admin/RimuoviProdotto.css">
</head>
<body>
<header class="header">
    <h1>Benvenuto nell'area Amministratore</h1>
    <a href="<%=contextPath%>/logout" class="btn-logout">Logout</a>
    <a href="<%=contextPath%>/InitServlet" class="btn-logout">Home Utente</a>
</header>

<main class="container">

    <%
        String okR  = (String) session.getAttribute("mexSuccess");
        String errR = (String) session.getAttribute("mexError");

        if (okR != null) { %>
    <div class="toast success"><%= okR %></div>
    <% session.removeAttribute("mexSuccess"); %>
    <% }

        if (errR != null) { %>
    <div class="toast error"><%= errR %></div>
    <% session.removeAttribute("mexError"); %>
    <% } %>



    <section class="product-table-section">
        <table class="product-table">
            <thead>
            <tr>
                <th>Immagine</th>
                <th>Nome</th>
                <th>Prezzo</th>
                <th>Descrizione</th>
                <th>Peso</th>
                <th>Colore</th>
                <th>Categoria</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (prodotti != null) {
                    for (ProdottoBean p : prodotti) {
            %>
            <tr>
                <td>

                </td>
                <td><%=p.getNome()%></td>
                <td id="price-cell" data-prod-id="<%= p.getCodice() %>">
                    <%
                        double sconto = p.getPrezzoScontato();
                        if(p.getDiscounted() == 1) {
                    %>
                    <span id="old-price">&euro;<%= p.getPrezzo() %></span>
                    <span id="new-price">&euro;<%= sconto %></span>
                    <%
                    } else {
                    %>
                    <span class="price">&euro;<%= p.getPrezzo() %></span>
                    <%
                        }
                    %>
                </td>
                <td><%=p.getDescrizione()%></td>
                <td><%=p.getPeso()%> g</td>
                <td><%=p.getColore()%></td>
                <td><%=p.getCategoria()%></td>
                <td>
                    <form action="<%=contextPath%>/removeProduct" method="post"
                          onsubmit="return confirm('Sei sicuro di voler eliminare <%=p.getNome()%>?');">
                        <input type="hidden" name="codice" value="<%=p.getCodice()%>"/>
                        <button type="submit" class="btn-delete">Elimina</button>
                    </form>

                </td>

                <td>

                </td>

            </tr>
            <%
                }
            } else {
            %>
            <tr><td colspan="7">Nessun prodotto disponibile.</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </section>
</main>

</body>

</html>