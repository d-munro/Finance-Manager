package finance;

//imports
import java.time.LocalDate;

/**
 * Creates a parser object to generate requests from user commands
 *
 * @author Dylan Munro
 */
public class Parser {

    /**
     * Generates a Request which describes an action for the AccountManager to take
     *
     * @param action The action word describing the Request
     * 
     * @throws InvalidRequestException
     *
     * @return The created Request object
     */
    public Request generateRequest(String action) throws InvalidRequestException {
        return new Request(action);
    }

    /**
     * Generates an AccountRequest which describes an action for the AccountManager 
     * to take concerning the details of an account
     *
     * @param action The action word describing the Request
     * @param accountName The name of the account
     *
     * @return The created AccountRequest object
     *
     * @throws InvalidRequestException
     *
     */
    public AccountRequest generateAccountRequest(String action, String accountName) throws InvalidRequestException {
        return new AccountRequest(action, accountName);
    }
    

    /**
     * Generates an AccountRequest which describes the details of an account
     *
     * @param action The action word describing the Request
     * @param accountId The unique identifier referring to the account
     *
     * @return The created AccountRequest object
     *
     * @throws InvalidRequestException
     *
     */
    public AccountRequest generateAccountRequest(String action, int accountId) 
            throws InvalidRequestException {
        return new AccountRequest(action, accountId);
    }
    

    /**
     * Generates a TransactionRequest which describes the details and modifications of a transaction
     *
     * @param action The keyword describing how the user wishes to change the
     * transaction
     * @param itemName The name of the item involved in the transaction
     * @param itemFee The fee associated with the item involved in the
     * transaction
     * @param itemCategory The broad category which the item involved in the
     * transaction belongs to
     * @param date The date of the transaction
     * @param quantity The number of items involved in the transaction
     *
     * @throws InvalidRequestException
     *
     * @return New TransactionRequest object
     */
    public TransactionRequest generateTransactionRequest(String action, String itemName, double itemFee,
            String itemCategory, LocalDate date, int quantity) throws InvalidRequestException {
        return new TransactionRequest(action, itemName,
                itemFee, itemCategory, date, quantity);
    }
    
    /**
     * Generates a TransactionRequest which describes the details and modifications of a transaction
     *
     * @param action The keyword describing how the user wishes to change the
     * transaction
     * @param transactionId The unique identifier referring to the transaction
     *
     * @throws InvalidRequestException
     *
     * @return New TransactionRequest object
     */
    public TransactionRequest generateTransactionRequest(String action, int transactionId)
            throws InvalidRequestException {
        return new TransactionRequest(action, transactionId);
    }
        
    /**
     * Generates a SortingRequest to sort transactions in various ways
     * 
     * @param action The action word describing the SortingRequest
     * @param sortingMethod The method of which transactions are to be sorted
     * 
     * @throws InvalidRequestException
     * 
     * @return The SortingRequest object describing the sorting method
     */
    public SortingRequest generateSortingRequest(String action, int sortingMethod) throws InvalidRequestException {
        return new SortingRequest(action, sortingMethod);
    }

    /**
     * Parses an action word and determines if it relates to an account,
     * transaction, or neither
     *
     * @param action The action word to be parsed
     * 
     * @throws InvalidRequestException
     * 
     * @return Request.ACCOUNT if action relates to an account,
     * Request.TRANSACTION if action relates to a transaction Request.NONE if
     * action relates to neither
     */
    public int getActionObject(String action) throws InvalidRequestException {
        String[] words = action.split(" ");
        if (words.length == 1) {
            return Request.NONE;
        } else if (words.length == 2 && words[1].compareToIgnoreCase("account") == 0) {
            return Request.ACCOUNT;
        } else if (words.length == 2 && words[1].compareToIgnoreCase("transaction") == 0) {
            return Request.TRANSACTION;
        }
        throw new InvalidRequestException("Request not recongized");
    }
}
