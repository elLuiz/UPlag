package com.br.uplag.frontend;

import com.br.uplag.c.misc.StylizationRegex;
import com.br.uplag.c.operations.VariableOperationsRegex;
import com.br.uplag.c.operators.ArithmeticOperatorsRegex;
import com.br.uplag.c.operators.LogicalOperatorsRegexEnum;
import com.br.uplag.c.operators.RelationOperatorRegex;
import com.br.uplag.c.statements.StatementsRegex;

import java.util.ArrayList;
import java.util.List;

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
    private final BuiltInFunctionAnalyzer builtInFunctionAnalyzer;

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
        builtInFunctionAnalyzer = new BuiltInFunctionAnalyzer();
    }

    public String createTokenSequence(String codeText) {
        String tokenized = commentsAnalyzer.tokenize(codeText);
        tokenized = directiveAnalyzer.tokenize(tokenized);
        tokenized = conditionalsAnalyzer.tokenize(tokenized);
        tokenized = loopAnalyzer.tokenize(tokenized);
        tokenized = functionsAnalyzer.convertFunctionsCreationToItsToken(tokenized);
        tokenized = tokenizeRelationalOperators(tokenized);
        tokenized = tokenizeLogicalOperators(tokenized);
        tokenized = builtInFunctionAnalyzer.tokenize(tokenized);
        tokenized = tokenizeFunctions(tokenized);
        tokenized = tokenizeVariables(tokenized);
        tokenized = tokenizeStatements(tokenized);
        tokenized = tokenizeArithmetic(tokenized);
        tokenized = tokenizeStylization(tokenized);
        return tokenized.replaceAll("[a-z->_\\[\\]\"=]+", " ");
    }

    private String tokenizeStylization(String tokenized) {
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.LEFT_CURLY_BRACES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.RIGHT_CURLY_BRACES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.LEFT_PARENTHESES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.RIGHT_PARENTHESES);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.COMMA);
        tokenized = stylizationAnalyzer.beautifyTokenization(tokenized, StylizationRegex.SEMICOLON);
        return tokenized;
    }

    private String tokenizeArithmetic(String tokenized) {
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.ADD);
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.DIV);
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.SUB);
        tokenized = arithmeticAnalyzer.tokenizeArithmeticOccurrences(tokenized, ArithmeticOperatorsRegex.MOD);
        return tokenized;
    }

    private String tokenizeStatements(String tokenized) {
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.BREAK);
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.JUMP);
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.CONTINUE);
        tokenized = statementAnalyzer.tokenizeStatementsOccurrences(tokenized, StatementsRegex.RETURN);
        return tokenized;
    }

    private String tokenizeFunctions(String tokenized) {
        tokenized = functionsAnalyzer.convertInlineFunctionsCalls(tokenized);
        tokenized = functionsAnalyzer.convertFunctionCallsInsideParenthesis(tokenized);
        tokenized = functionsAnalyzer.convertFunctionBetweenOperatorsAndParenthesis(tokenized);
        return tokenized;
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
