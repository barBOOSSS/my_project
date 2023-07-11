<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 04.05.2023
  Time: 06:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Изменение заказа</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h2>ID заказа: ${order.id}</h2>
<h2>Пользователь: ${order.user}</h2>
<h2>Сумма заказа: ${order.price}</h2>
<h2>Статус заказа: ${order.statusOrder}</h2>
<h2>Решение: ${order.solution} </h2>

<form action="${pageContext.request.contextPath}/orders/update/${order.id}" method="post">

    <label for="pricedId">Сумма заказа:</label><br>
    <input type="text" id="pricedId" name="price" value="${order.price}"><br>

    <label for="statusOrderId">Статус заказа:</label><br>
    <select name="statusOrder" id="statusOrderId">
        <option value="NEW">NEW</option>
        <option value="CLOSE">CLOSE</option>
    </select>

    <label for="solutionId">Решение:</label><br>
    <select name="solution" id="solutionId">
        <option value="APPROVED">APPROVED</option>
        <option value="DENIED">DENIED</option>
        <option value="UNPROCESSED">UNPROCESSED</option>
    </select>

    <input type="submit" value="Изменить">
</form>

<c:if test="${param.error == true}">
    Заказ не изменен
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
