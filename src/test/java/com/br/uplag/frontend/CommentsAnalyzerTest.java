package com.br.uplag.frontend;

import com.br.uplag.CodeText;
import frontend.CommentsAnalyzer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class CommentsAnalyzerTest {
    private static final Logger LOGGER = Logger.getLogger("CommentsAnalyzerTest");

    @Test
    public void shouldRemoveAllOccurrencesOfDoubleSlashComments() {
        CommentsAnalyzer commentsAnalyzer = new CommentsAnalyzer();
        Assert.assertEquals(false, commentsAnalyzer.convertDoubleSlashCommentsToToken(CodeText.code).contains("//"));
    }

    @Test
    public void shouldRemoveAllOccurrencesOfAsterisksComments() {
        CommentsAnalyzer commentsAnalyzer = new CommentsAnalyzer();
        Assert.assertEquals(false, commentsAnalyzer.convertAsterisksCommentsToToken(CodeText.code).contains("/*"));
    }
}
