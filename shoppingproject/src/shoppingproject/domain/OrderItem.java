package shoppingproject.domain;

/**
 * INFO221 Project
 * OrderItem Class that models a List if Products be as an order item
 * @author jamal572
 */
public class OrderItem {

    private Double quantityPurchased;
    private Double purchasePrice;
    private final Product product;
    private final Order order;
    
    /**
     * Creates and order item object from details of a product object
     * @param quantityPurchased
     * @param purchasePrice 
     * @param product 
     * @param order 
     */
    public OrderItem(Double quantityPurchased, Double purchasePrice, Product product, Order order) {
        this.quantityPurchased = quantityPurchased;
        this.purchasePrice = purchasePrice;
        this.product = product;
        this.order = order;
    }
    
    public double getItemTotal() {
        return this.quantityPurchased * this.purchasePrice;
    }

    public Double getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(Double quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Product getProduct() {
        return product;
    }
    
    

}
