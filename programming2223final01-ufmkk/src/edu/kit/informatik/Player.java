package edu.kit.informatik;

import edu.kit.informatik.playtile.Barn;
import edu.kit.informatik.playtile.TileType;
import edu.kit.informatik.playtile.plantabletiles.PlantableTile;
import edu.kit.informatik.vector2d.Vector2d;
import edu.kit.informatik.vegetable.Vegetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for modeling the players in the game
 * 
 * @author ufmkk
 * @version 1.0
 */
public class



Player implements IPlayer {
    
    private final String name;
    private int currentGold;
    private final List<PlantableTile> listOfTerritories = new ArrayList<>();
    private final Barn barn = new Barn();
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    /**
     * gives each player the default starting board upon initiation
     * @param name the name of the player
     */
    Player(String name) {
        this.name = name;
        
        PlantableTile garden1 = new PlantableTile(TileType.GARDEN);
        garden1.setCoordinates(-1, 0);
        listOfTerritories.add(garden1);
        
        PlantableTile garden2 = new PlantableTile(TileType.GARDEN);
        garden2.setCoordinates(1, 0);
        listOfTerritories.add(garden2);
        
        PlantableTile field = new PlantableTile(TileType.FIELD);
        field.setCoordinates(0, 1);
        listOfTerritories.add(field);
        
        maxX = 1;
        minX = -1;
        minY = 0;
        maxY = 1;
    }

    /**
     * setter method for the current gold of the player
     * @param goldAmount the amount it will be set to
     */
    public void setCurrentGold(int goldAmount) {
        this.currentGold = goldAmount;
    }

    /**
     * the method used for selling vegetables
     * @param vegetables determines how many of each vegetable will be sold
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    @Override
    public String sell(String vegetables, QueensFarming app) {
        int sumVegetable = 0;
        int sumGold = 0;
        int[] soldNumbers = new int[4];
        StringBuilder output = new StringBuilder();
        
        if (vegetables.equals(" ")) {  // if the command is just "sell"
            app.decreaseRemainingActions();
            return "You have sold 0 vegetables for 0 gold.";
        }
        else if (vegetables.equals("all")) { // if the player wants to sell all
            for (int i = 0; i < barn.getInventory().length; ++i) {
                sumVegetable += barn.getInventory()[i];
                sumGold += barn.getInventory()[i] *  app.getMarket().getValue(i);
                soldNumbers[i] = barn.getInventory()[i];
            }
            
            barn.removeAll();
            
        } else {  // reforms the string containing the vegetables in to an array containing how many of which vegetable 
            soldNumbers[0] = vegetables.split("carrot", -1).length - 1; // will be sold
            soldNumbers[2] = vegetables.split("salad", -1).length - 1;
            soldNumbers[3] = vegetables.split("tomato", -1).length - 1;
            soldNumbers[1] = vegetables.split("mushroom", -1).length - 1;
            
            sumVegetable = Arrays.stream(soldNumbers).sum();
            for (int i = 0; i < soldNumbers.length; ++i) {             // calculates the gold earned
                sumGold += soldNumbers[i] * app.getMarket().getValue(i); 
            }

            String outputBarn = barn.remove(soldNumbers);
            if (outputBarn != null) return outputBarn;
        }
        
        app.getMarket().addToRecentlySold(soldNumbers);
        currentGold += sumGold;
        app.decreaseRemainingActions();

        output.append("You have sold ").append(sumVegetable).append(" vegetable");
        if (sumVegetable != 1) output.append("s");        // creates the output message with the correct information
        output.append(" for ").append(sumGold).append(" gold.");
        return output.toString();
    }

    /**
     * method used for buying vegetables
     * @param vegetable which vegetable will be bought
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    @Override
    public String buyVegetable(Vegetable vegetable, QueensFarming app) {
        int cost = app.getMarket().getValue(vegetable.getInventoryIndex());
        if (cost > currentGold) return Main.ERROR + "Not enough gold.";
        barn.add(vegetable, 1);
        currentGold -= cost;
        app.decreaseRemainingActions();
        return "You have bought a " + vegetable.getName() + " for " + cost + " gold.";
    }

    /**
     * counts down for the player when it is their turn
     * @return the results of the countdown
     */
    public String countDown() {
        StringBuilder output = new StringBuilder();
        String didBarnRot = barn.rot(); // checks if the vegetables in the barn have rotten
        int grownVegetableNumber = 0;
        for (PlantableTile tile : listOfTerritories) {
            grownVegetableNumber += tile.grow(); // calls for the "grow" method for each tile the player has
        }
        if (grownVegetableNumber == 0) return didBarnRot;
        output.append("\n").append(grownVegetableNumber).append(" vegetable");
        if (grownVegetableNumber > 1) output.append("s have");
        else output.append(" has");
        output.append(" grown since your last turn.");
        if (didBarnRot != null) output.append(didBarnRot);
        return output.toString();
    }

    /**
     * method used for buying land
     * @param vector2d the coordinates the new tile is wanted to place on
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    @Override
    public String buyLand(Vector2d vector2d, QueensFarming app) {
        
        if (vector2d.isEqual(Main.ORIGIN)) return Main.ERROR + "There is already a tile there.";
        
        final int cost = calculateCost(vector2d);
        
        if (!(this.currentGold >= cost)) {
            return Main.ERROR + "Not enough gold";
        }
        
        PlantableTile territory = listOfTerritories.stream()  // checks if there is already a tile there
            .filter(tile -> tile.coordinates().isEqual(vector2d))
            .findAny()
            .orElse(null);
        
        if (territory != null) return Main.ERROR + "There is already a tile there.";

        territory = listOfTerritories.stream()   // checks if there is any adjacent tiles
            .filter(tile -> tile.coordinates().adjacent(vector2d))
            .findAny()
            .orElse(null);
        
        if (territory == null) return Main.ERROR + "No adjacent tiles";
        
        PlantableTile newTile = app.getNextTile();
        if (newTile == null) return Main.ERROR + "No more tiles remaining!";
        
        newTile.setCoordinates(vector2d);
        listOfTerritories.add(newTile);
        
        minX = Math.min(vector2d.getX(), minX); // updates the min/max of the x/y coordinate values of the board
        minY = Math.min(vector2d.getY(), minY); // used for printing the board
        maxX = Math.max(vector2d.getX(), maxX);
        maxY = Math.max(vector2d.getY(), maxY);
        
        currentGold -= cost;
        app.decreaseRemainingActions();
        
        return "You have bought a " + newTile.getName() + " for " + cost + " gold.";
        
    }

    /**
     * method for planting vegetables
     * @param vector2d the coordinates of the tile that will be planted on
     * @param vegetable the type of the vegetable
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    @Override
    public String plant(Vector2d vector2d, Vegetable vegetable, QueensFarming app) {
        
        if (barn.getInventory()[vegetable.getInventoryIndex()] <= 0) {
            return Main.ERROR + "You don't have enough " + vegetable.getName() + " in your barn.";
        }
        
        PlantableTile territory = listOfTerritories.stream() // checks if the player has a tile there
            .filter(tile -> tile.coordinates().isEqual(vector2d))
            .findAny()
            .orElse(null);
        
        if (territory == null) return Main.ERROR + "You don't have a land there.";
        return territory.plant(vegetable, app);
    }

    /**
     * method used for harvesting
     * @param vector2d the coordinates of the tile whose crops will be harvested
     * @param numberBeingHarvested the number the player wants to harvest
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    @Override
    public String harvest(Vector2d vector2d, int numberBeingHarvested, QueensFarming app) {
        PlantableTile territory = listOfTerritories.stream() // checks if the player has a tile there
            .filter(tile -> tile.coordinates().isEqual(vector2d))
            .findAny()
            .orElse(null);

        if (territory == null) return Main.ERROR + "You don't have a land there.";
        return territory.harvest(numberBeingHarvested, app);
    }

    /**
     * method for printing the board in the correct format
     * @return the board as string
     */
    @Override
    public String showBoard() {
        StringBuilder territoriesAsString = new StringBuilder();
        for (int i = maxY; i >= minY; i--) {
            territoriesAsString.append(getRowAsString(i)); // calls for the method for every row
        }
        
        return territoriesAsString.toString().substring(0, territoriesAsString.length() - 1);
    }

    /**
     * method for printing the player's barn, only calls for the barn's toString method
     * @param app the application the game is running on
     * @return the barn as string
     */
    @Override
    public String showBarn(QueensFarming app) {
        return barn.toString(app);
    }

    /**
     * getter method for the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * getter method for the barn of the player
     * @return player's barn
     */
    public Barn getBarn() {
        return barn;
    }

    /**
     * getter method for the player's current gold
     * @return player's current gold
     */
    public int getCurrentGold() {
        return currentGold;
    }

    /**
     * transforms the row of the player's board in to the correct string format
     * @param rowNumber which row will be turned in to string
     * @return the row as string
     */
    private String getRowAsString(int rowNumber) {
        StringBuilder firstRow = new StringBuilder(); // every tile row is represented as 3 rows of strings
        StringBuilder secondRow = new StringBuilder(); 
        StringBuilder thirdRow = new StringBuilder();

        StringBuilder rowAsString = new StringBuilder();
        
        int maxWidth = (maxX - minX + 1) * 6 + 1;

        for (int i = minX; i <= maxX; ++i) { // for every coordinate in the row

            Vector2d vector2d = new Vector2d(i, rowNumber);
            
            if (vector2d.isEqual(new Vector2d(0, 0))) { // prints the barn
                int turnsUntilRot = barn.getTurnsUntilRot();
                secondRow.append(" B ");
                if (turnsUntilRot == -1) secondRow.append("*"); 
                else secondRow.append(turnsUntilRot);
                firstRow.append("    "); // fills in the whitespaces
                thirdRow.append("    ");
            }

            while (firstRow.length() < (i - minX) * 6) { // fills in the whitespaces
                firstRow.append(" ");
                secondRow.append(" ");
                thirdRow.append(" ");
            }

            PlantableTile territory = listOfTerritories.stream() // checks if there is a tile on these coordinates
                .filter(tile -> tile.coordinates().isEqual(vector2d))
                .findAny()
                .orElse(null);

            if (territory == null) continue; //if not continue

            if (firstRow.length() == 0 || firstRow.charAt(firstRow.length() - 1) != '|') {
                firstRow.append("|"); // puts the "|"s if not there
                secondRow.append("|");
                thirdRow.append("|");
            }
            // fills the string builders with the correct information
            firstRow.append(territory.getTypeAsString()).append(territory.getRemainingTimeAsString());
            secondRow.append("  ").append(territory.getCurrentVegetable()).append("  |");
            thirdRow.append(" ").append(territory.getCurrentStatus()).append(" |");

            if (firstRow.length() == secondRow.length() - 2) firstRow.append(" ");
            firstRow.append("|");
            
        }
        while (firstRow.length() < maxWidth) {  // fills in the whitespaces
            firstRow.append(" ");
            secondRow.append(" ");
            thirdRow.append(" ");
        }
        firstRow.append('\n');
        secondRow.append('\n');
        thirdRow.append('\n');
        rowAsString.append(firstRow).append(secondRow).append(thirdRow);
        return rowAsString.toString();
    }

    /**
     * calculates the cost of the tile
     * @param vector2d the coordinate the tile whose cost will be calculated
     * @return the cost
     */
    private int calculateCost(Vector2d vector2d) {
        return (vector2d.calculateDistance(Main.ORIGIN) - 1) * 10;
    }
}
