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
import shoppingproject.dao.JdbcConnector;
import shoppingproject.domain.Order;

/**
 *
 * @author jay
 */
public class OrderDao {

    private String url = "jdbc:h2:tcp://localhost:9420/project;IFEXISTS=TRUE";

    public OrderDao() {
    }

    public OrderDao(String url) {
        this.url = url;
    }

    public void generateReceipt(Integer orderID) {
        // get a connection to the database
        Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost:9420/project", "sa", "");
        // select from the view
        String sql = "select * from order_receipt where orderid=?";
        // execute the statement
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, orderID);
        ResultSet rs = stmt.executeQuery();
        // create the report using the result set as a data source
        JasperPrint report = JasperFillManager.fillReport("h:/report_folder/receipt.jasper", new HashMap<String, Object>(), new JRResultSetDataSource(rs));
        // display the report
        JasperViewer.viewReport(report);
        JasperExportManager.exportReportToPdfFile(report, "h:/report_folder/receipt.pdf");
        
        // clean up
        stmt.close;
        con.close();
    }

    public void save(Order order) {
               
    //create prepared sql statement to prevent SQL inject
        String preparedSQL = "insert into order ("
                + "date, customer) "
                + "values(?, ?, ?)";
        //Try connecting to database
        try (
            Connection dbConnect = JdbcConnector.getConnection(url);
                
            PreparedStatement stmt = dbConnect.prepareStatement(preparedSQL, 
                    Statement.RETURN_GENERATED_KEYS);
        ){
            
            
            stmt.setTimestamp(1, order.getDate());
            stmt.setString(2, order.getCustomer().getUserName());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
