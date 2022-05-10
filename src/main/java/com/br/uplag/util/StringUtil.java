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
        String filename = input.substring(getLastIndexOfSlashByOS(input) + 1);
        return filename.replaceAll("\\.[\\w]+", "");
    }

    private static int getLastIndexOfSlashByOS(String input) {
        return input.lastIndexOf('/') == -1 ? input.lastIndexOf('\\') : input.lastIndexOf('/');
    }
}
