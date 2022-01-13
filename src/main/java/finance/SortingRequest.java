package finance;

//imports
import java.util.HashMap;

/**
 * Generates an SortingRequest object which can be executed to sort the transactions 
 * in an account
 * @author Dylan Munro
 */
public class SortingRequest extends Request{

    private int sortingMethod;
    
    private static final HashMap<Integer, Boolean> VALID_SORTING_METHODS = new HashMap<Integer, Boolean>();
    private static final int SORTED_CHRONOLOGICALLY = 1;
    private static final int SORTED_BY_COST = 2;
    private static final int SORTED_BY_CATEGORY = 3;  
 
    static {
        VALID_SORTING_METHODS.put(SORTED_CHRONOLOGICALLY, true);
        VALID_SORTING_METHODS.put(SORTED_BY_COST, true);
        VALID_SORTING_METHODS.put(SORTED_BY_CATEGORY, true);
    } 
    /**
     * Constructor
     *
     * @param action The keyword describing how the user wishes to change the
     * account
     * @param sortingMethod The method of which transactions are to be sorted by
     *
     * @throws InvalidRequestException
     */        
    public SortingRequest(String action, int sortingMethod) throws InvalidRequestException {
        super(action, true);
        if (!VALID_SORTING_METHODS.containsKey(sortingMethod)) {
            throw new InvalidRequestException("Invalid sorting method.");
        }
        this.sortingMethod = sortingMethod;
    }
    
    public int getSortingMethod() {
        return sortingMethod;
    }
    
    public static HashMap<Integer, Boolean> getVALID_SORTING_METHODS() {
        return VALID_SORTING_METHODS;
    }
}
