<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="Css/chiSiamo.css">
    <link rel="stylesheet" href="Css/homePage.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon/favicon.ico">
    <link rel="stylesheet" href="Css/carrello.css">
    <link rel="stylesheet" href="Css/wishList.css">
    <link rel="stylesheet" href="Css/Ordini.css">
    <link rel="stylesheet" href="Css/footer.css">
    <link rel="stylesheet" href="Css/navbar.css">
    <title>Chi Siamo</title>
</head>
<body>
<%@include file="nav-bar.jsp"%>

<div class="page-content">
    <h2 style="text-align:center">Il Nostro Team</h2>
    <div class="row">
        <div class="column">
            <div class="card">
                <img src="img/nikolas.jpg" alt="Nikolas Tullo" class="chiSiamo">
                <div class="container">
                    <h2>Nikolas Tullo</h2>
                    <p class="title">CEO & Founder</p>
                    <p>nikolastullo@gmail.com</p>
                    <p><button class="button">Contatta</button></p>
                </div>
            </div>
        </div>

        <div class="column">
            <div class="card">
                <img src="img/luca-jpg.jpg" alt="Luca Staiano" class="chiSiamo">
                <div class="container">
                    <h2>Luca Staiano</h2>
                    <p class="title">CEO & Founder</p>
                    <p>lucastaiano@gmail.com</p>
                    <p><button class="button">Contatta</button></p>
                </div>
            </div>
        </div>

        <div class="column">
            <div class="card">
                <img src="img/mirko.jpg" alt="Mirko Strianese" class="chiSiamo">
                <div class="container">
                    <h2>Mirko Strianese</h2>
                    <p class="title">CEO & Founder</p>
                    <p>mirkostrianese@gmail.com</p>
                    <p><button class="button">Contatta</button></p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp"%>

<script src="Js/carrello.js"></script>
<script src="Js/wishlist.js"></script>
<script src="Js/Ordini.js"></script>
<script src="Js/homePage.js"></script>
<script src="Js/navbar.js"></script>
</body>
</html>
