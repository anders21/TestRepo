package shoppingproject;

import javax.swing.JOptionPane;
import shoppingproject.dao.DAOException;
import shoppingproject.dao.DbDao;
import shoppingproject.dao.IDAO;
import shoppingproject.dao.ListDao;
import shoppingproject.gui.ProductAdmin;

/**
 * INFO221 Project
 * Main entry point of application
 * @author jamal572
 */
public class Main {
    
    private static final IDAO PRODUCTLIST = new DbDao();
    
    /**
     * @param args the command line arguments
     * command line arguments are not required
     */
    public static void main(String[] args) {
        try {
        //Test connection to database by tryying to retrieve a list of products
        PRODUCTLIST.getProductsList();
        
         //Main Frame of application
        ProductAdmin adminFrame = new ProductAdmin(PRODUCTLIST);
        
        adminFrame.setLocationRelativeTo(null);
        
        adminFrame.setVisible(true);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Databse connection Error", JOptionPane.ERROR_MESSAGE);
        }
        

    }
    
}
