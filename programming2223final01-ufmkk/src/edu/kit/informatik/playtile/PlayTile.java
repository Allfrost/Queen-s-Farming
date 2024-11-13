package edu.kit.informatik.playtile;

import edu.kit.informatik.vector2d.Vector2d;

/**
 * Class for modeling the play tiles
 * 
 * @author ufmkk
 * @version 1.0
 */
public class PlayTile implements IPlayTile {
    private Vector2d coordinates;

    /**
     * getter method for the coordinates of the tile
     * @return coordinates of the tile
     */
    @Override
    public Vector2d coordinates() {
        return this.coordinates;
    }
    
    /**
     * transforms the tile into a string
     * @return the tile in the correct string format
     */
    @Override
    public String toString() {
        return null;
    }

    /**
     * setter method for the coordinates of the tile
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setCoordinates(int x, int y) {
        this.coordinates = new Vector2d(x, y);
    }

    /**
     * an alternative setter method for the coordinates of the tile
     * @param vector2d a 2d vector containing the x and y coordinate
     */
    public void setCoordinates(Vector2d vector2d) {
        this.coordinates = new Vector2d(vector2d.getX(), vector2d.getY());
    }
    
}
