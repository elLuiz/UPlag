package parameters;

public enum ParametersInputRegex {
    DIRECTORY("-d"),
    LANGUAGE("-l"),
    PROGRAMS("-p");

    private final String parameter;

    ParametersInputRegex(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
