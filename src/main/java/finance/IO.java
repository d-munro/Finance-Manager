/*------------------------TODO---------------------------
Display a meaningful message if user enters an invalid filepath
    (No valid json file is present at location)
Don't put account prompting in load
Check if requests containing args are for accounts or classes
Rewrite to work with new Request implementation
 */
package finance;

//imports
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles all input and output for the finance package
 *
 * @author Dylan Munro
 */
public class IO {

    private AccountManager manager = new AccountManager();

    /**
     * Creates a new account if desired
     *
     * @param input The Scanner reading the user's input
     */
    /*public void createAccount(Scanner input) {
        String name;
        System.out.println("Enter the name of the account");
        name = input.nextLine();
        manager.addAccount(new Account(name));
    }*/
    /**
     * Displays all options that a user can choose to display info
     */
    /*public void displayOptions() {
        System.out.println("Type create to make a new account");
        System.out.println("Type delete to delete an account");
        System.out.println("Type edit to modify the transactions for the current account");
        System.out.println("Type history to display all previous transactions"
                + " for the current account");
        System.out.println("Type open to open a new account");
        System.out.println("Type quit to terminate the program");
        System.out.println("Type sort to sort account transactions");
        System.out.println("Type display accounts to display all accounts loaded");
    }*/

    private void editAccount(Scanner input) throws NumberFormatException{
        String cont;
        String name;
        double fee;
        String category;
        int quantity;
        do {
            System.out.println("Enter the name of the item");
            name = input.nextLine();
            System.out.println("Enter the category of the item");
            category = input.nextLine();
            System.out.println("Enter the cost of the item");
            fee = getDouble(input, "Enter the cost of the item");
            quantity = (int)getDouble(input, "Enter the quantity of the item purchased");           
            cont = getYesOrNoResponse("Would you like to add a transaction to the account? (Yes/No)", input);
            try {
              manager.executeRequest(new Request("add transaction"));                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (cont.compareToIgnoreCase("no") != 0);
        
    }
    
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
    private String getRequestArgs(String requestChoice, Scanner input) {
        String params = null;
        switch (requestChoice) { //Handles cases where multiple parameters are needed
            case "add account":
                System.out.println("Enter the name of the account:");
                params = input.nextLine();
                break;
            case "change":
                params = input.nextLine();
                break;
            case "delete account":
                System.out.println("Enter the name of the account to delete:");
                params = input.nextLine();
                break;
            /*case "history":
                System.out.println("");
                break;*/
            case "open":
                System.out.println("Enter the name of the account to open");
                params = input.nextLine();
                break;
            /*case "quit":
                System.out.println("");
                break;*/
            case "sort":
                System.out.println("Enter 1 to sort the "
                        + "transactions chronologically");
                System.out.println("Enter 2 to sort the "
                        + "transactions by cost");
                System.out.println("Enter 3 to sort the "
                        + "transactions by category");
                params = input.nextLine();
                break;
        }
        return params;
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
                currentRequest = new Request(userChoice, getAccountRequestParameters(userChoice, input));
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
