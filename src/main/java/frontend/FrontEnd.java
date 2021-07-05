package frontend;

public abstract class FrontEnd {
    protected static final String FUNCTION_TOKEN = "FUN";
    protected static final String VARIABLE_TOKEN = "VAR";
    protected static final String CONDITION_TOKEN = "COND";
    protected static final String LOOP_TOKEN = "LOOP";
    protected static final String ASSIGNMENT_TOKEN = "ASSIGN";

    public abstract void convertFunctionsOccurrencesToToken(String codeText);
    public abstract void convertVariableOccurrencesToToken(String codeText);
}