<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="../../../resources/css/style.css"/>" />
</head>
<body>
<header>
    <%@include file="../fragments/header.jsp"%>
</header>
<section class="login-page">
        <h2>Edycja profilu</h2>

        <form:form method="post" action="/app/profil/update" modelAttribute="userDto">
            <div class="form-group">
                <form:input type="text" name="firstName" placeholder="imię" path="firstName"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:input type="text" name="lastName" placeholder="nazwisko" path="lastName"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:input type="email" name="email" placeholder="Email" path="email"/>
                <form:errors path="email" cssClass="error"/>

                <form:hidden path="password"/>
                <form:errors path="password" cssClass="error"/>
            </div>
            <div class="form-group form-group--buttons">
                <button class="btn" type="submit">Zapisz</button>
            </div>
        </form:form>
</section>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="../../resources/js/app.js"></script>
</body>
</html>
