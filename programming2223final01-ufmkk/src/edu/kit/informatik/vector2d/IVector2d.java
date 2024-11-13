package edu.kit.informatik.vector2d;

/**
 * An interface for the class Vector2d
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IVector2d {

    /**
     * getter method for the x coordinate
     * @return x coordinate
     */
    int getX();
    
    /**
     * getter method for the y coordinate
     * @return y coordinate
     */
    int getY();
    
    /**
     * setter method for the coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    void setCoordinates(int x, int y);
    
    /**
     * checks if 2 vector2d objects are equal
     * @param vector2d the other object
     * @return true if they are equal, false otherwise
     */
    boolean isEqual(Vector2d vector2d);
    
    /**
     * checks if 2 vectors are adjacent to each other (in the question a tile upward does not count as adjacent)
     * @param vector2d the other vector
     * @return true, if adjacent; false, otherwise
     */
    boolean adjacent(Vector2d vector2d);
    
    /**
     * calculates the Manhatten-Distance between two vectors
     * @param vector2d the other vector
     * @return the Manhatten-Distance between the vectors
     */
    int calculateDistance(Vector2d vector2d);
    
}
