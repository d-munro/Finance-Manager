/*--------------------------------TODO---------------------
Complete delete, open, sort methods
Be sure to check for instances where activeAccount = null
 */
package finance;

import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Executes all functions related to accounts
 *
 * @author Dylan Munro
 */
public class AccountManager {

    private HashMap<String, Account> namesToAccounts = new HashMap<String, Account>();
    private Account activeAccount;

    /**
     * Adds a new account to
     *
     * @param newAccount The new account created
     */
    public void addAccount(Account newAccount) {
        namesToAccounts.put(newAccount.getName(), newAccount);
    }

    /**
     * Creates a new account with details specified from request. This new
     * account is set to be the active account
     *
     * @param request Request object containing all information for account
     * creation
     * @return Message indicating that account creation was successful
     */
    private String executeCreateRequest(Request request) {
        String accountName = request.getArgs();
        addAccount(new Account(accountName));
        activeAccount = namesToAccounts.get(accountName);
        return "New account created with the name " + accountName + "."
                + "\n" + accountName + " is now the active account.";
    }

    private String executeDeleteRequest(Request request) throws AccountNotFoundException {
        //TODO Complete method
        if (namesToAccounts.isEmpty()) {
            throw new AccountNotFoundException("There are currently no accounts loaded");
        }
        return "In delete";
    }

    private String executeDisplayRequest() throws AccountNotFoundException {
        StringBuilder sb = new StringBuilder();
        for (String current : getAccountNames()) {
            sb.append(current).append("\n");
        }
        return sb.toString();
    }

    private String executeHistoryRequest() throws AccountNotFoundException {
        if (activeAccount == null) {
            throw new AccountNotFoundException("No active account selected");
        }
        if (activeAccount.getTransactions().isEmpty()) {
            return "No transactions have been made on the account.";
        }
        StringBuilder sb = new StringBuilder();
        for (Transaction current : activeAccount.getTransactions()) {
            sb.append(current.toString()).append("\n");
        }
        return sb.toString();
    }

    private String executeOpenRequest(Request request) {
        //TODO Complete method
        return "In open";
    }

    private String executeQuitRequest() {
        return "Thank you for using Finance Manager!";
    }

    public String executeRequest(Request request)
            throws AccountNotFoundException, InvalidInputException {
        String output = "";
        switch (request.getAction()) {
            case "create":
                output = executeCreateRequest(request);
                break;
            case "delete":
                output = executeDeleteRequest(request);
                break;
            case "display":
                output = executeDisplayRequest();
                break;
            case "help":
                output = getHelp();
                break;
            case "history":
                output = executeHistoryRequest();
                break;
            case "open":
                output = executeOpenRequest(request);
                break;
            case "quit":
                output = executeQuitRequest();
                break;
            case "sort":
                output = executeSortRequest(request);
                break;
            default:
                throw new InvalidInputException("Request " + request.getAction()
                        + "is not recognized");
        }
        return output;
    }

    private String executeSortRequest(Request request) {
        //TODO Complete method
        return "In sort";
    }

    /**
     * Creates various Accounts from a JSONObject representation
     *
     * @param obj JSONObject containing details necessary for account creation
     * @throws CorruptJSONObjectException
     */
    public void generateAccounts(JSONObject obj) throws
            CorruptJSONObjectException {
        JSONArray accountsArray = (JSONArray) obj.get("accounts");
        for (int i = 0; i < accountsArray.size(); i++) {
            addAccount(new Account((JSONObject) accountsArray.get(i)));
        }
    }

    public Set<String> getAccountNames() throws AccountNotFoundException {
        if (namesToAccounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts are currently loaded");
        }
        return namesToAccounts.keySet();
    }

    /**
     *
     * @return Name of the active account if not null. If activeAccount is null,
     * throws AccountNotFoundException
     * @throws AccountNotFoundException
     */
    public String getActiveAccountName() throws AccountNotFoundException {
        if (activeAccount == null) {
            throw new AccountNotFoundException("No account is currently selected");
        }
        return activeAccount.getName();
    }

    public void setActiveAccount(String accountName) throws AccountNotFoundException {
        if (!namesToAccounts.containsKey(accountName)) {
            throw new AccountNotFoundException("The account " + accountName
                    + "is not recognized");
        }
        activeAccount = namesToAccounts.get(accountName);
    }

    /**
     *
     *
     * @return A list of useful commands for executing the program
     */
    public static String getHelp() {
        return "Type create to make a new account"
                + "\nType delete to delete an account"
                + "\nType display to display all accounts currently loaded"
                + "\nType history to display all previous transactions"
                + " for the current account"
                + "\nType open to open a new account"
                + "\nType quit to terminate the program"
                + "\nType sort to sort account transactions";
    }
}
