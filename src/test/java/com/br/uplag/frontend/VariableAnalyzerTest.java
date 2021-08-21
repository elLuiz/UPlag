package com.br.uplag.frontend;

import c.operations.VariableOperationsRegex;
import com.br.uplag.CodeText;
import frontend.VariableAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class VariableAnalyzerTest {
    private VariableAnalyzer variableAnalyzer = VariableAnalyzer.getInstance();
    @Test
    public void shouldTokenizeVariableIncrement() {
        String token = variableAnalyzer.tokenizeVariable(CodeText.binaryTreeCode, VariableOperationsRegex.INCREMENT_REGEX);
        Assert.assertEquals(true, token.contains("ASSIGN ADD DIG"));
    }

    @Test
    public void shouldTokenizeVariableDecrementIfAny() {
        String result = variableAnalyzer.tokenizeVariable(CodeText.binaryTreeCode, VariableOperationsRegex.DECREMENT_REGEX);
        Assert.assertEquals(true, result.contains("ASSIGN SUB DIG"));
    }

    @Test
    public void shouldTokenizeVariableAssignment() {
        String result = variableAnalyzer.tokenizeVariable(CodeText.binaryTreeCode, VariableOperationsRegex.NORMAL_ASSIGNMENT_REGEX);
        Assert.assertEquals(true, result.contains("ASSIGN"));
    }

    @Test
    public void shouldNotTokenizeVariablePlusEqualAssignmentWhenThereIsNone() {
        String result = variableAnalyzer.tokenizeVariable(CodeText.binaryTreeCode, VariableOperationsRegex.ASSIGNMENT_ADD);
        Assert.assertEquals(false, result.contains("ASSIGN ADD"));
    }
}
