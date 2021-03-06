<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Charity</title>
    <link rel="stylesheet" href="<c:url value="../../resources/css/style.css"/>"/>
</head>
<body>
<header>
<%@include file="fragments/header.jsp"%>
</header>
<section class="login-page">
    <h2>Zaloguj się</h2>
    <c:choose>
        <c:when test="${param.error=='not_active'}">
            <p class="error">Konto nieaktywne</p>
            <a href="<c:url value="/resend/token"/>" class="btn btn--small btn--without-border reset-password">Aktywuj</a>
        </c:when>
        <c:when test="${param.error=='bad_credentials'}">
            <p class="error">Błąd logowania</p>
            <a href="<c:url value="/resend/password"/>" class="btn btn--small btn--without-border reset-password">Przypomnij hasło</a>
        </c:when>
    </c:choose>
    <form method="post" action="<c:url value="/login"/>">

        <div class="form-group">
            <input type="text" name="email" placeholder="Email" />
        </div>
        <div class="form-group">
            <input type="password" name="password" placeholder="Hasło" />
        </div>

        <div class="form-group form-group--buttons">
            <a href="<c:url value="/register"/>" class="btn btn--without-border">Załóż konto</a>
            <button class="btn" type="submit">Zaloguj się</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </div>

    </form>
</section>
</body>
</html>
