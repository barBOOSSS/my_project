<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 09.04.2023
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<h1>Меню <a href=${pageContext.request.contextPath}/main>Главная</a></h1>

    <c:if test="${ sessionScope.SPRING_SECURITY_CONTEXT != null }">
        <form action="/logout" method="post">
        <input type="submit" value="Logout">
    </c:if>

</form>
