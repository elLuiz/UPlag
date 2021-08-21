package c.operators;

public enum BitwiseOperatorsRegex {
    B_AND("\\&", " B_AND "),
    B_OR("\\|", " B_OR "),
    B_XOR("\\^", " B_XOR "),
    B_COM("\\~", " B_COM "),
    SH_L("\\<\\<", " SH_L "),
    SH_R("\\>\\>", " SH_R ");

    private final String regex;
    private final String token;

    BitwiseOperatorsRegex(String regex, String token) {
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
