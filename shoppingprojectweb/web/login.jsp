<%-- 
    Document   : login
    Created on : 12/09/2016, 4:48:13 PM
    Author     : jamal572
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="./style.css" type="text/css">
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/nav.jspf" %>
        <h1>This is the login page</h1>
        <form action="Login" method="POST">
            <label for="userCode" >User Code</label>
            <input type="text" name="userCode" id="userCode">
            <br>
            <label for="userCode" >Password</label>
            <input type="password" name="password" id="password">
            <br>
            <input type="submit">
        </form>
    </body>
</html>
