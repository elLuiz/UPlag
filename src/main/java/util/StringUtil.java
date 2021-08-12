package util;

public class StringUtil {
    private StringUtil() {}

    public static boolean isValid(String input) {
        return input != null && !input.isEmpty();
    }

    public static String replaceBy(String character, String replacement, String input) {
        return input.replace(character, replacement);
    }
}
