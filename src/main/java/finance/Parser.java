//TODO - Parse user requests

package finance;

/**
 * Creates a parser object to parse all user commands
 * @author Dylan Munro
 */
public class Parser {
    
    public Request generateRequest() {
        Request request = null;
        //TODO
        return request;
    }
    
    public void parseActionWord(String action) {
        String[] words = action.split(" ");
        if (words.length == 1) {
            //request objectToModify = NONE
        } else if (words.length == 2 && words[1].compareToIgnoreCase("account") == 0) {
            //request objectToModify = ACCOUNT
        } else if (words.length == 2 && words[1].compareToIgnoreCase("transaction") == 0) {
            //request objectToModify = TRANSACTION
        }
    }
}
