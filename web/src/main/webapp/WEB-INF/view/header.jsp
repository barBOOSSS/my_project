<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 09.04.2023
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul class="nav justify-content-center">
    <li class="nav-item">
        <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/main">Главная</a>
    </li>
    <li class="nav-item">
        <c:if test="${sessionScope.SPRING_SECURITY_CONTEXT != null }">
            <form action="/logout" method="post">
                <input type="submit" value="Logout">
            </form>
        </c:if>
    </li>
    <%--    <li class="nav-item">--%>
    <%--        <a class="nav-link" href="#">Link</a>--%>
    <%--    </li>--%>
    <%--    <li class="nav-item">--%>
    <%--        <a class="nav-link disabled">Disabled</a>--%>
    <%--    </li>--%>
</ul>

<%--<h1>Меню <a href=${pageContext.request.contextPath}/main>Главная</a></h1>--%>

<%--<c:if test="${sessionScope.SPRING_SECURITY_CONTEXT != null }">--%>
<%--    <form action="/logout" method="post">--%>
<%--    <input type="submit" value="Logout">--%>
<%--</c:if>--%>
</form>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
