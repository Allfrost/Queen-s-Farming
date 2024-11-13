package edu.kit.informatik.playtile.plantabletiles;

import edu.kit.informatik.QueensFarming;
import edu.kit.informatik.playtile.IPlayTile;
import edu.kit.informatik.vegetable.Vegetable;

/**
 * An Interface for modeling the play tiles that can be planted on.
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IPlantableTile extends IPlayTile {
    /**
     * The method for planting vegetable on the tile
     *
     * @param vegetable determines which vegetable will be planted
     * @param app the application the game is running on
     * @return an error message if the action is invalid, null if it is valid
     */
    String plant(Vegetable vegetable, QueensFarming app);

    /**
     * A method for harvesting the crops on the tile
     *
     * @param numberBeingHarvested the number of crops the player wants to harvest
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the harvested crop type and number if the action is valid.
     */
    String harvest(int numberBeingHarvested, QueensFarming app);

    /**
     * Method responsible for the crops growing
     *
     * @return the number of crops have grown, null if zero
     */
    int grow();

    /**
     * getter method for the variable typeAsString
     *
         * @return typeAsString
     */
    String getTypeAsString();

    /**
     * getter methode for the current vegetable's name
     *
     * @return current vegetable's name
     */
    String getCurrentVegetable();

    /**                             
     * return the tile in the correct format to be printed in the show board method
     *
     * @return correct formatted string for printing the board
     */
    String getCurrentStatus();
}
