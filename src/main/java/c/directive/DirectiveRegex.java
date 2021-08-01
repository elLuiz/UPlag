package c.directive;

public enum DirectiveRegex {
    IMPORT("(#include\\s?[a-zA-Zp{Punct}<.>\"]+)", ""),
    DEFINE("#define\\s[A-Za-z_]+\\s[a-zA-Z_]?\\s?(-?)\\d{1,1000}", "CONST");

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
