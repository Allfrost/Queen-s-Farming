package edu.kit.informatik;

import edu.kit.informatik.vector2d.Vector2d;
import edu.kit.informatik.vegetable.Vegetable;

/**
 * An interface for the class player
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IPlayer {

    /**
     * the method used for selling vegetables
     * @param vegetables determines how many of each vegetable will be sold
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    String sell(String vegetables, QueensFarming app);
    
    /**
     * method used for buying vegetables
     * @param vegetable which vegetable will be bought
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    String buyVegetable(Vegetable vegetable, QueensFarming app);

    /**
     * method used for buying land
     * @param vector2d the coordinates the new tile is wanted to place on
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    String buyLand(Vector2d vector2d, QueensFarming app);

    /**
     * method for planting vegetables
     * @param vector2d the coordinates of the tile that will be planted on
     * @param vegetable the type of the vegetable
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    String plant(Vector2d vector2d, Vegetable vegetable, QueensFarming app);

    /**
     * method used for harvesting
     * @param vector2d the coordinates of the tile whose crops will be harvested
     * @param numberBeingHarvested the number the player wants to harvest
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the results of the action if it is valid
     */
    String harvest(Vector2d vector2d, int numberBeingHarvested, QueensFarming app);

    /**
     * method for printing the board in the correct format
     * @return the board as string
     */
    String showBoard();

    /**
     * method for printing the player's barn, only calls for the barn's toString method
     * @param app the application the game is running on
     * @return the barn as string
     */
    String showBarn(QueensFarming app);

    /**
     * getter method for the name of the player
     * @return the name of the player
     */
    String getName();
}
