package com.unina.oobd2324_37.entity.utils;

public class Cryptography {

    public static String cryptPassword(String input) {
        return caesarCipher(input);
    }

    /**
     * Metodo per criptare una stringa con il cifrario di Cesare.
     *
     * @param input stringa da criptare
     * @return stringa criptata
     */
    private static String caesarCipher(String input) {
        StringBuilder result = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                // Shift per lettere maiuscole
                char shifted = (char) (((ch - 'A' + 1) % 26) + 'A');
                result.append(shifted);
            } else if (Character.isLowerCase(ch)) {
                // Shift per lettere minuscole
                char shifted = (char) (((ch - 'a' + 1) % 26) + 'a');
                result.append(shifted);
            } else if (Character.isDigit(ch)) {
                // Shift per numeri
                char shifted = (char) (((ch - '0' + 1) % 10) + '0');
                result.append(shifted);
            } else {
                // Altri caratteri rimangono invariati
                result.append(ch);
            }
        }

        return result.toString();
    }
}
