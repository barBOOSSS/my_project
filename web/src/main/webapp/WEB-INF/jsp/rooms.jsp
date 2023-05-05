<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 01.05.2023
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список номеров</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h1> ${sessionScope.user.name}: ${sessionScope.user.role} </h1>

<form action="${pageContext.request.contextPath}/room-created" method="get">
    <input type="submit" value="Создать комнату">

<c:forEach var="room" items="${requestScope.rooms}">
    <h2>ID комнаты: ${room.id}</h2>
    <h2>Номер комнаты: ${room.number}</h2>
    <h2>На сколько мест номер: ${room.places}</h2>
    <h2>Класс номера: ${room.classRoom}</h2>
    <h2>Статус номера: ${room.statusRoom}</h2>
    <h2><a href=${pageContext.request.contextPath}/rooms?id=${room.id}>Показать номер</a></h2>
</c:forEach>

<%@ include file="footer.jsp" %>
</body>
</html>
