package c.statements;

public enum StatementsRegex {
    RETURN("return(?!\\;)", "STMT");

    private final String regex;
    private String token;

    StatementsRegex(String regex, String token) {
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
