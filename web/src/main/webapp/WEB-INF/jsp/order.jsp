<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 10.04.2023
  Time: 19:05
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

    <h2>ID заказа: ${order.id}</h2>
    <h2>Пользователь: ${order.user}</h2>
    <h2>На сколько мест номер: ${order.places}</h2>
    <h2>Класс номера: ${order.classRoom}</h2>
    <h2>Статус заказа: ${order.statusOrder}</h2>
    <h2>Решение: ${order.solution} </h2>

<%@ include file="footer.jsp" %>
</body>
</html>
