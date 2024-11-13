package edu.kit.informatik.vegetable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * the enumeration of the possible vegetable types also recognizes them for the commands
 */
public enum VegetableType {
    /**
     *  the type carrot also has the recognising pattern "carrot"
     */
    CARROT("carrot"),
    /**
     *   the type salad also has the recognising pattern "salad"
     */
    SALAD("salad"),
    /**
     *   the type tomato also has the recognising pattern "tomato"
     */
    TOMATO("tomato"),
    /**
     *   the type mushroom also has the recognising pattern "mushroom"
     */
    MUSHROOM("mushroom");
    
    private final Pattern pattern;

    /**
     * assigns the pattern
     * @param pattern the assigned pattern
     */
    VegetableType(final String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * find the vegetable type given in the command
     * @param input the input that will be analyzed
     * @return the found vegetable type
     */
    public static VegetableType findVegetableType(final String input) {
        for (final VegetableType vegetableType : VegetableType.values()) {
            final Matcher matcher = vegetableType.pattern.matcher(input);
            if (matcher.matches()) {
                return vegetableType;
            }
        }
        return null;
    }
    
}
