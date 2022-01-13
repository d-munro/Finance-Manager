package finance;

/**
 * Creates an exception which indicates that unexpected input occurred
 *
 * @author Dylan Munro
 */
public class InvalidRequestException extends Exception {

    /**
     * Default Constructor for InvalidRequestException class. By default,
     * InvalidRequestException has the message "The Request could not be completed"
     */
    public InvalidRequestException() {
        this("The Request could not be completed");
    }

    /**
     * Constructor for InvalidRequestException class
     *
     * @param message The message describing the exception
     */
    public InvalidRequestException(String message) {
        super(message);
    }

}