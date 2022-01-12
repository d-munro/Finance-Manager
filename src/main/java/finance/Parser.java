//TODO - Parse user requests

package finance;

//imports
import java.util.Date;

/**
 * Creates a parser object to parse all user commands
 * @author Dylan Munro
 */
public class Parser {
    
    /**
     * Generates a request dealing with an account
     * @return 
     */
    public Request generateSimpleRequest(String action) throws InvalidInputException{
        Request request = new Request(action);
        return request;
    }
    
    public AccountRequest generateAccountRequest(String action, String accountName) throws InvalidInputException {
        AccountRequest request = new AccountRequest(action, accountName);
        return request;
    }
    
    /**
     * Generates a request dealing with a transaction
     * @param action The keyword describing how the user wishes to change the
     * transaction
     * @param itemName The name of the item involved in the transaction
     * @param itemFee The fee associated with the item involved in the transaction
     * @param itemCategory The broad category which the item involved in the transaction belongs to
     * @param date The date of the transaction
     * @param quantity The number of items involved in the transaction
     * 
     * @throws InvalidInputException
     * 
     * @return New TransactionRequest object
     */
    public TransactionRequest generateTransactionRequest(String action, String itemName, double itemFee,
            String itemCategory, Date date, int quantity) throws InvalidInputException {
        TransactionRequest request = new TransactionRequest(action, itemName, 
            itemFee, itemCategory, date, quantity);
        return request;
    }
    
    /**
     * Parses an action word and determines if it relates to an account, transaction, or neither
     * @param action The action word to be parsed
     * @return Request.ACCOUNT if action relates to an account, 
     * Request.TRANSACTION if action relates to a transaction 
     * Request.NONE if action relates to neither
     */
    public int getActionObject(String action) throws InvalidInputException {
        String[] words = action.split(" ");
        if (words.length == 1) {
            return Request.NONE;
        } else if (words.length == 2 && words[1].compareToIgnoreCase("account") == 0) {
            return Request.ACCOUNT;
        } else if (words.length == 2 && words[1].compareToIgnoreCase("transaction") == 0) {
            return Request.TRANSACTION;
        }
        throw new InvalidInputException("Request not recongized");
    }
}
