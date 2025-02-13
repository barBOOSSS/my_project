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

<c:if test="${room.userSurname != null}">
    <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MANAGER')">
        <h2>Заказан клинтом: ${room.userName} ${room.userSurname}</h2>
    </sec:authorize>
</c:if>

<sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MANAGER')">
    <form action="${pageContext.request.contextPath}/rooms/update/${room.id}" method="get">
        <input type="submit" value="Изменить">
    </form>

    <form action="${pageContext.request.contextPath}/rooms/${room.id}/delete" method="post">
        <input type="submit" value="Удалить">
    </form>
</sec:authorize>


<sec:authorize access="hasAuthority('USER')">
    <c:if test="${room.statusRoom == 'FREE'}">
    <form action="${pageContext.request.contextPath}/orders/create" method="post">
            <%--    <label for="userId">Id пользователя:</label><br>--%>
        <input type="hidden" name="userId" value=${user.get().id}>
            <%--    <label for="pricedId">Сумма заказа:</label><br>--%>
        <input type="hidden" name="price" value=${room.price}>
            <%--    <label for="statusOrderId">Статус заказа:</label>--%>
        <input type="hidden" name="statusOrder" value="NEW">
            <%--    <label for="solutionId">Решение:</label><br>--%>
        <input type="hidden" name="solution" value="UNPROCESSED">
            <%--    <label for="numberId">Номер комнаты:</label><br>--%>
        <input type="hidden" name="number" value=${room.number}>
            <%--        <label for="placesId">Количество мест:</label><br>--%>
        <input type="hidden" name="places" value=${room.places}>
            <%--        <label for="priceId">Стоимость номера:</label><br>--%>
        <input type="hidden" name="price" value=${room.price}>
            <%--        <label for="classRoomId">Класс комнаты:</label><br>--%>
            <%--        <select name="classRoom" id="classRoomId">--%>
            <%--            <option value="STANDARD">STANDARD</option>--%>
            <%--            <option value="SUITE">SUITE</option>--%>
            <%--        </select>--%>
        <input type="hidden" name="classRoom" value=${room.classRoom}>
            <%--        <label for="statusRoomId">Статус комнаты:</label><br>--%>
            <%--        <select name="statusRoom" id="statusRoomId">--%>
            <%--            <option value="FREE">FREE</option>--%>
            <%--            <option value="NOT_FREE">NOT FREE</option>--%>
            <%--        </select>--%>
        <input type="hidden" name="statusRoom" value="NOT_FREE">
        <input type="hidden" name="numberRoom" value=${room.number}>
        <input type="submit" value="Заказать">
    </form>
    </c:if>
</sec:authorize>

<c:if test="${param.error == true}">
    Комната не быда удалена
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
