//TODO implement ability to sort transactions chronologically, by category, by cost
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

    //constants
    private final String name;

    private int sortingMethod;
    private LinkedList<Transaction> transactions = new LinkedList<>();

    /**
     * Constructor for the Account class
     *
     * @param name The name of the account
     */
    public Account(String name) {
        this.name = name;
    }

    /**
     * Constructor for the Account class
     *
     * @param obj The JSONObject containing all details about the account
     * @throws CorruptJSONObjectException
     */
    public Account(JSONObject obj) throws CorruptJSONObjectException {
        this.name = obj.get("name").toString();
        initializeTransactions((JSONArray) obj.get("transactions"));
    }

    /**
     * Adds a transaction to the account in chronological order
     *
     * @param transaction The transaction to be added
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    /**
     * Determines if the account contains a transaction belonging to an id
     * 
     * @param transactionId The id belonging to the transaction
     * @return true if the account contains a transaction with the id, 
     * false otherwise
     */
    /*public boolean containsTransaction(long transactionId) {
        return idToTransaction.containsKey(transactionId);
    }*/
    
    /**
     * Determines if the account contains any transactions
     * 
     * @return true if the account contains transactions, false otherwise
     */
    public boolean containsTransactions() {
        return !transactions.isEmpty();
    }
    
    public String deleteTransaction(int index) throws TransactionNotFoundException {
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException(
                    "No transactions have been made on the account \"" + name + "\"");
        } else if (index > transactions.size()) {
            throw new TransactionNotFoundException(index);
        }
        ListIterator<Transaction> iterator = transactions.listIterator();
        
        //transaction numbers start at 1, so i must start at 1
        for (int i = 1; iterator.hasNext() && i <= index; i++) {
            iterator.next();
            if (i == index) {
                iterator.remove();
                return "Transaction " + index + " has been removed";
            }
        }
        throw new TransactionNotFoundException(index);
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
     * 
     * @return The name of the account
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
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
     * Returns the name of the account and a list of all transactions
     *
     * @return The name of the account and a list of all transactions
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

    /**
     * Sorts all transactions in the account in chronological order
     */
    private void sortChronologically() {

    }

    private void sortByCategory() {

    }

    private void sortByCost() {

    }

    public String setSortingMethod(int method) {
        String returnedText = "";
        /*HashMap<Integer, Boolean> SORTING_METHODS = SortingRequest.getVALID_SORTING_METHODS();
        switch (method) {
            case SORTING_METHODS.getKey(SORTED_CHRONOLOGICALLY):
                sortChronologically();
                sortingMethod = SORTED_CHRONOLOGICALLY;
            case SORTED_BY_CATEGORY:
                sortByCategory();
                sortingMethod = SORTED_BY_CATEGORY;
            case SORTED_BY_COST:
                sortByCost();
                sortingMethod = SORTED_BY_COST;
        }*/
        return returnedText;
    }

}
