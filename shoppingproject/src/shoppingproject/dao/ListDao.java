package shoppingproject.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import shoppingproject.domain.Product;

/**
 *ListDAO implements IDAO interface and uses collections based for runtime 
 * storage and doesn't provide permanent persistence
 * @author jay
 */
public class ListDao implements IDAO{
    private static final Logger LOGGER = Logger.getLogger(ListDao.class.getName());
    
    private static Collection<Product> productsList = new TreeSet<>();

    private static Collection<String> categoryList = new TreeSet<>();

    public ListDao() {
    }
    
    //Read multiple
    @Override
    public Collection<Product> getProductsList() {
        return productsList;
    }
    
    //Read multiple
    @Override
    public Collection<String> getCategoryList() {
        return categoryList;
    }
    
    @Override
    public Collection<Product> getByCategory(String category){
        Collection<Product> productsFiltered = new ArrayList();
        Iterator<Product> iterProduct = productsList.iterator();
        
        while (iterProduct.hasNext()) {
            Product next = iterProduct.next();
            if (next.getCategory().equals(category)) {
                productsFiltered.add(next);
            }
        }
        return productsFiltered;
    }
    
    //Create
    @Override
    public void addProduct(Product product) {
        productsList.add(product);
        categoryList.add(product.getCategory());
    }
    
    //Read
    @Override
    public Product getProduct(Integer productID){
        Iterator<Product> iterProduct = productsList.iterator();
        Product returnProduct = null;
        
        while (iterProduct.hasNext()) {
            returnProduct = iterProduct.next();
            if(returnProduct.getProductID().equals(productID)){
                return returnProduct;
            }
        }
        return null;
    }
    
    //Update
    @Override
    public void updateProduct(Product product){
        Iterator<Product> iterProduct = productsList.iterator();
        
        while (iterProduct.hasNext()) {
            Product next = iterProduct.next();
            if (next.equals(product)) {
                next.setName(product.getName());
                next.setDescription(product.getDescription());
                next.setCategory(product.getCategory());
                next.setPrice(product.getPrice());
                next.setQuantityInStock(product.getQuantityInStock());
                
            }
        }
    }
    
    //Delete
    @Override
    public void deleteProduct(Product product){
        try {
            productsList.remove(product);
            categoryList.remove(product.getCategory());
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }       
    }
}
