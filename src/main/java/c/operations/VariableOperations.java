package c.operations;

public enum VariableOperations {
    INCREMENT_REGEX("[a-z_]+\\s?\\+{2};?", "ASSIGN PLUS NUM"),
    DECREMENT_REGEX("[a-z_]+\\s?\\+{2};?", "ASSIGN MINUS NUM");

    private final String regex;
    private final String token;

    VariableOperations(String regex, String token) {
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