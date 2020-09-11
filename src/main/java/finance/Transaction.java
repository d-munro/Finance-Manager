//TODO move the value (cost) of item to the Item class
package finance;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Creates a transaction object that contains the value and date
 *
 * @author Dylan Munro
 */
public class Transaction {

    private Item item;
    private double quantity;
    private Date date;

    /**
     * Constructor for the Transaction class By default, the date of the
     * transaction is the current date
     *
     * @param item The item involved in the transaction
     */
    public Transaction(Item item) {
        this(item, new Date()); //Default date is current date
    }

    /**
     * Constructor for the transaction class
     *
     * @param item The item involved in the transaction
     * @param date The date of the transaction
     */
    public Transaction(Item item, Date date) {
        this.item = item;
        this.date = date;
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
     * Returns the item involved in the transaction
     *
     * @return The item involved in the transaction
     */
    public Item getItem() {
        return item;
    }

    /**
     * Returns the date of the transaction
     *
     * @return The date of the transaction
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the cost and date of the transaction
     *
     * @return The cost and date of the transaction
     */
    @Override
    public String toString() {
        return item + new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
