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
    <title>Charity</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="../../../resources/css/style.css"/>" />

</head>
<body>
<header>
    <%@include file="../fragments/header.jsp"%>
</header>
<div class="slogan container container--90">
    <div class="slogan--item form-group form-group--buttons">
        <h2>Moje zbiórki</h2>
        <div class="stats--item" style="padding-bottom:  2em">
            <a href="<c:url value="/app/donation"/>" class="btn btn--without-border">Nowy dar</a>
        </div>
        <section>
    <c:choose>
        <c:when test="${empty donations}">
            <h2>Jeszcze nic nie podarowałeś</h2>
        </c:when>
            <c:otherwise>
                <table id="table" class="table table-hover" style="font-size: medium">
                    <tr>
                        <th>Obdarowna instytucja</th>
                        <th>Kategoria</th>
                        <th>Ilość</th>
                        <th>Adres odbioru</th>
                        <th>Data odbioru</th>
                        <th>Komentarz</th>
                        <th>Status</th>
                        <th>Akcje</th>
                    </tr>
                    <c:forEach items="${donations}" var="i">
                        <tr>
                            <td>${i.institution}</td>
                            <td>${i.categories}</td>
                            <td>${i.quantity}</td>
                            <td>${i.street}, ${i.zipCode} ${i.city}</td>
                            <td>${i.pickUpDate}</td>
                            <td>${i.pickUpComment}</td>
                            <td>${i.state}</td>
                            <td>
                                <a href="<c:url value="/app/donation/confirm/pickup/${i.id}"/>">potwierdź odbiór</a><br>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        </section>
    </div>
</div>
<br>
<br>

<%@include file="../fragments/footer.jsp"%>
</body>
</html>
