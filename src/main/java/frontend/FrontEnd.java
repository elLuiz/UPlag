package frontend;

public abstract class FrontEnd {
    protected static final String FUNCTION_TOKEN = "FUN";
    protected static final String VARIABLE_TOKEN = "VAR";
    protected static final String CONDITION_TOKEN = "COND";
    protected static final String LOOP_TOKEN = "LOOP";
    protected static final String ASSIGNMENT_TOKEN = "ASSIGN";
    protected static final String FUNCTION_CALL = "FUNC";
    protected static final String CONST_TOKEN = "CONST";
    protected static final String RELATIONAL_TOKEN = "R_OP";
    protected static final String LOGICAL_TOKEN = "L_OP";
    protected static final String RETURN_STATEMENT = "R_ST";
    protected static final String BREAK_STATEMENT = "B_ST";
    protected static final String STATEMENT = "ST";
    protected static final String LEFT_CURLY_BRACE = "L_C";
    protected static final String RIGHT_CURLY_BRACE = "R_C";
    protected static final String LEFT_PARENTHESIS = "L_P";
    protected static final String RIGHT_PARENTHESIS = "R_P";
    protected static final String CONDITIONAL_ASSIGNMENT = CONDITION_TOKEN + ASSIGNMENT_TOKEN;
    public String convertCodeTextToLowerCase(String codeText) {
        return codeText.toLowerCase();
    }

    public String transformTextIntoSingleLinedCode(String code) {
        return code.replaceAll("\n", " ");
    }

    public abstract void convertFunctionsOccurrencesToToken(String codeText);
    public abstract void convertVariableOccurrencesToToken(String codeText);
}