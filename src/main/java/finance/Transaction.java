/*TODO
-Refactor Transaction(JSONObject) constructor by parsing date and quantity
    in their own methods
-Implement total transaction cost (itemPrice * quantity)
*/
package finance;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import org.json.simple.JSONObject;

/**
 * Creates a Transaction object which describes changes in an Account's value
 *
 * @author Dylan Munro
 */
public class Transaction {

    private final Item item;
    private final int quantity;
    private final LocalDate date;

    /**
     * Creates a Transaction which contains various information about a purchase
     *
     * @param item The physical object which caused the transaction to take
     * place
     * @param date Describes when the transaction occurred
     * @param quantity The quantity of items purchased
     * @param transactionNumber The sequential number describing transactions chronologically. 
     * Note that the transactionNumber should start at 1, not 0, to make reading receipts more intuitive
     */
    public Transaction(Item item, LocalDate date, int quantity, int transactionNumber) {
        this.item = item;
        this.date = date;
        this.quantity = quantity;
    }

    /**
     * Creates a Transaction which contains various information about a purchase
     *
     * @param itemName The name of the item purchased
     * @param itemFee The fee associated with the item purchased
     * @param itemCategory The broad category describing the item purchased
     * @param date The date of the purchase
     * @param quantity The number of items purchased
     */
    public Transaction(String itemName, double itemFee, String itemCategory,
            LocalDate date, int quantity) {
        this.item = new Item(itemFee, itemName, itemCategory);
        this.date = date;
        this.quantity = quantity;
    }

    /**
     * Constructor
     *
     * @param obj JSONObject representation of the transaction to be added
     *
     * @throws CorruptJSONObjectException
     */
    public Transaction(JSONObject obj) throws CorruptJSONObjectException,
            DateTimeParseException, InputMismatchException {
        String dateString = "";
        String temp;

        //process the transaction item
        if (obj.get("item") == null) {
            throw new CorruptJSONObjectException("Transaction does not have an item");
        }
        this.item = new Item((JSONObject) obj.get("item"));

        //process the quantity of items involved in the transaction
        if (obj.get("quantity") == null) {
            throw new CorruptJSONObjectException(
                    "No integer quantity attached to the item:\n" + this.item.toString());
        }
        try {
            temp = obj.get("quantity").toString();
            this.quantity = Integer.parseInt(temp);
        } catch (InputMismatchException e) {
            throw new CorruptJSONObjectException(
                    "No integer quantity attached to the item:\n" + this.item.toString());
        }
        
        //process the date of transaction
        if (obj.get("date") == null) {
            this.date = LocalDate.now();
        } else {
            try {
                dateString = (String) obj.get("date");
                this.date = LocalDate.parse(dateString);
            } catch (DateTimeParseException e) {
                throw new CorruptJSONObjectException(
                        "The date " + dateString + " must be in the format (yyyy-mm-dd)");
            } catch (Exception e) {
                throw new CorruptJSONObjectException("Transaction date can not be parsed");
            }
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
     * The quantity describes the number of items purchased in the transaction
     *
     * @return The quantity of items purchased in the transaction
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return The item, cost, and date of the transaction formatted as a string
     */
    @Override
    public String toString() {
        return "-----------Transaction----------"
                + "\n" + item
                + "\nQuantity purchased: " + quantity
                + "\nDate: " + date;
    }

}
