
<div id="content-container-wishlist">
    <div id="overlays" class="hidden"></div>

    <button id="bottoneWishlist">
        <img src="${pageContext.request.contextPath}/img/heart-svgrepo-com.svg" height="30px" width="30px" alt="Wishlist"/>
    </button>

    <div id="wishlist" class="hidden">
        <h2 id="titolo">WishList</h2>
        <div id="oggetti"></div>
        <div id="totale">
            <% if (session.getAttribute("utente") != null){%>
            <button id="addToCart" title="Aggiungi tutti al carrello">
                <img src="${pageContext.request.contextPath}/img/addAllToCart.svg" width="30px" height="30px" alt="Aggiungi tutti al carrello">
            </button>
            <%}else{%>
            <button id="gotoLoginWishlist" onclick="window.location.href = '${pageContext.request.contextPath}/login.jsp'">Accedi</button>
            <%}%>
        </div>
    </div>
</div>
