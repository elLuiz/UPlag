package com.br.uplag.frontend;

import com.br.uplag.CodeText;
import frontend.DirectiveAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class DirectiveAnalyzerTest {
    private static final Logger LOGGER = Logger.getLogger("CommentsAnalyzerTest");
    @Test
    public void shouldRemoveAllImports() {
        DirectiveAnalyzer directiveAnalyzer = new DirectiveAnalyzer();
        Assert.assertEquals(false, directiveAnalyzer.convertImportsToToken(CodeText.code).contains("#include"));
    }

    @Test
    public void shouldConvertAllDefineToItsToken() {
        DirectiveAnalyzer directiveAnalyzer = new DirectiveAnalyzer();
        Assert.assertEquals(true, directiveAnalyzer.convertDefineToToken(CodeText.code).contains("CONST"));
    }

    @Test
    public void shouldRemoveAllStructOccurrences() {
        DirectiveAnalyzer directiveAnalyzer = new DirectiveAnalyzer();
        String result = directiveAnalyzer.removeStructOccurrences(CodeText.binaryTreeCode);
        Assert.assertEquals(false, result.contains("struct NO{"));
    }
}
