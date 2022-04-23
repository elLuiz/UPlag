package com.br.uplag.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringUtilTest {
    @Test
    public void shouldReturnTrueWhenStringIsValid() {
        String input = "Files.com.br.uplag.c";
        Assert.assertEquals(true, StringUtil.isNotNullNorEmpty(input));
    }

    @Test
    public void shouldReturnFalseWhenStringIsEmpty() {
        Assert.assertFalse(StringUtil.isNotNullNorEmpty(""));
    }

    @Test
    public void shouldReturnFalseWhenStringIsNull() {
        Assert.assertFalse(StringUtil.isNotNullNorEmpty(null));
    }

    @Test
    public void shouldReplaceDotCharacter() {
        Assert.assertEquals("c", StringUtil.replaceByCharacter(".c", "", "."));
    }

    @Test
    public void shouldGetTheMatchResult() {
        String input = "src/main/test/font.com.br.uplag.c";
        Assert.assertEquals("font", StringUtil.getFileNameAfterLastSlash(input));
    }
}
