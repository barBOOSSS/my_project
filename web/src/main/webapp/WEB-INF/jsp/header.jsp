<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 09.04.2023
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Меню <a href=${pageContext.request.contextPath}/orders>Главная</a></h1>

    <c:if test="${ sessionScope.user != null }">
        <form action="${pageContext.request.contextPath}/logout" method="get">
        <input type="submit" value="Logout">
    </c:if>

</form>
