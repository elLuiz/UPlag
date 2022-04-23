package com.br.uplag.util;

public class StringUtil {
    private StringUtil() {}

    public static boolean isNotNullNorEmpty(String input) {
        return input != null && !input.isEmpty();
    }

    public static String replaceByCharacter(String input, String replacement, String character) {
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
