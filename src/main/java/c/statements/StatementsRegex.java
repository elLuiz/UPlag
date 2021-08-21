package c.statements;

public enum StatementsRegex {
    BREAK("break;", "JUMP"),
    JUMP("return;", "JUMP"),
    CONTINUE("continue;", "JUMP"),
    RETURN("return(?!\\;)", " STMT ");

    private final String regex;
    private final String token;

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
