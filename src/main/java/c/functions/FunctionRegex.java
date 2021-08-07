package c.functions;

public enum FunctionRegex {
    // It must come after conditionals
    FUNCTION_CREATION_REGEX("[a-zA-Z]+\\*?\\s\\*?[a-z_A-Z\\d{0, 10000}]+\\s?\\(.*\\)\\s?\\{", "FUN L_C "),
    FUNCTION_CALL_REGEX("(?:[a-zA-Z*_\\d{1,1000}\\\\p{Punct}]+\\(.*\\));", "FUNC"),
    FUNCTION_CALL_BETWEEN_PARENTHESES("(?<=\\()[\\w]+\\(.*\\)+(?=\\))", "FUNC"),
    FUNCTION_CALL_BETWEEN_PARENTHESES_AND_OPERATORS("(?<=\\w\\s)[\\w]+\\(.*\\)+(?=\\))", "FUNC");
    private final String regex;
    private final String token;

    FunctionRegex(String regex, String token) {
        this.regex = regex;
        this.token = token;
    }

    public String getRegex() {
        return regex;
    }

    public String getToken() {
        return token;
    }
}
