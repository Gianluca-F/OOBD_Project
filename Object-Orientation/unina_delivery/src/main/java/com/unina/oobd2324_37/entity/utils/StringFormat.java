package com.unina.oobd2324_37.entity.utils;

public class StringFormat {

    /**
     * Private constructor to avoid instantiation.
     */
    private StringFormat() {}

    /**
     * This method is used to capitalize the words in a string.
     * @param input The input string
     * @return The formatted string
     */
    public static String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return capitalized.toString().trim();
    }
}
