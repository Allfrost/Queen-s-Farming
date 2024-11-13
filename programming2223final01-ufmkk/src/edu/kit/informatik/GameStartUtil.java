package edu.kit.informatik;

/**
 * A util class for checking the validity of the parameters
 * 
 * @author ufmkk
 * @version 1.0
 */
public final class GameStartUtil {
    
    private GameStartUtil() {
        throw new IllegalStateException(Main.UTILITY_CLASS_INSTANTIATION);
    }
    
    /**
     * checks if the input is valid for being the target gold
     * @param input the input
     * @param app the application the game is running on
     * @return an error message if the input is invalid, null otherwise
     */
    public static String targetGoldCheck(String input, QueensFarming app) {
        int targetGold;
        try {
            targetGold = Integer.parseInt(input);
        }  catch (IllegalArgumentException illegalArgumentException) {
            return (Main.ERROR + "Illegal target gold.");
        }
        if (targetGold <= 0) {
            return (Main.ERROR + "Illegal target gold.");
        }
        app.setTargetGold(targetGold);
        return null;
    }

    /**
     * checks if the input is valid for being the starting gold
     * @param input the input
     * @param app the application the game is running on
     * @return an error message if the input is invalid, null otherwise
     */
    public static String startingGoldCheck(String input, QueensFarming app) {
        int startingGold;
        try {
            startingGold = Integer.parseInt(input);
        }  catch (IllegalArgumentException illegalArgumentException) {
            return (Main.ERROR + "Illegal starting gold.");
        }                                
        if (startingGold < 0) {
            return (Main.ERROR + "Illegal starting gold.");
        }
        app.setStartingGold(startingGold);
        return null; 
    }

    /**
     * checks if the input is valid for being the number of players
     * @param input the input
     * @return an error message if the input is invalid, null otherwise
     */
    public static String playerNumberCheck(String input) {
        int numberOfPlayers;
        try {
            numberOfPlayers = Integer.parseInt(input);
        }   catch (IllegalArgumentException illegalArgumentException) {
            return Main.ERROR + "Illegal number of players.";
        }
        if (numberOfPlayers <= 0) {
            return Main.ERROR + "Illegal number of players.";
        }
        return null;
    }

    /**
     * checks if the input is valid for being the seed
     * @param input the input
     * @param app the application the game is running on
     * @return an error message if the input is invalid, null otherwise
     */
    public static String seedFormatCheck(String input, QueensFarming app) {
        long seed;
        try {
            seed = Long.parseLong(input);
        }   catch (IllegalArgumentException illegalArgumentException) {
            return Main.ERROR + "Illegal formatted seed";
        }
        if (seed < -2147483648 || seed > 2147483647) return Main.ERROR + "Illegal formatted seed";
        app.shuffle(seed);
        return null;
    }
    
}
