package com.br.uplag.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import util.StringUtil;

@RunWith(JUnit4.class)
public class StringUtilTest {
    @Test
    public void shouldReturnTrueWhenStringIsValid() {
        String input = "Files.c";
        Assert.assertEquals(true, StringUtil.isValid(input));
    }

    @Test
    public void shouldReturnFalseWhenStringIsEmpty() {
        Assert.assertEquals(false, StringUtil.isValid(""));
    }

    @Test
    public void shouldReturnFalseWhenStringIsNull() {
        Assert.assertEquals(false, StringUtil.isValid(null));
    }

    @Test
    public void shouldReplaceDotCharacter() {
        Assert.assertEquals("c", StringUtil.replaceBy(".", "", ".c"));
    }
}
