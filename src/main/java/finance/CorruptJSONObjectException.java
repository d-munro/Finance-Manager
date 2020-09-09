/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

/**
 *
 * @author dylmu
 */
public class CorruptJSONObjectException extends Exception{
    
    /**
     * Constructor
     * The default message is "Corrupt JSON Object"
     */
    public CorruptJSONObjectException() {
        this("Corrupt JSON Object");
    }
    
    /**
     * Constructor
     * @param message The message describing the Exception
     */
    public CorruptJSONObjectException(String message) {
        super(message);
    }
    
}
