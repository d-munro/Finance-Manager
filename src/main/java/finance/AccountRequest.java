//TODO - Refactor Constructor so only action and data is required

package finance;

/**
 *
 * @author Dylan Munro
 */
public class AccountRequest extends Request{

    private Account data;
    
    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param args The account which the action is acting upon
     * @param data The account being acted upon
     *
     * @throws InvalidInputException
     */        
    public AccountRequest(String action, String args, Account data) throws InvalidInputException {
        super(action, args);
        this.data = data;
    }
    
    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param args Specifies the object or method describing what the action is
     * performed on
     * @param accountName The name of the account
     *
     * @throws InvalidInputException
     */        
    public AccountRequest(String action, String args, String accountName) throws InvalidInputException {
        super(action, args);
        this.data = new Account(accountName);
    }
    
    public Account getData() {
        return data;
    }
}
