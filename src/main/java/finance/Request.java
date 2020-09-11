package finance;

//imports
import java.util.HashMap;

/**
 * Creates a Request object which determines if a user's request is valid
 * 
 * @author Dylan Munro
 */
public class Request {
    
    private static final HashMap<String, Boolean> VALID_REQUESTS;
    private String action;
    
    //Static initialization of VALID_REQUESTS
    static {
        VALID_REQUESTS = new HashMap<String, Boolean>();
        VALID_REQUESTS.put("help", true);
        VALID_REQUESTS.put("account", true);
        VALID_REQUESTS.put("history", true);
        VALID_REQUESTS.put("sort", true);
        VALID_REQUESTS.put("search", true); //search for new account
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
     * @param action The user's request
     * @throws InvalidInputException 
     */
    public Request(String action) throws InvalidInputException {
        if (! VALID_REQUESTS.containsKey(action)) {
            throw new InvalidInputException("The request \"" + action + "\" is not recognized");
        } 
        this.action = action;
    }
    
    /**
     * Returns the action of the user's command
     * The action describes the intention of the user's request
     * 
     * @return The action of the command
     */
    public String getAction() {
        return action;
    }
}
