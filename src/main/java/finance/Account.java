package finance;

import java.util.LinkedList;
import java.util.ListIterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Creates an Account object which contains a list of transactions created by a
 * user
 *
 * @author Dylan Munro
 */
public class Account {

    private final LinkedList<Transaction> transactions = new LinkedList<>();   
    private final String name;

    /**
     * Creates an Account object given the name of the Account
     *
     * @param name The name of the account
     */
    public Account(String name) {
        this.name = name;
    }

    /**
     * Creates an Account object from its JSON representation
     *
     * @param obj The JSONObject containing all details about the account
     * 
     * @throws CorruptJSONObjectException
     */
    public Account(JSONObject obj) throws CorruptJSONObjectException {
        this.name = obj.get("name").toString();
        initializeTransactions((JSONArray) obj.get("transactions"));
    }

    /**
     * Adds a transaction to the account
     *
     * @param transaction The transaction to be added
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    /**
     * Determines if the account contains any transactions
     * 
     * @return true if the account contains transactions, false otherwise
     */
    public boolean containsTransactions() {
        return !transactions.isEmpty();
    }
    
    /**
     * Deletes a transaction from the account
     * 
     * @param transactionNumber The number of the transaction to be removed
     * 
     * @return A message informing the user that the transaction deletion was successful
     * 
     * @throws TransactionNotFoundException 
     */
    public String deleteTransaction(int transactionNumber) throws TransactionNotFoundException {
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException(
                    "No transactions have been made on the account \"" + name + "\"");
        } else if (transactionNumber > transactions.size()) {
            throw new TransactionNotFoundException(transactionNumber);
        }
        ListIterator<Transaction> iterator = transactions.listIterator();
        
        //transaction numbers start at 1, so i must start at 1
        for (int i = 1; iterator.hasNext() && i <= transactionNumber; i++) {
            iterator.next();
            if (i == transactionNumber) {
                iterator.remove();
                return "Transaction " + transactionNumber + " has been removed";
            }
        }
        throw new TransactionNotFoundException(transactionNumber);
    }

    /**
     * Returns a list of all transactions which have happened in the account
     *
     * @return The list of all transactions in the account
     */
    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @return The name of the account
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return The number of transactions currently stored on the account
     */
    public int getNumOfTransactions() {
        return transactions.size();
    }

    /**
     * Initializes all transactions for the account given a JSONObject of the
     * transactions
     *
     * @param transactionsJSON JSONArray containing all transactions of the
     * account
     * 
     * @throws CorruptJSONObjectException
     */
    private void initializeTransactions(JSONArray transactionsJSON) throws CorruptJSONObjectException {
        JSONObject currentTransactionJson;
        for (int i = 0; i < transactionsJSON.size(); i++) {
            currentTransactionJson = (JSONObject) transactionsJSON.get(i);
            transactions.add(new Transaction(currentTransactionJson));
        }
    }

    /**
     * @return The name of the account and a list of its transactions formatted as a string
     */
    @Override
    public String toString() {
        ListIterator<Transaction> iterator = transactions.listIterator();
        StringBuilder returnedString = new StringBuilder();
        returnedString.append(name).append("\n");
        int i = 0;
        while (iterator.hasNext()) {
            returnedString.append(iterator.next())
                    .append("\nTransaction Number: ").append(i).append("\n");
        }
        return returnedString.toString();
    }
}
