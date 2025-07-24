<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Errore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/error.css">
    <link rel = "icon" type ="image/x-icon" href = "${pageContext.request.contextPath}/img/favicon/favicon.ico">

</head>
<body>
<%
    System.out.println(exception);
%>
<div class="container">
    <div class="error-code">
        <%= request.getAttribute("jakarta.servlet.error.status_code") != null ? request.getAttribute("jakarta.servlet.error.status_code") : "Errore" %>
    </div>
    <div class="message">
        Si è verificato un problema. Ti chiediamo scusa per l'inconveniente.
    </div>

    <form action="${pageContext.request.contextPath}/InitServlet" method="GET">
        <button class="submit" type="submit">Torna alla Home</button>
    </form>


</div>
</body>
</html>
