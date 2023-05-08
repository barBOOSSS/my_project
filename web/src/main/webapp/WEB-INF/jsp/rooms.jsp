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
</form>

<c:forEach var="room" items="${requestScope.rooms}">
    <h2>ID комнаты: ${room.id}</h2>
    <h2>Номер комнаты: ${room.number}</h2>
    <h2>На сколько мест номер: ${room.places}</h2>
    <h2>Класс номера: ${room.classRoom}</h2>
    <h2>Статус номера: ${room.statusRoom}</h2>
    <h2><a href=${pageContext.request.contextPath}/rooms?id=${room.id}>Показать номер</a></h2>
</c:forEach>


<form action="${pageContext.request.contextPath}/rooms" method="get">

    <div>
        <label for="page">Номер страницы:</label>
        <input type="text" title="" id="page" name="page" value="${param.page}">
    </div>
    <div>
        <label for="limit">Количество на странице:</label>
        <input type="text" title="" id="limit" name="limit" value="${param.limit}">
    </div>
    <div>
        <label for="places">Количество мест в комнате:</label>
        <input id="places" type="text" title="" name="places" value="${param.places}">
    </div>
    <div>
        <label for="classRoomId">Класс комнаты:</label><br>
        <select name="classRoom" id="classRoomId">
            <option value="STANDARD">STANDARD</option>
            <option value="SUITE">SUITE</option>
        </select>
    </div>
    <div>
        <label for="statusRoomId">Статус комнаты:</label><br>
        <select name="statusRoom" id="statusRoomId">
            <option value="FREE">FREE</option>
            <option value="NOT_FREE">NOT FREE</option>
        </select>
    </div>

    <input type="submit" value="Поиск">
</form>

<%@ include file="footer.jsp" %>
</body>
</html>
