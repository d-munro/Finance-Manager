/*------------------------TODO---------------------------
Implement delete and sort transaction functionality
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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

/**
 * Handles all input and output for the finance package
 *
 * @author Dylan Munro
 */
public class IO {
    
    //When testMode is on, automatically loads accounts.json into program
    private boolean testMode = false;

    private final AccountManager manager = new AccountManager();
    private final Parser parser = new Parser();

    /**
     * Obtains a double from a prompt with exception checking
     *
     * @param input The Scanner reading the user's input
     * @param prompt The question to prompt the user with
     * @return A valid double
     */
    public double getDouble(Scanner input, String prompt) {
        double response = 0;
        boolean isValidResponse = false;
        System.out.println(prompt);
        while (!isValidResponse) {
            try {
                response = input.nextDouble();
                isValidResponse = true;
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number containing only decimal digits");
                input.nextLine(); //Clear newline char entered with number
            }
        }
        return response;
    }

    /**
     * Obtains an int from a prompt with exception checking
     *
     * @param input The Scanner reading the user's input
     * @param prompt The question to prompt the user with
     * @return A valid int
     */
    public int getInt(Scanner input, String prompt) {
        int response = 0;
        boolean isValidResponse = false;
        System.out.println(prompt);
        while (!isValidResponse) {
            try {
                response = input.nextInt();
                isValidResponse = true;
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer");
                input.nextLine(); //Clear newline char entered with number
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
    /*private BufferedReader getAccountFilePath(Scanner input) {
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
    }*/
    
    private BufferedReader getAccountFilePath(Scanner input) throws IOException {
        BufferedReader fileStream = null;
        if (!testMode) {
            System.out.println("Enter the path to the file with the account details");
            String userResponse = input.nextLine();
            try {
                fileStream = new BufferedReader(new FileReader(userResponse));
            } catch (IOException e) {
                throw new IOException("The file \"" + userResponse + "\" could not be found");
            }           
        } 
        if (testMode) {
            //DELETE AFTER TESTING
            return new BufferedReader(new FileReader("src/main/resources/accounts.json"));
        }
        return fileStream;
    }

    /**
     * Obtains all necessary user input to create an AccountRequest object and
     * creates it
     *
     * @param action The action word describing the functionality of the Request
     * @param input The Scanner which user input is being read from
     *
     * @return The newly created AccountRequest object
     */
    private AccountRequest getAccountRequest(String action, Scanner input)
            throws InvalidRequestException, AccountNotFoundException {
        AccountRequest request;
        String userInput;
        switch (action) { //Handles cases where multiple parameters are needed
            case "add account":
                System.out.println("Enter the name of the account:");
                break;
            case "change account":
                if (manager.getNumOfAccountsLoaded() == 0) {
                    throw new AccountNotFoundException("You must load or create an account first");
                }
                System.out.println("Enter the name of the account to change to:");
                break;
            case "delete account":
                if (manager.getNumOfAccountsLoaded() == 0) {
                    throw new AccountNotFoundException("You must load or create an account first");
                }
                System.out.println("Enter the name of the account to delete:");
                break;
            default:
                throw new InvalidRequestException("The specified action could not be found");
        }
        userInput = input.nextLine();
        request = new AccountRequest(action, userInput);
        return request;
    }

    /**
     * Obtains all necessary user input to create an SortingRequest object and
     * creates it
     *
     * @param action The action word describing the functionality of the Request
     * @param input The Scanner which user input is being read from
     *
     * @return The newly created SortingRequest object
     */
    private SortingRequest getSortingRequest(String action, Scanner input)
            throws InvalidRequestException, AccountNotFoundException {
        if (!manager.hasActiveAccount()) {
            throw new AccountNotFoundException("Please select an account first");
        }
        String prompt = "Enter 1 to sort the transactions chronologically"
                + "\nEnter 2 to sort the transactions by cost"
                + "\nEnter 3 to sort the transactions by category";
        int sortingMethod = getInt(input, prompt);
        SortingRequest request = new SortingRequest(action, sortingMethod);
        return request;
    }

    /**
     * Obtains all necessary user input to create an TransactionRequest object
     * and creates it
     *
     * @param action The action word describing the functionality of the Request
     * @param input The Scanner which user input is being read from
     *
     * @return The newly created TransactionRequest object
     */
    private TransactionRequest getTransactionRequest(String action, Scanner input)
            throws InvalidRequestException, AccountNotFoundException, TransactionNotFoundException {
        TransactionRequest request = null;
        switch (action) {
            case "add transaction":
                if (!manager.hasActiveAccount()) {
                    throw new AccountNotFoundException("No active account selected");
                }
                System.out.println("Enter the name of the item");
                String itemName = input.nextLine();
                System.out.println("Enter the category of the item");
                String itemCategory = input.nextLine();
                double itemFee = getDouble(input, "Enter the fee associated with the item");
                int quantity = getInt(input, "Enter the quantity of the item purchased");
                LocalDate purchaseDate = getDate(input,
                        "Enter the date of the transaction (yyyy-mm-dd)."
                        + "\nAlternatively, enter \"today\" if the item was purchased today");
                request = new TransactionRequest(action, itemName, itemFee,
                        itemCategory, purchaseDate, quantity);
                break;
            case "delete transaction":
                if (!manager.hasActiveAccount()) {
                    throw new AccountNotFoundException("No active account selected");
                }
                printTransactions();
                int transactionNumber = getInt(input,
                        "Enter the transaction number you wish to delete");
                request = new TransactionRequest(action, transactionNumber);
                break;
            default:
                throw new InvalidRequestException("The specified action could not be found");
        }
        return request;
    }

    /**
     * Generates a date given a prompt and checks if the date is legitimate
     *
     * @param input The Scanner which input is being read from
     * @param prompt The question being asked to the user
     */
    private LocalDate getDate(Scanner input, String prompt) {
        LocalDate date = LocalDate.now();
        boolean isValid = false;
        String userInput;
        while (!isValid) {
            try {
                System.out.println(prompt);
                userInput = input.nextLine();
                date = generateDate(userInput);
                isValid = true;
            } catch (Exception e) {
                System.out.println("Please enter a valid date in the format (yyyy-mm-dd)");
                System.out.println("Alternatively, type \"today\" to enter the current date");
            }
        }
        return date;
    }

    /**
     * Generates a date from a String given in the form (yyyy-mm-dd).
     * Alternatively, if the String given is "today", the current date is
     * generated.
     *
     * @param str The date formatted as a string in the format (yyyy-mm-dd)
     *
     * @throws InvalidInputException
     *
     * @return A date object representing the input String
     */
    private LocalDate generateDate(String str) throws ParseException {
        if (str.compareToIgnoreCase("today") == 0) {
            return LocalDate.now();
        }
        return LocalDate.parse(str);
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
     *
     * @param str The string being converted
     * @return true if the string can be converted to an integer, false
     * otherwise
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
        JSONParser jParser = new JSONParser();
        try {
            accountsJSON = (JSONObject) jParser.parse(inputStream);
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
        
        //DELETE AFTER TESTING
        if (testMode) {
            try {
                manager.generateAccounts(loadAccountsJSON(getAccountFilePath(input)));                
            } catch (Exception e ) {
                System.out.println(e.getMessage());
            }
            return;
        }
        
        JSONObject accountsJson;
        BufferedReader activeStream = null;
        boolean loadedValidFile = false;
        String userResponse = getYesOrNoResponse(
                "Would you like to load a file with account details? (Yes/No)", input);
        if (userResponse.compareToIgnoreCase("Yes") == 0) {
            while (!loadedValidFile) {
                try {
                    activeStream = getAccountFilePath(input);
                    accountsJson = loadAccountsJSON(activeStream);
                    manager.generateAccounts(accountsJson);
                    loadedValidFile = true;
                } catch (CorruptJSONObjectException | InputMismatchException
                        | AccountNotFoundException | DateTimeParseException e) {
                    System.out.println(e.getMessage());
                    userResponse = getYesOrNoResponse(
                            "Would you like to load a different file? (Yes/No)", input);
                    if (userResponse.compareToIgnoreCase("no") == 0) {
                        return;
                    }
                } finally {
                    if (activeStream != null) {
                        activeStream.close();
                    }
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
     *
     * @throws InvalidRequestException
     * @throws AccountNotFoundException
     * @throws TransactionNotFoundException
     */
    public void printTransactions() throws InvalidRequestException,
            AccountNotFoundException, TransactionNotFoundException {
        System.out.println("Here are all transactions for the current account:");
        System.out.println(manager.executeRequest(parser.generateRequest("display transaction")));
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
                    case Request.SORTING:
                        currentRequest = getSortingRequest(userChoice, input);
                        break;
                    default:
                        currentRequest = parser.generateRequest(userChoice);
                        break;
                }
                output = manager.executeRequest(currentRequest);
                System.out.println(output);
            } catch (InvalidRequestException | AccountNotFoundException | TransactionNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
