package de.frauas.fb2.javaproject.ws25.group22.util;

/**
 * Utility class containing helper methods.
 *
 * @author Tobias Ilcker
 */
public class Util {
    /**
     * Extracts an integer ID from a URL-like string.
     *
     * @param s the string containing the ID.
     * @return the extracted ID as an integer.
     */
    public static int cut_id(String s) {
        String[] parts = s.split("/");
        if (parts.length > 5) {
            try {
                return Integer.parseInt(parts[5]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid ID format in string: " + s, e);
            }
        } else {
            throw new IllegalArgumentException("String does not contain enough parts to extract ID: " + s);
        }
    }
}
