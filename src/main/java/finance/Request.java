package finance;

//imports
import java.util.LinkedHashMap;

/**
 * Creates a Request argument which ensures that user requests contain valid syntax
 *
 * @author Dylan Munro
 */
public class Request {
    
    //Private Constants
    private static final LinkedHashMap<String, String> ONE_PARAM_ACTION_DESCRIPTIONS;
    private static final LinkedHashMap<String, String> TWO_PARAM_ACTION_DESCRIPTIONS;
    
    //Public Constants
    public static final int NONE = 0; //Indicates Request is not acting on an object
    public static final int ACCOUNT = 1; //Indicates Request is modifying an account
    public static final int TRANSACTION = 2; //Indicates Request is modifying a transaction
    
    //Class variables
    private final String action;

    static {
        ONE_PARAM_ACTION_DESCRIPTIONS = new LinkedHashMap<String, String>();
        ONE_PARAM_ACTION_DESCRIPTIONS.put("help", "display the help menu");
        ONE_PARAM_ACTION_DESCRIPTIONS.put("quit", "terminate the program");
        ONE_PARAM_ACTION_DESCRIPTIONS.put("display account", "display all currently loaded accounts");
        ONE_PARAM_ACTION_DESCRIPTIONS.put("display transaction", "display all transactions for the current active account");

        TWO_PARAM_ACTION_DESCRIPTIONS = new LinkedHashMap<String, String>();
        TWO_PARAM_ACTION_DESCRIPTIONS.put("add account", "make a new account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("add transaction", "add a transaction to the current active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("change account", "change the active account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("delete account", "delete an account");
        TWO_PARAM_ACTION_DESCRIPTIONS.put("delete transaction", "delete a transaction from the current active account");
    }

    /**
     * Creates a Request not containing any action words
     *
     * @throws InvalidRequestException
     */
    public Request() throws InvalidRequestException {
        this("");
    }

    /**
     * Creates a Request for an action without any arguments
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @throws InvalidRequestException
     */
    public Request(String action) throws InvalidRequestException {
        if (ONE_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) {
            this.action = action.toLowerCase();
        } else {
            throw new InvalidRequestException("The action " + action + " does"
                + " not exist or requires arguments");           
        }
    }
     
    /**
     * Creates a Request for an action that creates a Transaction
     * <p>
     * This constructor should only be called from subclasses of request
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param isCalledFromSubclass Specifies if a subclass of Request is calling the constructor
     *
     * @throws InvalidRequestException
     */        
    public Request(String action, boolean isCalledFromSubclass) throws InvalidRequestException {
        if (!isCalledFromSubclass) {
            throw new InvalidRequestException("Constructor not called from subclass");
        }
        if (!TWO_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) {
            throw new InvalidRequestException("The request \"" + action
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
    
    /**
     * 
     * @return HashMap containing all transactions valid with only an action word
     */
    public static LinkedHashMap<String, String> getONE_PARAM_ACTION_DESCRIPTIONS() {
        return ONE_PARAM_ACTION_DESCRIPTIONS;
    }
    
    /**
     * 
     * @return HashMap containing all transactions which must have an object to act upon
     */
    public static LinkedHashMap<String, String> getTWO_PARAM_ACTION_DESCRIPTIONS() {
        return TWO_PARAM_ACTION_DESCRIPTIONS;
    }
    
}
