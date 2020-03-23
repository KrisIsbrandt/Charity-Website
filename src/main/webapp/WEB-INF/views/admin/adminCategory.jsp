<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Charity</title>
    <link rel="stylesheet" href="<c:url value="../../../resources/css/adminStyle.css"/>" />
</head>
<%@include file="../fragments/headerAdmin.jsp"%>
<body>

<a href="<c:url value="/admin/form?type=category"/>" type="button">Dodaj</a>

<table style="width:100%">
    <tr>
        <th>Nazwa</th>
        <th>Akcje</th>
    </tr>
<c:forEach items="${categories}" var="i">
    <tr>
        <td>${i.name}</td>
        <td>
            <a href="<c:url value="/admin/form?type=category&id=${i.id}"/>">edytuj</a>
            <a href="<c:url value="/admin/category/delete/${i.id}"/>"onclick="return confirm('Czy na pewno chcesz to usunać?');">usuń</a>
        </td>
    </tr>
</c:forEach>
</table>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</body>
</html>
