package edu.kit.informatik.playtile;

import edu.kit.informatik.vector2d.Vector2d;

/**
 * An interface for the class PlayTile
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IPlayTile {

    /**
     * getter method for the coordinates of the tile
     * @return coordinates of the tile
     */
    Vector2d coordinates();

    /**
     * transforms the tile into a string
     * @return the tile in the correct string format
     */
    String toString();
    
}
