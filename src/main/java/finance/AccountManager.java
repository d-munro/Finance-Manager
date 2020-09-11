/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import java.util.HashMap;
import java.util.Set;

/**
 * Executes all functions related to accounts
 *
 * @author Dylan Munro
 */
public class AccountManager {

    private HashMap<String, Account> namesToAccounts = new HashMap<String, Account>();
    private Account activeAccount;

    public void executeRequest(Request request) {

    }

    /**
     * Adds a new account to
     * 
     * @param newAccount The new account created
     */
    public void addAccount(Account newAccount) {
        namesToAccounts.put(newAccount.getName(), newAccount);
    }
    
    public void setActiveAccount(String accountName) throws AccountNotFoundException {
        if (! namesToAccounts.containsKey(accountName)) {
            throw new AccountNotFoundException("The account " + accountName
                + "is not recognized");
        }
        activeAccount = namesToAccounts.get(accountName);
    }
    
    public Set<String> getAccountNames() throws AccountNotFoundException {
        if (namesToAccounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts are currently loaded");
        }
        return namesToAccounts.keySet();
    }

}
