package finance;

//imports
import java.util.HashMap;

/**
 * Creates a Request object which determines if a user's request is valid
 *
 * @author Dylan Munro
 */
public class Request {

    private static final HashMap<String, Boolean> VALID_REQUESTS_ONE_PARAM;
    private static final HashMap<String, Boolean> VALID_REQUESTS_TWO_PARAMS;
    private String action;
    private String name;
    private String args;

    //Static initialization of VALID_REQUESTS_ONE_PARAM
    static {
        VALID_REQUESTS_ONE_PARAM = new HashMap<String, Boolean>();
        VALID_REQUESTS_ONE_PARAM.put("help", true);
        VALID_REQUESTS_ONE_PARAM.put("history", true);
        VALID_REQUESTS_ONE_PARAM.put("quit", true);
        VALID_REQUESTS_ONE_PARAM.put("display", true);
        VALID_REQUESTS_ONE_PARAM.put("edit", true);

        VALID_REQUESTS_TWO_PARAMS = new HashMap<String, Boolean>();
        VALID_REQUESTS_TWO_PARAMS.put("sort", true);
        VALID_REQUESTS_TWO_PARAMS.put("open", true); //search for new account
        VALID_REQUESTS_TWO_PARAMS.put("create", true);
        VALID_REQUESTS_TWO_PARAMS.put("delete", true);
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
        this(action, null);
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
        if (VALID_REQUESTS_ONE_PARAM.containsKey(action)) { //valid 1 param request
            this.action = action;
        } else if (VALID_REQUESTS_TWO_PARAMS.containsKey(action) && args == null) {
            throw new InvalidInputException("You must enter an argument to "
                    + "use with " + action);
        } else if (!VALID_REQUESTS_TWO_PARAMS.containsKey(action)) {
            throw new InvalidInputException("The request \"" + action
                    + "\" is not recognized or requires no arguments");
        } else { //valid 2 param request
            this.action = action;
            this.args = args;
        }
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
}
