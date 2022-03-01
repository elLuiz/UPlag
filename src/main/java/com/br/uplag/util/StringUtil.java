package com.br.uplag.util;

public class StringUtil {
    private StringUtil() {}

    public static boolean isValid(String input) {
        return input != null && !input.isEmpty();
    }

    public static String replaceBy(String character, String replacement, String input) {
        return input.replace(character, replacement);
    }

    public static String getFileNameAfterLastSlash(String input) {
        int lastIndex = input.lastIndexOf('/');
        String filename = input.substring(lastIndex + 1);
        return filename.replaceAll("\\.[\\w]+", "");
    }

    public static String buildPairsTemplate(String document1Name, String document2Name) {
        return "(" + document1Name + ", " + document2Name + ")";
    }
}
