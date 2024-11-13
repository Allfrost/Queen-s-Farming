package edu.kit.informatik;

/**
 * An interface for the class Market
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IMarket {

    /**
     * determines the new values of the vegetables
     */
    void determineNewValues();

    /**
     * determines the status of the market depending on the recently sold vegetables
     */
    void determineMarketStatus();

    /**
     * transforms the market into a string
     * @return the market in the correct string format
     */
    String showMarket();
    
}
