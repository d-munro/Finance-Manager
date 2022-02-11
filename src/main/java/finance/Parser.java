package finance;

//imports
import java.time.LocalDate;
import java.util.HashMap;

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
     * @param transactionNumber The index of the transaction in the account records
     *
     * @throws InvalidRequestException
     *
     * @return New TransactionRequest object
     */
    public TransactionRequest generateTransactionRequest(String action, int transactionNumber)
            throws InvalidRequestException {
        return new TransactionRequest(action, transactionNumber);
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
     * Request.TRANSACTION if action relates to a transaction,
     * Request.NONE if action relates to neither
     */
    public int getActionObject(String action) throws InvalidRequestException {
        String[] words = action.split(" ");
        HashMap<String, String> oneParamActions = Request.getONE_PARAM_ACTION_DESCRIPTIONS();
        if (words.length == 1 || oneParamActions.containsKey(action)) {
            return Request.NONE;
        } else if (words.length == 2 && words[1].compareToIgnoreCase("account") == 0) {
            return Request.ACCOUNT;
        } else if (words.length == 2 && words[1].compareToIgnoreCase("transaction") == 0) {
            return Request.TRANSACTION;
        }
        throw new InvalidRequestException("Request not recongized");
    }
}
