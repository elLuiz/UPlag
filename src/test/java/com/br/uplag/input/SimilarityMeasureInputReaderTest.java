package com.br.uplag.input;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SimilarityMeasureInputReaderTest {
    @Test
    public void shouldDefineCosineSimilarity() {
        String[] args = new String[2];
        args[0] = "-tfidf";
        args[1] = "-cosine";
        SimilarityMeasureInputReader similarityMeasureInputReader = new SimilarityMeasureInputReader(args);
        similarityMeasureInputReader.defineParameter();
        Assert.assertEquals("-cosine", similarityMeasureInputReader.getSimilarityMeasure());
    }
}
