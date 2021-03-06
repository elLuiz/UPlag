package com.br.uplag.frontend;

import com.br.uplag.CodeText;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FunctionAnalyzerTest {
    @Test
    public void shouldConvertFunctionsCreationPhase() {
        FunctionsAnalyzer functionsAnalyzer = FunctionsAnalyzer.getInstance();
        String result = functionsAnalyzer.convertFunctionsCreationToItsToken(CodeText.binaryTreeCode);
        Assert.assertTrue(result.contains("F"));
    }
}
