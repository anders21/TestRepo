<%-- 
    Document   : products
    Created on : 12/09/2016, 4:37:06 PM
    Author     : jamal572
--%>

<%@page import="java.util.Collection"%>
<%@page import="shoppingproject.domain.Product"%>
<%@page import="shoppingproject.dao.DbDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Collection<Product> products;
    String noProducts = "Unfortunatly there are no products for this category";
    if (request.getQueryString() != null &&  !request.getParameter("category").isEmpty()) {
        products = new DbDao().getByCategory(request.getParameter("category"));
    } else {
        products = new DbDao().getProductsList();
    }
    
    Collection<String> categories = new DbDao().getCategoryList();
%>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <link rel="stylesheet" href="./style.css" type="text/css">
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/nav.jspf" %>
        <h1>Shopping project products</h1>
        
        <div id="categories">
        <span>Categories :</span>
            <ul>
                <% for (String category : categories) { %>
                <li><a href="?category=<%=category.toString()%>"> <%= category.toString() %></a></li>
                <% } %>

            </ul>
        </div>
        <div id="products">
            <form action="Buy" method="GET">
        <% if(products.size() > 0) {%>
            <table>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Available</th>
            </tr>
            
            <% for (Product product : products) {%>
            <tr>
                <td><%= product.getName()%></td>
                <td><%= product.getDescription()%></td>
                <td><%= product.getPrice()%></td>
                <td><%= product.getQuantityInStock()%></td>
                <td><button type="submit" name="addProduct" value="<%= product.getProductID()%>">Buy</button></td>
            </tr>
            <% }%>
        </table>
         </form>
            <%} else { %>
                <p><%= noProducts%></p>
            <% } %>
            
        </div>
    </body>
</html>
