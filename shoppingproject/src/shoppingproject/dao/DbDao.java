/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import shoppingproject.domain.Product;

/**
 *
 * @author jamal572
 */
public class DbDao implements IDAO {
    
    private String url = "jdbc:h2:tcp://localhost:9420/project;IFEXISTS=TRUE";
    
    public DbDao() {
    }
    
    public DbDao(String url){
        this.url = url;
    }

    @Override
    public Collection<Product> getProductsList() {
        //create prepared sql statement to prevent SQL inject
        String preparedSQL = "select * from Products";
        
        //Temporary storage
        List<Product> products = new ArrayList<>();
        
        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
            
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL);
        ) {
            ResultSet rs = stmt.executeQuery();
            //List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Integer productID = rs.getInt("productID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                Double quantityInStock = rs.getDouble("quantityInStock");
                
                Product productToAdd = new Product(
                        productID, 
                        name, 
                        description, 
                        category, 
                        price, 
                        quantityInStock);
                products.add(productToAdd);
            }
            
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return products;
    }

    @Override
    public Collection<String> getCategoryList() {
        String preparedSQL = "select distinct category from Products order by category";
        List<String> temporyStorage = new ArrayList<>();
        try (
            Connection dbconnect = JdbcConnector.getConnection(url);
            
            PreparedStatement stmt = dbconnect.prepareStatement(preparedSQL);
            ) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                temporyStorage.add(rs.getString("category"));
            }
            
        } catch (SQLException e){
            throw new DAOException(e.getMessage(), e);
        }
        
        return temporyStorage;
    }
    
        @Override
    public Collection<Product> getByCategory(String category){
        Collection<Product> productsFiltered = new ArrayList();
        String preparedSQL = "select * from Products where category=?";
        
        //try connecting to database
        try(
              Connection dbconnect = JdbcConnector.getConnection(url);
              
              PreparedStatement stmt = dbconnect.prepareStatement(preparedSQL);
                
            ) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Integer productID = rs.getInt("productID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String rcategory = rs.getString("category");
                Double price = rs.getDouble("price");
                Double quantityInStock = rs.getDouble("quantityInStock");
                
                Product productToAdd = new Product(
                        productID, 
                        name, 
                        description, 
                        rcategory, 
                        price, 
                        quantityInStock);
               
                productsFiltered.add(productToAdd);
            }
            
        } catch (SQLException e){
            throw new DAOException(e.getMessage(), e);
        }
        return productsFiltered;
    }

    @Override
    public void addProduct(Product product) {
        //create prepared sql statement to prevent SQL inject
        String preparedSQL = "merge into Products ("
                + "productID, name, description, category, price, quantityInStock) "
                + "values(?, ?, ?, ?, ?, ?)";
        
        //Try connecting to database
        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
                
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL);
        ){
            stmt.setInt(1, product.getProductID());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setDouble(5, product.getPrice());
            stmt.setDouble(6, product.getQuantityInStock());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public Product getProduct(Integer productID) {
        String preparedSQL = "select * from Products where productID=?";
        Product returnProduct = new Product();
        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
                
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL);
        ){
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
            //retrieve data from ResultSet
            Integer productId = rs.getInt("productID");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String category = rs.getString("category");
            Double price = rs.getDouble("price");
            Double quantityInStock = rs.getDouble("quantityInStock");
            
            //update product model with data
            returnProduct.setProductID(productId);
            returnProduct.setName(name);
            returnProduct.setDescription(description);
            returnProduct.setCategory(category);
            returnProduct.setPrice(price);
            returnProduct.setQuantityInStock(quantityInStock);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return returnProduct;
    }

    @Override
    public void updateProduct(Product product) {
        /*
        Not sure if a specific implementation for updateProduct() is needed
        since addProduct() uses SQL merge instead of insert. For now in order to
        DRY up the code we resuse addProduct to do the updates of product for us
        */
        addProduct(product);
    }

    @Override
    public void deleteProduct(Product product) {
        String preparedSQL = "delete from Products where productID = ?";
        
        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
                
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL);
        ){
            stmt.setInt(1, product.getProductID());
            stmt.executeUpdate();         
        } catch (SQLException e){
            throw new DAOException(e.getMessage(), e);
        }
    }
    
    

}
