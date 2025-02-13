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
<h2>Пользователь: ${order.userName} ${order.userSurname}</h2>
<h2>Номер комнаты: ${order.numberRoom}</h2>
<h2>Сумма заказа: ${order.price}</h2>
<h2>Статус заказа: ${order.statusOrder}</h2>
<h2>Решение: ${order.solution} </h2>

<sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MANAGER')">
    <c:if test="${order.statusOrder == 'NEW'}">
        <form action="${pageContext.request.contextPath}/orders/update/${order.id}" method="get">
            <input type="submit" value="Обработать">
        </form>
    </c:if>
    <c:if test="${order.statusOrder == 'CLOSE'}">
        <form action="${pageContext.request.contextPath}/orders/${order.id}/delete" method="post">
            <input type="submit" value="Удалить">
        </form>
    </c:if>
</sec:authorize>

<c:if test="${param.error == true}">
    Заказ не удален
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
