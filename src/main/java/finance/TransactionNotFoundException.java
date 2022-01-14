package finance;

/**
 *
 * @author Dylan Munro
 */
public class TransactionNotFoundException extends Exception {

    public TransactionNotFoundException() {
        this("The transaction could not be found");
    }
    
    public TransactionNotFoundException(long transactionId) {
        this("The transaction associated with the id " + transactionId
            + " could not be found");
    }
    
    public TransactionNotFoundException(String message) {
        super(message);
    }  
}
