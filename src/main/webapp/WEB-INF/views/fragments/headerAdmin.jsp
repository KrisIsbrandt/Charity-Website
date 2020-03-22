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
        <table style="width:100%">
            <tr>
                <th><a class="toolbar" href="<c:url value="/admin/user"/>" >Użytkownicy</a></th>
                <th><a class="toolbar" href="<c:url value="/admin/institution"/>" >Instytucje</a></th>
                <th><a class="toolbar" href="<c:url value="/admin/donation"/>" >Dary</a></th>
                <th><a class="toolbar" href="<c:url value="/admin/category"/>" >Kategorie</a></th>
            </tr>
        </table>
       <br><br>
    </nav>
</header>