package edu.kit.informatik.vector2d;

/**
 * Class for modeling the 2 dimensional positions
 * 
 * @author ufmkk
 * @version 1.0
 */
public class Vector2d implements IVector2d {
    
    private int xCoordinate;
    private int yCoordinate;

    /**
     * sets the x and y coordinate of the vector
     * @param x x coordinate
     * @param y y coordinate
     */
    public Vector2d(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /**
     * getter method for the x coordinate
     * @return x coordinate
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * getter method for the y coordinate
     * @return y coordinate
     */
    public int getY() {
        return yCoordinate;
    }

    /**
     * setter method for the coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    @Override
    public void setCoordinates(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }
    
    /**
     * checks if 2 vector2d objects are equal
     * @param vector2d the other object
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean isEqual(Vector2d vector2d) {
        return vector2d.getX() == this.xCoordinate && vector2d.getY() == this.yCoordinate;
    }

    /**
     * checks if 2 vectors are adjacent to each other (in the question a tile upward does not count as adjacent)
     * @param vector2d the other vector
     * @return true, if adjacent; false, otherwise
     */
    @Override
    public boolean adjacent(Vector2d vector2d) {
        return calculateDistance(vector2d) == 1 && this.yCoordinate <= vector2d.getY();
    }

    /**
     * calculates the Manhatten-Distance between two vectors
     * @param vector2d the other vector
     * @return the Manhatten-Distance between the vectors
     */
    @Override
    public int calculateDistance(Vector2d vector2d) {
        return Math.abs(vector2d.getX() - this.xCoordinate) + Math.abs(vector2d.getY() - this.yCoordinate);
    }
}
