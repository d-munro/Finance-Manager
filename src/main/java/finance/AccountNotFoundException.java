/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package finance;

/**
 *
 * @author Dylan Munro
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() {
        this("The account could not be found");
    }
    
    public AccountNotFoundException(String message) {
        super(message);
    }
    
}
