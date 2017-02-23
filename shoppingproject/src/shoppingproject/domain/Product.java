/**
 * INFO221 Project
 * Product Class that models a Products details
 * @author jamal572
 */
package shoppingproject.domain;

import java.util.Objects;
import net.sf.oval.constraint.Length;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class Product implements Comparable<Product>{

    private Integer productID;
    
    @NotNull(message = "Name must be provided.")
    @NotBlank(message="Name must be provided.")
    @Length(min=2, message="Name must contain at least two characters.")
    private String name;
    
    @NotNull(message = "Descript must be provided.")
    @NotBlank(message="Descript must be provided.")
    @Length(min=2, message="Descript must contain at least 50 characters.")
    private String description;
    
    @NotNull(message = "Category must be provided.")
    @NotBlank(message="Category must be provided.")
    @Length(min=2, message="Category must contain at least two characters.")
    private String category;
    
    @NotNull(message = "Price must be provided.")
    @NotNegative(message = "Price must be a positive number.")
    private Double price;
    
    @NotNull(message = "Quantity must be provided.")
    @NotNegative(message = "Quantity must be a positive number.")
    private Double quantityInStock;

    /**
     * Creates a product object with passed in parameters
     * @param productID
     * @param name
     * @param description
     * @param category
     * @param price
     * @param quantityInStock 
     */
    public Product(Integer productID, String name, String description, String category, Double price, Double quantityInStock) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }    
    
    public Product(){}
    
    public Integer getProductID() {
        return productID;
    } 

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Double quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "productID:" + productID + " name:" + name + " Price:" + price; //+ " Category:"+ category;
    }

    @Override
    public int compareTo(Product compareProduct) {
        Integer thisID = this.productID;
        Integer thatId = compareProduct.getProductID();
        return thisID.compareTo(thatId);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }
    
    
}
