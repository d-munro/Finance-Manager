/*--------------------------------TODO---------------------
Correct getSpecification() methods in account/transaction requests
    -Either implement getSpecification() in appropriate classes, or change getSpecification() to getData()
Refactor request execution methods to work with Request subclasses
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
    private String executeAddAccountRequest(AccountRequest request) {
        String accountName = request.getAccountName();
        Account account = new Account(accountName);
        namesToAccounts.put(account.getName(), account);
        activeAccount = account;
        return "New account created with the name " + accountName + "."
                + "\n" + accountName + " is now the active account.";
    }

    private String executeDeleteAccountRequest(AccountRequest request) throws AccountNotFoundException {
        StringBuilder returnedString = new StringBuilder();
        /*if (namesToAccounts.isEmpty()) {
            throw new AccountNotFoundException("There are currently no accounts loaded");
        }
        if (namesToAccounts.get(request.getSpecification()) == null) {
            throw new AccountNotFoundException("The account \""
                    + request.getSpecification() + "\" does not exist");
        }
        if (namesToAccounts.get(request.getSpecification()).equals(activeAccount)) {
            activeAccount = null;
        }
        namesToAccounts.remove(request.getSpecification());
        numOfAccountsLoaded--;
        returnedString.append("The account \"").append(request.getSpecification()).append("\" has been deleted.");
        if (numOfAccountsLoaded == 1) {
            setActiveAccount(namesToAccounts.entrySet().iterator().next().getKey());
        }*/
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

    private String executeChangeRequest(AccountRequest request) throws AccountNotFoundException {
        /*setActiveAccount(request.getSpecification());
        return request.getAccountName() + " is now the active account.";*/
        return "DELETE THIS TEXT IN AccountManager.executeChangeRequest()";
    }

    private String executeQuitRequest() {
        return "Thank you for using Finance Manager!";
    }

    public String executeRequest(Request request)
            throws AccountNotFoundException, InvalidRequestException {
        String output = "";
        /*switch (request.getAction()) {
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
                output = executeSortRequest((SortingRequest)request);
                break;
            default:
                throw new InvalidRequestException("Request \"" + request.getAction()
                        + "\" is not recognized");
        }*/
        return output;
    }

    private String executeSortRequest(SortingRequest request) throws InvalidRequestException {
        return activeAccount.setSortingMethod(request.getSortingMethod());
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
        HashMap<String, String> map1 = Request.getONE_PARAM_ACTION_DESCRIPTIONS();
        HashMap<String, String> map2 = Request.getTWO_PARAM_ACTION_DESCRIPTIONS();
        for (HashMap.Entry<String, String> current : map1.entrySet()) {
            sb.append("Type \"").append(current.getKey()).append("\" to ").append(current.getValue()).append("\n");
        }
        for (HashMap.Entry<String, String> current : map2.entrySet()) {
            sb.append("Type \"").append(current.getKey()).append("\" to ").append(current.getValue()).append("\n");
        }
        return sb.toString();
    }

    private void setActiveAccount(String accountName) throws AccountNotFoundException {
        if (!namesToAccounts.containsKey(accountName)) {
            throw new AccountNotFoundException("The account " + accountName
                    + " is not recognized");
        }
        activeAccount = namesToAccounts.get(accountName);
    }
}
