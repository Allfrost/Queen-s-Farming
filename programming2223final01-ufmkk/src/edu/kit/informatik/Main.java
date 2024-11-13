package edu.kit.informatik;

import edu.kit.informatik.vector2d.Vector2d;

import java.util.Scanner;

/**
 * The main class used for IO and defining global constants
 * 
 * @author ufmkk
 * @version 1.0
 */
public final class Main {
    /**
     *  The template for the error messages
     */
    public static final String ERROR = "Error: ";

    /**
     * The first parameter index for the commands
     */
    public static final int FIRST_PARAMETER_INDEX = 1;

    /**
     *  the coordinates of the origin also the barn
     */
    public static final Vector2d ORIGIN = new Vector2d(0, 0);
    /**
     * Exception message in case of utility class instantiation.
     */
    public static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";

    private Main() {
        throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION);
    }
    
    /**
     * the main method used for reading inputs and printing
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numberOfPlayers = 0;
        Scanner inputScanner = new Scanner(System.in);
        boolean earlyQuit = false;
        if (args.length > 0) throw new IllegalArgumentException("Error:Command_line_arguments_are_not_allowed");
        System.out.println(AsciiArtUtil.artQF());
        System.out.println("How many players?");
        while (true) {  // reads how many players there will be
            String input = inputScanner.nextLine();
            if (input.equals("quit")) {
                earlyQuit = true;
                break; }
            String output = GameStartUtil.playerNumberCheck(input);
            if (output == null) {
                numberOfPlayers = Integer.parseInt(input);
                break; }
            else System.out.println(output);
        }
        QueensFarming app = new QueensFarming(numberOfPlayers); // initiates the app
        if (!earlyQuit) {
            for (int i = 0; i < numberOfPlayers; ++i) { // initiates the players
                System.out.printf("Enter the name of player %d:\n", i + 1);
                while (true) {
                    boolean nameControl;
                    String name = inputScanner.nextLine();
                    if (name.equals("quit")) {
                        earlyQuit = true;
                        break; }
                    nameControl = app.initiatePlayer(name);
                    if (nameControl) break;
                    else System.out.println(ERROR + "Illegal player name.");
                }
            }
        }
        if (!earlyQuit) {
            System.out.println("With how much gold should each player start?");
            while (true) { // reads the starting gold and checks if it is valid
                String input = inputScanner.nextLine();
                if (input.equals("quit")) {
                    earlyQuit = true;
                    break; }
                String output = GameStartUtil.startingGoldCheck(input, app);
                if (output == null) break;
                else System.out.println(output);
            }
        }
        if (!earlyQuit) {
            System.out.println("With how much gold should a player win?");
            while (true) {  // reads the target gold and checks if it is valid
                String input = inputScanner.nextLine();
                if (input.equals("quit")) {
                    earlyQuit = true;
                    break; }
                String output = GameStartUtil.targetGoldCheck(input, app);
                if (output == null) break;
                else System.out.println(output);
            }
        }
        if (!earlyQuit) {
            System.out.println("Please enter the seed used to shuffle the tiles:");
            while (true) { // reads the seed and checks if it is valid
                String input = inputScanner.nextLine();
                if (input.equals("quit")) { 
                    earlyQuit = true;
                    break; }
                String output = GameStartUtil.seedFormatCheck(input, app);
                if (output == null) break;
                else System.out.println(output);
            }
        }
        if (earlyQuit) app.setRunning(false);
        while (app.isRunning()) { // if the app is still running take commands and output the results
            String output;
            if (app.getRemainingActions() == 0) output = app.changePlayer();
            else {
                String input = inputScanner.nextLine();
                output = Command.executeCommand(input, app);
            } if (output != null) System.out.println(output);
        } if (!earlyQuit) System.out.println(app.showPlayers()); // show the winner upon exiting
    }
}
