package com.br.uplag.weight;

import com.br.uplag.index.InvertedIndex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.br.uplag.reader.CReader;
import com.br.uplag.reader.Reader;
import com.br.uplag.util.FileInputUtil;

import java.util.*;

@RunWith(JUnit4.class)
public class NormalizedWeightTest {
    @Test
    public void shouldGetTermsWeight() {
        List<String> files = FileInputUtil.walkTroughDirectory("src/test/resources/", "c");
        Reader reader = new CReader();
        Map<String, String> stringStringMap = reader.startReadingInputFiles(files);
        InvertedIndex invertedIndex = new InvertedIndex();
        invertedIndex.createInvertedIndex(stringStringMap);
        NormalizedWeight normalizedWeight = new NormalizedWeight(invertedIndex.getInvertedIndex(), files);
        normalizedWeight.calculateTermWeight();
    }
}
