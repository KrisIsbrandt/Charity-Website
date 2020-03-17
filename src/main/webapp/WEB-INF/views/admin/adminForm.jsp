<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="../../../resources/css/adminStyle.css"/>" />
</head>
<%@include file="../fragments/headerAdmin.jsp"%>
<body>
    <c:choose>
        <c:when test="${type=='institution'}">
            <h3>Instytucja</h3>
            <form:form action="/admin/institution" method="post" modelAttribute="institution">
                Nazwa instytucji:<br>
                <form:input type="text" path="name"/>
                <form:errors path="name" cssClass="error"/><br><br>
                Cel i misja instytucji:<br>
                <form:textarea rows="3" cols="50" path="description"/><br>
                <form:hidden path="id"/>
                <input type="submit" value="Wyślij"><br>

            </form:form>
        </c:when>

        <c:when test="${type=='category'}">
            <h3>Kategoria darów</h3>
            <form:form action="/admin/category" method="post" modelAttribute="category">
                Nazwa:<br>
                <form:input type="text" path="name"/>
                <form:errors path="name" cssClass="error"/><br>
                <form:hidden path="id"/>
                <input type="submit" value="Wyślij"><br>
            </form:form>
        </c:when>

        <c:when test="${type=='user'}">
            <h3>Użytkownik</h3>
            <form:form action="/admin/user" method="post" modelAttribute="user">
                Typ użytkownika:<br>
                <form:select path="role">
                    <form:option value="${user.role}" label="--wybierz--"/>
                    <form:options items="${roles}"/>
                </form:select><form:errors path="role" cssClass="error"/>
                <br><br>
                <form:hidden path="id"/>

                Imię:<br>
                <form:input type="text" path="firstName"/><form:errors path="firstName" cssClass="error"/><br><br>
                Nazwisko:<br>
                <form:input type="text" path="lastName"/><form:errors path="lastName" cssClass="error"/><br><br>
                Email:<br>
                <form:input type="email" path="email"/><form:errors path="email" cssClass="error"/><br><br>
                Hasło:<br>
                <form:input type="password" path="password"/><form:errors path="password" cssClass="error"/><br><br>


                <input type="submit" value="Wyślij"><br>
            </form:form>
        </c:when>

        <c:when test="${type=='donation'}">
            <h3>Dar</h3>
            <form:form action="/admin/donation" method="post" modelAttribute="donation">
                <strong>Obdarowana instytucja:</strong><br>
                <form:select path="institution">
                    <form:option value="${donation.institution}" label="--wybierz--"/><br>
                    <form:options items="${institutions}" itemValue="id" itemLabel="name"/>
                </form:select><form:errors path="institution" cssClass="error"/><br><br>

                <strong>Kategoria:</strong><br>
                <form:checkboxes path="categories" items="${categories}"
                                 itemLabel="name" itemValue="id"/><form:errors path="categories" cssClass="error"/><br><br>

                <strong>Ilość:</strong><br>
                <form:input type="number" path="quantity"/><form:errors path="quantity" cssClass="error"/><br><br>

                <strong>Dane adresowe</strong><br>

                Ulica: <form:input path="street"/><form:errors path="street" cssClass="error"/><br>
                Misto: <form:input path="city"/><form:errors path="city" cssClass="error"/><br>
                Kod pocztowy: <form:input path="zipCode"/><form:errors path="zipCode" cssClass="error"/><br>
                Numer telefonu: <form:input path="phoneNumber"/><br><br>

                <strong>Dane odbioru</strong><br>

                Data: <form:input type="date" path="pickUpDate"/><br>
                Czas: <form:input type="time" path="pickUpTime" step="600"/><br>
                Komentarz: <br><form:textarea path="pickUpComment" rows="5"/><br><br>

                <form:hidden path="id"/>
                <input type="submit" value="Wyślij"><br>
            </form:form>
        </c:when>

        <c:otherwise>
            <p>Brak wsparcia dla tego typu</p>
        </c:otherwise>
    </c:choose>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</body>
</html>
