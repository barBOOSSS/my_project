<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 01.05.2023
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список заказов</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h2>ID комнаты: ${room.id}</h2>
<h2>Номер комнаты: ${room.number}</h2>
<h2>Количество мест в номере: ${room.places}</h2>
<h2>Стоимость номера: ${room.price}</h2>
<h2>Класс номера: ${room.classRoom}</h2>
<h2>Статус номера: ${room.statusRoom}</h2>

<sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MANAGER')">
    <form action="${pageContext.request.contextPath}/rooms/update/${room.id}" method="get">
        <input type="submit" value="Изменить">
    </form>

<form action="${pageContext.request.contextPath}/rooms/${room.id}/delete" method="post">
    <input type="submit" value="Удалить">
</form>
</sec:authorize>

<c:if test="${param.error == true}">
    Комната не быда удалена
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
