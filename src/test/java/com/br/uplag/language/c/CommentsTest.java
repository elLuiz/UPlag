package com.br.uplag.language.c;

import com.br.uplag.c.misc.CommentsRegex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CommentsTest {
    public final String code = "/*\n" +
            "Grupo GVY\n" +
            "Integrantes:\n" +
            "Gabriel Mendes de Souza Santiago - 11621BCC015\n" +
            "Vin�cius Guardieiro Sousa - 11811BCC008\n" +
            "Yan Lucas Damasceno Dias - 11621BCC029\n" +
            "*/\n" +
            "\n" +
            "#include<stdio.h>\n" +
            "#include<stdlib.h>\n" +
            "#include<math.h>\n" +
            "#include \"SkipList.h\"\n" +
            "\n" +
            "// Recebe uma skilist e o valor que deseja-se saber se pertence a ela.\n" +
            "// Retorna 0 caso ela nao tenha sido alocada ou esteja vazia.\n" +
            "// Retorna 1 caso o elemento esteja na lista.\n" +
            "int buscaSkipList(skiplist *sk, int chave){\n" +
            "\n" +
            "    // Verifica se a lista foi alocada e se est� vazia.\n" +
            "\tif(vaziaSkipList(sk)){\n" +
            "        printf(\"Lista nao alocada ou vazia.\\n\");\n" +
            "\t\treturn 0;\n" +
            "\t}\n" +
            "\n" +
            "    // Aponta para o n� mais acima do cabe�alho.\n" +
            "    no *x = sk->inicio->prox;\n" +
            "\n" +
            "    // Enquanto o n� for diferente de nulo percorrer a lista.\n" +
            "    while(x != NULL){\n" +
            "\n" +
            "        // Se a chave for maior que o valor do n� atual, ir para o proximo n�.\n" +
            "        while(x->prox != NULL && x->prox->info < chave)\n" +
            "            x = x->prox;\n" +
            "\n" +
            "        // Verifica se o valor do n� atual � igual ao valor da chave e retorna 1 caso seja.\n" +
            "        if(x->prox != NULL && x->prox->info == chave)\n" +
            "            return 1;\n" +
            "\n" +
            "        // Caso n�o satisfa�a nenhuma das condi��es acima, vai para o n� abaixo e repete o processo.\n" +
            "        x = x->abaixo;\n" +
            "    }\n" +
            "    return 0;\n" +
            "}";

    @Test
    public void shouldRemoveDoubleSlashComments() {
        String result = code.replaceAll(CommentsRegex.DOUBLE_SLASH_COMMENTS.getRegex(), CommentsRegex.DOUBLE_SLASH_COMMENTS.getToken());
        Assert.assertEquals(false, result.contains("//"));
    }

    @Test
    public void shouldRemoveTextBetweenCode() {
        String result = code.replaceAll(CommentsRegex.DOUBLE_ASTERISK_COMMENT_REGEX.getRegex(), CommentsRegex.DOUBLE_ASTERISK_COMMENT_REGEX.getToken());
        result = result.replaceAll(CommentsRegex.SLASH_ASTERISK.getRegex(), CommentsRegex.SLASH_ASTERISK.getToken());
        Assert.assertEquals(false, result.contains("/*"));
    }
}
