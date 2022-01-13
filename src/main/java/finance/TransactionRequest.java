package finance;

//imports
import java.util.Date;

/**
 * Generates an TransactionRequest object which can be executed to add or delete a transaction to or from an account.
 * The TransactionRequest object contains all relevant details about the transaction
 * @author Dylan Munro
 */
public class TransactionRequest extends Request{
    
    //Details for creating a new transaction
    private String itemName;
    private double itemFee;
    private String itemCategory;
    private Date date;
    private int quantity;
    
    //Details for deleting a transaction
    private int id;
    
    /**
     * Generates a Request capable of building a new Transaction
     *
     * @param action The keyword describing how the user wishes to change the
     * transaction
     * @param itemName The name of the item involved in the transaction
     * @param itemFee The fee associated with the item involved in the transaction
     * @param itemCategory The broad category which the item involved in the transaction belongs to
     * @param date The date of the transaction
     * @param quantity The number of items involved in the transaction
     *
     * @throws InvalidInputException
     */        
    public TransactionRequest(String action, String itemName, double itemFee,
            String itemCategory, Date date, int quantity) throws InvalidInputException {
        super(action, true);
        this.itemName = itemName;
        this.itemFee = itemFee;
        this.itemCategory = itemCategory;
        this.date = date;
        this.quantity = quantity;
    }
    
    /**
     * Generates a Request capable of deleting a Transaction
     *
     * @param action The keyword describing how the user wishes to change the
     * transaction
     * @param id A unique identifier used to reference the transaction
     */
    public TransactionRequest(String action, int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemFee() {
        return itemFee;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public Date getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public int getId() {
        return id;
    }
}
