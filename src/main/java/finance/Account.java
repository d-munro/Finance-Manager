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
    private static final int SORTED_CHRONOLOGICALLY = 1;
    private static final int SORTED_BY_COST = 2;
    private static final int SORTED_BY_CATEGORY = 3;

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
     * Returns a list of all transactions which have happened in the account
     *
     * @return The list of all transactions in the account
     */
    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Returns the name of account
     *
     * @return The name of the account
     */
    public String getName() {
        return name;
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
        JSONObject currentTransaction;
        for (int i = 0; i < transactionsJSON.size(); i++) {
            currentTransaction = (JSONObject) transactionsJSON.get(i);
            transactions.add(new Transaction(currentTransaction));
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
        while (iterator.hasNext()) {
            returnedString.append(iterator.next()).append("\n");
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

    public void setSortingMethod(int method) {
        switch (method) {
            case SORTED_CHRONOLOGICALLY:
                sortChronologically();
                sortingMethod = SORTED_CHRONOLOGICALLY;
            case SORTED_BY_CATEGORY:
                sortByCategory();
                sortingMethod = SORTED_BY_CATEGORY;
            case SORTED_BY_COST:
                sortByCost();
                sortingMethod = SORTED_BY_COST;
        }
    }

}
