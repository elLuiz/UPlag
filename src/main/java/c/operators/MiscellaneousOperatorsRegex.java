package c.operators;

public enum MiscellaneousOperatorsRegex {
    SIZEOF("sizeof(.*)", " SIZE "),
    NULL("NULL", " NULL ");

    private final String regex;
    private final String token;

    MiscellaneousOperatorsRegex(String regex, String token) {
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
