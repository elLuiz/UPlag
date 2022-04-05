package com.br.uplag.frontend;

import com.br.uplag.CodeText;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConditionalsAnalyzerTest {
    @Test
    public void shouldConvertAllConditionsToCommonToken() {
        ConditionalsAnalyzer conditionalsAnalyzer = ConditionalsAnalyzer.getInstance();
        String code = conditionalsAnalyzer.tokenize(CodeText.binaryTreeCode);
        Assert.assertTrue(code.contains("C"));
    }
}
