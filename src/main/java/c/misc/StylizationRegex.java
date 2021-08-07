package c.misc;

public enum StylizationRegex {
    LEFT_CURLY_BRACES("\\{", " L_C "),
    RIGHT_CURLY_BRACES("\\}", " R_C "),
    LEFT_PARENTHESES("\\(", " L_P "),
    RIGHT_PARENTHESES("\\)", " R_P ");

    private final String regex;
    private final String token;

    StylizationRegex(String regex, String token) {
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
