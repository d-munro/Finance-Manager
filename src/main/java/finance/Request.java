package finance;

//imports
import java.util.LinkedHashMap;
import java.util.Date;

/**
 * Creates a Request argument which ensures that user requests contain valid syntax
 *
 * @author Dylan Munro
 */
public class Request {
    
    //Constants
    private static final LinkedHashMap<String, String> ONE_PARAM_ACTION_DESCRIPTIONS;
    private static final LinkedHashMap<String, String> TWO_PARAM_ACTION_DESCRIPTIONS;
    
    public static final int NONE = 0;
    public static final int ACCOUNT = 1;
    public static final int TRANSACTION = 2;
    
    private String action;
    
    //Parameters for sorting transaction request
    private int sortingMethod;

    //Static initialization of ONE_PARAM_ACTION_DESCRIPTIONS
    static {
        ONE_PARAM_ACTION_DESCRIPTIONS = new LinkedHashMap<String, String>();
        ONE_PARAM_ACTION_DESCRIPTIONS.put("help", "add a transaction to the current active account");
        ONE_PARAM_ACTION_DESCRIPTIONS.put("quit", "terminate the program");
        ONE_PARAM_ACTION_DESCRIPTIONS.put("display account", "display all currently loaded accounts");
        ONE_PARAM_ACTION_DESCRIPTIONS.put("display transaction", "display all transactions for the current active account");

        TWO_PARAM_ACTION_DESCRIPTIONS = new LinkedHashMap<String, String>();
        TWO_PARAM_ACTION_DESCRIPTIONS.put("add account", "make a new account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("add transaction", "add a transaction to the current active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("change account", "change the active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("delete account", "delete an account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("delete transaction", "delete a transaction from the current active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("sort transaction", "sort the transactions for the current active account");
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
     * Creates a Request for an action without any arguments
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @throws InvalidInputException
     */
    public Request(String action) throws InvalidInputException {
        if (ONE_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) {
            this.action = action.toLowerCase();
        }
        throw new InvalidInputException("The action " + action + " does"
                + " not exist or requires arguments");
    }
     
    /**
     * Creates a Request for an action that creates a Transaction
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param isCalledFromSubclass Specifies if a subclass is calling the constructor
     *
     * @throws InvalidInputException
     */        
    public Request(String action, boolean isCalledFromSubclass) throws InvalidInputException {
        if (!isCalledFromSubclass) {
            throw new InvalidInputException("Constructor not called from subclass");
        }
        if (!TWO_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) {
            throw new InvalidInputException("The request \"" + action
                    + "\" is not recognized or requires no arguments");
        } 
        this.action = action.toLowerCase();
    }

    /**
     * Returns the action of the user's request. The action describes the
     * intention of the user's request
     *
     * @return The action of the command
     */
    public String getAction() {
        return action;
    }
    
    public static LinkedHashMap<String, String> getONE_PARAM_ACTION_DESCRIPTIONS() {
        return ONE_PARAM_ACTION_DESCRIPTIONS;
    }
    
    public static LinkedHashMap<String, String> getTWO_PARAM_ACTION_DESCRIPTIONS() {
        return TWO_PARAM_ACTION_DESCRIPTIONS;
    }
    
}
