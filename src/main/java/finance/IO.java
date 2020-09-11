/*------------------------TODO---------------------------
Display menu of all namesToAccounts and allow user to select one
Display a meaningful message if user enters an invalid filepath
    (No valid json file is present at location)
Don't put account prompting in load
Change fmanager implementation in main
 */
package finance;

//imports
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
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
    public void createAccount(Scanner input) {
        String name;
        System.out.println("Enter the name of the account");
        name = input.nextLine();
        manager.addAccount(new Account(name));
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
     * Loads all preliminary data needed to execute program
     */
    private void load(Scanner input) {
        try {
            loadFiles(input);
        } catch (IOException | CorruptJSONObjectException e) {
            System.out.println(e.getMessage());
        }

        //create an account
        Account newAccount;
        String userInput = getYesOrNoResponse(
                "Would you like to create a new account? (Yes/No)", input);
        if (userInput.compareToIgnoreCase("Yes") == 0) {
            createAccount(input);
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
     * Creates various namesToAccounts from a JSONObject
     *
     * @param obj JSONObject containing various details about an account
     * @throws CorruptJSONObjectException
     */
    private void loadAccountDetails(JSONObject obj) throws
            CorruptJSONObjectException {
        Account newAccount;
        JSONArray accountsArray = (JSONArray) obj.get("accounts");
        for (int i = 0; i < accountsArray.size(); i++) {
            manager.addAccount(new Account((JSONObject) accountsArray.get(i)));
        }
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
                loadAccountDetails(accountsJson);
            } catch (CorruptJSONObjectException e) {
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
        AccountManager manager = new AccountManager();
        Request currentRequest;
        System.out.println("To view a list of all options, type \"help\"");
        while (userChoice.compareToIgnoreCase("quit") != 0) {
            try {
                currentRequest = new Request(input.nextLine());
                manager.executeRequest(currentRequest);
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //TODO display all accounts, prompt user to select one, change 
    public void selectAccount(Scanner input) throws AccountNotFoundException {
        System.out.println("The accounts currently loaded are:");
        for (String current : manager.getAccountNames()) {
            System.out.println(current);
        }
        System.out.println("Enter an account to view:");
        manager.setActiveAccount(input.nextLine());
    }

}
