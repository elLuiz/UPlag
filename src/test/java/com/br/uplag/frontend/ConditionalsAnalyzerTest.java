package com.br.uplag.frontend;

import com.br.uplag.CodeText;
import frontend.ConditionalsAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConditionalsAnalyzerTest {
    @Test
    public void shouldConvertAllConditionsToCommonToken() {
        ConditionalsAnalyzer conditionalsAnalyzer = new ConditionalsAnalyzer();
        String code = conditionalsAnalyzer.convertConditionsToItsToken(CodeText.binaryTreeCode);
        Assert.assertEquals(true, code.contains("COND"));
    }
}
