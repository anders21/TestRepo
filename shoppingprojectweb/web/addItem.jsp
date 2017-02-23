<%-- 
    Document   : addToCart
    Created on : 2/10/2016, 5:56:56 PM
    Author     : jay
--%>
<%@page import="shoppingproject.domain.OrderItem"%>
<%@page import="java.util.List"%>
<%@page import="shoppingproject.domain.Product"%>
<%@page import="java.util.Collection"%>
<%@page import="shoppingproject.dao.DbDao"%>
<%
    Product product;
    List<OrderItem> orderList;
    
    if (request.getQueryString() != null &&  !request.getParameter("productID").isEmpty()) {
        Integer productID = Integer.parseInt(request.getParameter("productID"));
        product = new DbDao().getProduct(productID);
    } else {
        product = null;
    }
    
    Collection<String> categories = new DbDao().getCategoryList();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./style.css" type="text/css">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/nav.jspf" %>
        <h1>Shopping project add product</h1>
        
        <div id="products">    
            <%if (product == null) {%>
            <p>Invalid product</p>
            <%} else {%>
            <table>
                <tr><td>ID:</td> <td><%= product.getName()%></td></tr>
                <tr><td>Description:</td> <td><%= product.getDescription()%></td></tr>
                <tr><td>Price:</td> <td><%= product.getPrice()%></td></tr>
                <tr><td>Quantity available:</td> <td><%= product.getQuantityInStock()%></td></tr>
            <% }%>
        </table>
        </div>
        
        <div>
            <form action="AddToCart" method="POST"> 
                <input type="text" name="quantity">
                <button type="submit" name="productID" value="<%= product.getProductID()%>">Add to cart</button> 
            </form>
        </div>
        
    </body>
</html>
