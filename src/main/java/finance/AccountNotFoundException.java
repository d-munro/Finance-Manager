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
