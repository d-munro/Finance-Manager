//TODO create ability to load accounts from JSON file

package finance;

import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Handles all input and output for the finance package
 * @author Dylan Munro
 */
public class FinanceManager {

    private LinkedList<Account> accounts = new LinkedList<>();

    /**
     * Creates a new account if desired
     *
     * @param input The Scanner reading the user's input
     */
    public void createAccount(Scanner input) {
        String userInput = getYesOrNoResponse(
                "Would you like to create a new account? (Yes/No)", input);
        if (userInput.compareToIgnoreCase("Yes") == 0) {
            accounts.add(initializeAccountDetails(input));
        }
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
     * Creates and returns the details of a new finance account
     *
     * @return The Account created
     */
    private Account initializeAccountDetails(Scanner input) {
        String name;
        System.out.println("Enter the name of the account");
        name = input.nextLine();
        return new Account(name);
    }

    /**
     * Returns true if the string is yes or no (Case insensitive), false
     * otherwise
     *
     * @param str The string being compared to yes and no
     * @return True if the string is equal to yes or no, false otherwise
     */
    public boolean isYesOrNoResponse(String str) {
        return (str.compareToIgnoreCase("Yes") == 0
                || str.compareToIgnoreCase("No") == 0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FinanceManager manager = new FinanceManager();
        manager.run();
    }

    /**
     * Main method to handle program execution
     */
    public void run() {
        Scanner input = new Scanner(System.in);
        //loadAccounts(input);
        createAccount(input);
    }
}
