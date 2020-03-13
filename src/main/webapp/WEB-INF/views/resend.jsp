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
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="../../resources/css/style.css"/>"/>
</head>
<body>
<header>
    <%@include file="fragments/header.jsp"%>
</header>
<div class="slogan container container--90">
    <h2>
        <c:choose>
            <c:when test="${(message == 'Taki kod weryfikacyjny nie istnieje') ||(message == 'Nieważny kod weryfikacyjny') || (param.q == 'not_active')}">Wyślij ponownie kod weryfikujący</c:when>
            <c:when test="${param.q} == 'password_reset'">Wyślij link do zresetowania hasła</c:when>
            <c:otherwise>${message}</c:otherwise>
        </c:choose>
    </h2>
    <div>
        <c:choose>
            <c:when test="${(message == 'Taki kod weryfikacyjny nie istnieje') ||(message == 'Nieważny kod weryfikacyjny') || (param.q == 'not_active')}">
                <section class="error-page">
                    <form method="post" action="<c:url value="/resendToken"/>">
                        <div class="form-group">
                            <input type="text" name="email" placeholder="Email" />

                        </div>
                        <div class="form-group">
                            <button class="btn" type="submit">Aktywuj ponownie</button>
                        </div>
                    </form>
                </section>
            </c:when>
            <c:when test="${param.q} == 'password_reset'">
                <section class="error-page">
                    <form method="post" action="<c:url value="/resetPassword"/>">
                        <div class="form-group">
                            <input type="text" name="email" placeholder="Email" />

                        </div>
                        <div class="form-group">
                            <button class="btn" type="submit">Zresetuj hasło</button>
                        </div>
                    </form>
                </section>
            </c:when>
        </c:choose>

    </div>
</div>

</body>
</html>
