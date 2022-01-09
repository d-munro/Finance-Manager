//TODO - Refactor Constructor so only action and data is required in subclasses
package finance;

//imports
import java.util.HashMap;

/**
 * Creates a Request object which determines if a user's request is valid
 *
 * @author Dylan Munro
 */
public class Request {
    
    //Constants
    public static final HashMap<String, String> ONE_PARAM_ACTION_DESCRIPTIONS;
    public static final HashMap<String, String> TWO_PARAM_ACTION_DESCRIPTIONS;
    public static final int NONE = 0;
    public static final int ACCOUNT = 1;
    public static final int TRANSACTION = 2;
    
    private String action;
    private String specification;

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
     * @param specification Specifies the object that the action is performing on, 
     * or how the action is being performed
     *
     * @throws InvalidInputException
     */    
    public Request(String action, String specification) throws InvalidInputException {
        action = action.toLowerCase();
        if (ONE_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) { //valid 1 param request
            this.action = action;
        } else if (TWO_PARAM_ACTION_DESCRIPTIONS.containsKey(action) && specification.compareTo("") == 0) {
            throw new InvalidInputException("You must enter an argument to "
                    + "use with " + action);
        } else if (!TWO_PARAM_ACTION_DESCRIPTIONS.containsKey(action)) {
            throw new InvalidInputException("The request \"" + action
                    + "\" is not recognized or requires no arguments");
        } 
        this.action = action;
        this.specification = specification;
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
    
    public String getSpecification() {
        return specification;
    }
}
