package com.br.uplag.language.c;

import com.br.uplag.c.directive.DirectiveRegex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DirectiveTest {
    private final String code= "#include<stdio.h>\n" +
            "#include<stdlib.h>\n" +
            "#include<math.h>\n" +
            "#include \"SkipList.h\"\n" +
            "#define MAX 400;";

    @Test
    public void shouldRemoveImportDirective() {
        String result = code.replaceAll(DirectiveRegex.IMPORT.getRegex(), DirectiveRegex.IMPORT.getToken());
        Assert.assertEquals(false, result.contains("#import"));
    }

    @Test
    public void shouldConvertDefineDirectiveToConst() {
        String result = code.replaceAll(DirectiveRegex.DEFINE.getRegex(), DirectiveRegex.DEFINE.getToken());
        Assert.assertEquals(true, result.contains("CONST"));
    }
}
