package com.br.uplag.frontend;

import com.br.uplag.CodeText;
import frontend.LoopAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LoopAnalyzerTest {
    @Test
    public void shouldConvertLoopsOccurrences() {
        LoopAnalyzer loopAnalyzer = new LoopAnalyzer();
        String result = loopAnalyzer.convertLoopsToCommonToken(CodeText.code);
        Assert.assertEquals(true, result.contains("LOOP"));
    }
}
