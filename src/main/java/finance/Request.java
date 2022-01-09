//TODO
//Create subclasses for AccountRequest and TransactionRequest
//Complete request parsing for requests containing args
package finance;

//imports
import java.util.HashMap;

/**
 * Creates a Request object which determines if a user's request is valid
 *
 * @author Dylan Munro
 */
public class Request {

    public static final HashMap<String, String> ONE_PARAM_ACTION_DESCRIPTIONS;
    public static final HashMap<String, String> TWO_PARAM_ACTION_DESCRIPTIONS;
    private String action;
    private String args;
    private boolean isAccountRequest;
    private boolean isTransactionRequest;
    
    //Necessary for all operations involving transactions due to the number of arguments
    private Transaction transaction;

    //Static initialization of ONE_PARAM_ACTION_DESCRIPTIONS
    static {
        ONE_PARAM_ACTION_DESCRIPTIONS = new HashMap<String, String>();
        ONE_PARAM_ACTION_DESCRIPTIONS.put("help", "add a transaction to the current active account");
        ONE_PARAM_ACTION_DESCRIPTIONS.put("quit", "terminate the program");

        TWO_PARAM_ACTION_DESCRIPTIONS = new HashMap<String, String>();
        TWO_PARAM_ACTION_DESCRIPTIONS.put("add account", "make a new account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("add transaction", "add a transaction to the current active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("change", "change the active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("delete account", "delete an account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("delete transaction", "delete a transaction from the current active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("display account", "display all currently loaded accounts");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("display transaction", "display all transactions for the current active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("sort", "sort the transactions for the current active account");
    }

    /**
     * Constructor
     *
     * @throws InvalidInputException
     */
    public Request() throws InvalidInputException {
        this("");
    }

    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @throws InvalidInputException
     */
    public Request(String action) throws InvalidInputException {
        this(action, "");
    }

    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param args Specifies the object or method describing what the action is
     * performed on
     *
     *
     * @throws InvalidInputException
     */
    public Request(String action, String args) throws InvalidInputException {
        action = action.toLowerCase();
        if (ONE_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) { //valid 1 param request
            this.action = action;
        } else if (TWO_PARAM_ACTION_DESCRIPTIONS.containsKey(action) && args == "") {
            throw new InvalidInputException("You must enter an argument to "
                    + "use with " + action);
        } else if (!TWO_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) {
            throw new InvalidInputException("The request \"" + action
                    + "\" is not recognized or requires no arguments");
        } else { //valid 2 param request
            parseRequestWithArgs(action, args);
        }
    }
    
    public Request(String action, Transaction transaction) throws InvalidInputException {
        action = action.toLowerCase();
        if (action.compareToIgnoreCase("transaction") != 0) {
            throw new InvalidInputException("Transaction argument passed "
                    + "when action does not relate to transactions");
        }
        this.action = action;
        this.transaction = transaction;
    }
    
    private void parseRequestWithArgs(String action, String args) {
        //Determine if the request action deals with a transaction or account
        
        this.action = action;
        this.args = args;
    }

    /**
     * Returns the action of the user's command. The action describes the
     * intention of the user's request
     *
     * @return The action of the command
     */
    public String getAction() {
        return action;
    }
    
    public String getArgs() {
        return args;
    }
    
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
}
