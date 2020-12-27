/*--------------------------------TODO---------------------
Create methods for processing all requests (help, account, history, sort, search,
create, delete)
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
    
    private void executeCreateRequest(Request request) {
        
    }

    public void executeRequest(Request request) throws InvalidInputException {
        switch (request.getAction()) {
            case "create":
            //executeCreateRequest(request);
            case "delete":
            //executeDeleteRequest(request);
            case "help":
            //executeHelpRequest();
            case "history":
            //executeHistoryRequest();
            case "open":
            //executeOpenRequest(request);
            case "sort":
            //executeSortRequest();
            default:
                throw new InvalidInputException("Request " + request.getAction()
                        + "is not recognized");
        }
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

    public void setActiveAccount(String accountName) throws AccountNotFoundException {
        if (!namesToAccounts.containsKey(accountName)) {
            throw new AccountNotFoundException("The account " + accountName
                    + "is not recognized");
        }
        activeAccount = namesToAccounts.get(accountName);
    }
}
