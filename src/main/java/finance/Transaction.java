//TODO move the value (cost) of item to the Item class
package finance;

import java.time.LocalDate;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Creates a Transaction object which describes changes in an Account's value
 *
 * @author Dylan Munro
 */
public class Transaction {

    private Item item;
    private int quantity;
    private LocalDate date;
    private int id;

    /**
     * Creates a Transaction which contains various information about a purchase
     *
     * @param item The physical object which caused the transaction to take
     * place
     * @param date Describes when the transaction occurred
     * @param quantity The quantity of items purchased
     * @param id An identifier used to refer to the transaction
     */
    public Transaction(Item item, LocalDate date, int quantity, int id) {
        this.item = item;
        this.date = date;
        this.quantity = quantity;
        this.id = id;
    }
    
    /**
     * Creates a Transaction which contains various information about a purchase
     * 
     * @param itemName The name of the item purchased
     * @param itemFee The fee associated with the item purchased
     * @param itemCategory The broad category describing the item purchased
     * @param date The date of the purchase
     * @param quantity The number of items purchased
     * @param id An identifier used to refer to the transaction
     */
    public Transaction(String itemName, double itemFee, String itemCategory,
            LocalDate date, int quantity, int id) {
        this.item = new Item(itemFee, itemName, itemCategory);
        this.date = date;
        this.quantity = quantity;
        this.id = id;
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
            this.date = LocalDate.now();
        } else {
            this.date = LocalDate.parse((String) obj.get("date"));
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
     * yyyy-MM-dd
     *
     * @return The date of the transaction
     */
    public LocalDate getDate() {
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
     * The quantity describes the number of items purchased in the transaction
     * 
     * @return The quantity of items purchased in the transaction
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * @return The cost and date of the transaction formatted as a string
     */
    @Override
    public String toString() {
        return "-----------Transaction----------"
                + "\n" + item
                + "\nDate: " + date
                + "\nId: " + id;
    }

}
