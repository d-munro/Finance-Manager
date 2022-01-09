//TODO - Refactor Constructor so only action and data is required

package finance;

/**
 *
 * @author Dylan Munro
 */
public class TransactionRequest extends Request{

    private Transaction data;
    
    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param args The transaction which the action is acting upon
     * @param data The transaction being acted upon
     *
     * @throws InvalidInputException
     */        
    public TransactionRequest(String action, String args, Transaction data) throws InvalidInputException {
        super(action, args);
        this.data = data;
    }
    
    public Transaction getData() {
        return data;
    }
}
