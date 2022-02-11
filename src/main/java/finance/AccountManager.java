package finance;

//Imports
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

    private final HashMap<String, Account> namesToAccounts = new HashMap<String, Account>();
    private Account activeAccount;
    private int numOfAccountsLoaded;

    /**
     * Creates a new account with details specified from request. This new
     * account is set to be the active account
     *
     * @param request Request object containing all information for account
     * creation
     * 
     * @return Message indicating that account creation was successful
     * 
     * @throws AccountException
     */
    private String executeAddAccountRequest(AccountRequest request) 
        throws AccountException{
        String accountName = request.getAccountName();
        if (namesToAccounts.containsKey(accountName)) {
            throw new AccountException("The account \"" + accountName 
                    + "\"already exists");
        }
        Account account = new Account(accountName);
        namesToAccounts.put(account.getName(), account);
        activeAccount = account;
        numOfAccountsLoaded++;
        return "New account created with the name " + accountName + "."
                + "\n" + accountName + " is now the active account.";
    }
    
    /**
     * Handles execution of requests to add a transaction to the current active account
     * 
     * @param request Request object containing sufficient information to create
     * a transaction
     * 
     * @return A message informing the user that the request was successful
     * 
     * @throws AccountException 
     */
    private String executeAddTransactionRequest(TransactionRequest request) 
        throws AccountException {
        if (activeAccount == null) {
            throw new AccountException("Please select an active account before making a transaciton");
        }
        activeAccount.addTransaction(new Transaction(request.getItemName(), request.getItemFee(),
            request.getItemCategory(), request.getDate(), request.getQuantity()));
        return "The transaction has been added to the account \"" + activeAccount.getName() + "\"";
    }
    
    /**
     * Handles execution of requests to change the active account
     * 
     * @param request The request containing the details of the new active account
     * 
     * @return A message informing the user of the new active account
     * 
     * @throws AccountException
     */
    private String executeChangeAccountRequest(AccountRequest request) throws AccountException {
        String activeAccountName = request.getAccountName();
        setActiveAccount(activeAccountName);
        return activeAccountName + " is now the active account.";
    }
    
     /**
     * Handles execution of requests to delete accounts
     * 
     * @param request The request containing all necessary specifications for 
     * account creation
     * 
     * @return A message informing the user that account deletion was successful
     * 
     * @throws AccountException 
     */
    private String executeDeleteAccountRequest(AccountRequest request) throws AccountException {
        StringBuilder returnedString = new StringBuilder();
        if (namesToAccounts.isEmpty()) {
            throw new AccountException("There are currently no accounts loaded");
        }
        if (namesToAccounts.get(request.getAccountName()) == null) {
            throw new AccountException("The account \""
                    + request.getAccountName() + "\" does not exist");
        }
        if (namesToAccounts.get(request.getAccountName()).equals(activeAccount)) {
            activeAccount = null;
        }
        namesToAccounts.remove(request.getAccountName());
        numOfAccountsLoaded--;
        returnedString.append("The account \"").append(request.getAccountName()).append("\" has been deleted.");
        if (numOfAccountsLoaded == 1) {
            setActiveAccount(namesToAccounts.entrySet().iterator().next().getKey());
        }
        return returnedString.toString();
    }

    /**
     * Handles execution of requests to display all presently loaded accounts
     * 
     * @return A list of all presently loaded accounts in the program
     * 
     * @throws AccountException 
     */
    private String executeDisplayAccountRequest() throws AccountException {
        StringBuilder sb = new StringBuilder();
        for (String current : getAccountNames()) {
            sb.append(current).append("\n");
        }
        return sb.toString();
    }

     /**
     * Handles execution of requests to display all transactions in the current
     * active account. Generates a string containing details of every transaction.
     * 
     * @return String containing the details of every transaction on the account
     * 
     * @throws AccountException
     * @throws TransactionNotFoundException
     */  
    private String executeDisplayTransactionRequest() throws
            AccountException, TransactionNotFoundException {
        if (activeAccount == null) {
            throw new AccountException("No active account selected");
        } else if (!activeAccount.containsTransactions()) {
            throw new TransactionNotFoundException(
                    "No transactions have been made on the account.");
        }
        StringBuilder sb = new StringBuilder();
        int currentNum = 1; 
        //Start at 1 because transactions start at 1, not 0 
        //(Ex: The 0th transaction doesn't exist)
        for (Transaction current : activeAccount.getTransactions()) {
            sb.append(current.toString()).append("\n");
            sb.append("Transaction Number: ").append(currentNum).append("\n");
            currentNum++;
        }
        return sb.toString();
    }

    /**
     * Handles execution of requests to delete a transaction from the current
     * active account
     * 
     * @param request The request containing the number of the transaction to delete
     * 
     * @return Message informing the user that transaction deletion was successful
     * 
     * @throws AccountException
     * @throws TransactionNotFoundException
     */    
    private String executeDeleteTransactionRequest(TransactionRequest request) throws 
            AccountException, TransactionNotFoundException {
        int transactionNumber = request.getTransactionNumber();
        if (activeAccount == null) {
            throw new AccountException("No active account selected");
        } 
        activeAccount.deleteTransaction(transactionNumber);
        return "Transaction number \"" + transactionNumber + "\" has been deleted";
    }

    /**
     * Handles execution of requests to quit the program
     * 
     * @return A message thanking the user for using the program
     */
    private String executeQuitRequest() {
        return "Thank you for using Finance Manager!";
    }

    /**
     * Manages execution of a request object to modify an account
     * 
     * @param request Request to be executed
     * 
     * @return Output text of the request
     * 
     * @throws AccountException
     * @throws InvalidRequestException
     * @throws TransactionNotFoundException 
     */
    public String executeRequest(Request request)
            throws AccountException, InvalidRequestException, TransactionNotFoundException {
        String output = "";
        switch (request.getAction()) {
            case "add account":
                output = executeAddAccountRequest((AccountRequest)request);
                break;
            case "add transaction":
                output = executeAddTransactionRequest((TransactionRequest)request);
                break;
            case "change account":
                output = executeChangeAccountRequest((AccountRequest)request);
                break;
            case "delete account":
                output = executeDeleteAccountRequest((AccountRequest)request);
                break;
            case "delete transaction":
                output = executeDeleteTransactionRequest((TransactionRequest)request);
                break;
            case "display account":
                output = executeDisplayAccountRequest();
                break;
            case "display transaction":
                output = executeDisplayTransactionRequest();
                break;
            case "help":
                output = getHelp();
                break;
            case "quit":
                output = executeQuitRequest();
                break;
            default:
                throw new InvalidRequestException("The request \"" + request.getAction()
                        + "\" is not recognized");
        }
        return output;
    }

    /**
     * Creates various Accounts from a JSONObject representation
     *
     * @param obj JSONObject containing details necessary for account creation
     * @throws CorruptJSONObjectException
     * @throws AccountException
     */
    public void generateAccounts(JSONObject obj) throws
            CorruptJSONObjectException, AccountException {
        Account currentAccount;
        JSONArray accountsArray;
        try {
            accountsArray = (JSONArray) obj.get("accounts");           
        } catch (Exception e) {
            throw new CorruptJSONObjectException("The JSON file is corrupted and could not be parsed");
        }
        for (int i = 0; i < accountsArray.size(); i++) {
            currentAccount = new Account((JSONObject) accountsArray.get(i));
            numOfAccountsLoaded++;
            namesToAccounts.put(currentAccount.getName(), currentAccount);
            if (accountsArray.size() == 1) {
                setActiveAccount(currentAccount.getName());
            }
        }
    }

    /**
     * 
     * @return Set containing all account names loaded in the account manager
     * @throws AccountException 
     */
    public Set<String> getAccountNames() throws AccountException {
        if (namesToAccounts.isEmpty()) {
            throw new AccountException("No accounts are currently loaded");
        }
        return namesToAccounts.keySet();
    }

    /**
     *
     * @return Name of the active account
     * 
     * @throws AccountException
     */
    public String getActiveAccountName() throws AccountException {
        if (activeAccount == null) {
            throw new AccountException("No account is currently selected");
        }
        return activeAccount.getName();
    }
    
    /**
     * 
     * @return Number of accounts currently loaded in the account manager
     */
    public int getNumOfAccountsLoaded() {
        return numOfAccountsLoaded;
    }

    /**
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
    
    /**
     * 
     * @return true if there is currently an active account in the account manager,
     * false otherwise
     */
    public boolean hasActiveAccount() {
        return activeAccount != null;
    }

    /**
     * Changes the active account in the account manager
     * 
     * @param accountName The name of the account becoming the active account
     * 
     * @throws AccountException 
     */
    private void setActiveAccount(String accountName) throws AccountException {
        if (!namesToAccounts.containsKey(accountName)) {
            throw new AccountException("The account " + accountName
                    + " is not recognized");
        }
        activeAccount = namesToAccounts.get(accountName);
    }
}
