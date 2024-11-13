package edu.kit.informatik;

import edu.kit.informatik.playtile.TileType;
import edu.kit.informatik.playtile.plantabletiles.PlantableTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class for modeling the application the game will run on
 * 
 * @author ufmkk
 * @version 1.0
 */
public class QueensFarming implements IQueensFarming {
    private final Pattern pattern = Pattern.compile("[a-zA-Z]+?");
    private int targetGold;
    private final List<Player> listOfPlayers = new ArrayList<>();
    private final List<PlantableTile> remainingTiles = new ArrayList<>();
    private int activePlayerIndex;
    private boolean running;
    private boolean firstTurn;
    private int remainingActions;
    private int nextTileIndex = 0;
    private final Market market = new Market();

    /**
     * fills the remaining tiles list accordingly to the number of players
     * @param numberOfPlayers number of players
     */
    QueensFarming(int numberOfPlayers) {
        int fieldMultiplier = 3;
        int gardenMultiplier = 2;
        int largeFieldMultiplier = 2;
        int forestMultiplier = 2;
        this.activePlayerIndex = numberOfPlayers - 1;

        for (int j = 0; j < gardenMultiplier * numberOfPlayers; ++j) {
            PlantableTile garden = new PlantableTile(TileType.GARDEN);
            remainingTiles.add(garden);
        }
            
        for (int j = 0; j < fieldMultiplier * numberOfPlayers; j++) {
            PlantableTile field = new PlantableTile(TileType.FIELD);
            remainingTiles.add(field);
        }

        for (int j = 0; j < largeFieldMultiplier * numberOfPlayers; j++) {
            PlantableTile largeField = new PlantableTile(TileType.LARGEFIELD);
            remainingTiles.add(largeField);
        }

        for (int j = 0; j < forestMultiplier * numberOfPlayers; ++j) {
            PlantableTile forest = new PlantableTile(TileType.FOREST);
            remainingTiles.add(forest);
        }
         
        for (int j = 0; j < numberOfPlayers; ++j) {
            PlantableTile largeForest = new PlantableTile(TileType.LARGEFOREST);
            remainingTiles.add(largeForest);
        }
        
        running = true;
        firstTurn = true;
        remainingActions = 0;
    }

    /**
     * initiates a player with the name given
     * @param name the name given
     * @return false if not successful, true if successful
     */
    @Override
    public boolean initiatePlayer(String name) {
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) return false;
        Player player = new Player(name);
        listOfPlayers.add(player);
        return true;
    }

    /**
     * getter method for the active player
     * @return the active player
     */
    @Override
    public Player getActivePlayer() {
        return listOfPlayers.get(activePlayerIndex);
    }

    /**
     * changes the active player
     * @return the results of the countdown for the player changed to
     */
    public String changePlayer() {
        if (firstTurn) {  // if first turn there are no winners
            firstTurn = false;
            activePlayerIndex = 0;
        }
        else if (activePlayerIndex == listOfPlayers.size() - 1) {  // if the players looped 
            activePlayerIndex = 0;                                 // meaning if the turn has ended
            
            market.determineNewValues();
            
            for (Player player : listOfPlayers) {  // checks if someone has won
                if (player.getCurrentGold() >= this.targetGold) {
                    this.running = false;
                    return null;
                }
            }
            
        }   else {
            activePlayerIndex++;
            market.determineNewValues();
        }
        remainingActions = 2;
        
        String grownVegetables = getActivePlayer().countDown();
        if (grownVegetables == null) return  "\nIt is " + getActivePlayer().getName() + "'s turn!";
        return "\nIt is " + getActivePlayer().getName() + "'s turn!" + grownVegetables;
    }

    /**
     * setter method for the starting gold
     * @param startingGold the amount
     */
    public void setStartingGold(int startingGold) {
        for (Player player : listOfPlayers) {
            player.setCurrentGold(startingGold);
        }
    }

    /**
     * setter method for the target gold
     * @param targetGold the amount
     */
    public void setTargetGold(int targetGold) {
        this.targetGold = targetGold;
    }

    /**
     * method for showing the players and the winners at the end or upon quitting
     * @return the players and winners as string
     */
    public String showPlayers() {
        StringBuilder s = new StringBuilder();
        List<String> winners = new ArrayList<>();
        int maxGold;
        
        for (int i = 0; i < listOfPlayers.size(); i++) { // first get every player and their gold amount
            int playerGold = listOfPlayers.get(i).getCurrentGold();
            String playerName = listOfPlayers.get(i).getName();
            
            s.append("Player ").append(i + 1).append(" (").append(playerName).append("): ")
                .append(playerGold).append("\n");
            if (playerGold >= this.targetGold) winners.add(playerName); // and get the winners in the process
                                                                        // fulfilled the condition
        }
        
        if (winners.size() == 0) { // if there is no one fulfilled the conditions
            maxGold = listOfPlayers.stream() // get the players with the max gold
                .mapToInt(Player::getCurrentGold)
                .max().orElse(0);
            winners = listOfPlayers.stream()
                .filter(player -> player.getCurrentGold() == maxGold)
                .map(Player::getName)
                .collect(Collectors.toList());
        }
        if (winners.size() == 1) {
            s.append(winners.get(0)).append(" has won!");
        }   
        else {
            for (int i = 0; i < winners.size(); ++i) {
                if (i == winners.size() - 1) s.append("and ");
                s.append(winners.get(i));
                if (i < winners.size() - 2) s.append(",");
                s.append(" ");
            }
            s.append("have won!");
        }
        
        return s.toString();
    }

    /**
     * checks if the game is running
     * @return true if it is running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * setter method for the variable running
     * @param running what it will be set to
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * shuffles the remaining tiles
     * @param seed the seed they will be shuffled with
     */
    public void shuffle(long seed) {
        Collections.shuffle(remainingTiles, new Random(seed));
    }

    /**
     *  decreases the remaining actions of the active player
     */
    public void decreaseRemainingActions() {
        remainingActions--;
    }

    /**
     * getter method for the next tile in line to be bought
     * @return the next tile if there is one, null if there are no tiles left
     */
    public PlantableTile getNextTile() {
        if (nextTileIndex == remainingTiles.size()) return null;
        PlantableTile nextTile = remainingTiles.get(nextTileIndex);
        nextTileIndex++;
        return nextTile;
    }

    /**
     * getter method for the remaining actions
     * @return remaining actions
     */
    public int getRemainingActions() {
        return remainingActions;
    }

    /**
     * getter method for the market
     * @return the market
     */
    public Market getMarket() {
        return market;
    }
}
