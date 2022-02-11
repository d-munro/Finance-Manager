package finance;

/**
 * Creates an exception which indicates that a transaction could not be found 
 *
 * @author Dylan Munro
 */
public class TransactionNotFoundException extends Exception {
    
    /**
     * Creates a TransactionNotFoundException with the message "The transaction could not be found"
     */
    public TransactionNotFoundException() {
        this("The transaction could not be found");
    }
  
    /**
     * Creates a TransactionNotFoundException indicating that the associated 
     * transaction number does not exist
     * 
     * @param transactionNumber The unrecognized transaction number
     */
    public TransactionNotFoundException(int transactionNumber) {
        this("Transaction number" + transactionNumber + " could not be found");
    }
 
    /**
     * Creates a TransactionNotFoundException with a custom message
     *
     * @param message The message describing the exception
     */
    public TransactionNotFoundException(String message) {
        super(message);
    }  
}
