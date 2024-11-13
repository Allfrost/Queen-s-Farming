package edu.kit.informatik;

/**
 * An interface for the class QueensFarming
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IQueensFarming {

    /**
     * initiates a player with the name given
     * @param name the name given
     * @return false if not successful, true if successful
     */
    boolean initiatePlayer(String name) throws IllegalArgumentException;

    /**
     * getter method for the active player
     * @return the active player
     */
    Player getActivePlayer();
    
}
