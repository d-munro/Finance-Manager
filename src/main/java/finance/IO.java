/*------------------------TODO---------------------------
Display a meaningful message if user enters an invalid filepath
    (No valid json file is present at location)
Don't put account prompting in load
Implement generateDate();
 */
package finance;

//imports
//simple json parser imports
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//io imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import java.util.Date;

/**
 * Handles all input and output for the finance package
 *
 * @author Dylan Munro
 */
public class IO {

    private final AccountManager manager = new AccountManager();
    private final Parser parser = new Parser();
    
    /**
     * Obtains a double from a prompt with exception checking
     * @param input The Scanner reading the user's input
     * @param prompt The question to prompt the user with
     * @return A valid double
     */
    public double getDouble(Scanner input, String prompt) {
        double response = 0;
        boolean isValidResponse = false;
        while (!isValidResponse) {
            try {
                System.out.println(prompt);
                response = input.nextDouble();
                isValidResponse = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number without alphabetical characters");
            }           
        }
        return response;
    }
    
    /**
     * Obtains an int from a prompt with exception checking
     * @param input The Scanner reading the user's input
     * @param prompt The question to prompt the user with
     * @return A valid int
     */
    public int getInt(Scanner input, String prompt) {
        int response = 0;
        boolean isValidResponse = false;
        while (!isValidResponse) {
            try {
                System.out.println(prompt);
                response = input.nextInt();
                isValidResponse = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
            }           
        }
        return response;
    }
    
    /**
     * Obtains the path from the user to the JSON file with account details
     *
     * @param input The Scanner which input is being read from
     * @return The path to the JSON file with account details
     */
    private BufferedReader getAccountFilePath(Scanner input) {
        BufferedReader fileStream = null;
        boolean isValidFile = false;
        while (!isValidFile) {
            System.out.println("Enter the path to the file with the account details");
            try {
                fileStream = new BufferedReader(new FileReader(input.nextLine()));
                isValidFile = true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return fileStream;
    }

    /**
     * Obtains all necessary user input to create a request object for an
     * account
     *
     * @param requestChoice The user's decision as to what to do next
     * @param input The Scanner which user input is being read from
     * @return String containing necessary parameters to initialize a request
     * object
     */
    private AccountRequest getAccountRequest(String action, Scanner input) throws InvalidInputException {
        AccountRequest request = null;
        String userInput;
        switch (action) { //Handles cases where multiple parameters are needed
            case "add account":
                System.out.println("Enter the name of the account:");
                break;
            case "change account":
                System.out.println("Enter the name of the account to change to");
                break;
            case "delete account":
                System.out.println("Enter the name of the account to delete:");
                break;
        }
        userInput = input.nextLine();
        request = new AccountRequest(action, userInput);
        return request;
    }
    
    private TransactionRequest getTransactionRequest(String action, Scanner input) throws InvalidInputException {
        TransactionRequest request = null;
        switch(action) {
            case "add transaction":
                System.out.println("Enter the name of the item");
                String itemName = input.nextLine();
                System.out.println("Enter the category of the item");
                String itemCategory = input.nextLine();
                System.out.println("Enter the cost of the item");
                double itemFee = getDouble(input, "Enter the fee associated with the item");
                int quantity = getInt(input, "Enter the quantity of the item purchased");
                Date purchaseDate = generateDate(input, 
                        "Enter the date of the transaction (yyyy-mm-dd)");
                request = new TransactionRequest(action, itemName, itemFee,
                    itemCategory, purchaseDate, quantity);
                break;
            case "delete transaction":
                printTransactions();
                int transactionNum = getInt(input,
                        "Enter the number of the transaction you wish to delete");
                request = new TransactionRequest(action, transactionNum);
                break;
            case "sort transaction":
                String prompt = "Enter 1 to sort the transactions chronologically"
                        + "\nEnter 2 to sort the transactions by cost"
                        + "\nEnter 3 to sort the transactions by category";
                int sortingMethod = getInt(input, prompt);
                request = new TransactionRequest(action, sortingMethod);
                break;
            default:
                throw new InvalidInputException("The specified action could not be found\n");
        }
        return request;
    }
    
    /**
     * Generates a date given a prompt and checks if the date is legitimate
     * @param input The Scanner which input is being read from
     * @param prompt The question being asked to the user
     */
    private Date generateDate(Scanner input, String prompt) {
        Date date = new Date();
        
        return date;
    }

    /**
     * Obtains a yes or no response from the user to a question
     *
     * @param question The question being asked
     * @param input The Scanner which input is being read from
     * @return The user's response to the question (yes or no)
     */
    private String getYesOrNoResponse(String question, Scanner input) {
        String response = "";
        while (!isYesOrNoResponse(response)) {
            try {
                System.out.println(question);
                response = input.nextLine();
                if (!isYesOrNoResponse(response)) {
                    throw new InvalidInputException("Please enter Yes or No");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
        return response;
    }

    /**
     * Returns true if the string is yes or no (Case insensitive), false
     * otherwise
     *
     * @param str The string being compared to yes and no
     * @return True if the string is equal to yes or no, false otherwise
     */
    private boolean isYesOrNoResponse(String str) {
        return (str.compareToIgnoreCase("Yes") == 0
                || str.compareToIgnoreCase("No") == 0);
    }
    
    /**
     * Determines if a string can be converted to an integer
     * @param str The string being converted
     * @return true if the string can be converted to an integer, false otherwise
     */
    private boolean isInteger(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) >= 48 && str.charAt(i) <= 57)) { //Check if ascii value of char gives a digit
                return false;
            }
        }
        return true;
    }

    /**
     * Loads all preliminary data needed to execute program
     */
    private void load(Scanner input) {
        try {
            loadFiles(input);
        } catch (IOException | CorruptJSONObjectException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Opens the JSON file containing details about the namesToAccounts
     *
     * @param filePath The path to the JSON file
     */
    private JSONObject loadAccountsJSON(BufferedReader inputStream) {
        JSONObject accountsJSON = null;
        JSONParser parser = new JSONParser();
        try {
            accountsJSON = (JSONObject) parser.parse(inputStream);
        } catch (ParseException | IOException e) {
            System.out.println(e.getMessage());
        }
        return accountsJSON;
    }

    /**
     * Loads all files needed to execute the program
     *
     * @param input The Scanner which input is being read from
     * @throws CorruptJSONObjectException, IOException
     */
    private void loadFiles(Scanner input) throws CorruptJSONObjectException,
            IOException {
        JSONObject accountsJson;
        BufferedReader activeStream = null;
        String userResponse = getYesOrNoResponse(
                "Would you like to load a file with account details? (Yes/No)", input);
        if (userResponse.compareToIgnoreCase("Yes") == 0) {
            try {
                activeStream = getAccountFilePath(input);
                accountsJson = loadAccountsJSON(activeStream);
                manager.generateAccounts(accountsJson);
            } catch (CorruptJSONObjectException | AccountNotFoundException e) {
                System.out.println(e.getMessage());
            } finally {
                if (activeStream != null) {
                    activeStream.close();
                }
            }
        }
    }

    /**
     * Initial method called
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IO ioHandler = new IO();
        Scanner input = new Scanner(System.in);
        ioHandler.load(input);
        ioHandler.run(input);
    }
    
    /**
     * Prints all transactions for the account selected in the account manager
     */
    public void printTransactions() {
        
    }

    /**
     * Main method used for running the program
     *
     * @param input The Scanner which input is being read from
     */
    private void run(Scanner input) {
        String userChoice = "";
        String output;
        Request currentRequest;
        System.out.println("To view a list of all options, type \"help\"");
        while (userChoice.compareToIgnoreCase("quit") != 0) {
            System.out.println("What would you like to do?");
            System.out.println(AccountManager.getHelp());
            userChoice = input.nextLine();
            try {
                switch (parser.getActionObject(userChoice)) {
                    case Request.ACCOUNT:
                        currentRequest = getAccountRequest(userChoice, input);
                        break;
                    case Request.TRANSACTION:
                        currentRequest = getTransactionRequest(userChoice, input);
                        break;
                    default:
                        currentRequest = parser.generateSimpleRequest(userChoice);
                        break;
                }
                output = manager.executeRequest(currentRequest);
                System.out.println(output + "\n");
            } catch (InvalidInputException | AccountNotFoundException e) {
                System.out.println(e.getMessage() + "\n");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    public void selectAccount(Scanner input) throws AccountNotFoundException {
        System.out.println("The accounts currently loaded are:");
        for (String current : manager.getAccountNames()) {
            System.out.println(current);
        }
        System.out.println("Enter an account to view:");
        //manager.setActiveAccount(input.nextLine());
    }

}
