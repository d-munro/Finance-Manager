package finance;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Creates an Account object which contains a list of transactions created by a user
 * @author Dylan Munro
 */
public class Account {
    
    private final String name;
    private LinkedList<Transaction> transactions = new LinkedList<>();
    
    /**
     * Constructor for the Account class
     * @param name The name of the account
     */
    public Account(String name) {
        this.name = name;
    }
    
    /**
     * Adds a transaction to the account
     * @param transaction The transaction to be added
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Returns a list of all transactions which have happened in the account
     * @return The list of all transactions in the account
     */
    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }
    
    /**
     * Returns the name of the account and a list of all transactions
     * @return The name of the account and a list of all transactions
     */
    @Override
    public String toString() {
        ListIterator<Transaction> iterator = transactions.listIterator();
        StringBuilder returnedString = new StringBuilder();
        returnedString.append(name).append("\n");
        while(iterator.hasNext()) {
            returnedString.append(iterator.next()).append("\n");
        }
        return returnedString.toString();
    }
}
