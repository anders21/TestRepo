package shoppingproject.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * INFO221 Project
 * Order Class that models a Customer Order
 * @author jamal572
 */
public class Order {
    private Integer ID;
    private Date date;
    private List<OrderItem> orderItems;
    private final Customer customer;
    
    public Order(Customer customer) {
        this.orderItems = new ArrayList<>();
        this.customer = customer;
    } 
    
    /**
     * Constructor creates an instance of an order with Customer and OrderItem
     * information
     * @param ID
     * @param date 
     * @param customer 
     * @param orderItems 
     */
    public Order(Integer ID, Date date, Customer customer, List<OrderItem> orderItems) {
        this.ID = ID;
        this.date = date;
        this.customer = customer;
        this.orderItems = orderItems;
    }

    public Integer getID() {
        return this.ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal(){
        return this.orderItems.size();
    }
    
    public void addItem(OrderItem item){
        this.orderItems.add(item);
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    
}
