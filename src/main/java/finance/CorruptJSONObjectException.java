package finance;

/**
 * Throws an exception describing errors with a JSONObject
 *
 * @author Dylan Munro
 */
public class CorruptJSONObjectException extends Exception {

    /**
     * Constructor The default message is "Corrupt JSON Object"
     */
    public CorruptJSONObjectException() {
        this("Corrupt JSON Object");
    }

    /**
     * Constructor
     *
     * @param message The message describing the Exception
     */
    public CorruptJSONObjectException(String message) {
        super(message);
    }

}
