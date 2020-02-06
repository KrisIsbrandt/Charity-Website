<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<header>
    <nav>
        <form action="<c:url value="/logout"/>" method="post">
            <input type="submit" value="Wyloguj"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h1>Panel Administratora</h1>
        <ul>
            <li><a href="<c:url value="/admin/user"/>" >Użytkownicy</a></li>
            <li><a href="<c:url value="/admin/institution"/>" >Instytucje</a></li>
            <li><a href="<c:url value="/admin/donation"/>" >Dary i Kategorie</a></li>
        </ul>
    </nav>
</header>