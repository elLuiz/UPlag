package c.operators;

public enum ArithmeticOperatorsRegex {
    ADD("(?<!\\+)\\+(?!\\+)", " ADD "),
    SUB("(?<!\\-)\\-(?!\\>)", " SUB "),
    // PROBLEM: WHAT IS COMMENT OR MULTIPLICATION
    // TODO: WORK ON THAT REGEX
    MULT("\\*", " MULT "),
    DIV("\\/", " DIV "),
    MOD("\\%", " MOD ");

    private final String regex;
    private final String token;

    ArithmeticOperatorsRegex(String regex, String token) {
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
