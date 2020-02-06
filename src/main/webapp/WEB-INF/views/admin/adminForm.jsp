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
            <%--TODO--%>
        </c:when>

        <c:when test="${param.type=='user'}">
            <%--TODO--%>
        </c:when>

        <c:when test="${param.type=='donnation'}">
            <%--TODO--%>
        </c:when>

        <c:otherwise>
            <p>Brak wsparcia dla tego typu</p>
        </c:otherwise>
    </c:choose>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</body>
</html>
