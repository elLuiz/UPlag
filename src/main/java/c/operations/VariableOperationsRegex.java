package c.operations;

public enum VariableOperationsRegex {
    INCREMENT_REGEX("([->\\w]+\\s?\\+{2}|\\s?\\+{2}[->\\w]+);?", " ASSIGN ADD DIG "),
    DECREMENT_REGEX("([->\\w_]+\\s?\\-{2}|\\s?\\-{2}[->\\w_]+);?", " ASSIGN SUB DIG "),
    NORMAL_ASSIGNMENT_REGEX("[\\da-z*]{0,}\\s?[*a-z_\\d\\->]+(\\[.*\\])?\\s?\\=", " ASSIGN "),
    ASSIGNMENT_WITH_CASTING("ASSIGN \\([a-z_A-Z*]+\\)", " ASSIGN CAST "),
    ASSIGNMENT_ADD("\\+\\=", " ASSIGN ADD "),
    ASSIGNMENT_SUB("\\-\\=", " ASSIGN SUB "),
    ASSIGNMENT_DIV("\\/\\=", " ASSIGN DIV "),
    ASSIGNMENT_MULT("\\/*\\=", " ASSIGN MULT ");


    private final String regex;
    private final String token;

    VariableOperationsRegex(String regex, String token) {
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
