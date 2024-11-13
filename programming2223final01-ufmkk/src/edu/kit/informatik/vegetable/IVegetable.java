package edu.kit.informatik.vegetable;

/**
 * An interface for the class Vegetable
 * 
 * @author ufmkk
 * @version 1.0
 */
public interface IVegetable {

    /**
     * getter method for the growth time
     * @return the growth time
     */
    int getGrowthTime();

    /**
     * getter method for the vegetable type
     * @return the vegetable type
     */
    VegetableType getVegetableType();

    /**
     * getter method for the abbreviated name of the vegetable 
     * @return the abbreviated name of the vegetable
     */
    @Override
    String toString();

}
