<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 04.05.2023
  Time: 05:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Изменение комнаты</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h2>ID комнаты: ${room.id}</h2>
<h2>Номер комнаты: ${room.number}</h2>
<h2>Количество мест в номере: ${room.places}</h2>
<h2>Класс номера: ${room.classRoom}</h2>
<h2>Статус номера: ${room.statusRoom}</h2>

<form action="${pageContext.request.contextPath}/room-edit" method="post">
    <label for="numberId">Номер комнаты:</label><br>
    <input type="text" id="numberId" name="number"><br>

    <label for="placesId">Количество мест:</label><br>
    <input type="text" id="placesId" name="places"><br><br>

    <label for="classRoomId">Класс комнаты:</label><br>
    <select name="classRoom" id="classRoomId">
        <option value="STANDARD">STANDARD</option>
        <option value="SUITE">SUITE</option>
    </select>

    <label for="statusRoomId">Статус комнаты:</label><br>
    <select name="statusRoom" id="statusRoomId">
        <option value="FREE">FREE</option>
        <option value="NOT_FREE">NOT FREE</option>
    </select>

    <input type="hidden" name="id" value="${room.id}">
    <input type="submit" value="Изменить">
</form>

<c:if test="${ param.error == true}">
    Комната не изменена
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
