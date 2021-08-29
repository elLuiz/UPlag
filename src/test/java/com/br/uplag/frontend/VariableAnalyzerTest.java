package com.br.uplag.frontend;

import com.br.uplag.c.operations.VariableOperationsRegex;
import com.br.uplag.CodeText;
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
        Assert.assertEquals(true, token.contains("A a D"));
    }

    @Test
    public void shouldNotTokenizeVariablePlusEqualAssignmentWhenThereIsNone() {
        String result = variableAnalyzer.tokenizeVariable(CodeText.binaryTreeCode, VariableOperationsRegex.DECREMENT_REGEX);
        Assert.assertEquals(false, result.contains("A s D"));
    }

    @Test
    public void shouldTokenizeVariableAssignment() {
        String result = variableAnalyzer.tokenizeVariable(CodeText.binaryTreeCode, VariableOperationsRegex.NORMAL_ASSIGNMENT_REGEX);
        Assert.assertEquals(true, result.contains("A"));
    }

    @Test
    public void shouldTokenizeVariableDecrementIfAny() {
        String result = variableAnalyzer.tokenizeVariable(CodeText.binaryTreeCode, VariableOperationsRegex.ASSIGNMENT_ADD);
        Assert.assertEquals(true, result.contains("A a"));
    }
}
