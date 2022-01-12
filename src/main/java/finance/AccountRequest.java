package finance;

/**
 * Generates an AccountRequest object which can be executed to modify an account. 
 * The AccountRequest object contains all relevant details about the account
 * @author Dylan Munro
 */
public class AccountRequest extends Request{

    private final String accountName;
    
    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param accountName The name of the account
     *
     * @throws InvalidInputException
     */        
    public AccountRequest(String action, String accountName) throws InvalidInputException {
        super(action, accountName);
        this.accountName = accountName;
    }
    
    public String getAccountName() {
        return accountName;
    }
}
