/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import shoppingproject.dao.DAOException;
import shoppingproject.dao.JdbcConnector;
import shoppingproject.domain.Customer;
import shoppingproject.domain.Product;

/**
 *
 * @author jay
 */
public class CustomerDao {
    
    private String url = "jdbc:h2:tcp://localhost:9420/project;IFEXISTS=TRUE";
    
    public CustomerDao() {
    }
    
    public CustomerDao(String url){
        this.url = url;
    }

    
    public void save(Customer customer) {
        
    //create prepared sql statement to prevent SQL inject
        String preparedSQL = "insert into Customers ("
                + "userName, custName, creditCardDetails, email, shippingAddress, password) "
                + "values(?, ?, ?, ?, ?, ?)";

        //Try connecting to database
        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
                
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL, 
                    Statement.RETURN_GENERATED_KEYS);
        ){
            
            stmt.setString(1, customer.getUserName());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getCreditCardDetails());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getShippingAddress());
            stmt.setString(6, customer.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean login(String userCode, String password) {
        String preparedSQL = "select USERNAME, CUSTNAME, CREDITCARDDETAILS, EMAIL from CUSTOMERS where username=? and password=?";
        //Try connecting to database
        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
                
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL);
        ){
            
            stmt.setString(1, userCode);
            stmt.setString(2, password);
            ResultSet rs =  stmt.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Customer getCustomer(String userName) {
        String preparedSQL = "select * from Customers where userName=?";

        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
                
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL);
        ){
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
            //retrieve data from ResultSet
            String usrName = rs.getString("userName");
            String custName = rs.getString("custName");
            String creditCardDetails = rs.getString("creditCardDetails");
            String shippingAddress = rs.getString("shippingAddress");
            String email = rs.getString("email");
            
            //update product model with data
            return new Customer(usrName,custName, creditCardDetails, "", shippingAddress, email);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
