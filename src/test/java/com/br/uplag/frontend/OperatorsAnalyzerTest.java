package com.br.uplag.frontend;

import com.br.uplag.c.operators.LogicalOperatorsRegexEnum;
import com.br.uplag.c.operators.RelationOperatorRegex;
import com.br.uplag.CodeText;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OperatorsAnalyzerTest {
    @Test
    public void shouldTokenizeEqualRelationalOperator() {
        OperatorsAnalyzer operatorsAnalyzer = OperatorsAnalyzer.getInstance();
        String result = operatorsAnalyzer.tokenizeRelationalOperator(CodeText.binaryTreeCode, RelationOperatorRegex.EQ);
        Assert.assertEquals(true, result.contains("E"));
    }

    @Test
    public void shouldTokenizeGreaterThanRelationalOperator() {
        OperatorsAnalyzer operatorsAnalyzer = OperatorsAnalyzer.getInstance();
        String result = operatorsAnalyzer.tokenizeRelationalOperator(CodeText.binaryTreeCode, RelationOperatorRegex.GT);
        Assert.assertEquals(true, result.contains("G"));
    }

    @Test
    public void shouldTokenizeAndOperators() {
        OperatorsAnalyzer operatorsAnalyzer = OperatorsAnalyzer.getInstance();
        String result = operatorsAnalyzer.tokenizeLogicalOperators(CodeText.binaryTreeCode, LogicalOperatorsRegexEnum.AND);
        Assert.assertEquals(true, result.contains("H"));
    }
}
