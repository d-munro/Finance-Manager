package finance;

/**
 * Creates an exception which indicates that a request is unrecognized
 *
 * @author Dylan Munro
 */
public class InvalidRequestException extends Exception {

    /**
     * Creates InvalidRequestException with the message "The Request could not be completed"
     */
    public InvalidRequestException() {
        this("The request could not be completed");
    }

    /**
     * Creates InvalidRequestException with a custom message
     *
     * @param message The message describing the exception
     */
    public InvalidRequestException(String message) {
        super(message);
    }

}