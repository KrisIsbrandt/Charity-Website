<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
    <nav class="container container--70">
        <c:choose>
            <c:when test="${empty loggedUserName}">
                <ul class="nav--actions">
                    <li><a href="<c:url value="/login"/>" class="btn btn--small btn--without-border">Zaloguj</a></li>
                    <li><a href="<c:url value="/register"/>" class="btn btn--small btn--highlighted">Załóż konto</a></li>
                </ul>
            </c:when>
            <c:otherwise>
            <ul class="nav--actions">
                <li class="logged-user">
                    Witaj ${loggedUserName}
                    <ul class="dropdown">
                        <li><a href="<c:url value="/app/profil"/>">Profil</a></li>
                        <li><a href="<c:url value="/app/my_donations"/>">Moje zbiórki</a></li>
                        <li>
                            <form action="<c:url value="/logout"/>" method="post">
                                <input type="submit" value="Wyloguj"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                        </li>
                    </ul>
                </li>
            </ul>
            </c:otherwise>
        </c:choose>

        <ul>
            <li><a href="<c:url value="/"/>" class="btn btn--without-border active">Start</a></li>
            <li><a href="<c:url value="/idea"/>" class="btn btn--without-border">O co chodzi?</a></li>
            <li><a href="<c:url value="/about_us"/>" class="btn btn--without-border">O nas</a></li>
            <li><a href="<c:url value="/institution"/>" class="btn btn--without-border">Fundacje i organizacje</a></li>
            <li><a href="<c:url value="/contact"/>" class="btn btn--without-border">Kontakt</a></li>
        </ul>
    </nav>