/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoppingproject.dao;

/**
 *
 * @author jay
 */
public class DAOException extends RuntimeException {

    public DAOException() {
    }

    public DAOException(String reason, Throwable cause) {
        super(reason, cause);
    }
    
}
