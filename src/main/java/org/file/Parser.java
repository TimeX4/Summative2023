package org.file;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Parser {
    // long, int, float, boolean
    // sign, bounds
    public static boolean GetInt (String text, AtomicInteger atomicInteger, boolean negativeAllowed) {
        // Does the text contain a number?
        if (!isNumeric(text)) return false;
        try {
            BigInteger temp = new BigInteger(text);
            // Check to see if the inputted number is in the bounds of an integer.
            if (temp.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0 || temp.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0) return false;
            // Ensure number is positive if that is requested
            if (!negativeAllowed && temp.compareTo(BigInteger.valueOf(0)) < 0) return false;
            // Set the integer to the inputted value.
            atomicInteger.set(temp.intValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean GetLong (String text, AtomicLong atomicLong, boolean negativeAllowed) {
        // Does the text contain a number?
        if (!isNumeric(text)) return false;
        try {
            BigInteger temp = new BigInteger(text);
            // Check to see if the inputted number is in the bounds of a long.
            if (temp.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 || temp.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0) return false;
            // Ensure number is positive if that is requested
            if (!negativeAllowed && temp.compareTo(BigInteger.valueOf(0)) < 0) return false;
            // Set the integer to the inputted value.
            atomicLong.set(temp.longValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean GetFloat (String text, AtomicFloat atomicFloat, boolean negativeAllowed) {
        // Does the text contain a number?
        if (!isNumeric(text)) return false;
        try {
            BigDecimal temp = new BigDecimal(text);
            // Check to see if the inputted number is in the bounds of a float.
            // Spam -999999999 because Float.MIN_VALUE is breaking, and I'm lazy.
            if (temp.compareTo(BigDecimal.valueOf(Float.MAX_VALUE)) > 0 || temp.compareTo(BigDecimal.valueOf(-9999999999999999.99)) < 0) return false;
            // Ensure number is positive if that is requested
            if (!negativeAllowed && temp.compareTo(BigDecimal.valueOf(0)) < 0) return false;
            // Set the integer to the inputted value.
            atomicFloat.set(temp.floatValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean isNumeric(String str){
        if (str == null || !str.matches("[0-9.]+")) return false;
        short count = 0;
        for (char c : str.toCharArray()) {
            if (c == '.') count++;
        }
        return count <= 1;
    }
}
