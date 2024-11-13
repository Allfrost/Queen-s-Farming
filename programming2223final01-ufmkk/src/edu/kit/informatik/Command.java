package edu.kit.informatik;

import edu.kit.informatik.vector2d.Vector2d;
import edu.kit.informatik.vegetable.Vegetable;
import edu.kit.informatik.vegetable.VegetableType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The command recognition system of the game
 * 
 * @author ufmkk
 * @version 1.0
 */
public enum Command {

    /**
     * command for ending the turn
     */
    END_TURN ("end turn") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            return app.changePlayer();
        }
    },

    /**
     *  command for selling vegetable
     */
    SELL ("sell" + "(( all)|( tomato| mushroom| carrot| salad)+)?") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            if (input.group(Main.FIRST_PARAMETER_INDEX - 1).equals("sell")) {
                return app.getActivePlayer().sell(" ", app);
            }
            return app.getActivePlayer().sell(input.group(Main.FIRST_PARAMETER_INDEX).substring(1), app);
        }
    },

    /**
     *   command for harvesting
     */
    HARVEST ("harvest (-?\\d+) (-?\\d+) (\\d)") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            int xCoordinate;
            int yCoordinate;
            int numberHarvested;
            try {
                xCoordinate = Integer.parseInt(input.group(Main.FIRST_PARAMETER_INDEX));
                yCoordinate = Integer.parseInt(input.group(Main.FIRST_PARAMETER_INDEX + 1));
                numberHarvested = Integer.parseInt(input.group(Main.FIRST_PARAMETER_INDEX + 2));
            } catch (IllegalArgumentException illegalArgumentException) {
                return Main.ERROR + "Illegal input format";
            }
            return app.getActivePlayer().harvest(new Vector2d(xCoordinate, yCoordinate), numberHarvested, app);
        }
    },

    /**
     *   command for planting
     */
    PLANT ("plant (-?\\d+) (-?\\d+) (tomato|mushroom|carrot|salad)") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            int xCoordinate;
            int yCoordinate;
            try {
                xCoordinate = Integer.parseInt(input.group(Main.FIRST_PARAMETER_INDEX));
                yCoordinate = Integer.parseInt(input.group(Main.FIRST_PARAMETER_INDEX + 1));
            } catch (IllegalArgumentException illegalArgumentException) {
                return Main.ERROR + "Illegal input format";
            }
            VegetableType vegetableType = VegetableType.findVegetableType(input.group(Main.FIRST_PARAMETER_INDEX + 2));
            if (vegetableType == null) return "No such Vegetable exist";
            
            return app.getActivePlayer().plant(new Vector2d(xCoordinate, yCoordinate),
                new Vegetable(vegetableType), app);
        }
    },

    /**
     *  command for buying land
     */
    BUY_LAND ("buy land (-?\\d+) (-?\\d+)") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            int xCoordinate;
            int yCoordinate;
            try {
                xCoordinate = Integer.parseInt(input.group(Main.FIRST_PARAMETER_INDEX));
                yCoordinate = Integer.parseInt(input.group(Main.FIRST_PARAMETER_INDEX + 1));
            } catch (IllegalArgumentException illegalArgumentException) {
                return Main.ERROR + "Illegal input format";
            }
            
            return app.getActivePlayer()
                .buyLand(new Vector2d(xCoordinate, yCoordinate), app);
        }
    },

    /**
     *  command for buying vegetable
     */
    BUY_VEGETABLE ("buy vegetable (tomato|mushroom|carrot|salad)") {
        @Override                            
        String execute(Matcher input, QueensFarming app) {
            VegetableType vegetableType = VegetableType.findVegetableType(input.group(Main.FIRST_PARAMETER_INDEX));
            if (vegetableType == null) return "No such Vegetable exist";
            
            return app.getActivePlayer().buyVegetable(new Vegetable(vegetableType), app);
        }
    },

    /**
     *  command for printing the board
     */
    SHOW_BOARD ("show board") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            return app.getActivePlayer().showBoard(); 
        }
    },

    /**
     *  command for printing the barn
     */
    SHOW_BARN ("show barn") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            return app.getActivePlayer().showBarn(app);
        }
    },

    /**
     *  command for printing the status of the market
     */
    SHOW_MARKET ("show market") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            return app.getMarket().showMarket();
        }                                                   
    },

    /**
     *   command for quitting the program
     */
    QUIT ("quit") {
        @Override
        String execute(Matcher input, QueensFarming app) {
            app.setRunning(false);
            return null;
        }
    };

    /**
     *  The error message for invalid commands
     */
    public static final String COMMAND_NOT_FOUND = Main.ERROR + "Command not found!";
    private final Pattern pattern;

    /**
     * assigns the pattern
     * @param pattern the assigned pattern
     */
    Command(final String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * method for executing the command given by player
     * recognized with regular expressions the command an the parameters
     * then executes the according action
     * @param input the input
     * @param app the application the game is running on
     * @return the output of the action, if the command is valid; the error message if the command is invalid
     */
    public static String executeCommand(final String input, final QueensFarming app) {
        for (final Command command : Command.values()) {
            final Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                return command.execute(matcher, app);
            }
        }
        return Main.ERROR + COMMAND_NOT_FOUND;
    }

    /**
     * executes the according action
     * @param input the input
     * @param app the application the game is running on
     * @return the output of the action
     */
    abstract String execute(Matcher input, QueensFarming app);
    
}
