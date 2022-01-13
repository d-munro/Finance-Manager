//TODO move the value (cost) of item to the Item class
package finance;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Creates a Transaction object which describes changes in an Account's value
 *
 * @author Dylan Munro
 */
public class Transaction {

    private Item item;
    private double quantity;
    private Date date;
    private int id;

    /**
     * Constructor for the Transaction class By default, the date of the
     * transaction is the current date
     *
     * @param item The physical object which caused the transaction to take
     * place
     */
    public Transaction(Item item) {
        this(item, new Date()); //Default date is current date
    }
    
    public Transaction(Item item, Date date) {
        this(item, date, 1);
    }

    /**
     * Constructor for the transaction class
     *
     * @param item The physical object which caused the transaction to take
     * place
     * @param date Describes when the transaction occurred
     * @param quantity The quantity of items purchased
     */
    public Transaction(Item item, Date date, int quantity) {
        this.item = item;
        this.date = date;
        this.quantity = quantity;
    }

    /**
     * Constructor
     *
     * @param obj JSONObject representation of the transaction to be added
     * @throws CorruptJSONObjectException
     */
    public Transaction(JSONObject obj) throws CorruptJSONObjectException {

        //process the value of transaction
        if (obj.get("item") == null) {
            throw new CorruptJSONObjectException("Transaction does not have an item");
        }
        this.item = new Item((JSONObject) obj.get("item"));

        //process the date of transaction
        if (obj.get("date") == null) {
            this.date = new Date();
        } else {
            this.date = new Date((String) obj.get("date"));
        }
    }

    /**
     * The item describes the physical object which caused the transaction to
     * take place.
     *
     * @return The item involved in the transaction
     */
    public Item getItem() {
        return item;
    }

    /**
     * The date describes when the transaction was created. It is in the format
     * year-month-day.
     *
     * @return The date of the transaction
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * The id is an identifier used to reference the transaction
     * 
     * @return The transaction id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return The cost and date of the transaction formatted as a string
     */
    @Override
    public String toString() {
        return "-----------Transaction----------"
                + "\n" + item
                + "\nDate: " + new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
