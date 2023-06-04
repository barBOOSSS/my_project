<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 04.05.2023
  Time: 03:48
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

<h2>Имя: ${user.name}</h2>
<h2>Фамилия: ${user.surname}</h2>
<h2>email: ${user.email}</h2>
<h2>Роль: ${user.role}</h2>
<h2>Адрес: ${user.address.city}, ${user.address.street}, ${user.address.building}, ${user.address.flat}</h2>
<h2>Номер паспорта: ${user.passport.number}</h2>

<form action="${pageContext.request.contextPath}/user-edit" method="get">
    <input type="hidden" name="id" value="${user.id}">
    <input type="submit" value="Изменить">
</form>

<form action="${pageContext.request.contextPath}/users" method="post">
    <input type="hidden" name="id" value="${user.id}">
    <input type="submit" value="Удалить">
</form>

<c:if test="${ param.error == true}">
    Пользователь не удален
    </c:if>

    <%@ include file="footer.jsp" %>
</body>
</html>
