<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 04.05.2023
  Time: 03:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h1> ${sessionScope.user.name}: ${sessionScope.user.role} </h1>

<c:forEach var="user" items="${requestScope.users}">
    <h2>Имя: ${user.name}</h2>
    <h2>Фамилия: ${user.surname}</h2>
    <h2>email: ${user.email}</h2>
    <h2>Роль: ${user.role}</h2>
    <h2><a href=${pageContext.request.contextPath}/users?id=${user.id}>Показать пользователя</a></h2>
</c:forEach>

    <%@ include file="footer.jsp" %>
</body>
</html>
