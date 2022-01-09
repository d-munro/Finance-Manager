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
    private int numOfAccountsLoaded;

    /**
     * Creates a new account with details specified from request. This new
     * account is set to be the active account
     *
     * @param request Request object containing all information for account
     * creation
     * @return Message indicating that account creation was successful
     */
    private String executeAddAccountRequest(Request request) {
        String accountName = request.getArgs();
        Account account = new Account(accountName);
        namesToAccounts.put(account.getName(), account);
        activeAccount = account;
        return "New account created with the name " + accountName + "."
                + "\n" + accountName + " is now the active account.";
    }

    private String executeDeleteAccountRequest(Request request) throws AccountNotFoundException {
        StringBuilder returnedString = new StringBuilder();
        if (namesToAccounts.isEmpty()) {
            throw new AccountNotFoundException("There are currently no accounts loaded");
        }
        if (namesToAccounts.get(request.getArgs()) == null) {
            throw new AccountNotFoundException("The account \""
                    + request.getArgs() + "\" does not exist");
        }
        if (namesToAccounts.get(request.getArgs()).equals(activeAccount)) {
            activeAccount = null;
        }
        namesToAccounts.remove(request.getArgs());
        numOfAccountsLoaded--;
        returnedString.append("The account \"").append(request.getArgs()).append("\" has been deleted.");
        if (numOfAccountsLoaded == 1) {
            setActiveAccount(namesToAccounts.entrySet().iterator().next().getKey());
        }
        return returnedString.toString();
    }

    private String executeDisplayAccountRequest() throws AccountNotFoundException {
        StringBuilder sb = new StringBuilder();
        for (String current : getAccountNames()) {
            sb.append(current).append("\n");
        }
        return sb.toString();
    }

    private String executeDisplayTransactionRequest() throws AccountNotFoundException {
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

    private String executeChangeRequest(Request request) throws AccountNotFoundException {
        setActiveAccount(request.getArgs());
        return request.getArgs() + " is now the active account.";
    }

    private String executeQuitRequest() {
        return "Thank you for using Finance Manager!";
    }

    public String executeRequest(Request request)
            throws AccountNotFoundException, InvalidInputException {
        String output = "";
        switch (request.getAction()) {
            case "add account":
                System.out.println("add acc");
                output = executeAddAccountRequest(request);
                break;
            case "add transaction":
                System.out.println("add trans");
                //output = executeAddTransactionRequest(request);
                break;
            case "change":
                System.out.println("change");
                output = executeChangeRequest(request);
                break;
            case "delete account":
                System.out.println("delete account");
                output = executeDeleteAccountRequest(request);
                break;
            case "delete transaction":
                System.out.println("delete transaction");
                //output = executeDeleteTransactionRequest(request);
                break;
            case "display account":
                System.out.println("display account");
                output = executeDisplayAccountRequest();
                break;
            case "display transaction":
                System.out.println("display transaction");
                output = executeDisplayTransactionRequest();
                break;
            case "help":
                System.out.println("help");
                output = getHelp();
                break;
            case "quit":
                System.out.println("quit");
                output = executeQuitRequest();
                break;
            case "sort":
                System.out.println("sort");
                output = executeSortRequest(request);
                break;
            default:
                throw new InvalidInputException("Request \"" + request.getAction()
                        + "\" is not recognized");
        }
        return output;
    }

    private String executeSortRequest(Request request) throws InvalidInputException {
        //TODO Complete method
        try {
           activeAccount.setSortingMethod(Integer.parseInt(request.getArgs()));           
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter 1, 2, or 3");
        }
        return "In sort";
    }

    /**
     * Creates various Accounts from a JSONObject representation
     *
     * @param obj JSONObject containing details necessary for account creation
     * @throws CorruptJSONObjectException
     * @throws AccountNotFoundException
     */
    public void generateAccounts(JSONObject obj) throws
            CorruptJSONObjectException, AccountNotFoundException {
        Account currentAccount;
        JSONArray accountsArray = (JSONArray) obj.get("accounts");
        for (int i = 0; i < accountsArray.size(); i++) {
            currentAccount = new Account((JSONObject) accountsArray.get(i));
            namesToAccounts.put(currentAccount.getName(), currentAccount);
            if (accountsArray.size() == 1) {
                setActiveAccount(currentAccount.getName());
            }
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

    /**
     *
     *
     * @return A list of useful commands for executing the program
     */
    public static String getHelp() {
        StringBuilder sb = new StringBuilder();
        for (HashMap.Entry<String, String> current : Request.ONE_PARAM_ACTION_DESCRIPTIONS.entrySet()) {
            sb.append("Type \"").append(current.getKey()).append("\" to ").append(current.getValue()).append("\n");
        }
        for (HashMap.Entry<String, String> current : Request.TWO_PARAM_ACTION_DESCRIPTIONS.entrySet()) {
            sb.append("Type \"").append(current.getKey()).append("\" to ").append(current.getValue()).append("\n");
        }
        return sb.toString();
        /*return "Type \"add account\" to make a new account"
                + "\nType \"add transaction\" to add a transaction to the current active account"
                + "\nType \"change\" to change the active account"
                + "\nType \"delete account\" to delete an account"
                + "\nType \"delete transaction\" to delete a transaction from the current active account"
                + "\nType \"display account\" to display all currently loaded accounts"
                + "\nType \"display transaction\" to display all transactions for the current active account"
                + "\nType \"quit\" to terminate the program"
                + "\nType \"sort\" to sort the transactions for the current active account";*/
    }

    private void setActiveAccount(String accountName) throws AccountNotFoundException {
        if (!namesToAccounts.containsKey(accountName)) {
            throw new AccountNotFoundException("The account " + accountName
                    + " is not recognized");
        }
        activeAccount = namesToAccounts.get(accountName);
    }
}
