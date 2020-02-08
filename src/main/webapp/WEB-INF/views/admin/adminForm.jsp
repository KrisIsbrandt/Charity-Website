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
        <c:when test="${param.type=='institution'}">
            <h3>Instytucja</h3>
            <form:form action="/admin/institution" method="post" modelAttribute="institution">
                Nazwa instytucji:<br>
                <form:input type="text" path="name"/><br><br>
                Cel i misja instytucji:<br>
                <form:textarea rows="3" cols="50" path="description"/><br>
                <form:hidden path="id"/>
                <input type="submit" value="Wyślij">
            </form:form>
        </c:when>

        <c:when test="${param.type=='category'}">
            <h3>Kategoria darów</h3>
            <form:form action="/admin/category" method="post" modelAttribute="category">
                Nazwa:<br>
                <form:input type="text" path="name"/><br>
                <form:hidden path="id"/>
                <input type="submit" value="Wyślij">
            </form:form>
        </c:when>

        <c:when test="${param.type=='user'}">
            <h3>Użytkownik</h3>
            <form:form action="/admin/user" method="post" modelAttribute="user">
                Imię:<br>
                <form:input type="text" path="firstName"/><br><br>
                Nazwisko:<br>
                <form:input type="text" path="lastName"/><br><br>
                Email:<br>
                <form:input type="email" path="email"/><br><br>
                Hasło:<br>
                <form:input type="password" path="password"/><br><br>

                Typ użytkownika:<br>
               <form:select path="role">
                   <form:option value="${user.role}" label="--wybierz--"/>
                   <form:options items="${roles}"/>
               </form:select>
                <br><br>
                <form:hidden path="id"/>
                <input type="submit" value="Wyślij">
            </form:form>
        </c:when>

        <c:when test="${param.type=='donation'}">
            <h3>Dar</h3>
            <form:form action="/admin/donation" method="post" modelAttribute="donation">
                Obdarowana instytucja:<br>
                <form:select path="institution">
                    <form:option value="${donation.institution}" label="--wybierz--"/>
                    <form:options items="${institutions}" itemValue="id" itemLabel="name"/>
                </form:select>  <br>

                Kategoria:<br>
                <form:checkboxes path="categories" items="${categories}"
                                 itemLabel="name" itemValue="id"/><br><br>

                Ilość:<br>
                <form:input type="number" path="quantity"/><br><br>

                Dane adresowe<br>

                Ulica: <form:input path="street"/><br>
                Misto: <form:input path="city"/><br>
                Kod pocztowy: <form:input path="zipCode"/><br>
                Numer telefonu: <form:input path="phoneNumber"/><br><br>

                Dane odbioru<br>

                Data: <form:input type="date" path="pickUpDate"/><br>
                Czas: <form:input type="time" path="pickUpTime" step="600"/><br>
                Komentarz: <form:textarea path="pickUpComment" rows="5"/><br><br>

                <form:hidden path="id"/>
                <input type="submit" value="Wyślij">
            </form:form>
        </c:when>

        <c:otherwise>
            <p>Brak wsparcia dla tego typu</p>
        </c:otherwise>
    </c:choose>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</body>
</html>
