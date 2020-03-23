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
    <h2>Zresetuj hasło</h2>
    <form:form method="post" action="/reset/password" modelAttribute="userDto">
        <form:hidden path="email" value="${userDto.email}"/>

        <div class="form-group">
            <form:input type="password" name="password" placeholder="Nowe hasło" path="password"/>
        </div>
        <div class="form-group">
            <form:input type="password" name="password2" placeholder="Powtórz hasło" path="password2"/>
            <form:errors path="password" cssClass="error"/>
        </div>

        <div class="form-group form-group--buttons">
            <button class="btn" type="submit">Zresetuj hasło</button>
        </div>
    </form:form>
</section>
</body>
</html>
