package com.br.uplag.frontend;

import com.br.uplag.CodeText;
import frontend.FunctionsAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FunctionAnalyzerTest {
    @Test
    public void shouldConvertFunctionsCreationPhase() {
        FunctionsAnalyzer functionsAnalyzer = new FunctionsAnalyzer();
        String result = functionsAnalyzer.convertFunctionsCreationToItsToken(CodeText.binaryTreeCode);
        Assert.assertEquals(true, result.contains("FUN"));
    }
}
