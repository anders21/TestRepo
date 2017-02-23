package shoppingproject.domain;

/**
 * INFO221 Project
 * Customer Class models typical customer data
 * @author jamal572
 */

import java.util.Objects;
import net.sf.oval.constraint.Email;
import net.sf.oval.constraint.Length;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

public class Customer {
    
    @NotNull(message ="Username must be provided.")
    @NotBlank(message ="Username must be provided.")
    @Length(min=5, message="Username must contain at least 5 characters.")
    private String userName;
    
    @NotNull(message = "Name must be provided.")
    @NotBlank(message="Name must be provided.")
    @Length(min=2, message="Name must contain at least two characters.")
    private String name;
    
    @NotNull(message = "CreditCard details must be provided.")
    @NotBlank(message="CreditCard details must be provided.")
    @Length(min=13, message="CreditCard detals must contain at least 13 characters.")
    private String creditCardDetails;
    
    @NotNull(message = "Password must be provided.")
    @NotBlank(message="Password must be provided.")
    @Length(min=9, message="Password must contain at least 9 characters.")
    private String password;
    
    @NotNull(message = "Shipping address must be provided.")
    @NotBlank(message="Shipping address must be provided.")
    @Length(min=20, message="Shipping address must contain at least 20 characters.")
    private String shippingAddress;
    
    @NotNull(message = "Email must be provided.")
    @NotBlank(message="Email must be provided.")
    @Length(min=15, message="Email must contain at least two characters.")
    @Email(message = "Email is not properly formatted")
    private String email;

    /**
     * Construct for creating a Object model of a customer
     * @param userName 
     * @param name
     * @param creditCardDetails
     * @param password 
     */
    public Customer(String userName, String name, String creditCardDetails, String password) {
        this.userName = userName;
        this.name = name;
        this.creditCardDetails = creditCardDetails;
        this.password = password;
    }
    
    /**
     * Construct for creating a Object model of a customer
     * @param userName
     * @param name
     * @param creditCardDetails
     * @param password
     * @param shippingAddress
     * @param email
     */
    public Customer(String userName, String name, String creditCardDetails, String password, String shippingAddress, String email) {
        this.userName = userName;
        this.name = name;
        this.creditCardDetails = creditCardDetails;
        this.password = password;
        this.shippingAddress = shippingAddress;
        this.email = email;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
