<%-- 
    Document   : index
    Created on : 12/09/2016, 5:04:59 PM
    Author     : jamal572
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="shoppingproject.dao.DbDao"%>
<%@page import="java.util.Collection"%>
<%@page import="shoppingproject.domain.Product"%>
<% 
    DbDao dao = new DbDao();
    ArrayList<Product> products = new ArrayList(dao.getProductsList());
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Shop</title>
        <link rel="stylesheet" href="./style.css" type="text/css">
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/nav.jspf"%>
        <h1>Shopping project index</h1>
        <div class="products">
        <% for(int i = 0; i < 3; i++){ %>
            <div class="product">
            <img src="./cat.png" alt="picture of cat">
            <p><%= products.get(i).getName()%></p>
            <p></p>
            </div>
        <% } %>
        </div>
        <p>We sell the biggest range of frobnostificators, widgets, and dookies at the best prices!</p>
    </body>
</html>

