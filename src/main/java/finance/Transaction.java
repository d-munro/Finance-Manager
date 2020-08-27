package finance;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a transaction object that contains the amount and date
 * @author Dylan Munro
 */
public class Transaction {
    
    private double amount;
    private Date date;
    
    /**
     * Constructor for the Transaction class
     * By default, the date of the transaction is the current date
     * @param amount The cost of the transaction
     */
    public Transaction(double amount) {
        this(amount, new Date()); //Default date is current date
    }
    
    /**
     * Constructor for the transaction class
     * @param amount The cost of the transaction
     * @param date The date of the transaction
     */
    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.date = date;
    }
    
    /**
     * Returns the cost of the transaction
     * @return The cost of the transaction
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * Returns the date of the transaction
     * @return The date of the transaction
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Returns the cost and date of the transaction
     * @return The cost and date of the transaction
     */
    @Override
    public String toString() {
        return amount + new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
}
