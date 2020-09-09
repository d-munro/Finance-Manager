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

    private final String name;
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
     * Adds a transaction to the account
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
}
