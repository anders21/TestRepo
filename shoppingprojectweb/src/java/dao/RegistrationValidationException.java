/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author jay
 */
public class RegistrationValidationException extends RuntimeException {
    
    public RegistrationValidationException() {
    }

    public RegistrationValidationException(String reason) {
        super(reason);
    }
}
