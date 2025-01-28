package de.frauas.fb2.javaproject.ws25.group22.util;

/**
 * Utility class containing helper methods.
 */
public class Util {
    /**
     * Extracts an integer ID from a URL-like string.
     * @param s the string containing the ID.
     * @return the extracted ID as an integer.
     */
    public static int cut_id(String s) {
        String[] s2 = s.split("/");
        return Integer.parseInt(s2[5]);
    }
}
