package finance;

/**
 * Creates an exception which indicates that unexpected input occurred
 * @author Dylan Munro
 */
public class InvalidInputException extends Exception{
    
    /**
     * Default Constructor for InvalidInputException class
     * By default, InvalidInputException has the message "Error: Invalid Input"
     */
    public InvalidInputException() {
        this("Error: Invalid Input");
    }
    
    /**
     * Constructor for InvalidInputException class
     * @param message The message describing the exception
     */
    public InvalidInputException(String message) {
        super(message);
    }
    
    /**
     * Returns the message used to describe the InvalidInputException
     * @return The message used to describe the InvalidInputException
     */
    @Override
    public String toString() {
        return super.getMessage();
    }
    
}
