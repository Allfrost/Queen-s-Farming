package edu.kit.informatik.playtile;

import edu.kit.informatik.QueensFarming;
import edu.kit.informatik.vegetable.Vegetable;

/**
 * An interface for the class Barn
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IBarn extends IPlayTile {

    /**
     * Method for removing vegetable from the barn
     * 
     * @param numberToRemove how many of each vegetable will be removed
     * @return an error message if the action is invalid, null if it is valid
     */
    String remove(int[] numberToRemove);

    /**
     * Method for adding vegetable to barn
     * 
     * @param vegetable which type of vegetable will be added
     * @param number how many vegetables will be added
     */
    void add(Vegetable vegetable, int number);

    /**
     * Method responsible for checking if the vegetables in the barn have rotted or not and counting down
     * 
     * @return the rot message if they have rotten, null otherwise
     */
    String rot();

    /**
     * getter methode for the inventory
     * 
     * @return the inventory
     */
    Integer[] getInventory();

    /**
     * shows the barn in the correct string format
     * 
     * @param app the application the game is running on
     * @return the barn as string
     */
    String toString(QueensFarming app);
}
