/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import dao.RegistrationValidationException;

import shoppingproject.gui.helpers.ValidationHelper;

import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
/**
 *
 * @author jay
 */
public class FormValidationHelper extends ValidationHelper{
    
    public void registrationValid(Object domain) throws RegistrationValidationException {
        // create Oval validator
        Validator validator = new Validator();

        // validate the object
        List<ConstraintViolation> violations = validator.validate(domain);

        // were there any violations?
        if (!violations.isEmpty()) {
            // yes, so show constraint messages to user
            StringBuilder message = new StringBuilder();

            //	loop through the violations extracting the message for each
            for (ConstraintViolation violation : violations) {
                
                message.append("<p>").append(violation.getMessage()).append("</p>");
            }
            throw new RegistrationValidationException(message.toString());
        }
    }
}
