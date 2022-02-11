package finance;

/**
 * Creates an Exception describing errors when loading a JSON File
 *
 * @author Dylan Munro
 */
public class CorruptJSONObjectException extends Exception {

    /**
     * Creates a CorruptJSONObjectException with the message "Corrupt JSON Object"
     */
    public CorruptJSONObjectException() {
        this("Corrupt JSON Object");
    }

    /**
     * Creates a CorruptJSONObjectException with a custom message
     *
     * @param message The message describing the Exception
     */
    public CorruptJSONObjectException(String message) {
        super(message);
    }

}
