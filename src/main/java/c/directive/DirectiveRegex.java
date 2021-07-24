package c.directive;

public enum DirectiveRegex {
    IMPORT("#include[a-zA-Zp{Punct}<.>\"\\s]+", ""),
    DEFINE("#(define)\\s?[A-Z-a-z]+\\s[a-z]?\\s?(\\d{1,1000});", "CONST");

    private final String regex;
    private final String token;

    DirectiveRegex(String regex, String token) {
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
