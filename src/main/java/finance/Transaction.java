package finance;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Creates a transaction object that contains the value and date
 * @author Dylan Munro
 */
public class Transaction {
    
    private double value;
    private Date date;
    
    /**
     * Constructor for the Transaction class
     * By default, the date of the transaction is the current date
     * @param value The cost of the transaction
     */
    public Transaction(double value) {
        this(value, new Date()); //Default date is current date
    }
    
    /**
     * Constructor for the transaction class
     * @param value The cost of the transaction
     * @param date The date of the transaction
     */
    public Transaction(double value, Date date) {
        this.value = value;
        this.date = date;
    }
    
    /**
     * Constructor
     * @param obj JSONObject representation of the transaction to be added
     */
    public Transaction(JSONObject obj) throws CorruptJSONObjectException {
        
        //process the value of transaction
        if (obj.get("value") == null) {
            throw new CorruptJSONObjectException("Transaction does not have a value");
        }
        this.value = Double.parseDouble(obj.get("value").toString());
        
        //process the date of transaction
        if (obj.get("date") == null) {
            this.date = new Date();
        } else {
            this.date = new Date((String) obj.get("date"));
        }
    }
    
    /**
     * Returns the cost of the transaction
     * @return The cost of the transaction
     */
    public double getValue() {
        return value;
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
        return value + new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
}
