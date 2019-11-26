package com.providesupportLLC.utils;

public class NumericUtils {
    public static int parseIfPossible(String str, int variableForParsing) {
        try {
            variableForParsing = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            System.out.println("cannot parse to int " + nfe.getMessage());
            return variableForParsing;
        }
        return variableForParsing;
    }

    public static long parseIfPossible(String str, long variableForParsing) {
        try {
            variableForParsing = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            System.out.println("cannot parse to long " + nfe.getMessage());
            return variableForParsing;
        }
        return variableForParsing;
    }
}
