<%-- 
    Document   : invalidInput
    Created on : 26/09/2016, 12:28:15 AM
    Author     : jay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./style.css" type="text/css">
        <title>Error</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/nav.jspf"%>
        <h1>Oops. Something unexpected happened.</h1>
        <p><%=request.getAttribute("javax.servlet.error.message")%></p>
        <a href="javascript:history.back()">Back</a>
    </body>
</html>
