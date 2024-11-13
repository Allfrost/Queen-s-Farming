package edu.kit.informatik.playtile;

import edu.kit.informatik.Main;
import edu.kit.informatik.QueensFarming;
import edu.kit.informatik.vegetable.Vegetable;


import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A class for modeling the barn
 * 
 * @author ufmkk
 * @version 1.0 
 */
public class Barn extends PlayTile implements IBarn {
    
    private static final int START_OFFSET_ROTTING_TIME = 7; // offset timer for the first turn since the program
                                                            // counts down even in the first turn
    private static final int ROTTING_TIME = 6;
    private int turnsUntilRot = -1; // -1 means the barn is empty
    private final Integer[] inventory = new Integer[4]; // 0: carrot , 1: mushroom, 2: salad, 3: tomato

    /**
     * Fills the barn for the start
     */
    public Barn() {
        Arrays.fill(inventory, 1);
        turnsUntilRot = START_OFFSET_ROTTING_TIME;
    }

    /**
     * Method for removing vegetable from the barn
     *
     * @param numberToRemove how many of each vegetable will be removed
     * @return an error message if the action is invalid, null if it is valid
     */
    @Override
    public String remove(int[] numberToRemove) {
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] < numberToRemove[i]) return Main.ERROR + "You don't have enough vegetables.";
        }
        for (int i = 0; i < inventory.length; ++i) {
            inventory[i] -= numberToRemove[i];
        }
        int sum = 0;
        for (int vegetableNumber : inventory) {
            sum += vegetableNumber;
        }
        if (sum == 0) turnsUntilRot = -1;
        return null;
    }

    /**
     * empties the barn
     */
    public void removeAll() {
        Arrays.fill(inventory, 0);
        turnsUntilRot = -1;
    }

    /**
     * Method for adding vegetable to barn
     *
     * @param vegetable which type of vegetable will be added
     * @param number how many vegetables will be added
     */
    @Override
    public void add(Vegetable vegetable, int number) {
        inventory[vegetable.getInventoryIndex()] += number;
        if (turnsUntilRot == -1) turnsUntilRot = ROTTING_TIME;
    }

    /**
     * Method responsible for checking if the vegetables in the barn have rotted or not and counting down
     *
     * @return the rot message if they have rotten, null otherwise
     */
    @Override
    public String rot() {
        if (turnsUntilRot == -1) return null;
        turnsUntilRot--;
        if (turnsUntilRot == 0) {
            removeAll();
            turnsUntilRot = -1;
            return "\nThe vegetables in your barn are spoiled.";
        }
        return null;
    }

    /**
     * getter methode for the inventory
     *
     * @return the inventory
     */
    @Override
    public Integer[] getInventory() {
        return inventory;
    }

    /**
     * getter method for the variable turnsUntilRot
     * @return turns until rot
     */
    public int getTurnsUntilRot() {
        return turnsUntilRot;
    }

    /**
     * shows the barn in the correct string format
     *
     * @param app the application the game is running on
     * @return the barn as string
     */
    @Override
    public String toString(QueensFarming app) {
        StringBuilder barn = new StringBuilder();
        StringBuilder[] lines = new StringBuilder[8];
        int width = 6; // minimum 6 since there is always the "Gold: "
        int playerGold = app.getActivePlayer().getCurrentGold();
        int playerGoldDigitLength = String.valueOf(playerGold).length();
        int sum = 0;
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = new StringBuilder();
        }
        lines[0].append("Barn");
        if (turnsUntilRot != -1) {  // if the barn is not empty
            lines[0].append(" (spoils in ").append(turnsUntilRot).append(" turn");
            if (turnsUntilRot > 1) lines[0].append("s");
            lines[0].append(")");
                                                    
            int[] sortedIndices = IntStream.range(0, inventory.length)  // sort the vegetable from the least to most
                .boxed().sorted((i, j) -> inventory[i].compareTo(inventory[j]))
                .mapToInt(ele -> ele).toArray();

            for (int i = 0; i < sortedIndices.length; i++) {  // adds the vegetables to the string from least
                switch (sortedIndices[i]) {                   // to most and then alphabetically in the switch
                    case 0 -> {
                        if (inventory[sortedIndices[i]] <= 0) break;
                        width = Math.max(9, width);
                        lines[i + 1].append("carrots: ").append(inventory[sortedIndices[i]]);
                    }
                    case 1 -> {
                        if (inventory[sortedIndices[i]] <= 0) break;
                        width = Math.max(11, width);
                        lines[i + 1].append("mushrooms: ").append(inventory[sortedIndices[i]]);
                    }
                    case 2 -> {
                        if (inventory[sortedIndices[i]] <= 0) break;
                        width = Math.max(8, width);
                        lines[i + 1].append("salads:  ").append(inventory[sortedIndices[i]]);
                    }
                    case 3 -> {
                        if (inventory[sortedIndices[i]] <= 0) break;
                        width = Math.max(10, width);
                        lines[i + 1].append("tomatoes: ").append(inventory[sortedIndices[i]]);
                    }
                    default -> { } // will not execute
                }
            }
            for (Integer temp : inventory) {
                sum += temp;                   // the sum of vegetables
            }
            int sumDigitLength = String.valueOf(sum).length();
            width += Math.max(sumDigitLength, playerGoldDigitLength);
            for (int i = 1; i < inventory.length + 1; ++i) {
                if (lines[i].length() == 0) continue;
                while (lines[i].length() < width) {    // fills in the whitespaces to make the string rightbound
                    lines[i].insert(lines[i].length() - (String.valueOf(inventory[i - 1]).length() + 1), " ");
                }
            }
            while (lines[5].length() < width) {
                lines[5].append("-");
            }
            lines[6].append("Sum: ").append(sum);
            while (lines[6].length() < width) {  // fills in the whitespaces to make the string rightbound
                lines[6].insert(lines[6].length() - sumDigitLength, " ");
            }
            lines[6].append("\n");
        }
        lines[7].append("Gold: ").append(playerGold);
        while (lines[7].length() < width) {  // fills in the whitespaces to make the string rightbound
            lines[7].insert(lines[7].length() - playerGoldDigitLength, " ");
        }
        lines[7].append('\n');
        for (StringBuilder line : lines) {
            if (line.isEmpty()) continue;
            barn.append(line).append('\n');
        }
        return barn.toString().substring(0, barn.length() - 2);
    }
}
