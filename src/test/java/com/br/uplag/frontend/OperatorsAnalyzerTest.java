package com.br.uplag.frontend;

import c.operators.LogicalOperatorsRegexEnum;
import c.operators.RelationOperatorRegex;
import com.br.uplag.CodeText;
import frontend.OperatorsAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OperatorsAnalyzerTest {
    @Test
    public void shouldTokenizeEqualRelationalOperator() {
        OperatorsAnalyzer operatorsAnalyzer = new OperatorsAnalyzer();
        String result = operatorsAnalyzer.tokenizeRelationalOperator(CodeText.binaryTreeCode, RelationOperatorRegex.EQ);
        Assert.assertEquals(true, result.contains("EQ"));
    }

    @Test
    public void shouldTokenizeGreaterThanRelationalOperator() {
        OperatorsAnalyzer operatorsAnalyzer = new OperatorsAnalyzer();
        String result = operatorsAnalyzer.tokenizeRelationalOperator(CodeText.binaryTreeCode, RelationOperatorRegex.GT);
        Assert.assertEquals(true, result.contains("GT"));
    }

    @Test
    public void shouldTokenizeAndOperators() {
        OperatorsAnalyzer operatorsAnalyzer =new OperatorsAnalyzer();
        String result = operatorsAnalyzer.tokenizeLogicalOperators(CodeText.binaryTreeCode, LogicalOperatorsRegexEnum.AND);
        Assert.assertEquals(true, result.contains("AND"));
    }
}
