package com.br.uplag.ngram;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NGramTest {
    @Test
    public void shouldCreateNGrams() {
        NGram nGram = new NGram(4);
        String content = "cFoAaDRp";
        Assert.assertTrue(nGram.createNGrams(content).contains("cFoA FoAa oAaD AaDR aDRp"));
    }
}