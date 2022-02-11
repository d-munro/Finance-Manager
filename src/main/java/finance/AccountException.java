package finance;

/**
 * Creates an exception indicating an error with an account
 *
 * @author Dylan Munro
 */
public class AccountException extends Exception {

     /**
     * Creates an AccountException with the message "The transaction could not be found"
     */
    public AccountException() {
        this("The account could not be found");
    }
    
    /**
     * Creates an AccountException with a custom message
     * 
     * @param message The message describing the exception
     */
    public AccountException(String message) {
        super(message);
    }
    
}
