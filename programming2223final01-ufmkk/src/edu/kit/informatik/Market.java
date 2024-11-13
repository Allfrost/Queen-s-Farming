package edu.kit.informatik;

import java.util.Arrays;

/**
 * A class modeling the market in the game
 * 
 * @author ufmkk
 * @version 1.0
 */
public class Market implements IMarket {
    private final int[] recentlySold = new int[4]; // recently sold means in the last turn
    private final int[] vegetableValues = new int[4]; // 0: carrot, 1: mushroom, 2: salad, 3: tomato
    private int mcStatus;
    private int tsStatus;
    
    /**
     * defaults the starting status to the middle
     */
    Market() {
        mcStatus = 3;
        tsStatus = 3;
        determineNewValues();
    }

    /**
     * determines the new values of the vegetables
     */
    @Override
    public void determineNewValues() {
        determineMarketStatus();
        switch (mcStatus) {
            case 1 -> {
                vegetableValues[0] = 3;
                vegetableValues[1] = 12;
            }
            case 2 -> {
                vegetableValues[0] = 2;
                vegetableValues[1] = 15;
            }
            case 3 -> {
                vegetableValues[0] = 2;
                vegetableValues[1] = 16;
            }
            case 4 -> {
                vegetableValues[0] = 2;
                vegetableValues[1] = 17;
            }
            case 5 -> {
                vegetableValues[0] = 1;
                vegetableValues[1] = 20;
            }
            default -> { } // will not be relevant
        }
        
        switch (tsStatus) {
            case 1 -> {
                vegetableValues[2] = 6;
                vegetableValues[3] = 3;
            }
            case 2 -> {
                vegetableValues[2] = 5;
                vegetableValues[3] = 5;
            }
            case 3 -> {
                vegetableValues[2] = 4;
                vegetableValues[3] = 6;
            }
            case 4 -> {
                vegetableValues[2] = 3;
                vegetableValues[3] = 7;
            }
            case 5 -> {
                vegetableValues[2] = 2;
                vegetableValues[3] = 9;
            }
            default -> { } // will not be relevant
        }
    }

    /**
     * determines the status of the market depending on the recently sold vegetables
     */
    @Override
    public void determineMarketStatus() {
        int changeInMC = Math.abs(recentlySold[0] - recentlySold[1]) / 2;
        int changeInTS = Math.abs(recentlySold[2] - recentlySold[3]) / 2;
        
        if (recentlySold[0] > recentlySold[1]) this.mcStatus += changeInMC;
        else this.mcStatus -= changeInMC;
        if (recentlySold[2] > recentlySold[3]) this.tsStatus += changeInTS;
        else this.tsStatus -= changeInTS;
        
        if (this.mcStatus > 5) mcStatus = 5;
        if (this.mcStatus < 1) mcStatus = 1;
        if (this.tsStatus > 5) tsStatus = 5;
        if (this.tsStatus < 1) tsStatus = 1;
        
        Arrays.fill(recentlySold, 0);
    }

    /**
     * transforms the market into a string
     * @return the market in the correct string format
     */
    @Override
    public String showMarket() {

        return "mushrooms: " + vegetableValues[1] + "\ncarrots:    " + vegetableValues[0] 
            + "\ntomatoes:   " + vegetableValues[3] + "\nsalads:     " + vegetableValues[2];
    }

    /**
     * getter method for the market value of the vegetable
     * @param i the inventory index of the vegetable
     * @return the market value of the vegetable with the index i
     */
    public int getValue(int i) {
        return vegetableValues[i];
    }

    /**
     * adds vegetables to recently sold array
     * @param recentlySold determines how many of which vegetable will be added
     */
    public void addToRecentlySold(int[] recentlySold) {
        for (int i = 0; i < this.recentlySold.length; ++i) {
            this.recentlySold[i] += recentlySold[i];
        }
    }
}
