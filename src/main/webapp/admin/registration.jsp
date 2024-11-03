<%--
  Created by IntelliJ IDEA.
  User: amirkurmaev
  Date: 21.10.2024
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="<c:url value="/css/registration.css"/>" type="text/css"/>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Ubuntu+Condensed&display=swap');
    </style>
</head>
<body>
<jsp:include page="../header.jsp"/>
<div class="registration-page">
    <h1>Регистрация нового пользователя</h1>
  <div class="form-registration">
    <c:if test="${not empty status}">
        <p>${status}</p>
    </c:if>
    <form action="registration" method="post">
        <label>Имя:</label><br>
        <input type="text" name="name" required><br>

        <label>Фамилия:</label><br>
        <input type="text" name="surname" required><br>

        <label>Отчество:</label><br>
        <input type="text" name="patronymic"><br>

        <label>Email:</label><br>
        <input type="email" name="email" required><br>

        <label>Пароль:</label><br>
        <input type="password" name="password" required><br>

        <label>Подтверждение пароля:</label><br>
        <input type="password" name="confirmation_password" required><br>

        <label>Номер телефона:</label><br>
        <input type="tel" name="phone_number" required><br>

        <label>Роль:</label><br>
        <select name="role" required>
            <option value="manager">Менеджер</option>
            <option value="admin">Администратор</option>
        </select><br><br>

        <button type="submit">Зарегистрировать</button>
    </form>
  </div>
</div>
</body>
</html>
