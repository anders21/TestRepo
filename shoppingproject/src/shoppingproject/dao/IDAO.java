package shoppingproject.dao;

import java.util.Collection;
import java.util.logging.Level;
import shoppingproject.domain.Product;

/**
 * INFO221 Project
 * DAO interface
 * @author jamal572
 */
public interface IDAO {
    
    //Read multiple
    public Collection<Product> getProductsList();
    
    //Read multiple
    public Collection<String> getCategoryList();
    
    //Read multiple
    public Collection<Product> getByCategory(String category);

    //Create
    public void addProduct(Product product);
    
    //Read
    public Product getProduct(Integer productID);
    
    //Update
    public void updateProduct(Product product);
    
    //Delete
    public void deleteProduct(Product product);
}
