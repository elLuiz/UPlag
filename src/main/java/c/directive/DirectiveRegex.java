package c.directive;

public enum DirectiveRegex {
    IMPORT("(#include\\s?[a-zA-Zp{Punct}<.>\"]+)", ""),
    DEFINE("#define\\s.+", "CONST"),
    STRUCT("(typedef\\s)?struct\\s[\\w]+\\s?\\{[\\s\\S]*?\\}\\s?[a-z]*?;", "");


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
