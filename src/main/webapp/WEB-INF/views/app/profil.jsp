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
<div class="slogan container container--90">
    <div class="slogan--item form-group form-group--buttons">
        <h2>Twój profil</h2>
        <section class="profile">
            <div class="container container--85">
                <div class="profile--item">
                    <p style="width: 70%; font-size: 2.2rem; line-height: 2em; display: block;margin: auto; padding-left: 10em ">
                        Imię: ${user.firstName}<br>
                        Nazwisko: ${user.lastName}<br>
                        Email: ${user.email}<br>
                    </p>
                </div>
                <div class="stats--item" style="padding: 2em">
                    <a href="<c:url value="/app/profil/update"/>" class="btn btn--without-border">Edytuj profil</a>
                </div>
            </div>
        </section>
    </div>
</div>
<br><br>

<%@include file="../fragments/footer.jsp"%>
</body>
</html>
