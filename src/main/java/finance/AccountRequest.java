package finance;

/**
 * Generates an AccountRequest object which can be executed to modify an account. 
 * The AccountRequest object contains all relevant details about the account
 * @author Dylan Munro
 */
public class AccountRequest extends Request{

    private String accountName;
    
    private int id;
    
    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param accountName The name of the account
     *
     * @throws InvalidRequestException
     */        
    public AccountRequest(String action, String accountName) throws InvalidRequestException {
        super(action, true);
        this.accountName = accountName;
    }
    
    public AccountRequest(String action, int id) throws InvalidRequestException {
        super(action);
        this.id = id;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public int getId() {
        return id;
    }
}
