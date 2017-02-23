<%-- 
    Document   : registerCustomer
    Created on : 5/09/2016, 4:56:19 PM
    Author     : jamal572
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <link rel="stylesheet" href="./style.css" type="text/css">
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/nav.jspf" %>
        <h1>Shopping project Register Customer</h1>
        <% if (request.getQueryString() != null && request.getParameter("status").equals("fail")) {%>
        <p>There was an error processing your form (user name already exists), try a different username</p>
        <% } %>
        <div> 
            <form action="Registration" method="POST">
                <label for="userCode">User Code</label>
                <input type="text" name="userCode" id="userCode">
                <br>
                <label for="name">Name</label>
                <input type="text" name="name" id="name">
                <br>
                <label for="email">Email</label>
                <input type="email" name="email" id="email">
                <br>
                <label for="userCode">Address</label>
                <textarea name="address" rows="5" id="address"></textarea>
                <br>
                <label for="creditCard">Credit Card</label>
                <input type="text" name="creditCard" id="creditCard">
                <br>
                <label for="password">password</label>
                <input type="password" name="password" id="password">
                <br>
                <input type="submit">
            </form>
        </div>
    </body>
</html>
