package edu.kit.informatik.vegetable;

/**
 * Class for modeling the vegetable found in the game
 * 
 * @author ufmkk
 * @version 1.0
 */
public class Vegetable implements IVegetable {
    
    private final VegetableType vegetableType;
    private int growthTime;
    private String typeAsString;
    private String typeAsStringLong;
    private int inventoryIndex;
    private String pluralNameAsString;

    /**
     * Assigns the attributes depending on the type of the vegetable
     *
     * @param vegetableType determines which type of vegetable the object is
     */
    public Vegetable(VegetableType vegetableType) {
        this.vegetableType = vegetableType;
        
        switch (vegetableType) {
            case CARROT -> {
                growthTime = 1;
                typeAsString = "C";
                typeAsStringLong = "carrot";
                inventoryIndex = 0;
                pluralNameAsString = "carrots";
            }
            case SALAD -> {
                growthTime = 2;
                typeAsString = "S";
                typeAsStringLong = "salad";
                inventoryIndex = 2;
                pluralNameAsString = "salads";
            }
            case TOMATO -> {
                growthTime = 3;
                typeAsString = "T";
                typeAsStringLong = "tomato";
                inventoryIndex = 3;
                pluralNameAsString = "tomatoes";
            }
            case MUSHROOM -> {
                growthTime = 4;
                typeAsString = "M";
                typeAsStringLong = "mushroom";
                inventoryIndex = 1;
                pluralNameAsString = "mushrooms";
            }
            default -> { } // will not execute
        }
    }

    /**
     * getter method for the growth time
     * @return the growth time
     */
    @Override
    public int getGrowthTime() {
        return growthTime;
    }

    /**
     * getter method for the vegetable type
     * @return the vegetable type
     */
    @Override
    public VegetableType getVegetableType() {
        return vegetableType;
    }

    /**
     * getter method for the abbreviated name of the vegetable 
     * @return the abbreviated name of the vegetable
     */
    @Override
    public String toString() {
        return typeAsString;
    }

    /**
     * getter method for the name of the vegetable
     * @return the name of the vegetable
     */
    public String getName() {
        return typeAsStringLong;
    }

    /**
     * getter method for the index of the inventory in which the vegetable is stored
     * @return the inventory index
     */
    public int getInventoryIndex() {
        return inventoryIndex;
    }

    /**
     * getter method for the plural name of the vegetable
     * here since tomato gets an "-es" for the plural form and the others have just "-s"
     * @return the plural name of the vegetable
     */
    public String getPluralNameAsString() {
        return pluralNameAsString;
    }
}
