package edu.kit.informatik;

/**
 * A util class containing the pixel art for the start
 * 
 * @author ufmkk
 * @version 1.0
 */
public final class AsciiArtUtil {
    
    private AsciiArtUtil() { 
        throw new IllegalStateException(Main.UTILITY_CLASS_INSTANTIATION);
    }
    
    /**
     * getter method for the pixel art
     * @return the pixel art as string
     */
    public static String artQF() {
        return """
                                       _.-^-._    .--.\040\040\040\040
                                    .-'   _   '-. |__|\040\040\040\040
                                   /     |_|     \\|  |\040\040\040\040
                                  /               \\  |\040\040\040\040
                                 /|     _____     |\\ |\040\040\040\040
                                  |    |==|==|    |  |\040\040\040\040
              |---|---|---|---|---|    |--|--|    |  |\040\040\040\040
              |---|---|---|---|---|    |==|==|    |  |\040\040\040\040
            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            ^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^
            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^""";
    }
}
