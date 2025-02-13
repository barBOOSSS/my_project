<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 13.04.2023
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>==Отель==</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h1> Пользователь: ${user.get().name} ${user.get().surname}</h1>
<h1> Роль: ${user.get().role}</h1>

<h2><a href=${pageContext.request.contextPath}/rooms>1 -Номера</a></h2>
<h2><a href=${pageContext.request.contextPath}/orders>2 - Заказы</a></h2>

<sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MANAGER')">
    <h2><a href=${pageContext.request.contextPath}/users>3 - Пользователи</a></h2>
</sec:authorize>


<%@ include file="footer.jsp" %>
</body>
</html>
