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
<h2>Класс номера: ${room.classRoom}</h2>
<h2>Статус номера: ${room.statusRoom}</h2>

<form action="${pageContext.request.contextPath}/room-edit" method="get">
    <input type="submit" value="Изменить">

    <form action="${pageContext.request.contextPath}/rooms" method="post">
        <input type="hidden" name="id" value="${room.id}">
        <input type="submit" value="Удалить">

        <c:if test="${ param.error == true}">
        Комната не удалена
        </c:if>

        <%@ include file="footer.jsp" %>
</body>
</html>
