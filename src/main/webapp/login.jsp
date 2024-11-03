<%--
  Created by IntelliJ IDEA.
  User: amirkurmaev
  Date: 20.10.2024
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Авторизация</title>
    <link rel="stylesheet" href="<c:url value="/css/login.css"/>" type="text/css"/>
</head>
<body>
<div class="login-page">
    <h1>Вход в систему</h1>
    <div class="login-card">
        <c:if test="${not empty error}">
            <p>${error}</p>
        </c:if>
        <div class="form-login">
            <form action="login" method="post">
                <label for="username">Логин:</label>
                <input type="email" id="username" name="username" required><br><br>
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required><br><br>
                <button type="submit">Войти</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
