<%-- 
    Document   : cart
    Created on : 12/09/2016, 5:12:29 PM
    Author     : jamal572
--%>
<%@page import="shoppingproject.domain.Order"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="shoppingproject.domain.OrderItem"%>
<%
     Order sale;
     List<OrderItem> orderList;
    if (session.getAttribute("order") != null) {
        sale = (Order)session.getAttribute("order");
        orderList = sale.getOrderItems();
    } else {
        sale = null;
        orderList = new ArrayList<>();
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link rel="stylesheet" href="./style.css" type="text/css">    
   </head>
    <body>
        <%@include file="/WEB-INF/jspf/nav.jspf"%>
        <h1>Shopping project Cart</h1>
        

        <%if (sale == null) {%>
            <p>Your Cart is empty</p>
        <%} else {%>

        <table>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
            </tr>
            
            <% for (OrderItem product : orderList) {%>
            <tr>
                <td><%= product.getProduct().getName()%></td>
                <td><%= product.getProduct().getDescription()%></td>
                <td><%= product.getProduct().getPrice()%></td>
                <td><%= product.getQuantityPurchased()%></td>
                <td><%= product.getItemTotal()%></td>
            </tr>
            <% }%>
        </table>
        
        <%}%>
        
        <form action="Checkout" method="POST">
        <button type="submit">Checkout Order</button>
        </form>
    </body>
</html>
