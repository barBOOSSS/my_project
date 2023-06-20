<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 04.05.2023
  Time: 00:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создание новой комнаты</title>
</head>
<body>
<%@ include file="header.jsp" %>

<form action="${pageContext.request.contextPath}/room-created" method="post">
    <label for="numberId">Номер комнаты:</label><br>
    <input type="text" id="numberId" name="number"><br>

    <label for="placesId">Количество мест:</label><br>
    <input type="text" id="placesId" name="places"><br><br>

    <label for="pricedId">Стоимость номера в сутки:</label><br>
    <input type="text" id="pricedId" name="price"><br><br>

    <label for="classRoomId">Класс комнаты:</label><br>
    <select name="classRoom" id="classRoomId">
        <option value="STANDARD">STANDARD</option>
        <option value="SUITE">SUITE</option>
    </select>
    <input type="submit" value="Создать">
</form>

<c:if test="${ param.error == true}">
    Комната не создана
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
