<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 04.05.2023
  Time: 03:35
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

<form action="${pageContext.request.contextPath}/order-created" method="post">

  <label for="userId">Id пользователя:</label><br>
  <input type="text" id="userId" name="user"><br><br>

  <label for="pricedId">Сумма заказа:</label><br>
  <input type="text" id="pricedId" name="price"><br><br>

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

  <input type="submit" value="Создать">
</form>

<c:if test="${ param.error == true}">
  Комната не создана
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
