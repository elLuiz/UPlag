package frontend;

import c.misc.StylizationRegex;
import c.operations.VariableOperationsRegex;
import c.operators.ArithmeticOperatorsRegex;
import c.operators.LogicalOperatorsRegexEnum;
import c.operators.RelationOperatorRegex;
import c.statements.StatementsRegex;

public class FrontEndFacade {
    private final CommentsAnalyzer commentsAnalyzer;
    private final DirectiveAnalyzer directiveAnalyzer;
    private final ConditionalsAnalyzer conditionalsAnalyzer;
    private final FunctionsAnalyzer functionsAnalyzer;
    private final LoopAnalyzer loopAnalyzer;
    private final OperatorsAnalyzer operatorsAnalyzer;
    private final VariableAnalyzer variableAnalyzer;
    private final StatementAnalyzer statementAnalyzer;
    private final ArithmeticAnalyzer arithmeticAnalyzer;
    private final StylizationAnalyzer stylizationAnalyzer;

    public FrontEndFacade() {
        commentsAnalyzer = CommentsAnalyzer.getInstance();
        directiveAnalyzer = DirectiveAnalyzer.getInstance();
        conditionalsAnalyzer = ConditionalsAnalyzer.getInstance();
        loopAnalyzer = LoopAnalyzer.getInstance();
        functionsAnalyzer = FunctionsAnalyzer.getInstance();
        operatorsAnalyzer = OperatorsAnalyzer.getInstance();
        variableAnalyzer = VariableAnalyzer.getInstance();
        statementAnalyzer = StatementAnalyzer.getInstance();
        arithmeticAnalyzer = ArithmeticAnalyzer.getInstance();
        stylizationAnalyzer = StylizationAnalyzer.getInstance();
    }

    public String createTokenSequence(String codeText) {
        String tokenized = commentsAnalyzer.tokenize(codeText);
        tokenized = directiveAnalyzer.tokenize(tokenized);
        tokenized = conditionalsAnalyzer.tokenize(tokenized);
        tokenized = loopAnalyzer.tokenize(tokenized);
        tokenized = functionsAnalyzer.convertFunctionsCreationToItsToken(tokenized);
        tokenized = tokenizeRelationalOperators(tokenized);
        tokenized = tokenizeLogicalOperators(tokenized);
        tokenized = functionsAnalyzer.convertInlineFunctionsCalls(tokenized);
        tokenized = functionsAnalyzer.convertFunctionCallsInsideParenthesis(tokenized);
        tokenized = functionsAnalyzer.convertFunctionBetweenOperatorsAndParenthesis(tokenized);
        tokenized = tokenizeVariables(tokenized);
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.BREAK);
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.JUMP);
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.CONTINUE);
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.RETURN);
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.ADD);
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.DIV);
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.SUB);
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.MOD);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.LEFT_CURLY_BRACES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.RIGHT_CURLY_BRACES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.LEFT_PARENTHESES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.RIGHT_PARENTHESES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.COMMA);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.SEMICOLON);
        return tokenized.replaceAll("[^A-Z]+", " ");
    }

    private String tokenizeRelationalOperators(String tokenized) {
        tokenized = operatorsAnalyzer.tokenizeRelationalOperator(tokenized, RelationOperatorRegex.EQ);
        tokenized = operatorsAnalyzer.tokenizeRelationalOperator(tokenized, RelationOperatorRegex.GT);
        tokenized = operatorsAnalyzer.tokenizeRelationalOperator(tokenized, RelationOperatorRegex.LT);
        tokenized = operatorsAnalyzer.tokenizeRelationalOperator(tokenized, RelationOperatorRegex.NE);
        tokenized = operatorsAnalyzer.tokenizeRelationalOperator(tokenized, RelationOperatorRegex.GE);
        tokenized = operatorsAnalyzer.tokenizeRelationalOperator(tokenized, RelationOperatorRegex.LE);
        return tokenized;
    }

    private String tokenizeLogicalOperators(String tokenized) {
        tokenized = operatorsAnalyzer.tokenizeLogicalOperators(tokenized, LogicalOperatorsRegexEnum.AND);
        tokenized = operatorsAnalyzer.tokenizeLogicalOperators(tokenized, LogicalOperatorsRegexEnum.OR);
        tokenized = operatorsAnalyzer.tokenizeLogicalOperators(tokenized, LogicalOperatorsRegexEnum.NOT);
        return tokenized;
    }

    private String tokenizeVariables(String tokenized) {
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.INCREMENT_REGEX);
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.DECREMENT_REGEX);
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.ASSIGNMENT_ADD);
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.ASSIGNMENT_SUB);
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.ASSIGNMENT_DIV);
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.ASSIGNMENT_MULT);
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.NORMAL_ASSIGNMENT_REGEX);
        tokenized = variableAnalyzer.tokenizeVariable(tokenized, VariableOperationsRegex.ASSIGNMENT_WITH_CASTING);
        return tokenized;
    }
}
