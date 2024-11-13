package edu.kit.informatik.playtile.plantabletiles;

import edu.kit.informatik.Main;
import edu.kit.informatik.QueensFarming;
import edu.kit.informatik.playtile.PlayTile;
import edu.kit.informatik.playtile.TileType;
import edu.kit.informatik.vegetable.Vegetable;
import edu.kit.informatik.vegetable.VegetableType;

import java.util.Arrays;
import java.util.List;

/**
 * A class for modeling the play tiles that can be planted on.
 * 
 * @author ufmkk
 * @version 1.0
 */
public class PlantableTile extends PlayTile implements IPlantableTile {

    private int capacity;
    private List<VegetableType> allowedPlantations;
    private final TileType tileType;
    private String typeAsString;
    private String typeAsStringLong;
    private Vegetable currentVegetable;
    private int currentVegetableNumber;
    private int remainingTime = -1;   // remaining time to grow, -1 means there are no vegetables currently planted

    /**
     * Assigns the attributes depending on the type of the tile
     * 
     * @param tileType determines which type of tile the object is
     */
    public PlantableTile(TileType tileType) {
        this.tileType = tileType;
        
        switch (tileType) {
            case GARDEN -> {
                this.capacity = 2;
                allowedPlantations = Arrays
                    .asList(VegetableType.CARROT, VegetableType.SALAD, VegetableType.TOMATO, VegetableType.MUSHROOM);
                typeAsString = " G ";
                typeAsStringLong = "Garden";
            }
            case FIELD -> {
                this.capacity = 4;
                allowedPlantations = Arrays.asList(VegetableType.CARROT, VegetableType.SALAD, VegetableType.TOMATO);
                typeAsString = " Fi ";
                typeAsStringLong = "Field";
            }
            case FOREST -> {
                this.capacity = 4;
                allowedPlantations = Arrays.asList(VegetableType.CARROT, VegetableType.MUSHROOM);
                typeAsString = " Fo ";
                typeAsStringLong = "Forest";
            }
            case LARGEFIELD -> {
                this.capacity = 8;
                allowedPlantations = Arrays.asList(VegetableType.CARROT, VegetableType.SALAD, VegetableType.TOMATO);
                typeAsString = "LFi ";
                typeAsStringLong = "Large Field";
            }
            case LARGEFOREST -> {
                this.capacity = 8;
                allowedPlantations = Arrays.asList(VegetableType.CARROT, VegetableType.MUSHROOM);
                typeAsString = "LFo ";
                typeAsStringLong = "Large Forest";
            }
            default -> { } // is not relevant
        }
        
    }

    /**
     * The method for planting vegetable on the tile
     * 
     * @param vegetable determines which vegetable will be planted
     * @param app the application the game is running on
     * @return an error message if the action is invalid, null if it is valid
     */

    @Override
    public String plant(Vegetable vegetable, QueensFarming app) {
        if (currentVegetable != null) return Main.ERROR + "There is already a vegetable planted.";
        if (!allowedPlantations.contains(vegetable.getVegetableType())) {
            return Main.ERROR + "You cannot plant " + vegetable.getVegetableType() + " here.";
        }
        currentVegetable = new Vegetable(vegetable.getVegetableType());
        currentVegetableNumber = 1;
        remainingTime = currentVegetable.getGrowthTime();
        int[] removeParameter = new int[4];
        Arrays.fill(removeParameter, 0);   // The remove method in the barn class takes an array as parameter
        removeParameter[vegetable.getInventoryIndex()] = 1; // Here the planted vegetable is turned into the respective 
        app.getActivePlayer().getBarn().remove(removeParameter); // array

        app.decreaseRemainingActions();
        return null;
    }

    /**
     * A method for harvesting the crops on the tile
     * 
     * @param numberBeingHarvested the number of crops the player wants to harvest
     * @param app the application the game is running on
     * @return an error message if the action is invalid, the harvested crop type and number if the action is valid.
     */
    @Override
    public String harvest(int numberBeingHarvested, QueensFarming app) {
        if (currentVegetable == null) return Main.ERROR + "There is no vegetable planted in the tile.";
        
        if (numberBeingHarvested == 0) return Main.ERROR + "You cannot harvest 0 crops.";
        
        if (numberBeingHarvested > currentVegetableNumber) return Main.ERROR + "You don't have enough vegetables.";
        currentVegetableNumber -= numberBeingHarvested;
        
        String vegetableType;
        if (numberBeingHarvested > 1) vegetableType = currentVegetable.getPluralNameAsString();
        else vegetableType = currentVegetable.getName();

        app.getActivePlayer().getBarn().add(currentVegetable, numberBeingHarvested);
        
        if (remainingTime == -1) remainingTime = currentVegetable.getGrowthTime();
        
        if (currentVegetableNumber == 0) {
            currentVegetable = null;
            remainingTime = -1;
        }
        
        app.decreaseRemainingActions();
        
        return "You have harvested " + numberBeingHarvested + " " + vegetableType + ".";
    }

    /**
     * Method responsible for the crops growing
     * 
     * @return the number of crops have grown, null if zero
     */
    @Override
    public int grow() {
        if (remainingTime == -1) return 0;
        remainingTime--;
        if (remainingTime != 0) {
            return 0;
        }
        final int output = Math.min(capacity, currentVegetableNumber * 2) - currentVegetableNumber;
        currentVegetableNumber = Math.min(capacity, currentVegetableNumber * 2);
        
        if (currentVegetableNumber == capacity) remainingTime = -1;
        else remainingTime = currentVegetable.getGrowthTime();
        
        return output;
    }

    /**
     * getter method for the variable typeAsString
     * 
     * @return typeAsString
     */
    @Override
    public String getTypeAsString() {
        return typeAsString;
    }

    /**
     * return the tile in the correct format to be printed in the show board method
     * 
     * @return correct formatted string for printing the board
     */
    @Override
    public String getCurrentStatus() {
        return currentVegetableNumber + "/" + capacity;
    }

    /**
     * getter methode for the current vegetable's name
     * 
     * @return current vegetable's name
     */
    @Override
    public String getCurrentVegetable() {
        if (currentVegetable == null) return " ";
        return currentVegetable.toString();
    }

    /**
     * getter methode for the remaining time for the crop's multiplication
     * 
     * @return remaining time as String, * if nothing is planted
     */         

    public String getRemainingTimeAsString() {
        if (remainingTime == -1) return "*";
        return String.valueOf(remainingTime);
    }

    /**
     * getter methode for the tile type
     * 
     * @return the tile type
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * getter methode for the tile's name
     * 
     * @return tile's name
     */
    public String getName() {
        return typeAsStringLong;
    }
}
